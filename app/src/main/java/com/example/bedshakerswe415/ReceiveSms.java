package com.example.bedshakerswe415;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import java.io.IOException;

public class ReceiveSms extends BroadcastReceiver{

    // If False, then the phone doesn't check the number sending the text, only the message
    private boolean checkSender = false;
    // Contains a list of numbers which the app registers as a person that can send the wake up text
    private String validSenders[] = {"6505551212"};
    // The text string which the sender should send to activate the device
    private String activateText = "WAKE UP";

    public static final String NUMBER = "number";

    /**
     * Checks if the sender is a person that can send a text to this phone, and the text be registered.
     * @param from The number the text was received from
     * @return True, if the sender is valid, otherwise false
     */
    private boolean isValidSender(String from) {
        for (int i = 0; i < validSenders.length; i++) {
            if (from.equals(validSenders[i])) {
                return true;
            }
        }
        return false;
    }

    private void startMainActivity(Context context) throws PackageManager.NameNotFoundException {
        PackageManager pm = context.getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(context.getPackageName());
        context.startActivity(intent);
    }

    /**
     * This method is called when the BroadcastReceiver is receiving an Intent broadcast.
     * A small alert appears which contains the number the text came from, and the content of
     * the message.
     * @param context The Context in which the receiver is running.
     * @param intent The Intent being received.
     */
   @Override
    public void onReceive(Context context, Intent intent) {
        MainActivity mainActivity = MainActivity.getInstanceActivity();
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared_Prefs", Context.MODE_PRIVATE);
        System.out.println("__ON RECEIVE SMS__");

        // If the users device receives an SMS message
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            System.out.println("__MSG RECEIVED__");
            Bundle bundle = intent.getExtras();
            System.out.println("___PRINT___\n" + bundle);

            SmsMessage[] msgs;
            String msgFrom;

            if (bundle != null) {
                try {
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];

                    for (int i = 0; i < msgs.length; i++) {
                        // There are two message formats 3gpp for GSM/UMTS/LTE messages, and 3gpp2 for CDMA/LTE messages
                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i], "3gpp2");

                        // LOGIC FOR RECEIVING A MESSAGE
                        msgFrom = msgs[i].getOriginatingAddress();
                        msgFrom =  msgFrom.substring(1);
                        if (!checkSender || isValidSender(msgFrom)) {
                            String msgBody = msgs[i].getMessageBody();
                            System.out.println("__MSG BODY__\n" + msgBody);
                            if (msgBody.equals(activateText)) {
                                // Makes the small pop up appear on the screen
                                Toast.makeText(context, "From: " + msgFrom + ", Body: " + msgBody, Toast.LENGTH_LONG).show();

                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(NUMBER, msgFrom);
                                editor.commit();

                                // TODO: Turn switch on, can create new switch object or try to reference one in main
                                try {
                                    MainActivity.getInstanceActivity().switch1.TurnOn();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }

                                System.out.println("hey");

                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
