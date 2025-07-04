package com.example.mapmate.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mapmate.R;

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_TIME_OUT = 2000; // 2 seconds delay

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Fade-in animation for the app name
        TextView appName = findViewById(R.id.appName);
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(1500);
        appName.startAnimation(fadeIn);

        // Delay then navigate
        new Handler().postDelayed(this::navigateToNextScreen, SPLASH_TIME_OUT);
    }

    private void navigateToNextScreen() {
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        boolean isFirstTime = prefs.getBoolean("isFirstTime", true);
        Intent intent;

        if (isFirstTime) {
            // First-time launch → Intro slide
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isFirstTime", false);
            editor.apply();

            intent = new Intent(this, SlideActivity.class);
        } else {
            // Not first time → Go to LoginActivity instead of MainActivity
            intent = new Intent(this, com.example.mapmate.activities.LoginActivity.class);
        }

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}
