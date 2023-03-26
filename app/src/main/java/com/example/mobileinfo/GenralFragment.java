package com.example.mobileinfo;
import android.annotation.SuppressLint;

import android.hardware.camera2.CameraCharacteristics;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class GenralFragment extends Fragment {
    ArrayList<ListModel> list;
    CameraCharacteristics.Key<Boolean> flashInfoAvailable;

    public GenralFragment() {
        // Required empty public constructor
    }

    RecyclerView cam_recycle_view;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_genral, container, false);

        cam_recycle_view=view.findViewById(R.id.cam_recycle_view);

            list = new ArrayList<>();

      GetCameraInfo info =new GetCameraInfo();
       list.add(new ListModel("AUTOFOCUS",info.hasAutoFocus(view.getContext()))) ;
        list.add(new ListModel("FLASH",info.hasFlash(view.getContext()))) ;
        list.add(new ListModel("EXTERNAL CAMERA SUPPORT",info.supportsExternalCamera(view.getContext()))) ;
        list.add(new ListModel("AR SUPPORT",info.supportsAR(view.getContext()))) ;
        list.add(new ListModel("MANUAL POST PROCESSING",info.hasManualPostProcessing(view.getContext()))) ;
        list.add(new ListModel("MANUAL SENSOR",info.hasManualSensor(view.getContext()))) ;
        list.add(new ListModel("CAPABILITY ROW",info.hasCapabilityRaw(view.getContext()))) ;
        list.add(new ListModel("FULL HW CAPABILITY LEVEL",info.hasFulHWCapabilityLevel(view.getContext()))) ;
        info.hasFrontFacingCamera(view.getContext());

        CamRVAdaptor adaptor=new CamRVAdaptor(view.getContext(),list);
        cam_recycle_view.setAdapter(adaptor);
        return view ;
    }
}