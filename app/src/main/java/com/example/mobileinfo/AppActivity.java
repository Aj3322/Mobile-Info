package com.example.mobileinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import java.util.ArrayList;

public class AppActivity extends AppCompatActivity {
    RecyclerView app_recycle_view;
    ArrayList<ListModel> list;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        list = new ArrayList<>();

        list.add(new ListModel("AJAY","hii"));
        app_recycle_view=findViewById(R.id.app_recycle_view);

        app_recycle_view.setAdapter(new CamRVAdaptor(this,list));
    }
}