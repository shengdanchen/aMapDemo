package com.example.commonlibrary.netty;

import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;


import com.example.commonlibrary.interf.ServiceMsgListener;
import com.example.commonlibrary.units.SharedPreferenceUtils;
import com.example.commonlibrary.units.Utils;

import java.util.List;

import static android.content.Context.BIND_AUTO_CREATE;
import static com.example.commonlibrary.netty.NettyConstant.CONNECT;
import static com.example.commonlibrary.netty.NettyConstant.SET_AUTOCONNECT;


/**
 * author : ChenShengDan
 * date   : 2021/6/1
 * desc   :
 */
public class NettyManager {
//    private static Application mContext;
//    private static NettyManager instance;
//    private NettyService.NettyBinder mBinder;
//    private ServiceMsgListener serviceMsgListener;
//    private boolean isAutoConnect = false;
//
//    //    private boolean isBind = false;
//    public static void init(Application context) {
//        if (mContext != null) return;
//        mContext = context;
//        instance = new NettyManager();
//    }
//
//    public static NettyManager getInstance() {
//        return instance;
//    }
//
//    /**
//     * 开启netty服务，并监听
//     *
//     * @param serverIp
//     * @param serverPoint
//     * @param serviceMsgListener
//     */
//    public void startNetty(String serverIp, int serverPoint, ServiceMsgListener serviceMsgListener) {
//        if (Utils.isServiceRunning(mContext, mContext.getPackageName() + ".netty.NettyService")) {
//            Intent intent = new Intent(mContext, NettyService.class);
//            mContext.stopService(intent);
//            clearNettyResListener();
//        }
//
//        this.serviceMsgListener = serviceMsgListener;
//        Intent intent = new Intent(mContext, NettyService.class);
//        intent.putExtra("serverIp",serverIp);
//        intent.putExtra("serverPoint",serverPoint);
//        intent.setAction(CONNECT);
//        mContext.startService(intent);
//        mContext.bindService(intent, mConnection, BIND_AUTO_CREATE);
//        setAutoConnect(isAutoConnect);
//    }
//
//    /**
//     * 设置是否自动连接
//     *
//     * @param isAutoConnect
//     */
//    public void setAutoConnect(boolean isAutoConnect) {
//        this.isAutoConnect = isAutoConnect;
//        Intent intent = new Intent(mContext, NettyService.class);
//        intent.setAction(SET_AUTOCONNECT);
//        intent.putExtra("isAutoConnect", isAutoConnect);
//        mContext.startService(intent);
//    }
//
//    /**
//     * 销毁时记得调用，否则会造成内存泄漏
//     */
//    public void clearNettyResListener() {
//        if (mBinder != null) {
//            mBinder.setServiceMsgListener(null);
//            serviceMsgListener = null;
//            mBinder = null;
//            mContext.unbindService(mConnection);
//        }
//    }
//
//    private ServiceConnection mConnection = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            mBinder = (NettyService.NettyBinder) service;
//            mBinder.setServiceMsgListener(serviceMsgListener);
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//
//        }
//    };
}
