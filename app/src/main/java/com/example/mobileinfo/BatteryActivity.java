package com.example.mobileinfo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Locale;

public class BatteryActivity extends AppCompatActivity {
    ArrayList<ListModel> list;
    RecyclerView battery_recycle_view;
    BatteryManager batteryManager;
    @RequiresApi(api = Build.VERSION_CODES.P)
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery);
        list = new ArrayList<>();

         BroadcastReceiver batteryReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                float batteryPct = 100 * level / (float) scale;

                float batTemp = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1) / 10.0f;
                int batVoltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1);
                int batPlugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
                boolean batPresent = intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false);
                int batStatus = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                int batHealth = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1);
                String batTech = intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);


                list.add(new ListModel("Level", String.format(Locale.ENGLISH, "%.1f", batteryPct), "%"));
                list.add(new ListModel("Voltage", String.format(Locale.ENGLISH, "%d", batVoltage), "mV"));
                list.add(new ListModel("Temp", String.format(Locale.ENGLISH, "%.1f", batTemp), "°C"));


                if (batPlugged == BatteryManager.BATTERY_PLUGGED_AC)
                    list.add(new ListModel("Charging", "AC Power"));
                else if (batPlugged == BatteryManager.BATTERY_PLUGGED_USB)
                    list.add(new ListModel("Charging", "USB Connected"));
                else if (batPlugged == BatteryManager.BATTERY_PLUGGED_WIRELESS) {
                    list.add(new ListModel("Charging", "Wireless Charging"));
                } else {
                    list.add(new ListModel("Charging", "None"));
                }

                list.add(new ListModel("Status", getBatStatus(batStatus)));
                list.add(new ListModel("Health", getBatHealth(batHealth)));
            }};

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    batteryManager = (BatteryManager) getSystemService(BATTERY_SERVICE);

                    int chargeCounterInt = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER);
                    list.add(new ListModel("Capacity", String.valueOf(chargeCounterInt / 1000), "mAh"));



                    int avgCurrentInt = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_AVERAGE);

                        list.add(new ListModel("Average Current", String.valueOf(avgCurrentInt), "uA"));



                    int actualCurrentInt = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW);
                    if (actualCurrentInt != Integer.MAX_VALUE && actualCurrentInt != Integer.MIN_VALUE) {
                        list.add(new ListModel("Actual Current", String.valueOf(actualCurrentInt), "uA"));
                    }

                    long remainingEnergyInt = batteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_ENERGY_COUNTER);
                    if (remainingEnergyInt >= 0 && remainingEnergyInt != Long.MAX_VALUE) {
                        list.add(new ListModel("Remaining Energy", String.valueOf(remainingEnergyInt)));
                    }



                        long timeToFullLong = batteryManager.computeChargeTimeRemaining();
                        if (timeToFullLong > 0) {
                            list.add(new ListModel("Time To Full", String.valueOf((int) (timeToFullLong / 1000))));

                    }
                }








        battery_recycle_view=findViewById(R.id.battery_recycle_view);

        battery_recycle_view.setAdapter(new CamRVAdaptor(this,list));
    }
    public String getBatStatus(int i ){
        switch (i) {
            case BatteryManager.BATTERY_STATUS_CHARGING:
                return "Charging";
            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                return "Discharging";
            case BatteryManager.BATTERY_STATUS_FULL:
                return "Full";
            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                return "Not Charging";
            case BatteryManager.BATTERY_STATUS_UNKNOWN:
                return "Unknown";
        }
        return "Available Info";
    }


    protected  String getBatHealth(int value) {
        switch (value) {
            case BatteryManager.BATTERY_HEALTH_COLD:
                return "Cold";
            case BatteryManager.BATTERY_HEALTH_DEAD:
                return "Dead";
            case BatteryManager.BATTERY_HEALTH_GOOD:
                return "Good";
            case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                return "Over Voltage";
            case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                return "Over Heated";
            case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                return "Health Unknown";
            case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                return "Unspecified";
        }
        return"Not Available";
    }
}