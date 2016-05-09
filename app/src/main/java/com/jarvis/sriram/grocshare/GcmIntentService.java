package com.jarvis.sriram.grocshare;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GcmIntentService extends IntentService {

    NotificationCompat.Builder notification;
    PendingIntent pIntent;
    NotificationManager manager;
    Intent resultIntent;
    TaskStackBuilder stackBuilder;

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (extras != null && !extras.isEmpty()) {  // has effect of unparcelling Bundle
            // Since we're not using two way messaging, this is all we really to check for
            if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                Logger.getLogger("GCM_RECEIVED").log(Level.INFO, extras.toString());

                String message = extras.getString("message");

                //Creating Notification Builder
                 notification = new NotificationCompat.Builder(GcmIntentService.this);
                //Title for Notification
                notification.setContentTitle("GrocShare ");
                //Message in the Notification
                notification.setContentText(message);
                //Alert shown when Notification is received
                notification.setTicker("Your Order Details");
                //Icon to be set on Notification
                notification.setSmallIcon(R.drawable.notification_icon);
                //Creating new Stack Builder
                stackBuilder = TaskStackBuilder.create(GcmIntentService.this);
                stackBuilder.addParentStack(GrocShare.class);
                //Intent which is opened when notification is clicked
                //resultIntent = new Intent(GcmIntentService.this, GrocShare.class);
                //stackBuilder.addNextIntent(resultIntent);
                //pIntent =  stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
                //notification.setContentIntent(pIntent);
                manager =(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(0, notification.build());

                showToast(message);
            }
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    protected void showToast(final String message) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }
}