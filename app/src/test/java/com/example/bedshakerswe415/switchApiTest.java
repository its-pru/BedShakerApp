package com.example.bedshakerswe415;

import org.junit.Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class switchApiTest {
    @Test
    public void connectionTest() throws IOException {
        Switch testSwitch = new Switch(0);
        assertTrue(testSwitch.TurnOn());
        testSwitch.setConfig();
        testSwitch.getStatus();
    }
}
