package com.example.mobileinfo;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GetCameraInfo {


    public String hasAutoFocus(Context context) {
        String r;
        if (checkCameraFeature(context, PackageManager.FEATURE_CAMERA_AUTOFOCUS)){
            r="YES";
        }else {
            r = "NO";
        }
        return r ;
    }

    public String hasFlash(Context context) {
        String r;
        if ( checkCameraFeature(context, PackageManager.FEATURE_CAMERA_FLASH)){
            r="YES";
        }else {
            r = "NO";
        }
        return r ;
    }

    public String hasFrontFacingCamera(Context context) {
        String r;
        if ( checkCameraFeature(context, PackageManager.FEATURE_CAMERA_FRONT)){
            r="YES";
        }else {
            r = "NO";
        }
        return r ;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    public String supportsExternalCamera(Context context) {
        String r;
        if ( checkCameraFeature(context, PackageManager.FEATURE_CAMERA_EXTERNAL)){
            r="YES";
        }else {
            r = "NO";
        }
        return r ;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public String supportsAR(Context context) {
        String r;
        if ( checkCameraFeature(context, PackageManager.FEATURE_CAMERA_AR)){
            r="YES";
        }else {
            r = "NO";
        }
        return r ;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public String hasManualPostProcessing(Context context) {
        String r;
        if ( checkCameraFeature(context, PackageManager.FEATURE_CAMERA_CAPABILITY_MANUAL_POST_PROCESSING)){
            r="YES";
        }else {
            r = "NO";
        }
        return r ;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public String hasManualSensor(Context context) {
        String r;
        if ( checkCameraFeature(context, PackageManager.FEATURE_CAMERA_CAPABILITY_MANUAL_SENSOR)){
            r="YES";
        }else {
            r = "NO";
        }
        return r ;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public String hasCapabilityRaw(Context context) {
        String r;
        if ( checkCameraFeature(context, PackageManager.FEATURE_CAMERA_CAPABILITY_RAW)){
            r="YES";
        }else {
            r = "NO";
        }
        return r ;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public String hasFulHWCapabilityLevel(Context context) {
        String r;
        if ( checkCameraFeature(context, PackageManager.FEATURE_CAMERA_LEVEL_FULL)){
            r="YES";
        }else {
            r = "NO";
        }
        return r ;
    }

    public static boolean checkCameraFeature(Context context, String feature) {
        if (context.getPackageManager().hasSystemFeature(feature))
            return true;
        else
            return false;
    }


    public List<ListModel> getCameraSpecParams(Context context, Camera.Parameters parameters, Camera.CameraInfo cameraInfo) {

        List<ListModel> list = new ArrayList<>();

        int camOrientation = cameraInfo.orientation;

        float vertAngle = parameters.getVerticalViewAngle();
        float horizontalAngle = parameters.getHorizontalViewAngle();
        float focalLen = parameters.getFocalLength();
        float step = parameters.getExposureCompensationStep();
        int min = Math.round(step * parameters.getMinExposureCompensation());
        int max = Math.round(step * parameters.getMaxExposureCompensation());
        int jpegQ = parameters.getJpegQuality();
        int faces = parameters.getMaxNumDetectedFaces();
        String sMinMaxEv, sSmoothZoom, maxZoomRatio;

        String position = context.getString(R.string.camera_rear_facing);
        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            position = context.getString(R.string.camera_front_facing);
        }

        list.add(new ListModel(context.getString(R.string.camera_position), position));
        list.add(new ListModel(context.getString(R.string.camera_vertical_view_angle), String.format(Locale.ENGLISH, "%.02f", vertAngle), "°"));
        list.add(new ListModel(context.getString(R.string.camera_horizontal_view_angle), String.format(Locale.ENGLISH, "%.02f", horizontalAngle), "°"));
        list.add(new ListModel(context.getString(R.string.camera_focal_length), String.format(Locale.ENGLISH, "%.02f", focalLen), "mm"));
        if (min != 0 || max != 0) {
            sMinMaxEv = min + "/" + max;
        } else {
            sMinMaxEv = context.getResources().getString(R.string.not_available_info);
        }
        list.add(new ListModel(context.getString(R.string.camera_ev_min_max), String.format(Locale.ENGLISH, "%s", sMinMaxEv)));

        if (parameters.isZoomSupported()) {
            maxZoomRatio = getMaxZoomRatio(parameters);
            if (parameters.isSmoothZoomSupported())
                sSmoothZoom = context.getResources().getString(R.string.yes_string);
            else
                sSmoothZoom = context.getResources().getString(R.string.no_string);
        } else {
            maxZoomRatio = context.getResources().getString(R.string.no_string);
            sSmoothZoom = context.getResources().getString(R.string.no_string);
        }
        list.add(new ListModel(context.getString(R.string.camera_max_zoom), maxZoomRatio, "x"));
        list.add(new ListModel(context.getString(R.string.camera_smooth_zoom), sSmoothZoom));
        list.add(new ListModel(context.getString(R.string.camera_orientation), String.valueOf(camOrientation), "°"));
        list.add(new ListModel(context.getString(R.string.camera_face_detection), (faces != 0) ?
                String.valueOf(faces) : context.getResources().getString(R.string.not_supported), "max"));

        list.add(new ListModel(context.getString(R.string.camera_focus_area), String.valueOf(parameters.getMaxNumFocusAreas()), "max"));

        list.add(new ListModel(context.getString(R.string.camera_video_snapshot), parameters.isVideoSnapshotSupported()
                ? ThreeState.YES : ThreeState.NO, 2));
        list.add(new ListModel(context.getString(R.string.camera_video_stabilization), parameters.isVideoStabilizationSupported()
                ? ThreeState.YES : ThreeState.NO, 2));
        list.add(new ListModel(context.getString(R.string.camera_auto_exposure), parameters.isAutoExposureLockSupported()
                ? ThreeState.YES : ThreeState.NO, 2));
        list.add(new ListModel("Auto-White Balance Locking Support", parameters.isAutoWhiteBalanceLockSupported()
                ? ThreeState.YES : ThreeState.NO, 2));
        list.add(new ListModel("JPEG Quality", String.valueOf(jpegQ), "%"));

        return list;
    }


    public static String getMaxZoomRatio(Camera.Parameters parameters) {
        List<Integer> zoomRatList = parameters.getZoomRatios();
        int zoom = zoomRatList.get(zoomRatList.size() - 1);
        return String.format(Locale.ENGLISH, "%.1f", zoom / 100.0);
    }

}
