package com.example.mapmate.recievers;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "GeofenceReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        if (geofencingEvent.hasError()) {
            Log.e(TAG, "Geofencing error: " + geofencingEvent.getErrorCode());
            return;
        }

        // Get the transition type
        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        // Get the geofences that were triggered
        List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();

        // Process based on transition type
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            // User entered a geofence
            for (Geofence geofence : triggeringGeofences) {
                String reminderId = geofence.getRequestId();
                Log.d(TAG, "User entered geofence for reminder: " + reminderId);

                // For now, just log the event
                // Later we'll implement notifications
            }
        } else if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
            // User exited a geofence
            for (Geofence geofence : triggeringGeofences) {
                String reminderId = geofence.getRequestId();
                Log.d(TAG, "User exited geofence for reminder: " + reminderId);

                // For now, just log the event
                // Later we'll implement notifications
            }
        }
    }
}