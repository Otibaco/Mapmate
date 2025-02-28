package com.example.mapmate;

import android.content.IntentFilter;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private NetworkReceiver networkReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        // Force Light Mode
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Register network receiver to detect internet status changes
        networkReceiver = new NetworkReceiver();
        registerReceiver(networkReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister network receiver when the activity is destroyed
        unregisterReceiver(networkReceiver);
    }
}