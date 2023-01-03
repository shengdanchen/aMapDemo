package com.hikailink.gdmap.utils;

import android.content.Context;

import com.amap.api.maps.model.LatLng;
import com.example.commonlibrary.units.LngLonUtil;

import java.io.IOException;
import java.io.InputStream;

/**
 * author : ChenShengDan
 * date   : 2022/12/15
 * desc   : 高德地图工具类
 */
public class GdMapUtils {

    /**
     * 获取assets中的自定义地图样式文件
     * @param context
     * @param fileName
     * @return
     */
    public static byte[] getAssetsStyle(Context context, String fileName) {
        byte[] buffer1 = null;
        InputStream is1 = null;
        try {
            is1 = context.getResources().getAssets().open(fileName);
            int lenght1 = is1.available();
            buffer1 = new byte[lenght1];
            is1.read(buffer1);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is1 != null) {
                    is1.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return buffer1;
    }

    /**
     * wgs84转GC
     * @param lat
     * @param lon
     * @return
     */
    public static LatLng gps2Gaode(double lat, double lon) {
        double[] latAndLon = LngLonUtil.gps84_To_Gcj02(lat, lon);
        lat = latAndLon[0];
        lon = latAndLon[1];
        LatLng latLng = new LatLng(lat, lon);
        return latLng;
    }
}
