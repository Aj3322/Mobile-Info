package com.example.mobileinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class CameraInfoActivity extends AppCompatActivity {

    TabLayout tab_lay_cam;
    ViewPager view_pager_cam;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_info);

        tab_lay_cam=findViewById(R.id.tab_lay_Cam);
        view_pager_cam=findViewById(R.id.view_pager_cam);

        CDVPAdaptor adaptor= new CDVPAdaptor(getSupportFragmentManager());
        view_pager_cam.setAdapter(adaptor);
        tab_lay_cam.setupWithViewPager(view_pager_cam);


    }
}