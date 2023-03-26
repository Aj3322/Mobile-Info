package com.example.mobileinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class CPUActivity extends AppCompatActivity {
    RecyclerView cpu_recycle_view;
    ArrayList<ListModel> list;
    String maxFrequency = null;
    String currentFrequency = null;
    float temp = 0;
    File fin1 ,fin2,fin3;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpuactivity);

        list = new ArrayList<>();

        String.format(Locale.ENGLISH, "%.2f", (readUsage() * 100));
        String processor = null;
        String chipset = null;
        String features = null;
        String architecture = null;
        String variant = null;
        String revision = null;
        String implementer = null;

        if (currentFrequency != null) {
            list.add(new ListModel("Current Frequency", currentFrequency, "GHz"));
        }
        if (maxFrequency != null) {
            list.add(new ListModel("Max Frequency", maxFrequency, "GHz"));
        }

        //use to get current directory
         fin1 = new File("/proc/cpuinfo");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(fin1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (fis == null || !fin1.exists() || !fin1.canRead()) {
            Log.e("CPUInfo", "Cannot access CPUINFO.");
        }
        //Construct BufferedReader from InputStreamReader
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String line = null;
        try {
            while ((line = br.readLine()) != null) {

                if (line.startsWith("Processor\t:")) {
                    int i = line.indexOf(":");
                    processor = line.substring(i + 1).trim();
                } else if (line.startsWith("Features\t:")) {
                    int i = line.indexOf(":");
                    features = line.substring(i + 1).trim();
                } else if (line.startsWith("Hardware\t:")) {
                    int i = line.indexOf(":");
                    chipset = line.substring(i + 1).trim();
                } else if (line.startsWith("CPU architecture:")) {
                    int i = line.indexOf(":");
                    architecture = line.substring(i + 1).trim();
                } else if (line.startsWith("CPU variant\t:")) {
                    int i = line.indexOf(":");
                    variant = line.substring(i + 1).trim();
                } else if (line.startsWith("CPU revision\t:")) {
                    int i = line.indexOf(":");
                    revision = line.substring(i + 1).trim();
                } else if (line.startsWith("CPU implementer\t:")) {
                    int i = line.indexOf(":");
                    implementer = line.substring(i + 1).trim();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (processor != null) {
            list.add(new ListModel("Processor", processor.toUpperCase(Locale.ROOT)));
        }
        if (chipset != null) {
            list.add(new ListModel("Chipset", chipset.toUpperCase(Locale.ROOT)));
        } else {
            if (chipset.length() == 0) {
                chipset = Build.HARDWARE;
            }
            list.add(new ListModel("Chipset", chipset.toUpperCase(Locale.ROOT)));
        }
        if (features != null) {
            list.add(new ListModel("Features", features.toUpperCase(Locale.ROOT)));
        }
        if (features != null) {
            list.add(new ListModel("Revision", revision));
        }
        if (features != null) {
            list.add(new ListModel("Variant", variant));
        }
        if (features != null) {
            list.add(new ListModel("Architecture", architecture));
        }
        if (features != null) {
            list.add(new ListModel("Implementer", implementer));
        }


        //read max freq CPU
        fin2 = new File("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq");

        if (fin2.exists()) {
            try {
                fis = new FileInputStream(fin2);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            if (fis != null) {
                //Construct BufferedReader from InputStreamReader
                br = new BufferedReader(new InputStreamReader(fis));

                try {
                    while ((line = br.readLine()) != null) {
                        temp = Float.parseFloat(line) / 1000000;
                        maxFrequency = "" + temp;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            /// read the current CPU freq

            //use to get current directory
            fin3 = new File("/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq");
            if (fin3.exists()) {
                fis = null;
                try {
                    fis = new FileInputStream(fin3);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                if (fis != null) {
                    //Construct BufferedReader from InputStreamReader
                    br = new BufferedReader(new InputStreamReader(fis));
                    try {
                        while ((line = br.readLine()) != null) {
                            temp = Float.parseFloat(line) / 1000000;
                            currentFrequency = "" + String.format("%.3f", temp);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        list.add(new ListModel("Available Cores", String.valueOf(Runtime.getRuntime().availableProcessors())));
        list.add(new ListModel("Active Cores", String.valueOf(getNumCores())));

        cpu_recycle_view=findViewById(R.id.cpu_recycle_view);
        cpu_recycle_view.setAdapter(new CamRVAdaptor(this,list));
    }

    private int getNumCores () {

        //Private Class to display only CPU devices in the directory listing

        class CpuFilter implements FileFilter {

            @Override

            public boolean accept(File pathname) {
                if (Pattern.matches("cpu[0-9]+", pathname.getName())) {
                    return true;
                }
                return false;
            }
        }

        try {
            //Get directory containing CPU info
            File dir = new File("/sys/devices/system/cpu/");
            File[] files = dir.listFiles(new CpuFilter());
            return files.length;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    private float readUsage () {
        try {
            RandomAccessFile reader = new RandomAccessFile("/proc/stat", "r");
            String load = reader.readLine();

            String[] toks = load.split(" +");  // Split on one or more spaces

            long idle1 = Long.parseLong(toks[4]);
            long cpu1 = Long.parseLong(toks[2]) + Long.parseLong(toks[3]) + Long.parseLong(toks[5])
                    + Long.parseLong(toks[6]) + Long.parseLong(toks[7]) + Long.parseLong(toks[8]);

            try {
                Thread.sleep(360);
            } catch (Exception e) {
                e.printStackTrace();
            }

            reader.seek(0);
            load = reader.readLine();
            reader.close();

            toks = load.split(" +");

            long idle2 = Long.parseLong(toks[4]);
            long cpu2 = Long.parseLong(toks[2]) + Long.parseLong(toks[3]) + Long.parseLong(toks[5])
                    + Long.parseLong(toks[6]) + Long.parseLong(toks[7]) + Long.parseLong(toks[8]);

            return (float) (cpu2 - cpu1) / ((cpu2 + idle2) - (cpu1 + idle1));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return 0;
    }
    }