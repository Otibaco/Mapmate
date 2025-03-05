package com.example.mapmate.broadcastR;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.widget.Toast;

public class NetworkReceiver extends BroadcastReceiver {

    private static boolean wasConnected = true; // Track previous network state

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
            boolean isConnected = isConnected(context);

            if (!isConnected && wasConnected) {
                // Lost connection
                Toast.makeText(context, "No Internet", Toast.LENGTH_SHORT).show();
                wasConnected = false;
            } else if (isConnected && !wasConnected) {
                // Connection restored
                Toast.makeText(context, "Internet Restored", Toast.LENGTH_SHORT).show();
                wasConnected = true;
            }
        }
    }

    private boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
            return capabilities != null &&
                    (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR));
        }
        return false;
    }
}
