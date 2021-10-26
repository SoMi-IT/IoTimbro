package com.somi.smartbadgechecker.util;


import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.somi.smartbadgechecker.R;


public class NFCDialog extends Dialog implements Button.OnClickListener {


    public static final int TYPE_IN = 1, TYPE_OUT = 2;
    private final Button b_dismiss;
    private Activity activity;
    private Thread thread;
    private int timeout;

    public NFCDialog(Activity _context, int type) {

        super(_context);
        activity = _context;

        ColorDrawable back = new ColorDrawable(Color.TRANSPARENT);
        InsetDrawable inset = new InsetDrawable(back, 20);

        this.getWindow().setBackgroundDrawable(inset);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.dialog_nfc);

        Window window = this.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);

        b_dismiss = findViewById(R.id.b_dialog_nfc_dismiss);
        b_dismiss.setOnClickListener(this);

        ImageView iv_icon;
        TextView tv_label, tv_info;

        iv_icon = findViewById(R.id.iv_dialog_nfc);
        tv_label = findViewById(R.id.tv_dialog_nfc_label);
        tv_info = findViewById(R.id.tv_dialog_nfc_nfc);

        if (type == TYPE_IN) {
            iv_icon.setImageDrawable(ContextCompat.getDrawable(_context, R.drawable.icon_in_large));
            tv_info.setText("Ingresso timbrato con successo");

        }else if (type == TYPE_OUT) {
            iv_icon.setImageDrawable(ContextCompat.getDrawable(_context, R.drawable.icon_out_large));
            tv_info.setText("Uscita timbrata con successo");

        }

        tv_label.setText("Complimenti!");
        b_dismiss.setText("Chiudi (5)");
        timeout = 5;
        startClock();

    }//InfoDialog



    protected void onStop() {

        if (thread != null)thread.interrupt();
        super.onStop();

    }//onStop

    private void startClock() {

        thread = new Thread() {

            public void run() {
                try {
                    while (!isInterrupted()) {

                        Thread.sleep(1000);

                        activity.runOnUiThread(() -> {
                            timeout--;
                            b_dismiss.setText("Chiudi (" + timeout + ")");
                            if(timeout <= 0) dismiss();


                        });

                    }

                } catch (InterruptedException e) {
                }

            }

        };

        thread.start();

    }//startClock
    public void onClick(View view) {

        if(view == b_dismiss) {
            if (thread != null)thread.interrupt();
            dismiss();

        }

    }//onClick



}//InfoDialog