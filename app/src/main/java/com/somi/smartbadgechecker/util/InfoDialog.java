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

public class InfoDialog extends Dialog implements Button.OnClickListener {


    public static final int TYPE_IN = 1, TYPE_OUT = 2, TYPE_INFO = 3;
    private final Button b_dismiss;
    private Activity activity;

    public InfoDialog(Activity _context, int type) {

        super(_context);
        activity = _context;

        ColorDrawable back = new ColorDrawable(Color.TRANSPARENT);
        InsetDrawable inset = new InsetDrawable(back, 20);

        this.getWindow().setBackgroundDrawable(inset);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.dialog_info);

        Window window = this.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.CENTER);

        b_dismiss = findViewById(R.id.b_dialog_info_dismiss);
        b_dismiss.setOnClickListener(this);

        TextView  tv_info;

        tv_info = findViewById(R.id.tv_dialog_info_info);

        tv_info.setText("Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum\n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum\n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum\n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum\n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum \n Lorem ipsum\n ");
        b_dismiss.setText("Chiudi");


    }//InfoDialog

    public void onClick(View view) {

        if(view == b_dismiss) {
            dismiss();

        }

    }//onClick



}//InfoDialog