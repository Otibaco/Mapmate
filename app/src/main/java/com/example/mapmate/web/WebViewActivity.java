package com.example.mapmate.web;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mapmate.R;

public class WebViewActivity extends AppCompatActivity {
    private WebView webView;
    private ProgressBar progressBar;
    private String url;

    @SuppressLint("SetJavaScriptEnabled") // Suppress warning for enabling JavaScript
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        setTheme(R.style.ThemeOverlay_AppCompat_Dark); // Apply dark theme overlay
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        // Toolbar setup
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Loading...");
        }

        // ProgressBar setup
        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(100);
//        progressBar.setVisibility(View.VISIBLE);

        // WebView setup
        webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Enable JavaScript (be careful of XSS)
        webSettings.setDomStorageEnabled(true); // Enable local storage
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        // Get URL from intent
        url = getIntent().getStringExtra("URL");
        if (url == null || url.isEmpty()) {
            url = "https://www.google.com"; // Default URL
        }

        // WebViewClient to handle page loading inside the WebView
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.setVisibility(ProgressBar.VISIBLE);
                getSupportActionBar().setTitle("Loading...");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(ProgressBar.GONE);
                getSupportActionBar().setTitle(view.getTitle()); // Set page title
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false; // Load URL inside WebView
            }
        });

        // WebChromeClient to track loading progress
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
            }
        });

        // Load the webpage
        webView.loadUrl(url);
    }

    // Handle back button in toolbar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Close activity
            return true;
        } else if (item.getItemId() == R.id.menu_open_browser) {
            // Open URL in external browser
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Inflate the menu with "Open in Browser" button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_webview, menu);
        return true;
    }
}
