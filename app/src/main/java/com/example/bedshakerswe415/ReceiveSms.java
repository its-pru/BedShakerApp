package com.example.bedshakerswe415;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Handles what happens when an SMS message is received.
 */
public class ReceiveSms extends BroadcastReceiver{

    public static final String TAG_RECEIVE_SMS = "BEDSHAKER_DEBUG_STATEMENTS_RECEIVE";

    // If False, then the phone doesn't check the number sending the text, only the message
    public boolean checkSender = false;
    // Contains a list of numbers which the app registers as a person that can send the wake up text
    private String[][] contacts = {
            {"Android", "6505551212"},
            {"Tommy", "17179453401"},
    };
    // The text string which the sender should send to activate the device
    private String[] keywords = { "WAKE UP", "GET UP" };
    private String msgFrom = "";

    public static final String NUMBER = "number";

    /**
     * Extracts the message from the bundle which is received from the Intent for an SMS.
     * @param bundle Contains the intent data.
     * @return A 2d array with the parts of the message (msgbody, msgfrom)
     */
    private String[][] extractMessageInfo(Bundle bundle) {
        //
        SmsMessage[] msgs;
        String[][] msgsInfo = new String[0][2];

        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];
            msgsInfo = new String[pdus.length][2];

            for (int i = 0; i < msgs.length; i++) {
                // There are two message formats 3gpp for GSM/UMTS/LTE messages, and 3gpp2 for CDMA/LTE messages
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i], "3gpp2");
                msgsInfo[i][0] = msgs[i].getMessageBody();
                msgsInfo[i][1] = msgs[i].getOriginatingAddress().substring(1);
            }
        }

        return msgsInfo;
    }

    /**
     * Checks if the sender is a person that can send a text to this phone.
     * @param from The number the text was received from
     * @return True, if the sender is valid, otherwise false
     */
    private boolean isValidSender(String from) {
        for (int i = 0; i < contacts.length; i++) {
            if (from.equals(contacts[i][1])) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the text contains a keyword.
     * @param msgBody The number the text was received from
     * @return True, if the text contains the keyword, otherwise false
     */
    private boolean containsKeyword(String msgBody) {
        for (int i = 0; i < keywords.length; i++) {
            if (msgBody.contains(keywords[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the message is ignored or not based on if
     * it contains a keyword and is from a valid sender.
     * @param msgsInfo Contains the msgbody text and the sender.
     * @return False if message is to be ignored, otherwise true.
     */
    private boolean checkIfMsgValid(String[][] msgsInfo) {
        for (int i = 0; i < msgsInfo.length; i++) {
            msgFrom = msgsInfo[i][1];
            if ((!checkSender || isValidSender(msgsInfo[i][1])) && containsKeyword(msgsInfo[i][0])) {
                    return true;
            }
        }
        return false;
    }

    /**
     * This method is called when the BroadcastReceiver is receiving an Intent broadcast.
     * The function will check if an SMS is received. If it is then it checks if the message
     * is from a valid sender (if applicable) and if the message contains the keyword.
     * @param context The Context in which the receiver is running.
     * @param intent The Intent being received.
     */
   @Override
    public void onReceive(Context context, Intent intent) {
        // If the user's device receives an SMS message
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Log.d(TAG_RECEIVE_SMS, "Received Text Message");
            SharedPreferences sharedPreferences = context.getSharedPreferences("shared_Prefs", Context.MODE_PRIVATE);

            Bundle bundle = intent.getExtras();
            String[][] messageInfo = extractMessageInfo(bundle);
            boolean isValidMessage = checkIfMsgValid(messageInfo);

            if (isValidMessage) {
                Toast.makeText(context, "Received Message", Toast.LENGTH_LONG).show();

                Log.d(TAG_RECEIVE_SMS, "Storing in Shared Preferences");
                // Share who it is from in shared prefs
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(NUMBER, msgFrom);
                editor.commit();

                try {
                    Log.d(TAG_RECEIVE_SMS, "Turning on Switch");
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try  {
                                Switch switch2 = new Switch(0, sharedPreferences);
                                //switch2.TurnOn(); // Returns error with connect() when trying after boot
                                MainActivity.continueRunning = true;
                                switch2.repeater(1,1,7);
                                Log.d(TAG_RECEIVE_SMS, "Switch On");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    thread.start();
                } catch (Exception e) {
                    Log.e(TAG_RECEIVE_SMS, e.getMessage(), e);
                }
            }
        }
    }
}
