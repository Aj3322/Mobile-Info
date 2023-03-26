package com.example.mobileinfo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobileinfo.R;

import java.security.Policy;
import java.util.ArrayList;
import java.util.List;


public class Cam1Fragment extends Fragment {
RecyclerView cam1_recycle_view;
    ArrayList<ListModel> list;
    public Cam1Fragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_cam1, container, false);

        list=new ArrayList<>();
        cam1_recycle_view=view.findViewById(R.id.cam1_recycle_view);

        GetCameraInfo info=new GetCameraInfo();

         int cameraCount = 1;
        int count = cameraCount;

            try {
                Camera camera = Camera.open(1);
                Parameters params = camera.getParameters();
                Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
                Camera.getCameraInfo(1, cameraInfo);
                camera.release();
            } catch (Exception e) {
                e.printStackTrace();
            }




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            list.add(new ListModel("AUTOFOCUS", info.hasAutoFocus(view.getContext())));
            list.add(new ListModel("FLASH", info.hasFlash(view.getContext())));
            list.add(new ListModel("EXTERNAL CAMERA SUPPORT", info.supportsExternalCamera(view.getContext())));
            list.add(new ListModel("AR SUPPORT", info.supportsAR(view.getContext())));
            list.add(new ListModel("MANUAL POST PROCESSING", info.hasManualPostProcessing(view.getContext())));
            list.add(new ListModel("MANUAL SENSOR", info.hasManualSensor(view.getContext())));
            list.add(new ListModel("CAPABILITY ROW", info.hasCapabilityRaw(view.getContext())));
            list.add(new ListModel("FULL HW CAPABILITY LEVEL", info.hasFulHWCapabilityLevel(view.getContext())));

        }
        cam1_recycle_view.setAdapter(new CamRVAdaptor(view.getContext(),list));



        return  view;
    }
    }