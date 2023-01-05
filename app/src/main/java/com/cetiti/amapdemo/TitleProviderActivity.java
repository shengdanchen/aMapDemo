package com.cetiti.amapdemo;

import static com.amap.api.maps.model.BitmapDescriptorFactory.getContext;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.TileOverlayOptions;
import com.amap.api.maps.model.TileProvider;
import com.amap.api.maps.model.UrlTileProvider;
import com.example.commonlibrary.units.AssetsFileUtils;

import java.net.URL;

public class TitleProviderActivity extends Activity {

    private AMap amap;
    private MapView mapview;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.urlpro_activity);

        AssetsFileUtils.getInstance(this).copyAssetsToSD("tiles_xy", "tiles_xy");
        AMapLocationClient.updatePrivacyShow(getContext(), true, true);
        AMapLocationClient.updatePrivacyAgree(getContext(), true);

        mapview = (MapView) findViewById(R.id.map);
        mapview.onCreate(savedInstanceState);// 此方法必须重写
        init();
    }

    /**
     * 初始化AMap对象
     */
    private void init() {
        if (amap == null) {
            amap = mapview.getMap();
            addOnlineTile();
        }
    }

    protected TileProvider getTileProvider() {
        final String url = "file://" + getContext().getExternalFilesDir("tiles_xy") + "/%d/R12/C25/%d_%d_%d.png";

        return new TileOverlayOptions().tileProvider(new UrlTileProvider(256, 256) {

            @Override
            public URL getTileUrl(int x, int y, int zoom) {
                try {
                    URL url1 = new URL(String.format(url, zoom, zoom, x, y));

                    return url1;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }).getTileProvider();
//        return null; //返回为null时不会出现卡顿情况
    }


    /**
     * 添加瓦片
     */
    protected void addOnlineTile() {
        TileProvider tileProvider = getTileProvider();
        if (tileProvider != null) {
            amap.addTileOverlay(new TileOverlayOptions()
                    .tileProvider(getTileProvider())
                    .zIndex(1111)//1111
                    .memCacheSize(10485760)
                    .memoryCacheEnabled(true)
                    .diskCacheDir("/storage/amap/cache").diskCacheEnabled(true)
                    .diskCacheSize(23480));
        }
        CameraPosition cameraPosition = new CameraPosition(new LatLng(32.157892, 112.205518), 20, 90, 0);
        amap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    /**
     * 方法必须重写
     */
    protected void onResume() {
        super.onResume();
        mapview.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapview.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapview.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapview.onDestroy();
    }
}
