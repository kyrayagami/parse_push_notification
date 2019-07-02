package com.example.parse_push_notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.NotificationChannel;


import android.content.Intent;
import android.util.Log;
import android.os.Build;

//import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.parse.PLog;
import com.parse.PushRouter;
import com.parse.fcm.ParseFCM;
import android.content.Context;
import android.graphics.Color;



import org.json.JSONException;
import org.json.JSONObject;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.example.parse_push_notification.R;
import android.media.RingtoneManager;


import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.Random;
import android.app.PendingIntent;
import androidx.core.graphics.drawable.IconCompat;

import android.content.res.AssetManager;

//package com.parse.fcm;

public class ParseFirebaseMessagingServiceAdair extends FirebaseMessagingService {
    public static final String ACTION_REMOTE_MESSAGE =
            "com.example.parse_push_notification.NOTIFICATION";
    public static final String EXTRA_REMOTE_MESSAGE = "notification";

    public static final String ACTION_TOKEN = "com.example.parse_push_notification.TOKEN";
    public static final String EXTRA_TOKEN = "token";
    private static final String DRAWABLE = "drawable";


    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        try {
            ParseFCM.register(token);
            Log.e("FIrebaseMessage", "Se registro con exito");
        } catch (Exception ex) {
            Log.e("FIrebaseMessage", "No se pudo hacer el registro");
        }
    }


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        //new ParsePushApplication().onCreate();
        Log.e("ParseFCM ADAIR", "onMessageReceived");
        PLog.d("ParseFCM ADAIR", "onMessageReceived");

        String pushId = remoteMessage.getData().get("push_id");
        String timestamp = remoteMessage.getData().get("time");
        String dataString = remoteMessage.getData().get("data");
        String channel = remoteMessage.getData().get("channel");

        JSONObject data = null;
        if (dataString != null) {
            try {
                data = new JSONObject(dataString);
                Log.e("ParseFCM ADAIR", "122222222222");
                try {
                    //PushRouter.getInstance().handlePush(pushId, timestamp, channel, data);
                    shownoti(getApplicationContext(), data);
                    Log.e("ADAIR FIREBASE", "TRUEEEE  :O");
                } catch (Exception ex) {

                    shownoti(getApplicationContext(), data);
                    Log.e("ADAIR FIREBASE", "CATCH TEST :C");
                }
            } catch (JSONException e) {
                PLog.e("ParseFCM ADAIR", "Ignoring push because of JSON exception while processing: " + dataString, e);
                return;
            }
        }


        //PushRouter.getInstance().handlePush(pushId, timestamp, channel, data);

    }

    void shownoti(Context context,JSONObject data){


        /*AssetManager assetManager = registrar.context().getAssets();
        String key = registrar.lookupKeyForAsset("icons/icon_c10.png");
        AssetFileDescriptor fd = assetManager.openFd(key);*/

        //ContextCompat.getDrawable(context, R.drawable.icon_c10.png)
        Log.e("ADAIR SHOWNOTI","ENTRO AL SHOW NOTI");
        Intent intent = new Intent(context , getMainActivityClass(context));
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "parse_push_notification")
                .setContentTitle(data.optString("title"))
                .setContentText(data.optString("alert") )
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent)
                .setContentInfo(data.optString("alert") )
                //.setLargeIcon(icon)
                .setSmallIcon(R.drawable.app_icon)
                //.setSmallIcon(fd)
                //.setColor(Color.RED)
                .setLights(Color.GREEN, 1000, 300)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setSmallIcon(R.drawable.app_icon);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Notification Channel is required for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channelL = new NotificationChannel(
                    "parse_push_notification", "parse_push_notification", NotificationManager.IMPORTANCE_DEFAULT
            );
            channelL.setDescription("channel description");
            channelL.setShowBadge(true);
            channelL.canShowBadge();
            channelL.enableLights(true);
            channelL.setLightColor(Color.GREEN);
            channelL.enableVibration(true);
            channelL.setVibrationPattern(new long[]{100, 200, 300, 400, 500});
            notificationManager.createNotificationChannel(channelL);
        }
        int random = new Random().nextInt(100000);
        Log.e("RANDOM ", ""+random);
        notificationManager.notify(random, notificationBuilder.build());
    }


    /*void showNotification(String title, String message) {
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("parse_push_notification",
                    "YOUR_CHANNEL_NAME",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("YOUR_NOTIFICATION_CHANNEL_DISCRIPTION");
            mNotificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "parse_push_notification")
                .setSmallIcon(R.drawable.app_icon) // notification icon
                .setContentTitle(title) // title for notification
                .setContentText(content)// message for notification
                .setAutoCancel(true); // clear notification after click
        Intent intent = new Intent(getApplicationContext(), ACTIVITY_NAME.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pi);


    }*/
/*
    public static Notification createNotification(Context context, NotificationDetails notificationDetails) {
        setupNotificationChannel(context, notificationDetails);



        Intent intent = new Intent(context, getMainActivityClass(context));
        intent.setAction(SELECT_NOTIFICATION);
        intent.putExtra(PAYLOAD, notificationDetails.payload);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notificationDetails.id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        DefaultStyleInformation defaultStyleInformation = (DefaultStyleInformation) notificationDetails.styleInformation;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, notificationDetails.channelId)
                .setContentTitle(defaultStyleInformation.htmlFormatTitle ? fromHtml(notificationDetails.title) : notificationDetails.title)
                .setContentText(defaultStyleInformation.htmlFormatBody ? fromHtml(notificationDetails.body) : notificationDetails.body)
                .setTicker(notificationDetails.ticker)
                .setAutoCancel(BooleanUtils.getValue(notificationDetails.autoCancel))
                .setContentIntent(pendingIntent)
                .setPriority(notificationDetails.priority)
                .setOngoing(BooleanUtils.getValue(notificationDetails.ongoing))
                .setOnlyAlertOnce(BooleanUtils.getValue(notificationDetails.onlyAlertOnce));

        setSmallIcon(context, notificationDetails, builder);
        if (!StringUtils.isNullOrEmpty(notificationDetails.largeIcon)) {
            builder.setLargeIcon(getBitmapFromSource(context, notificationDetails.largeIcon, notificationDetails.largeIconBitmapSource));
        }
        if (notificationDetails.color != null) {
            builder.setColor(notificationDetails.color.intValue());
        }

        applyGrouping(notificationDetails, builder);
        setSound(context, notificationDetails, builder);
        setVibrationPattern(notificationDetails, builder);
        setLights(notificationDetails, builder);
        setStyle(context, notificationDetails, builder);
        setProgress(notificationDetails, builder);
        return builder.build();
    }
    */

    private static Class getMainActivityClass(Context context) {
        String packageName = context.getPackageName();
        Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        String className = launchIntent.getComponent().getClassName();
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}