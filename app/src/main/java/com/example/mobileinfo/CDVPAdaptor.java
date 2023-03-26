package com.example.mobileinfo;

import android.hardware.Camera;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class CDVPAdaptor extends FragmentPagerAdapter {


    public CDVPAdaptor(@NonNull FragmentManager fm) {
        super(fm);


    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position==0){
            return new GenralFragment();
        }else if (position==1){
            return new Cam1Fragment();
        }else {
            return new Cam2Fragment();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position==0){
            return "GENERAL";
        }else if (position==1){
            return "CAMERA 1";
        }else {
            return "CAMERA 2";
        }
    }

    @Override
    public int getCount() {
        return Camera.getNumberOfCameras() + 1;
    }
}
