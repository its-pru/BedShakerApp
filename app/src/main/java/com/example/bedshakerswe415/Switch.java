package com.example.bedshakerswe415;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Switch {
    public void setPrivateIP(String privateIP) {
        this.privateIP = privateIP;
    }

    private final int id;
    private String privateIP = "";

    public Switch(int id) {

        this.id = id;
    }

    public boolean TurnOn() throws IOException {
        URL url = new URL("http://" + privateIP + "/rpc/Switch.Toggle?id=" + id);
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

    public String getStatus() throws IOException {
        privateIP = "";
        URL url = new URL("http://192.168.33.1/rpc/WiFi.GetStatus?id=" + id);
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
                privateIP = parseGetStatus(informationString);
                if(privateIP.equals("ull,")){
                    return "192.168.33.1";
                }
                return privateIP;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "192.168.33.1";
    }

    public String getPrivateIP() {
        return privateIP;
    }

    public boolean setConfig() throws IOException {
        URL url = new URL("http://192.168.33.1/rpc/WiFi.SetConfig?config={\"sta\":{\"ssid\":\"Fios-V9QV4\",\"pass\":\"bond832sad5073copy\",\"enable\":true}}");
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

    private String parseGetStatus(StringBuilder informationString) {
        for(int i=11;i <informationString.length();i++) {
            if (informationString.charAt(i) != '"') {
                privateIP = privateIP + informationString.charAt(i);
            } else if(informationString.charAt(i) == '"'){
                return privateIP;
            }
        }
        return "Not Valid";
    }


}
