package com.example.bedshakerswe415;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Switch {
    private final int id;

    public Switch(int id) {

        this.id = id;
    }

    public boolean TurnOn() throws MalformedURLException {
        URL url = new URL("http://192.168.33.1/rpc/Switch.Toggle?id=" + id);
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //conn.setRequestMethod("Get");
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

    public static void main(String[] args) throws MalformedURLException {
        Switch switch1 = new Switch(0);
        switch1.TurnOn();
    }




}
