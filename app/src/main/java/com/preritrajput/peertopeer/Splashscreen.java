package com.preritrajput.peertopeer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class Splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.black_trans80));
        setContentView(R.layout.activity_splashscreen);

        SharedPreferences settings = getSharedPreferences(MainActivity.LOGIN, 0);
        boolean hasLoggedIn = settings.getBoolean("hasLoggedIn", false);
        boolean hasRegistered = settings.getBoolean("hasRegistered", false);
        boolean seenOnBoarding = settings.getBoolean("seenOnBoarding", false);


        int SPLASH_SCREEN = 2500;
        if (seenOnBoarding && !(hasLoggedIn && hasRegistered)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(Splashscreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }
            }, SPLASH_SCREEN);
        } else if(hasLoggedIn || hasRegistered) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(Splashscreen.this, Dashboard.class);
                    startActivity(intent);
                    finish();

                }
            }, SPLASH_SCREEN);
        } else {

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    Intent intent = new Intent(Splashscreen.this, OnBoarding.class);
                    startActivity(intent);
                    finish();

                }
            }, SPLASH_SCREEN);
        }
    }
}