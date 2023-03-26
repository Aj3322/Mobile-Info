package com.example.mobileinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DisplayActivity extends AppCompatActivity {
    RecyclerView display_recycle_view;
    ArrayList<ListModel> list;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);


        list = new ArrayList<>();

        display_recycle_view=findViewById(R.id.display_recycle_view);



        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);


        Point size = new Point();

        display.getRealSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;
        double widthInch = (double) screenWidth / (double) metrics.xdpi;
        double heightInch = (double) screenHeight / (double) metrics.ydpi;
        double widthDP = Math.pow(widthInch, 2);
        double heightDP = Math.pow(heightInch, 2);
        double diagonal = Math.sqrt(widthDP + heightDP);

        list.add(new ListModel("Display Resulution", String.format(Locale.ENGLISH, "%dx%d", screenWidth, screenHeight), "px"));
        list.add(new ListModel("Display Dimension", String.format(Locale.ENGLISH, "%.2fx%.2f", widthInch, heightInch), "in"));
        list.add(new ListModel("Display Dimension" + " [dp]", String.format(Locale.ENGLISH, "%.2fx%.2f",
                (160.0 * (float) screenWidth / metrics.densityDpi), (160.0 * (float) screenHeight / metrics.densityDpi)), "dp"));
        list.add(new ListModel("Display Diagonal", String.format(Locale.ENGLISH, "%.2f", diagonal), "in"));


        list.add(getDensity(metrics));
        list.add(getScaleFactor(metrics));
        list.add(getRefreshRate(display));
        list.add(getXYDpi(metrics));
        list.add(getOrientation(display));
        list.add(getLayoutSize(this));
        list.add(getType(display));
        list.add(getDrawType(metrics));


        display_recycle_view.setAdapter(new CamRVAdaptor(this,list));
    }


        static ListModel getDensity(DisplayMetrics metrics) {
            return new ListModel("Density", String.format(Locale.ENGLISH, "%d", metrics.densityDpi), "dpi");
        }

        static ListModel getScaleFactor(DisplayMetrics metrics) {
            return new ListModel("Scale Factor", String.format(Locale.ENGLISH, "%.1f", metrics.density));
        }

        static ListModel getRefreshRate(Display display) {
            return new ListModel("Refresh Rate", String.format(Locale.ENGLISH, "%.1f", display.getRefreshRate()), "fps");
        }


        static ListModel getXYDpi(DisplayMetrics metrics) {
            return new ListModel("X/Y DPI", String.format(Locale.ENGLISH, "%.2fx%.2f", metrics.xdpi, metrics.ydpi));
        }

        static ListModel getOrientation(Display display) {
            return new ListModel("Orientation", String.format(Locale.ENGLISH, "%s",getOrient(display.getRotation())), "°");
        }

        static ListModel getLayoutSize(Context context) {
            return new ListModel("Size", String.format(Locale.ENGLISH, "%s", getLayoutQualifier(context)));
        }

        static ListModel getType(Display display) {
            return new ListModel("Type", String.format(Locale.ENGLISH, "%s", display.getName()));
        }

        static ListModel getDrawType(DisplayMetrics metrics) {
            return new ListModel("Draw Size", String.format(Locale.ENGLISH, "%s", densityQualifier(metrics.densityDpi)));
        }


        private static String getLayoutQualifier(Context context) {
            Configuration config = context.getResources().getConfiguration();

            switch (config.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) {
                case Configuration.SCREENLAYOUT_SIZE_SMALL:
                    return "Small";
                case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                    return "Normal";
                case Configuration.SCREENLAYOUT_SIZE_LARGE:
                    return "Large";
                case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                    return "X-Large";
                default:
                    return "Unknown";
            }
        }

        private static String densityQualifier(int densityDPI) {

            switch (densityDPI) {
                case DisplayMetrics.DENSITY_LOW:
                    return "LDPI";
                case DisplayMetrics.DENSITY_MEDIUM:
                    return "MDPI";
                case DisplayMetrics.DENSITY_HIGH:
                    return "HDPI";
                case DisplayMetrics.DENSITY_XHIGH:
                    return "XHDPI";
                case DisplayMetrics.DENSITY_340:
                    return "340DPI XHDPI - XXHDPI";
                case DisplayMetrics.DENSITY_360:
                    return "360DPI XHDPI - XXHDPI";
                case DisplayMetrics.DENSITY_400:
                    return "400DPI XHDPI - XXHDPI";
                case DisplayMetrics.DENSITY_420:
                    return "420DPI XHDPI - XXHDPI";
                case DisplayMetrics.DENSITY_440:
                    return "440DPI XHDPI - XXHDPI";
                case DisplayMetrics.DENSITY_XXHIGH:
                    return "XXHDPI";
                case DisplayMetrics.DENSITY_560:
                    return "440DPI XXHDPI - XXXHDPI";
                case DisplayMetrics.DENSITY_XXXHIGH:
                    return "XXXHDPI";
                case DisplayMetrics.DENSITY_TV:
                    return "TVDPI";
                case DisplayMetrics.DENSITY_260:
                    return "260DPI HDPI - XHDPI";
                case DisplayMetrics.DENSITY_280:
                    return "280DPI HDPI - XHDPI";
                case DisplayMetrics.DENSITY_300:
                    return "300DPI HDPI - XHDPI";
                default:
                    return "Unknown";
            }
        }

        private static String getOrient(int i) {

            switch (i) {
                case 0:
                    return "0";
                case 1:
                    return "90";
                case 2:
                    return "180";
                case 3:
                    return "270";
            }
            return "Not Available";
        }
}