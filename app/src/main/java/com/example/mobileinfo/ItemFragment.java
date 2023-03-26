package com.example.mobileinfo;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobileinfo.placeholder.PlaceholderContent;

import java.util.ArrayList;
import java.util.Locale;

public class ItemFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    ArrayList<ListModel> list;
    public ItemFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);


        list=new ArrayList<>();

        list.add(new ListModel("OS Version", GetHardWareInfo.getOsVersion()));
        list.add(new ListModel( "Model" ,GetHardWareInfo.getModel()));
        list.add(new ListModel("Manufacture",Build.MANUFACTURER.toUpperCase(Locale.ROOT)));
        list.add(new ListModel("Brand",Build.BRAND.toUpperCase(Locale.ROOT)));
        list.add(new ListModel("Build Number", GetHardWareInfo.getBuildNumber()));
        list.add(new ListModel("Hardware",GetHardWareInfo.getHardware().toUpperCase(Locale.ROOT)));
        list.add(new ListModel("SIM COUNT",String.valueOf(GetHardWareInfo.getSimCount(view.getContext()))));
        list.add(new ListModel("Radio Firmware ", GetHardWareInfo.getRadioFirmware()));
        list.add(new ListModel("Bootloader",GetHardWareInfo.getBootloader().toUpperCase(Locale.ROOT)));
        list.add(new ListModel("Device Language", GetHardWareInfo.getDeviceLanguageSetting().toUpperCase(Locale.ROOT)));
        list.add(new ListModel("Serial", Build.SERIAL.toUpperCase(Locale.ROOT)));
        list.add(new ListModel("Model Id",Build.ID));
        list.add(new ListModel("Type",Build.TYPE.toUpperCase(Locale.ROOT)));
        list.add(new ListModel("User",Build.USER.toUpperCase(Locale.ROOT)));
        list.add(new ListModel("Security",Build.VERSION.SECURITY_PATCH));
        list.add(new ListModel("Android Version",Build.VERSION.RELEASE));
        list.add(new ListModel("SDK",Build.VERSION.SDK));
        list.add(new ListModel("BOARD",Build.BOARD.toUpperCase(Locale.ROOT)));
        list.add(new ListModel("HOST",Build.HOST.toUpperCase(Locale.ROOT)));




        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(context,list));
        }
        return view;
    }
}