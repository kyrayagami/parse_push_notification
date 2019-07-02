package com.example.parse_push_notification;

import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.InstanceIdResult;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import androidx.annotation.NonNull;
import com.parse.ParseException;

import com.parse.fcm.ParseFCM;
import com.parse.fcm.ParseFirebaseMessagingService;


import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** ParsePushNotificationPlugin */
public class ParsePushNotificationPlugin implements MethodCallHandler, PluginRegistry.NewIntentListener {
    /**
     * Plugin registration.
     */
    Activity context;
    MethodChannel methodChannel;
    private static boolean gForeground = false;
    private static final String SELECT_NOTIFICATION = "SELECT_NOTIFICATION";
    private static String PAYLOAD = "payload";
    private MethodChannel channel;

    //private final Registrar registrar;


  public static void registerWith(Registrar registrar) {
      //this.registrar = registrar;
      //this.registrar.addNewIntentListener(this);
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "parse_push_notification");
    channel.setMethodCallHandler(new ParsePushNotificationPlugin(registrar.activity(), channel));
  }

    /*public static void registerWith(Registrar registrar) {
        ParsePushNotificationPlugin plugin = new ParsePushNotificationPlugin(registrar);
    }

    public ParsePushNotificationPlugin(Registrar registrar) {
        this.registrar = registrar;
        this.registrar.addNewIntentListener(this);
        this.channel = new MethodChannel(registrar.messenger(), "example/parse_push_notification");
        this.channel.setMethodCallHandler(this);
    }*/

  public ParsePushNotificationPlugin(Activity activity, MethodChannel methodChannel){
    this.context = activity;
    this.methodChannel = methodChannel;
    this.methodChannel.setMethodCallHandler(this);
  }


    @Override
    public boolean onNewIntent(Intent intent) {
        Log.e("onNewIntent START", "entro al onNewIntent principal");
        return sendNotificationPayloadMessage(intent);
        //return false;
    }

    private Boolean sendNotificationPayloadMessage(Intent intent) {
        if (SELECT_NOTIFICATION.equals(intent.getAction())) {
            String payload = intent.getStringExtra(PAYLOAD);
            channel.invokeMethod("selectNotification", payload);
            return true;
        }
        return false;
    }

    @Override
    public void onMethodCall(MethodCall call, final Result result) {
        switch (call.method) {
            case "getPlatformVersion":
                result.success("Android " + android.os.Build.VERSION.RELEASE);
                break;
            case "showAlertDialog":
                Dialog dialog = new Dialog(context);
                dialog.setTitle("Hi, adair in plugin flutter");
                dialog.show();
                break;
            case "autoInitParse":
                //initializeParse();
                //new ParsePushApplication();
                Log.e("ADAIR", "Ya no debo de llamar a este method");
                //initParse();
                String serverUrl= call.argument("serverUrl"), applicationId = call.argument("applicationId");
                new ParsePushApplication().initParse(context, serverUrl, applicationId);
                //initializeParse();

                //createNewToken();
                break;
            default:
                result.notImplemented();
                break;
        }
    }


    private void initParse() {
        Log.e("PARSE TESTE RUND", "runining installation parse");
        String url = "http://192.168.2.1:1337/parse/";
        //String url = "http://10.0.0.6:1337/parse/";
        String applicationId = "parseTestA";
        Parse.initialize(new Parse.Configuration.Builder(context)
                .applicationId(applicationId)
                .server(url)
                .build()
        );
    }

    private void createNewToken() {
        Log.d("Parser-server", "InitParse");
        FirebaseInstanceId.getInstance()
                .getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.e("", "getInstanceId failed", task.getException());
                            //result.success(true);
                            return;
                        }
                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        Log.e("getInstanceId token", "" + token);
                        ParseFCM.register(token);
                    }
                });
    }


    private void initializeParse() {
        ParseInstallation.getCurrentInstallation().saveInBackground();
        Log.e("autoInitParse-", "entro al auto initParse");
        //result.success(true);
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.e("PARSE TESTE RUND", "runining installation parse");
                            //String url = "http://192.168.2.1:1337/parse/";
                            String url = "http://10.0.0.6:1337/parse/";
                            String applicationId = "parseTestA";
                            Parse.initialize(new Parse.Configuration.Builder(context)
                                    .applicationId(applicationId)
                                    .server(url)
                                    .build()
                            );
                            //Parse.setLogLevel(Parse.LOG_LEVEL_VERBOSE);
                            Log.d("Parser-server", "InitParse");
                            FirebaseInstanceId.getInstance()
                                    .getInstanceId()
                                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                            if (!task.isSuccessful()) {
                                                Log.e("", "getInstanceId failed", task.getException());
                                                //result.success(true);
                                                return;
                                            }
                                            // Get new Instance ID token
                                            String token = task.getResult().getToken();
                                            Log.e("getInstanceId token", "" + token);
                                            ParseFCM.register(token);
                                        }
                                    });
                            //return true;
                            //result.success(true);
                        } catch (RuntimeException ex) {
                            Log.e("FAILED test", ex.toString());
                            //return false;

                            //result.success(false);
                        }
                    }
                }).start();
    }


    public static boolean isInforeground() {
        return true;
    }


    /*private static void setSmallIcon(Context context){
        String defaultIcon = sharedPreferences.getString("default_icon", null);
        if (StringUtils.isNullOrEmpty(defaultIcon)) {
            // for backwards compatibility: this is for handling the old way references to the icon used to be kept but should be removed in future
            builder.setSmallIcon(notificationDetails.iconResourceId);

        } else {
            builder.setSmallIcon(getDrawableResourceId(context, defaultIcon));
        }
    }*/

}
