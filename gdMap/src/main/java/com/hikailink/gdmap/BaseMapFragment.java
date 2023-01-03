package com.hikailink.gdmap;



import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.databinding.BaseObservable;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CustomMapStyleOptions;
import com.amap.api.maps.model.GL3DModelOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.maps.model.TileOverlayOptions;
import com.amap.api.maps.model.TileProvider;
import com.amap.api.maps.utils.SpatialRelationUtil;
import com.amap.api.maps.utils.overlay.MovingPointOverlay;
import com.example.commonlibrary.base.BaseViewModel;
import com.hikailink.gdmap.base.BaseFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * Created by 陈胜旦 on 2022/12/7
 * mMapView3D,aMap,自定义地图样式，地图显示模式
 */
public abstract class BaseMapFragment<DB extends ViewDataBinding, VM extends BaseViewModel> extends BaseFragment<DB, VM> {
    MapView mMapView3D = null;
    protected AMap aMap = null;
    protected VM mViewModel;
    protected String TAG = getClass().getSimpleName();

    @Override
    protected void initView(View view) {
        AMapLocationClient.updatePrivacyShow(getContext(), true, true);
        AMapLocationClient.updatePrivacyAgree(getContext(), true);

        mMapView3D = getMapView();
       if (mMapView3D != null){
           initMapUI();//初始化地图
           mViewModel = getViewModel();//初始化VM
           if (mViewModel != null)initV2xObs();//订阅事件
       }

    }


    private void initMapUI() {
        //创建地图
        mMapView3D.onCreate(getSavedInstanceState());
        aMap = mMapView3D.getMap();
        //设置自定义的地图样式
        CustomMapStyleOptions customMapStyleOptions = getMapStyle();
        if (customMapStyleOptions != null)aMap.setCustomMapStyle(getMapStyle());
        //加载地图设置
        aMap.setOnMapLoadedListener(this::setMapStyle);
        //添加瓦片
        addOnlineTile();
    }

    /**
     * 地图控件实例
     * @return
     */
    protected abstract MapView getMapView();

    /**
     * 获取自定义地图样式
     * @return
     */
    protected abstract CustomMapStyleOptions getMapStyle();

    /**
     * 设置地图界面 如放大缩小按钮，初始中心点等
     */
    protected abstract void setMapStyle();

    /**
     * 添加瓦片
     */
    protected void addOnlineTile() {
        TileProvider tileProvider = getTileProvider();
        if (tileProvider != null){
            aMap.addTileOverlay(new TileOverlayOptions()
                    .tileProvider(tileProvider)
                    .zIndex(1111)
                    .memCacheSize(10485760)
                    .memoryCacheEnabled(true)
                    .diskCacheDir("/storage/amap/cache")
                    .diskCacheEnabled(true)
                    .diskCacheSize(23480));
        }
    }

    /**
     * 在线瓦片Provider
     * @return
     */
    protected abstract TileProvider getTileProvider();

    /**
     * 初始化订阅v2x地图事件
     */
    protected abstract void initV2xObs();

    /**
     * 订阅v2xViewModel
     * @return
     */
    protected abstract VM getViewModel();

//    /**
//     * 加载在线瓦片数据
//     */
//    private void useOMCMap() {
//        HeritageScopeTileProvider tileProvider = new HeritageScopeTileProvider(256,256,getContext(),"http://112.65.187.90:65009/service/map/wmts-raster?service=WMTS&version=1.0.0&request=GetTile&layer=basemap&format=image/png&style=bluenight&tilematrixset=3857&");
//        aMap.addTileOverlay(new TileOverlayOptions()
//                .tileProvider(tileProvider)
//                .zIndex(1111)
//                .memCacheSize(10485760)
//                .memoryCacheEnabled(true)
//                .diskCacheDir("/storage/amap/cache").diskCacheEnabled(true)
//                .diskCacheSize(23480));
//    }
}
