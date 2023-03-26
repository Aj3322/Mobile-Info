package com.example.mobileinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import kotlin.jvm.internal.Intrinsics;


public class NetworkActivity extends AppCompatActivity {
    private static final int PERMISSION_CODE = 12;
    RecyclerView network_recycle_view;
    ArrayList<ListModel> list;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        list = new ArrayList<>();
        list.add(new ListModel("AJAY","hii"));
        network_recycle_view=findViewById(R.id.network_recycle_view);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(NetworkActivity.this, new String[]{Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.INTERNET}, PERMISSION_CODE);

        }

        Context context=getApplicationContext();

        ArrayList<ListModel> list= new ArrayList<>();
        WifiManager wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String ssid = wifiInfo.getSSID();

        ConnectivityManager connectivityManager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Service.CONNECTIVITY_SERVICE);

        /* you can print your active network via using below */
        Log.i("myNetworkType: ", connectivityManager.getActiveNetworkInfo().getTypeName());
        WifiManager wifiManager2= (WifiManager) getApplicationContext().getSystemService(getApplicationContext().WIFI_SERVICE);


        Log.i("routes ", connectivityManager.getLinkProperties(connectivityManager.getActiveNetwork()).getRoutes().toString());

        Log.i("IP ADDRESS ", connectivityManager.getLinkProperties(connectivityManager.getActiveNetwork()).getLinkAddresses().toString());
        Log.i("DNS ADDRESS", connectivityManager.getLinkProperties(connectivityManager.getActiveNetwork()).getDnsServers().toString());
            list.add(new ListModel("NETWORK ", 1));
        list.add(new ListModel("NETWORK TYPE ", connectivityManager.getActiveNetworkInfo().getTypeName()));
            list.add(new ListModel("ROUTES ", connectivityManager.getLinkProperties(connectivityManager.getActiveNetwork()).getRoutes().toString()));
            list.add(new ListModel("IP ADDRESS ", connectivityManager.getLinkProperties(connectivityManager.getActiveNetwork()).getLinkAddresses().toString()));
            list.add(new ListModel("DNS ADDRESS", connectivityManager.getLinkProperties(connectivityManager.getActiveNetwork()).getDnsServers().toString()));


        if(connectivityManager.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI) {
            Log.i("myType ", "wifi");
            DhcpInfo d =wifiManager.getDhcpInfo();
            Log.i("info", d.toString()+"");
            list.add(new ListModel("NETWORK TYPE ", "WIFI"));
            list.add(new ListModel("ROUTES ", connectivityManager.getLinkProperties(connectivityManager.getActiveNetwork()).getRoutes().toString()));

        }
        else if(connectivityManager.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_ETHERNET) {
            /* there is no EthernetManager class, there is only WifiManager. so, I used this below trick to get my IP range, dns, gateway address etc */

            list.add(new ListModel("NETWORK TYPE ", "Ethernet"));
            list.add(new ListModel("ROUTES ", connectivityManager.getLinkProperties(connectivityManager.getActiveNetwork()).getRoutes().toString()));
            list.add(new ListModel("DOMAINS ", connectivityManager.getLinkProperties(connectivityManager.getActiveNetwork()).getDomains().toString()));
            list.add(new ListModel("IP ADDRESS ", connectivityManager.getLinkProperties(connectivityManager.getActiveNetwork()).getLinkAddresses().toString()));
            list.add(new ListModel("DNS ADDRESS ", connectivityManager.getLinkProperties(connectivityManager.getActiveNetwork()).getDnsServers().toString()));

            Log.i("myType ", "Ethernet");
            Log.i("routes ", connectivityManager.getLinkProperties(connectivityManager.getActiveNetwork()).getRoutes().toString());
            Log.i("domains ", connectivityManager.getLinkProperties(connectivityManager.getActiveNetwork()).getDomains().toString());
            Log.i("ip address ", connectivityManager.getLinkProperties(connectivityManager.getActiveNetwork()).getLinkAddresses().toString());
            Log.i("dns address ", connectivityManager.getLinkProperties(connectivityManager.getActiveNetwork()).getDnsServers().toString());

        }
        else {

        } }

        if (Build.VERSION.SDK_INT >= 21) {
            list.add(new ListModel("SUPPORTED FEATURES", 1));
            list.add(new ListModel("5GHZ BAND", wifiManager.is5GHzBandSupported() ? ThreeState.YES : ThreeState.MAYBE));
            list.add(new ListModel("DEVICE TO AP RTT", wifiManager.isDeviceToApRttSupported() ? ThreeState.YES : ThreeState.MAYBE));
            list.add(new ListModel("POWER REPORTING", wifiManager.isEnhancedPowerReportingSupported() ? ThreeState.YES : ThreeState.MAYBE));
            list.add(new ListModel("WIFI-DIRECT", wifiManager.isP2pSupported() ? ThreeState.YES : ThreeState.MAYBE));
            list.add(new ListModel("OFFLOADED CONNECTIVITY SCAN", wifiManager.isPreferredNetworkOffloadSupported() ? ThreeState.YES : ThreeState.MAYBE));
            list.add(new ListModel("SCAN ALWAYS AVAILABLE", wifiManager.isScanAlwaysAvailable() ? ThreeState.YES : ThreeState.MAYBE));
            list.add(new ListModel("TUNNEL DIRECTED LINK SETUP", wifiManager.isTdlsSupported() ? ThreeState.YES : ThreeState.MAYBE));
            if (Build.VERSION.SDK_INT <= 28) {
                list.add(new ListModel("", ThreeState.NO));
                list.add(new ListModel("", ThreeState.NO));
                list.add(new ListModel("", ThreeState.NO));
                list.add(new ListModel("s", ThreeState.NO));
            } else {
                list.add(new ListModel("WIFI EASY CONNECT (DPP)", wifiManager.isEasyConnectSupported() ? ThreeState.YES : ThreeState.NO));
                list.add(new ListModel("WIFI ENHANCED OPEN (OWE)", wifiManager.isEnhancedOpenSupported() ? ThreeState.YES : ThreeState.NO));
                list.add(new ListModel("WPA3-PERSONAL SAE", wifiManager.isWpa3SaeSupported() ? ThreeState.YES : ThreeState.NO));
                list.add(new ListModel("WPA3 ENTERPRISE SUITE-B-192", wifiManager.isWpa3SuiteBSupported() ? ThreeState.YES : ThreeState.NO));
                if (Build.VERSION.SDK_INT >= 30) {
                    list.add(new ListModel("b", wifiManager.is6GHzBandSupported() ? ThreeState.YES : ThreeState.NO));
                    list.add(new ListModel("j", wifiManager.isStaApConcurrencySupported() ? ThreeState.YES : ThreeState.NO));
                    list.add(new ListModel("j", wifiManager.isWapiSupported() ? ThreeState.YES : ThreeState.NO));
                    list.add(new ListModel("h", wifiManager.isScanThrottleEnabled() ? ThreeState.YES : ThreeState.NO));
                }
            }

        }



        CamRVAdaptor adaptor=new CamRVAdaptor(this,list);
        network_recycle_view.setAdapter(adaptor);
        adaptor.notifyDataSetChanged();
    }


    public static boolean checkPermission(Context context, String permission) {

        int status = context.checkCallingOrSelfPermission(permission);
        if (status == PermissionChecker.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==PERMISSION_CODE){
            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permissions Granted", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Please give the permission", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

}