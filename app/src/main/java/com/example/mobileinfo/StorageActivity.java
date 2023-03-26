package com.example.mobileinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StorageActivity extends AppCompatActivity {
    RecyclerView storage_recycle_view;
    ArrayList<ListModel> list;
    public static final int TYPE_DATA = 0;
    public static final int TYPE_INTERNAL_SD = 1;
    public static final int TYPE_EXTERNAL_SD = 2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);
        storage_recycle_view=findViewById(R.id.storage_recycle_view);
        list=new ArrayList<>();

        Context context =getApplicationContext();

        list.add(new ListModel("RAM",  1));

        String flashHW = getRAMHardware();
        if (flashHW != null) {
            list.add(new ListModel(context.getString(R.string.device_ram_hw), flashHW));
        }

        ByteValue totalRAM = getTotalMemory(context);
        list.add(new ListModel(context.getString(R.string.device_total), totalRAM.getValue(), totalRAM.getUnit()));

        ByteValue available = getAvailableMemory(context);
        list.add(new ListModel(context.getString(R.string.device_available), available.getValue(), available.getUnit()));

        list.add(new ListModel(context.getString(R.string.device_low_memory),getLowMemoryStatus(context)));

        //retrieve STORAGE OPTIONS

        List<StorageSpace> listStorage = getDeviceStorage(context);

        if (listStorage.size() > 0) {
            if (listStorage.size() > 1) {
                long total = 0;
                long free = 0;
                for (StorageSpace storage : listStorage) {
                    total += storage.getTotal();
                    free += storage.getFree();
                }

                ByteValue totalStorage = byteConvertor(total);
                ByteValue availableStorage = byteConvertor(free);
                list.add(new ListModel(context.getResources().getString(R.string.device_total), totalStorage.getValue(), totalStorage.getUnit()));
                list.add(new ListModel(context.getString(R.string.device_available), availableStorage.getValue(), availableStorage.getUnit()));
                ByteValue used = byteConvertor(total - free);
                list.add(new ListModel(context.getString(R.string.device_used), used.getValue(), used.getUnit()));
            }
            for (StorageSpace s : listStorage) {
                list.add(new ListModel("Internal Storage", 1));

                ByteValue totalSD = byteConvertor(s.getTotal());
                list.add(new ListModel(context.getResources().getString(R.string.device_total), totalSD.getValue(), totalSD.getUnit()));

                ByteValue freeSD = byteConvertor(s.getFree());
                list.add(new ListModel(context.getString(R.string.device_available), freeSD.getValue(), freeSD.getUnit()));

                ByteValue usedSD = byteConvertor(s.getTotal() - s.getFree());
                list.add(new ListModel(context.getString(R.string.device_used), usedSD.getValue(), usedSD.getUnit()));
            }
        }



        storage_recycle_view.setAdapter(new CamRVAdaptor(this,list));
    }


    static class StorageSpace {
        private int type;
        private long total;
        private long free;

        public StorageSpace(int type, long total, long free) {
            this.type = type;
            this.total = total;
            this.free = free;
        }

        public int getType() {
            return type;
        }

        public long getTotal() {
            return total;
        }

        public long getFree() {
            return free;
        }
    }




    public static String getDeviceProperty(String key) throws Exception {
        String result = "";
        Class<?> systemPropClass = Class.forName("android.os.SystemProperties");

        Class<?>[] parameter = new Class[1];
        parameter[0] = String.class;
        Method getString = systemPropClass.getMethod("get", parameter);
        Object[] obParameter = new Object[1];
        obParameter[0] = key;

        Object output;
        if (getString != null) {
            output = getString.invoke(systemPropClass, obParameter);
            if (output != null) {
                result = output.toString();
            }
        }
        return result;
    }

    public static ByteValue getTotalMemory(Context context) {

        ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);

        return byteConvertor(memoryInfo.totalMem);
    }

    public static ByteValue getAvailableMemory(Context context) {

        ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);

        return byteConvertor(memoryInfo.availMem);
    }

    public static String getLowMemoryStatus(Context context) {

        ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);

        return memoryInfo.lowMemory ?
                context.getResources().getString(R.string.yes_string) : context.getResources().getString(R.string.no_string);
    }

    public static String getRAMHardware() {
        try {
            String memorychip = getDeviceProperty("ro.boot.hardware.ddr");
            String[] chipelements = memorychip.split(",");
            return String.format(Locale.ENGLISH, "%s %s - %s", chipelements[1], chipelements[2], chipelements[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    protected static List<StorageSpace> getDeviceStorage(Context cont) {

        List<StorageSpace> listStorage = new ArrayList<>();
        List<String> listPaths = new ArrayList<>();

        try {
            listStorage.add(new StorageSpace(TYPE_DATA, getTotalMemoryForPath(Environment.getDataDirectory().toString()), getFreeMemoryForPath(Environment.getDataDirectory().toString())));
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            listPaths = getSDPaths();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (!Environment.isExternalStorageEmulated()) {
                    listStorage.add(new StorageSpace(TYPE_INTERNAL_SD, getTotalMemoryForPath(Environment.getExternalStorageDirectory().getAbsolutePath()),
                            getFreeMemoryForPath(Environment.getExternalStorageDirectory().getAbsolutePath())));
                }
            } else {
                if (!Environment.getExternalStorageDirectory().getAbsoluteFile().toString().contains("emulated")) {
                    listStorage.add(new StorageSpace(TYPE_INTERNAL_SD, getTotalMemoryForPath(Environment.getExternalStorageDirectory().getAbsolutePath()),
                            getFreeMemoryForPath(Environment.getExternalStorageDirectory().getAbsolutePath())));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (listPaths != null && listPaths.size() != 0) {

            for (String path : listPaths) {
                try {
                    listStorage.add(new StorageSpace(TYPE_EXTERNAL_SD, getTotalMemoryForPath(path),
                            getFreeMemoryForPath(path)));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return listStorage;
    }

    private static List<String> getSDPaths() throws Exception {
        List<String> sdPathList = new ArrayList<>();
        File mountList = new File("/proc/mounts");
        BufferedReader reader = null;
        try {
            if (mountList.exists()) {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(mountList), "UTF-8"));
                String line;

                while ((line = reader.readLine()) != null) {
                    if (line.contains(" /storage/")) {
                        String lineSplits[] = line.split(" ");
                        String sdPath = lineSplits[1];
                        if (!sdPath.equals(Environment.getExternalStorageDirectory().getAbsolutePath()) && !sdPath.contains("emulated") && !sdPath.contains("self")) {
                            if (!sdPathList.contains(sdPath))
                                sdPathList.add(sdPath);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return sdPathList;
    }

    private static long getTotalMemoryForPath(String path) {
        long total = 0L;
        if (new File(path).isDirectory()) {
            StatFs statFs = new StatFs(path);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1) {
                total = (statFs.getBlockCountLong() * statFs.getBlockSizeLong());
            } else {
                total = ((long) statFs.getBlockCount() * (long) statFs.getBlockSize());
            }
        }
        return total;
    }

    private static long getFreeMemoryForPath(String path) {
        long free = 0L;
        if (new File(path).isDirectory()) {
            StatFs statFs = new StatFs(path);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1) {
                free = (statFs.getAvailableBlocksLong() * statFs.getBlockSizeLong());
            } else {
                free = ((long) statFs.getAvailableBlocks() * (long) statFs.getBlockSize());
            }
        }
        return free;
    }

    public static ByteValue byteConvertor(long size) {
        long Kb = 1 * 1024;
        long Mb = Kb * 1024;
        long Gb = Mb * 1024;
        long Tb = Gb * 1024;
        long Pb = Tb * 1024;
        long Eb = Pb * 1024;

        if (size < Kb) return new ByteValue(floatForm(size), "Byte");
        if (size >= Kb && size < Mb) return new ByteValue(floatForm((double) size / Kb), "KB");
        if (size >= Mb && size < Gb) return new ByteValue(floatForm((double) size / Mb), "MB");
        if (size >= Gb && size < Tb) return new ByteValue(floatForm((double) size / Gb), "GB");
        if (size >= Tb && size < Pb) return new ByteValue(floatForm((double) size / Tb), "TB");
        if (size >= Pb && size < Eb) return new ByteValue(floatForm((double) size / Pb), "PB");
        if (size >= Eb) return new ByteValue(floatForm((double) size / Eb), "EB");

        return new ByteValue("error", "");
    }

    public static class ByteValue {
        private String value;
        private String unit;

        public ByteValue(String value, String unit) {
            this.value = value;
            this.unit = unit;
        }

        public String getValue() {
            return value;
        }

        public String getUnit() {
            return unit;
        }
    }

    public static String getTypeString(Context context, int type) {
        switch (type) {
            case TYPE_DATA:
                return context.getString(R.string.storage_internal);
            case TYPE_INTERNAL_SD:
                return context.getString(R.string.built_in_SD);
            case TYPE_EXTERNAL_SD:
                return context.getString(R.string.device_ext_sdcard);
        }
        return context.getResources().getString(R.string.unknown);
    }

    private static String floatForm(double d) {
        return new DecimalFormat("#.##").format(d);
    }

}