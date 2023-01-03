package com.example.commonlibrary.units;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import androidx.annotation.RequiresApi;
import com.example.commonlibrary.base.BaseApplication;

/**
 * 网络连接状态工具类
 */
public class NetworkInfoUtils {
    private static final String TAG = NetworkInfoUtils.class.getSimpleName();


    private NetworkInfoUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }


    /**
     * 判断是否有网络连接
     */
    public static boolean isNetworkConnected() {
        // 获取手机所有连接管理对象(包括对wi-fi,net等连接的管理)
        ConnectivityManager manager = (ConnectivityManager) BaseApplication.getInstance()
                                                                           .getApplicationContext()
                                                                           .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager != null) {
            // 获取NetworkInfo对象
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            //判断NetworkInfo对象是否为空
            if (networkInfo != null) {
                return networkInfo.isAvailable();
            }
        }
        return false;
    }


    /**
     * 判断WIFI网络是否可用
     */
    public static boolean isMobileConnected() {
        //获取手机所有连接管理对象(包括对wi-fi,net等连接的管理)
        ConnectivityManager manager = (ConnectivityManager) BaseApplication.getInstance()
                                                                           .getApplicationContext()
                                                                           .getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取NetworkInfo对象
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        //判断NetworkInfo对象是否为空 并且类型是否为MOBILE
        if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return networkInfo.isAvailable();
        }

        return false;
    }


    public enum NETWORK_TYPE {
        NONE("UNKNOW"), WIFI("WIFI"), G2("2G"), G3("3G"), G4("4G");

        private final String name;


        NETWORK_TYPE(String name) {
            this.name = name;
        }


        public String getName() {
            return name;
        }
    }


    /**
     * 获取当前的网络状态: WIFI网络1 2G网络-2 3G网络-3 4G网络-4 没有网络或unknown-5
     * 自定义
     */
    public static NETWORK_TYPE getAPNType() {
        //结果返回值
        NETWORK_TYPE netType = NETWORK_TYPE.NONE;
        //获取手机所有连接管理对象
        ConnectivityManager manager = (ConnectivityManager) BaseApplication.getInstance()
                                                                           .getApplicationContext()
                                                                           .getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取NetworkInfo对象
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        //NetworkInfo对象为空 则代表没有网络
        if (networkInfo == null) {
            return netType;
        }
        //否则 NetworkInfo对象不为空 则获取该networkInfo的类型
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_WIFI) {
            //WIFI
            netType = NETWORK_TYPE.WIFI;
        } else if (nType == ConnectivityManager.TYPE_MOBILE) {
            int nSubType = networkInfo.getSubtype();
            TelephonyManager telephonyManager = (TelephonyManager) BaseApplication.getInstance()
                                                                                  .getApplicationContext()
                                                                                  .getSystemService(Context.TELEPHONY_SERVICE);
            //3G   联通的3G为UMTS或HSDPA 电信的3G为EVDO
            if (nSubType == TelephonyManager.NETWORK_TYPE_LTE &&
                    !telephonyManager.isNetworkRoaming()) {
                netType = NETWORK_TYPE.G4;
            } else if (nSubType == TelephonyManager.NETWORK_TYPE_UMTS ||
                    nSubType == TelephonyManager.NETWORK_TYPE_HSDPA ||
                    nSubType == TelephonyManager.NETWORK_TYPE_EVDO_0 &&
                            !telephonyManager.isNetworkRoaming()) {
                netType = NETWORK_TYPE.G3;
                //2G 移动和联通的2G为GPRS或EGDE，电信的2G为CDMA
            }
            //            else if (nSubType == TelephonyManager.NETWORK_TYPE_GPRS
            //                    || nSubType == TelephonyManager.NETWORK_TYPE_EDGE
            //                    || nSubType == TelephonyManager.NETWORK_TYPE_CDMA
            //                    && !telephonyManager.isNetworkRoaming()) {
            //                netType = 2;
            //            }
            else {
                netType = NETWORK_TYPE.G2;
            }
        }
        return netType;
    }

    //检测当前的网络状态


    //API版本23以下时调用此方法进行检测
    //因为API23后getNetworkInfo(int networkType)方法被弃用
    public void checkState_23() {
        //步骤1：通过Context.getSystemService(Context.CONNECTIVITY_SERVICE)获得ConnectivityManager对象
        ConnectivityManager connMgr = (ConnectivityManager) BaseApplication.getInstance()
                                                                           .getApplicationContext()
                                                                           .getSystemService(Context.CONNECTIVITY_SERVICE);

        //步骤2：获取ConnectivityManager对象对应的NetworkInfo对象
        //NetworkInfo对象包含网络连接的所有信息
        //步骤3：根据需要取出网络连接信息
        //获取WIFI连接的信息
        NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        Boolean isWifiConn = networkInfo.isConnected();

        //获取移动数据连接的信息
        networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        Boolean isMobileConn = networkInfo.isConnected();

        LogUtils.debug(TAG, "Wifi是否连接:" + isWifiConn + " 移动数据是否连接:" + isMobileConn);
    }


    // HUZHU 23及以上时调用此方法进行网络的检测
    // getAllNetworks() 在API 21后开始使用
    //步骤非常类似
    //@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void checkState_23orNew() {
        //获得ConnectivityManager对象
        ConnectivityManager connMgr = (ConnectivityManager) BaseApplication.getInstance()
                                                                           .getApplicationContext()
                                                                           .getSystemService(Context.CONNECTIVITY_SERVICE);

        //获取所有网络连接的信息
        Network[] networks = connMgr.getAllNetworks();
        //用于存放网络连接信息
        StringBuilder sb = new StringBuilder();
        //通过循环将网络信息逐个取出来
        for (int i = 0; i < networks.length; i++) {
            //获取ConnectivityManager对象对应的NetworkInfo对象
            NetworkInfo networkInfo = connMgr.getNetworkInfo(networks[i]);
            sb.append(networkInfo.getTypeName() + " connect is " + networkInfo.isConnected());
        }
    }
}
