package com.example.mobileinfo;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobileinfo.R;

import java.util.ArrayList;

public class Cam2Fragment extends Fragment {

    RecyclerView cam2_recycle_view;
    ArrayList<ListModel> list;

    public Cam2Fragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cam2, container, false);

        list=new ArrayList<>();
        cam2_recycle_view=view.findViewById(R.id.cam2_recycle_view);

        cam2_recycle_view.setAdapter(new CamRVAdaptor(view.getContext(),list));



        return view;
    }
}