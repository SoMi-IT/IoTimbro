package com.somi.smartbadgechecker.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.somi.smartbadgechecker.MainActivity;
import com.somi.smartbadgechecker.R;

public class SplashActivity extends AppCompatActivity {


    private final int SPLASH_DISPLAY_LENGTH = 1800;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {

            Intent mainIntent = new Intent(this, MainActivity.class);
            //ActivityOptions options = ActivityOptions.makeCustomAnimation(this, R.anim.splash_in, R.anim.splash_out);
            //this.startActivity(mainIntent, options.toBundle());
            this.startActivity(mainIntent);
            this.finish();
        }, SPLASH_DISPLAY_LENGTH);

    }//onCreate

}//SplashActivity