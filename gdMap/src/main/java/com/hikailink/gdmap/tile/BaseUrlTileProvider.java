package com.hikailink.gdmap.tile;

import android.content.Context;

import com.amap.api.maps.model.UrlTileProvider;

import java.net.URL;

/**
 * author : ChenShengDan
 * date   : 2022/12/7
 * desc   :
 */
public abstract class BaseUrlTileProvider extends UrlTileProvider {

    protected String mRootUrl;
    //默认瓦片大小
//    private static int titleSize = 256;//a=6378137±2（m）
    //基本参数
    protected final double initialResolution = 156543.03392804062;//2*Math.PI*6378137/titleSize;
    protected final double originShift = 20037508.342789244;//2*Math.PI*6378137/2.0; 周长的一半

    protected final double HALF_PI = Math.PI / 2.0;
    protected final double RAD_PER_DEGREE = Math.PI / 180.0;
    protected final double HALF_RAD_PER_DEGREE = Math.PI / 360.0;
    protected final double METER_PER_DEGREE = originShift / 180.0;//一度多少米
    protected final double DEGREE_PER_METER = 180.0 / originShift;//一米多少度
    protected Context context;

    public BaseUrlTileProvider(int i, int i1,Context context,String mRootUrl) {
        super(i, i1);
        this.context = context;
        this.mRootUrl = mRootUrl;
    }

}
