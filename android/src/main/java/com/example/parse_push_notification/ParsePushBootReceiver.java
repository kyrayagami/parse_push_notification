package com.example.parse_push_notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ParsePushBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        System.out.println("rebooted");
        if (action != null && action.equals(android.content.Intent.ACTION_BOOT_COMPLETED)) {
            //FlutterLocalNotificationsPlugin.rescheduleNotifications(context);
        }
    }
}
