package com.example.mapmate;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 5000; // 5 seconds delay

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Force Light Mode
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Find the MapMate text and apply fade-in animation
        TextView appName = findViewById(R.id.appName);
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(2000);
        appName.startAnimation(fadeIn);

        // Wait for 4 seconds, then check the network
        new Handler().postDelayed(this::checkNetworkStatus, SPLASH_TIME_OUT);
    }

    private void checkNetworkStatus() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null && activeNetwork.isConnected()) {
            Toast.makeText(this, "Connected! Launching app...", Toast.LENGTH_SHORT).show();
            navigateToMain();
        } else {
            Toast.makeText(this, "No Internet. Waiting for connection...", Toast.LENGTH_LONG).show();
            showNoInternetScreen();
        }
    }

    private void navigateToMain() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    private void showNoInternetScreen() {

        Intent intent = new Intent(SplashActivity.this, NoInternetActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

}


