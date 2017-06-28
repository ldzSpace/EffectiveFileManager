package com.ldzspace.effectivefilemanager.utils;

import android.util.Log;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * Created by lixinyu on 2017/4/13.
 */
public class CpuUtil {

    private static String[] temp_urls = new String[]{
            "/sys/devices/system/cpu/cpu0/cpufreq/cpu_temp",
            "/sys/devices/system/cpu/cpu0/cpufreq/FakeShmoo_cpu_temp",
            "/sys/devices/system/cpu/cpufreq/cput_attributes/cur_temp",
            "/sys/devices/platform/tegra_tmon/temp1_input",
            "/sys/devices/platform/tegra-i2c.3/i2c-4/4-004c/temperature",
            "/sys/devices/platform/omap/omap_temp_sensor.0/temperature",
            "/sys/devices/platform/s5p-tmu/temperature",
            "/sys/devices/platform/s5p-tmu/curr_temp",
            "/sys/devices/virtual/thermal/thermal_zone0/temp",
            "/sys/devices/virtual/thermal/thermal_zone1/temp",
            "/sys/devices/virtual/K3G_GYRO-dev/k3g/gyro_get_temp",
            "/sys/kernel/debug/tegra_thermal/temp_tj",
            "/sys/class/thermal/thermal_zone0/temp",
            "/sys/class/thermal/thermal_zone1/temp",
            "/sys/class/thermal/thermal_zone2/temp",
            "/sys/class/thermal/thermal_zone3/temp",
            "/sys/class/thermal/thermal_zone4/temp",
            "/sys/class/thermal/thermal_zone5/temp",
            "/sys/class/hwmon/hwmon0/device/temp1_input",
            "/sys/class/i2c-adapter/i2c-4/4-004c/temperature",
    };

    /**
     *
     * @param tempTypeFlag 0:摄氏，1:华氏
     * @return
     */
    public static int getCpuTemp(int tempTypeFlag) {
        // Iterate through String Array
        // returns 0 if none found
        // default temperature value retrieved in Celsius
        boolean foundAlready = false;
        int temp = 0, temp2 = 0;
        String x;

        for (int i = 0; i < temp_urls.length; i++) {
            try {
                RandomAccessFile reader = new RandomAccessFile(temp_urls[i], "r");
                x = reader.readLine();
                int temp_value = Integer.parseInt(x);
                if (temp_value < 0) {
                    continue;
                }
                if (foundAlready) {
                    // if temp value already found
                    // take new one and average it with existing
                    temp2 = temp_value;
                    if (temp2 > 100) {
                        temp2 = (temp2 / 1000);
                    }
                    temp = (temp + temp2) / 2;
                    Log.d("EXCEPTION: ", "value " + temp2 + " found at p" + i);
//                    temp2 = 0;
                } else {
                    // if temp value hasn't been found yet, but found one!
                    temp = temp_value;
                    if (temp > 100) {
                        temp = (temp / 1000);
                    }
                    foundAlready = true;
                }
                reader.close();
            } catch (IOException e) {
                Log.d("EXCEPTION: ", "file not at p" + i);
            }
        }
        // this converts into Fahrenheit
        if (tempTypeFlag == 1) {
            temp = (int) (temp * 1.8) + 32;
        }
        // just return the celsius value
        return temp;
    }
}
