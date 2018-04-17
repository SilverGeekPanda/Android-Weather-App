package com.example.charlie.bladeprojectcharlie.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.charlie.bladeprojectcharlie.Activities.MainActivity;
import com.example.charlie.bladeprojectcharlie.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Log.d("DEBUG","SplashScreen");

        Thread splashThread = new Thread() {
          public void run() {
              try {
                  sleep(3000);
                  Intent intentToMainAct = new Intent(getApplicationContext(), MainActivity.class);
                  startActivity(intentToMainAct);
                  finish();
              } catch(InterruptedException e) {
                  e.printStackTrace();
              }
          }
        };
        splashThread.start();
    }
}