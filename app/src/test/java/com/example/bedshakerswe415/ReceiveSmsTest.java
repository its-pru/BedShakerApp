package com.example.bedshakerswe415;

import static org.junit.jupiter.api.Assertions.*;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import org.junit.Test;
import java.io.IOException;

class ReceiveSmsTest {
    Context context = new Application().getApplicationContext();

    private Intent createSmsIntent(String from, String body) {
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address", from);
        smsIntent.putExtra("sms_body", body);

        return smsIntent;
    }

    // Test when msg contains keyword
    @Test
    public void containKeywordTest() {
        ReceiveSms rs = new ReceiveSms();
        rs.checkSender = false;
        rs.onReceive(context, createSmsIntent("99999999999", "WAKE UP"));
        // TODO : Check if switch turned on
    }

    // Test when contact is not in contact list
    @Test
    public void isContactTest() {
        ReceiveSms rs = new ReceiveSms();
        rs.checkSender = true;
        rs.onReceive(context, createSmsIntent("17179453401", "WAKE UP"));

        // TODO : Check if switch turned on
    }

    // Test when contact is in contact list
    @Test
    public void notContactTest() {
        ReceiveSms rs = new ReceiveSms();
        rs.checkSender = true;
        rs.onReceive(context, createSmsIntent("99999999999", "WAKE UP"));
        // TODO : Check if switch did NOT turn on
    }

    // Test when you ignore the sender and contact is not in list
    @Test
    public void ignoreSenderTest() {
        ReceiveSms rs = new ReceiveSms();
        rs.checkSender = false;
        rs.onReceive(context, createSmsIntent("99999999999", "WAKE UP"));
        // TODO : Check if switch turned on
    }


    // Test when msg does not contain keyword
    @Test
    public void notContainKeywordTest() {
        ReceiveSms rs = new ReceiveSms();
        rs.onReceive(context, createSmsIntent("99999999999", "NOT A KEYWORD"));
        // TODO : Check if switch did NOT turn on
    }
}