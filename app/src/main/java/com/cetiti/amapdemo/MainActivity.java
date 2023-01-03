package com.cetiti.amapdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.MapboxMap;


public class MainActivity extends AppCompatActivity {
    MapView mMapView;
    MapboxMap mMapboxMap;
    private static final double DEFAULT_LOCATION_LATITUDE = 30.77347790731735;    // 初始地图显示坐标（深圳）
    private static final double DEFAULT_LOCATION_LONGITUDE = 120.68347644410971;
    private static final double DEFAULT_ZOOM_VALUE = 17.0;   // 初始地图缩放大小

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initStateBar();
        setContentView(R.layout.activity_main);
         mMapView = findViewById(R.id.mapview);

//         mMapboxMap = mMapView.getMapboxMap();
//
        mMapboxMap.setCamera(  // 设置地图相机初始位置
                new CameraOptions.Builder()
                        .center(Point.fromLngLat(DEFAULT_LOCATION_LONGITUDE, DEFAULT_LOCATION_LATITUDE))
                        .zoom(DEFAULT_ZOOM_VALUE)
                        .build());

    }
    /**
     * 沉浸式状态栏
     */
    private void initStateBar() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

}