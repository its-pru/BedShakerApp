package com.example.bedshakerswe415;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class Switch {
    public void setPrivateIP(String privateIP) {
        this.privateIP = privateIP;
    }

    private final int id;
    private String privateIP = "";
    public static final String TEXT = "text";
    private String shellyIP = "192.168.33.1";
    public static final String SHARED_PREFS = "shared_Prefs";
    //SharedPreferences sharedpreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
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
        String ip = "";
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
                ip = parseGetStatus(informationString);
                if(ip.equals("ull,")){
                    return "null";
                }
                return ip;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "192.168.33.1";
    }

    public String getPrivateIP() {
        return privateIP;
    }

    public boolean setConfig(String ssid, String password) throws IOException {
        String urlstring = "http://192.168.33.1/rpc/WiFi.SetConfig?config={\"sta\":{\"ssid\":\""+ssid+"\",\"pass\":\""+password+"\",\"enable\":true}}";
        URL url = new URL(urlstring);
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
        String ip = "";
        for(int i=11;i <informationString.length();i++) {
            if (informationString.charAt(i) != '"') {
                ip = ip + informationString.charAt(i);
            } else if(informationString.charAt(i) == '"'){
                return ip;
            }
        }
        return "null";
    }

    public boolean getstatusCheckandSetSharedPref(SharedPreferences sharedPreferences) throws IOException {
        ArrayList<String> invalid = new ArrayList<String>();
        invalid.add("192.168.33.1");
        invalid.add("0.0.0.0");
        invalid.add("null");
        long t= System.currentTimeMillis();
        long end = t+30000;
        while(System.currentTimeMillis() < end){
            String ipchecker = getStatus();
            if(!invalid.contains(ipchecker)){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(TEXT, ipchecker);
                editor.apply();
                setPrivateIP(ipchecker);
                return true;
            }
        }
        return false;
    }




}
