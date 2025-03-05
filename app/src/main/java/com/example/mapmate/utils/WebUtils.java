package com.example.mapmate.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.example.mapmate.web.WebViewActivity;

public class WebUtils {
    public static void openWebPage(Context context, String url, String title) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("URL", url);
        intent.putExtra("TITLE", title);
        context.startActivity(intent);
    }
}
