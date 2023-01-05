package com.hikailink.gdmap;

import androidx.databinding.BaseObservable;
import androidx.lifecycle.AndroidViewModel;

import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CustomMapStyleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.TileOverlayOptions;
import com.amap.api.maps.model.TileProvider;
import com.amap.api.maps.model.UrlTileProvider;
import com.example.commonlibrary.base.BaseViewModel;
import com.hikailink.gdmap.databinding.FragmentGdMapBinding;

import java.net.URL;

/**
 * author : ChenShengDan
 * date   : 2022/12/14
 * desc   :
 */
public class GDMapFragment extends BaseMapFragment<FragmentGdMapBinding, BaseViewModel>{
    @Override
    protected MapView getMapView() {
        return db.mapView;
    }

    @Override
    protected CustomMapStyleOptions getMapStyle() {
        return null;
    }

    @Override
    protected void setMapStyle() {
        aMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(32.157892,112.205518)));//设置地图的放缩
        aMap.moveCamera(CameraUpdateFactory.zoomTo(20));//设置地图的放缩
        aMap.moveCamera(CameraUpdateFactory.changeTilt(90));//设置地图的放缩
    }

    @Override
    protected TileProvider getTileProvider() {
        final String url = "file://" + getContext().getExternalFilesDir("tiles_xy") + "/%d/R12/C25/%d_%d_%d.png";
//        return null;
        return new TileOverlayOptions().tileProvider(new UrlTileProvider(256, 256) {

            @Override
            public URL getTileUrl(int x, int y, int zoom) {
                try {
                    URL url1 = new URL(String.format(url, zoom,zoom, x, y));

                    return url1;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }).getTileProvider();
    }

    @Override
    protected void initV2xObs() {

    }

    @Override
    protected BaseViewModel getViewModel() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gd_map;
    }

    @Override
    protected void initData() {

    }
}
