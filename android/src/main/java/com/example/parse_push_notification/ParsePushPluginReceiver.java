/*
package com.example.parse_push_notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.parse.ParsePushBroadcastReceiver;

import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;

import com.parse.PLog;
import com.parse.ParsePushBroadcastReceiver;
import com.parse.ParseAnalytics;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.app.PendingIntent;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.NotificationChannel;
import android.graphics.Color;

import com.example.parse_push_notification.ParsePushConfigReader;

//import android.support.v4.app.NotificationCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;
//import android.support.v4.app.TaskStackBuilder;

import android.net.Uri;
import android.os.Build;
import android.util.Log;

import org.json.JSONObject;
import org.json.JSONException;

import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import android.os.Bundle;

import java.util.List;
import java.util.Random;

import android.content.SharedPreferences;

//import me.leolin.shortcutbadger.ShortcutBadger;


public class ParsePushPluginReceiver extends ParsePushBroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        ParseAnalytics.trackAppOpenedInBackground(intent);

        String uriString = null;
        try {
            JSONObject pushData = new JSONObject(intent.getStringExtra("com.parse.Data"));
            uriString = pushData.optString("uri");
        } catch (JSONException e) {
            Log.v("parse_push_notification", "Unexpected JSONException when receiving push data: ", e);
        }
        Class<? extends Activity> cls = getActivity(context, intent);
        Intent activityIntent;
        if (uriString != null && !uriString.isEmpty()) {
            activityIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uriString));
        } else {
            activityIntent = new Intent(context, MainActivity.class);
        }
        activityIntent.putExtras(intent.getExtras());
        if (Build.VERSION.SDK_INT >= 16) {
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(cls);
            stackBuilder.addNextIntent(activityIntent);
            stackBuilder.startActivities();
        } else {
            activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(activityIntent);
        }
    }

}

*/