package com.example.mobileinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    TabLayout tab_layout;
    ViewPager view_pager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tab_layout =findViewById(R.id.tab_lay);
        view_pager=findViewById(R.id.view_pager);

        ViewPagerAdaptor adaptor=new ViewPagerAdaptor(getSupportFragmentManager());
        view_pager.setAdapter(adaptor);
        tab_layout.setupWithViewPager(view_pager);
    }
}