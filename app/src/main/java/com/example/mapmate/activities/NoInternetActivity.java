package com.example.mapmate.activities;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mapmate.R;

public class NoInternetActivity extends AppCompatActivity {

    private TextView statusText;
    private Button retryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Force Light Mode
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nointernet);

        statusText = findViewById(R.id.statusText);
        retryButton = findViewById(R.id.retryButton);


        retryButton.setOnClickListener(v -> checkInternetAndRetry());
    }

    private void checkInternetAndRetry() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null && activeNetwork.isConnected()) {
            Intent intent = new Intent(NoInternetActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            statusText.setText("Still no internet. Check your connection.");
        }
    }
}
