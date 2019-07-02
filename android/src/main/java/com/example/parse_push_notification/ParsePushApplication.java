package com.example.parse_push_notification;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.fcm.ParseFCM;

public class ParsePushApplication extends Application {
    Activity contextTEST;
    private static Context context;

    @Override
    public void onCreate() {
        Log.e("ADAIR"," ParsePushApplication Hola hola hola hola hola");

        super.onCreate();


        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);
        //initParse(this); //aun no se si se deba comentar esta linea

        /*try{
            Log.e("autoInitParse-","entro al auto initParse");
            //result.success(true);
            try {
                Log.e("PARSE TESTE RUND","runining installation parse");
                String url = "http://192.168.2.1:1337/parse/";
                //String url = "http://10.0.0.6:1337/parse/";
                String applicationId = "parseTestA";
                Parse.initialize(new Parse.Configuration.Builder(this)
                        .applicationId(applicationId)
                        .server(url)
                        .build()
                );
                //Parse.setLogLevel(Parse.LOG_LEVEL_VERBOSE);
                Log.d("Parser-server","InitParse");
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
                                Log.e("getInstanceId token",""+ token);
                                ParseFCM.register(token);
                            }
                        });
                //return true;
                //result.success(true);
            }catch (RuntimeException ex){
                Log.e("FAILED test", ex.toString());
                //return false;

                //result.success(false);
            }
        }catch (RuntimeException ex){
            Log.e("Error PARSE",ex.toString());
        }*/
    }

    public void initParse(Context context,String serverUrl, String applicationId){
        Log.e("ParsePushApplication","runining installation parse in ParsePushApplication");
        //String url = "http://192.168.2.1:1337/parse/";
        //String url = "http://10.0.0.6:1337/parse/";
        //String applicationId = "parseTestA";
        Log.e("ParsePush INIT","serverUrl" + serverUrl);
        Log.e("ParsePush INIT","applicationId" + applicationId);
        Parse.initialize(new Parse.Configuration.Builder(context)
                .applicationId(applicationId)
                .server(serverUrl)
                .build()
        );
         try {
             createNewToken();
         }catch (RuntimeException ex){
             Log.e("ERRORRRRRRR","ERROR NO SE GUARDA EL TOKEN");
         }
    }

    public void createNewToken(){
        Log.d("Parser-server","InitParse");
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
                        Log.e("getInstanceId token",""+ token);
                        try {
                            Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);
                            ParseFCM.register(token);
                            Log.e("FIrebaseMessage", "Se registro con exito");
                        } catch (Exception ex) {
                            Log.e("FIrebaseMessage", "No se pudo hacer el registro");
                        }
                        //ParseFCM.register(token);
                    }
                });
    }
    public static Context getContext() {
        return context.getApplicationContext();
    }


}
