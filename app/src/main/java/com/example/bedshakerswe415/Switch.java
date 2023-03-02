package com.example.bedshakerswe415;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Switch {
    private final int id;

    public Switch(int id) {

        this.id = id;
    }

    public boolean TurnOn() throws IOException {
        URL url = new URL("http://192.168.33.1/rpc/Switch.Toggle?id=" + id);
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
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

    public boolean getStatus() throws IOException {
        URL url = new URL("http://192.168.33.1/rpc/Switch.GetStatus?id=" + id);
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.connect();
            int responseCode = conn.getResponseCode();

            if(responseCode == 200) {
                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    informationString.append(scanner.nextLine());
                }
                //Close the scanner
                scanner.close();

                System.out.println(informationString);
                return true;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }


}
