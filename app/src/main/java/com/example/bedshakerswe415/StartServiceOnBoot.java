package com.example.bedshakerswe415;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class StartServiceOnBoot extends BroadcastReceiver {
    public static final String TAG_BOOT_BROADCAST_RECEIVER = "DARROW_A";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
            startActivityDirectly(context);
            startServiceDirectly(context);
        }
    }

    private void startActivityDirectly(Context context) {
        try {
            Log.d(TAG_BOOT_BROADCAST_RECEIVER, "BootService onReceive start activity directly");
            Intent startActivityIntent = new Intent(context,MainActivity.class);
            startActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(startActivityIntent);
        } catch (Exception e) {
            Log.e(TAG_BOOT_BROADCAST_RECEIVER, e.getMessage(), e);
        }

    }

    private void startServiceDirectly(Context context) {
//        try {

            Log.d(TAG_BOOT_BROADCAST_RECEIVER, "BootService onReceive start service directly");
            Intent startServiceIntent = new Intent(context,MyForegroundService.class);
            context.startService(startServiceIntent);
//            Thread.sleep(1000);
//
//        } catch (InterruptedException ex) {
//            Log.e(TAG_BOOT_BROADCAST_RECEIVER, ex.getMessage(), ex);
//        }
    }

}
