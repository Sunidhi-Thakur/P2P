package com.preritrajput.peertopeer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class Splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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