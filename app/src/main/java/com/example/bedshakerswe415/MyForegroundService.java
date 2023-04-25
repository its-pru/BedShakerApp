package com.example.bedshakerswe415;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;


public class MyForegroundService extends Service {
//    private ReceiveSms receiveSms = null;
//
//    public void onCreate() {
//        super.onCreate();
//
//        IntentFilter intentFilter = new IntentFilter();
//
//        // Filters for only SMS messages
//        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
//
//        // Set priority of broadcast receiver
//        intentFilter.setPriority(100);
//
//        receiveSms = new ReceiveSms();
//
//        // Register broadcast receiver with intent filter object
//        registerReceiver(receiveSms, intentFilter);
//
//        Log.d("DEBUG" , "Service is registered.");
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//
//        // Unregister receiver when destroy service
//        if (receiveSms != null) {
//            unregisterReceiver(receiveSms);
//            Log.d("DEBUG", "Service is unregistered.");
//        }
//    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("DARROW_A", "Beginning Foreground Service");
//        new Thread(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        while (true) {
//                            Log.e("Service", "Service is running...");
//                            try {
//                                Thread.sleep(2000);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                }
//        ).start();

        final String CHANNELID = "Foreground Service ID";
        NotificationChannel channel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel(
                    CHANNELID,
                    CHANNELID,
                    NotificationManager.IMPORTANCE_LOW
            );
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getSystemService(NotificationManager.class).createNotificationChannel(channel);
        }
        Notification.Builder notification = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notification = new Notification.Builder(this, CHANNELID)
                    .setContentText("Service is running")
                    .setContentTitle("Service enabled")
                    .setSmallIcon(R.drawable.ic_launcher_background);
        }

        startForeground(1001, notification.build());

        // return START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
