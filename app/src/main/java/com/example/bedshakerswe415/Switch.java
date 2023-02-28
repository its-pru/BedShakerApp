package com.example.bedshakerswe415;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Switch {
    private final int id;

    public Switch(int id, int id1) {

        this.id = id1;
    }

    public boolean TurnOn() throws MalformedURLException {
        URL url = new URL("");
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("Get");
            conn.connect();
            int responseCode = conn.getResponseCode();
            if(responseCode == 200) {
                return true;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
