package com.example.wifianalyzerrough;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

class WifiReceiver extends BroadcastReceiver {
    WifiManager wifiManager;
    StringBuilder sb;
    ListView wifiDeviceList;
    public WifiReceiver(WifiManager wifiManager, ListView wifiDeviceList) {
        this.wifiManager = wifiManager;
        this.wifiDeviceList = wifiDeviceList;
    }
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (WifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals(action)) {
            sb = new StringBuilder();
            List<ScanResult> wifiList = wifiManager.getScanResults();
            ArrayList<String> deviceList = new ArrayList<>();
            for (ScanResult scanResult : wifiList) {
                sb.append("\n").append(scanResult.SSID).append(" - ").append(scanResult.capabilities);
               // deviceList.add("SSID->"+scanResult.SSID + "\n" + scanResult.capabilities);

                deviceList.add("SSID  & AP Name  :   "+scanResult.SSID+
                              "\nSecurity :   "+getSecurity(scanResult)+
                             "\nChannel width   :   "+ scanResult.channelWidth+
                             "\nFrequency    :   "+scanResult.frequency+
                             "\nLevel    :   "+scanResult.level+
                            "\nMac Address    :   "+scanResult.BSSID+
                            "\nTimestamp   :   "+scanResult.timestamp+
                            "\nCapabilities  :   "+scanResult.capabilities

                );

            }
        //    Toast.makeText(context, sb, Toast.LENGTH_SHORT).show();
            ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, deviceList.toArray());
            wifiDeviceList.setAdapter(arrayAdapter);
        }
    }


    static String getSecurity(WifiConfiguration config) {
        if (config.allowedKeyManagement.get(WifiConfiguration.KeyMgmt.WPA_PSK)) {
            return "SECURITY_PSK";
        }
        if (config.allowedKeyManagement.get(WifiConfiguration.KeyMgmt.WPA_EAP) ||
                config.allowedKeyManagement.get(WifiConfiguration.KeyMgmt.IEEE8021X)) {
            return "SECURITY_EAP";
        }
        return (config.wepKeys[0] != null) ? "SECURITY_WEP" : "SECURITY_NONE";
    }

    static String getSecurity(ScanResult result) {
        if (result.capabilities.contains("WEP")) {
            return "SECURITY_WEP";
        } else if (result.capabilities.contains("PSK")) {
            return "SECURITY_PSK";
        } else if (result.capabilities.contains("EAP")) {
            return "SECURITY_EAP";
        }
        return "SECURITY_NONE";
    }


}