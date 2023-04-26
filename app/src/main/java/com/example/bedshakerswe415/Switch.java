package com.example.bedshakerswe415;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class Switch{
    public static final String TAG_SWITCH = "BEDSHAKER_DEBUG_STATEMENTS_SWITCH";
    public void setPrivateIP(String privateIP) {
        this.privateIP = privateIP;
    }

    public static final String SHARED_PREFS = "shared_Prefs";
    private final int id;
    private String privateIP = "";
    public static final String TEXT = "text";
    private String shellyIP = "192.168.33.1";

    public Switch(int id, SharedPreferences sharedpreferences) {
        this.id = id;
        if(sharedpreferences.getString(TEXT, "") == "") {
            privateIP = shellyIP;
        }
        else {
            privateIP = sharedpreferences.getString(TEXT, "");
            Log.d(TAG_SWITCH, "Shared preferences IP: " + privateIP + " :");

        }

    }

    /**
     * Turns on the shelly switch
     * @return True if the HTTP call was successfully called
     * @throws IOException
     */
    public boolean TurnOn() throws IOException {
        Log.d(TAG_SWITCH, "Switch TurnOn beginning process...");

        URL url = new URL("http://" + privateIP + "/rpc/Switch.Toggle?id=" + id + "&on=true");
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

    public boolean TurnOff() throws IOException {
        Log.d(TAG_SWITCH, "Switch TurnOff beginning process...");

        URL url = new URL("http://" + privateIP + "/rpc/Switch.Toggle?id=" + id + "&on=false");
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.connect();
            int responseCode = conn.getResponseCode();

            if(responseCode == 200) {
                return true;
            }
        } catch (IOException e) {
            Log.e(TAG_SWITCH, e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return false;
    }

    public void repeater(int shakeTime, int timeBetweenShakes, int numOfShakes) throws IOException {
        for (int i = 0; i<numOfShakes; i++) {
            Log.d(TAG_SWITCH, "Repeater continueRunning = " + MainActivity.continueRunning);
            if (MainActivity.continueRunning) {
                TurnOn();
                try {
                    Thread.sleep(shakeTime * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                TurnOff();
                try {
                    Thread.sleep(timeBetweenShakes * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                return;
            }
        }
    }

    /**
     *
     * @return String of switch private ip
     * @throws IOException
     */
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

    /**
     *
     * @param ssid
     * @param password
     * @return True if HTTP call is successfully called
     * @throws IOException
     */
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
