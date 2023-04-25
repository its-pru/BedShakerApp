package com.example.bedshakerswe415;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
public class ReceiveSmsTest {
    @Mock
    Context context = new Application().getApplicationContext();
    @Mock
    SharedPreferences sharedPreferences;
    @Mock
    SharedPreferences.Editor sharedPreferencesEditor;
    Switch testSwitch;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        //  when(sharedPreferences.edit()).thenReturn(sharedPreferencesEditor);
        //  when(sharedPreferencesEditor.putString(anyString(), anyString())).thenReturn(sharedPreferencesEditor);
        testSwitch  = new Switch(0, sharedPreferences);
    }

    private Intent createSmsIntent(String from, String body) {
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address", from);
        smsIntent.putExtra("sms_body", body);

        return smsIntent;
    }

    // Test when msg contains keyword
    @Test
    public void containKeywordTest() throws IOException {
        ReceiveSms rs = new ReceiveSms();
        rs.checkSender = false;
        rs.onReceive(context, createSmsIntent("99999999999", "WAKE UP"));
        verify(testSwitch.TurnOn(), times(1));
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