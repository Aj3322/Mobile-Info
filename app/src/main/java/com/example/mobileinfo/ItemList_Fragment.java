package com.example.mobileinfo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ItemList_Fragment extends Fragment {

    ImageView cammera,scanner,audio,screen,wifi,data_uses,signal,memory,apps;

    public ItemList_Fragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_item_list_, container, false);

        cammera=view.findViewById(R.id.camera);
        scanner=view.findViewById(R.id.Scanner);
        audio=view.findViewById(R.id.Audio);
        screen=view.findViewById(R.id.screen);
        wifi=view.findViewById(R.id.wifi);
        data_uses=view.findViewById(R.id.data_uses);
        signal=view.findViewById(R.id.signal);
        memory=view.findViewById(R.id.memory);
        apps=view.findViewById(R.id.apps);



        cammera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),CameraInfoActivity.class));
            }
        });

        scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),SensorActivity.class));
            }
        });

        screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),DisplayActivity.class));
            }
        });

        audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),BatteryActivity.class));
            }
        });

        memory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),CPUActivity.class));
            }
        });

        data_uses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),StorageActivity.class));
            }
        });

        wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),NetworkActivity.class));
            }
        });


        apps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),AppActivity.class));
            }
        });



        return view;
    }

}