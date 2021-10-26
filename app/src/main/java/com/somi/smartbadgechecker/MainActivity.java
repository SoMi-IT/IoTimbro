package com.somi.smartbadgechecker;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.somi.smartbadgechecker.data.Mark;
import com.somi.smartbadgechecker.data.MarkDataManager;
import com.somi.smartbadgechecker.data.User;
import com.somi.smartbadgechecker.data.UserDataManager;
import com.somi.smartbadgechecker.util.DateManager;
import com.somi.smartbadgechecker.util.InfoDialog;
import com.somi.smartbadgechecker.util.NFCDialog;
import com.somi.smartbadgechecker.util.NFCManager;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private final static int STATUS_TYPE_INSIDE = 0;
    private final static int STATUS_TYPE_OUTSIDE = 1;
    private final static int STATUS_TYPE_NONE = 2;

    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private ImageView iv_help;
    private TextView tv_tag, tv_time, tv_date;
    private Button b_start, b_end;

    private FirebaseFirestore db;
    private Thread thread;

    private int timeout;
    private boolean isTimeoutEnable;
    private boolean isTimeToAlert;
    private User currentUser;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv_help = findViewById(R.id.iv_main_help);
        iv_help.setOnClickListener(this);
        tv_tag = findViewById(R.id.tv_main_tag);
        tv_time = findViewById(R.id.tv_main_time);
        tv_date = findViewById(R.id.tv_main_date);

        b_start = findViewById(R.id.b_main_start);
        b_start.setOnClickListener(this);
        b_end = findViewById(R.id.b_main_end);
        b_end.setOnClickListener(this);


        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null){
            finish();
        }

        pendingIntent = PendingIntent.getActivity(this,0,new Intent(this,this.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),0);
        db = FirebaseFirestore.getInstance();

        tv_date.setText(DateManager.getCurrentItalianDate());
        tv_time.setText(DateManager.getCurrentTime());

        toggleStatus(STATUS_TYPE_NONE);

        startClock();

    }//onCreate


    protected void onResume() {

        super.onResume();
        assert nfcAdapter != null;
        nfcAdapter.enableForegroundDispatch(this,pendingIntent,null,null);

    }//onResume


    protected void onPause() {

        super.onPause();
        if (nfcAdapter != null) nfcAdapter.disableForegroundDispatch(this);

    }//onPause


    protected void onDestroy() {

        if (thread != null)thread.interrupt();
        super.onDestroy();

    }//onDestroy


    protected void onNewIntent(Intent intent) {

        super.onNewIntent(intent);
        setIntent(intent);

        if(!isTimeoutEnable) {
            currentUser = UserDataManager.getUserById(this, NFCManager.obtainTagFromIntent(intent).replace(" ", ""));

            if (currentUser.isInside()) toggleStatus(STATUS_TYPE_INSIDE);
            else toggleStatus(STATUS_TYPE_OUTSIDE);

        }

    }//onNewIntent


    private void badge(boolean isStart) {

        /*Mark mark = new Mark(DateManager.getCurrentMoment(), currentUser.getId(), isStart);

        MarkDataManager.addMark(this, mark);
        UserDataManager.editUserById(MainActivity.this, currentUser.getId());
        toggleStatus(STATUS_TYPE_NONE);*/
        Map<String, Object> user = new HashMap<>();
        user.put("user", currentUser.getId());
        user.put("time", DateManager.getCurrentMoment());

        if(isStart)user.put("type", "Ingresso");
        else user.put("type", "Uscita");

        db.collection("Timbrature")
                .add(user)
                .addOnSuccessListener(documentReference -> {

                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());

                    UserDataManager.editUserById(MainActivity.this, currentUser.getId());

                    runOnUiThread(() -> {
                        toggleStatus(STATUS_TYPE_NONE);
                        NFCDialog nfcDialog;
                        if(isStart) nfcDialog = new NFCDialog(MainActivity.this, NFCDialog.TYPE_IN);
                        else nfcDialog = new NFCDialog(MainActivity.this, NFCDialog.TYPE_OUT);
                        nfcDialog.show();
                    });
                })
                .addOnFailureListener(e -> runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this, "Errore: " + e, Toast.LENGTH_LONG).show();
                    toggleStatus(STATUS_TYPE_NONE);
                }));


    }


    private void startClock() {

        thread = new Thread() {

            public void run() {
                try {
                    while (!isInterrupted()) {

                        Thread.sleep(1000);

                        runOnUiThread(() -> {
                            tv_date.setText(DateManager.getCurrentItalianDate());
                            tv_time.setText(DateManager.getCurrentTime());

                            if(isTimeoutEnable) {
                                timeout++;
                                if(timeout >= 8) toggleStatus(STATUS_TYPE_NONE);

                            }

                            if(DateManager.getCurrentHour() == 22) isTimeoutEnable = true;
                            else if (DateManager.getCurrentHour() == 23 && isTimeoutEnable){
                                UserDataManager.saveAlertStatus(MainActivity.this);
                                isTimeoutEnable = false;

                            }

                        });

                    }

                } catch (InterruptedException e) {
                }

            }

        };

        thread.start();

    }//startClock


    private void toggleStatus(int statusType) {

        timeout = 0;
        Drawable buttonDrawableStart = b_start.getBackground();
        buttonDrawableStart = DrawableCompat.wrap(buttonDrawableStart);

        Drawable buttonDrawableEnd = b_end.getBackground();
        buttonDrawableEnd = DrawableCompat.wrap(buttonDrawableEnd);

        if (statusType == STATUS_TYPE_INSIDE) {

            DrawableCompat.setTint(buttonDrawableStart, getColor(R.color.dark_200));
            b_start.setBackground(buttonDrawableStart);
            b_start.setClickable(false);

            DrawableCompat.setTint(buttonDrawableEnd, getColor(R.color.white_100));
            b_end.setBackground(buttonDrawableEnd);
            b_end.setClickable(true);

            if(currentUser.isAlertOn()) tv_tag.setText(currentUser.getId() + "\n" + "Puoi timbrare l'uscita" +"\n" +"\n" + "ATTENZIONE! L'ultima volta non hai timbrato l'uscita!");
            else tv_tag.setText(currentUser.getId() + "\n" + "Puoi timbrare l'uscita");
            isTimeoutEnable = true;

        }else if(statusType == STATUS_TYPE_OUTSIDE) {

            DrawableCompat.setTint(buttonDrawableStart, getColor(R.color.white_100));
            b_start.setBackground(buttonDrawableStart);
            b_start.setClickable(true);

            DrawableCompat.setTint(buttonDrawableEnd, getColor(R.color.dark_200));
            b_end.setBackground(buttonDrawableEnd);
            b_end.setClickable(false);

            if(currentUser.isAlertOn()) tv_tag.setText(currentUser.getId() + "\n" + "Puoi timbrare l'ingresso" +"\n" +"\n" + "ATTENZIONE! L'ultima volta non hai timbrato l'uscita!");
            else  tv_tag.setText(currentUser.getId() + "\n" + "Puoi timbrare l'ingresso" +"\n");
            isTimeoutEnable = true;

        }else if(statusType == STATUS_TYPE_NONE) {

            DrawableCompat.setTint(buttonDrawableStart, getColor(R.color.dark_200));
            b_start.setBackground(buttonDrawableStart);
            b_start.setClickable(false);

            DrawableCompat.setTint(buttonDrawableEnd, getColor(R.color.dark_200));
            b_end.setBackground(buttonDrawableEnd);
            b_end.setClickable(false);

            tv_tag.setText("Avvicina il tuo badge!");
            isTimeoutEnable = false;

        }

    }//toggleStatus


    public void onClick(View v) {

        if(v == b_start) badge(true);
        else if (v == b_end) badge(false);
        else if (v == iv_help) {
            InfoDialog infoDialog = new InfoDialog(this, InfoDialog.TYPE_INFO);
            infoDialog.show();
        }

    }


}//MainActivity