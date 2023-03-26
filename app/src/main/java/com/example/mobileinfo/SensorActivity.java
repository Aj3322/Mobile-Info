package com.example.mobileinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import java.util.ArrayList;

public class SensorActivity extends AppCompatActivity {
    RecyclerView sensor_recycle_view;
    ArrayList<ListModel> list;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);


        list = new ArrayList<>();
        list.add(new ListModel("AJAY","hii"));
        sensor_recycle_view=findViewById(R.id.sensor_recycle_view);

        sensor_recycle_view.setAdapter(new CamRVAdaptor(this,list));
    }
}