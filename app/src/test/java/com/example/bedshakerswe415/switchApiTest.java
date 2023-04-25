package com.example.bedshakerswe415;

import static com.example.bedshakerswe415.Switch.TEXT;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class switchApiTest {
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



    /**
     * Testing the return of IP from getStatus when on shelly switch
     * Test1: no ip set on shelly
     * Test2: ip is home wifi
     * @throws IOException
     */
    @Test
    public void getStatusOnShellyNetwork() throws IOException {
        testSwitch.setConfig("Fios-V9QV4", "bond832sad5073copy");
        assertEquals("192.168.1.172", testSwitch.getStatus());
    }

    @Test
    public void testWifiCredentials() throws IOException {
        testSwitch.setConfig("Fios-V9QV4", "bond832sad5073copy");
        assertEquals("192.168.1.172", testSwitch.getStatus());
        assertTrue(testSwitch.setConfig("Fios-V9QV4", "bond832sad5073copy"));
    }

}
