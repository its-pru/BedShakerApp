package com.example.bedshakerswe415;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class ReceiveSms extends BroadcastReceiver {

    // Contains a list of numbers which the app registers as a person that can send the wake up text
    private String validSenders[] = {"6505551212"};
    // The text string which the sender should send to activate the device
    private String activateText = "SHAKE";

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

    /**
     * This method is called when the BroadcastReceiver is receiving an Intent broadcast.
     * A small alert appears which contains the number the text came from, and the content of
     * the message.
     * @param context The Context in which the receiver is running.
     * @param intent The Intent being received.
     */
    @Override
    public void onReceive(Context context, Intent intent) {

        // If the users device receives an SMS message
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
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
                        if (isValidSender(msgFrom)) {
                            String msgBody = msgs[i].getMessageBody();
                            if (msgBody.equals(activateText)) {
                                // Makes the small pop up appear on the screen
                                Toast.makeText(context, "From: " + msgFrom + ", Body: " + msgBody, Toast.LENGTH_LONG).show();
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
