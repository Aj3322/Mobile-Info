package com.example.mobileinfo;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdaptor extends FragmentPagerAdapter {

    public ViewPagerAdaptor(@NonNull FragmentManager fm) {
        super(fm);
    }



    @Override
    public Fragment getItem(int position) {

        if (position==0){
            return new ItemList_Fragment();
        }else{
            return new ItemFragment();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position==0){
            return "DASHBOARD" ;
        }else {
            return "MAIN INFO";
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
