package com.example.commonlibrary.netty;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;


import com.example.commonlibrary.interf.NettyConnectListener;
import com.example.commonlibrary.interf.NettyMsgListener;
import com.example.commonlibrary.units.ToastUtils;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelHandlerContext;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


public abstract class BaseObuService extends Service implements NettyMsgListener, NettyConnectListener {
    String TAG = "BaseObuService";
    protected static MyNettyThread myNettyThread;

    //是否断线自动重连
    protected static boolean isAutoConnect;
    //是已否连接
    private boolean isConnnected;

    private Disposable disposable;

    protected String serverIp;
    protected int serverPoint;
    protected static ChannelHandlerContext channelHandlerContext;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: " + startId);
        if (intent == null) return super.onStartCommand(intent, flags, startId);
        if (startId == 1) setForegroundService();
        serverIp = intent.getStringExtra("serverIp");
        serverPoint = intent.getIntExtra("serverPort", 0);
        isAutoConnect = intent.getBooleanExtra("isAutoConnect", false);
        myNettyThread = new MyNettyThread(this, this, serverIp, serverPoint);
        myNettyThread.start();

        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 开启前台服务
     */
    private void setForegroundService() {
        Notification.Builder builder = new Notification.Builder(this.getApplicationContext());
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            //修改安卓8.1以上系统报错
            NotificationChannel notificationChannel = new NotificationChannel("110", "OBU", NotificationManager.IMPORTANCE_MIN);
            notificationChannel.enableLights(false);//如果使用中的设备支持通知灯，则说明此通知通道是否应显示灯
            notificationChannel.setShowBadge(false);//是否显示角标
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(notificationChannel);
            builder.setChannelId("110");
        }

        Notification notification = builder.build(); // 获取构建好的Notification
        notification.defaults = Notification.DEFAULT_SOUND; //设置为默认的声音
        // 参数一：唯一的通知标识；参数二：通知消息。
        startForeground(1, notification);// 开始前台服务
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return null;
    }

    /**
     * 连接成功回调
     */
    @Override
    public void connectSuccess() {
        Log.d(TAG, "connectSuccess: ");
        isConnnected = true;
        onConnect(true, new Throwable());
    }

    /**
     * 连接失败回调
     *
     * @param cause 错误信息
     */
    @Override
    public void connectError(Throwable cause) {
        onConnect(false, cause);
        Log.d(TAG, "是否断线重连:" + isAutoConnect);
        isConnnected = false;
        if (isAutoConnect) {
            reConnect();
        }
    }

    /**
     * 处理服务端返回的消息
     *
     * @param res 服务端返回的消息
     */
    @Override
    public void resMsg(String res) {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
        onObuMessageReceive(res);
    }

    /**
     * 连接断开
     */
    @Override
    public void disConnect() {
        Log.d(TAG, "是否断线重连:" + isAutoConnect);
        isConnnected = false;
        if (isAutoConnect) {
            reConnect();
        }

        onDisConnect();
    }

    /**
     * 断开连接
     */
    public static void disConnectOBU() {
        if (myNettyThread != null) {
            isAutoConnect = false;//主动断开连接不能自动重连
            myNettyThread.close();
            myNettyThread = null;
        }
    }

    /**
     * 连接异常
     *
     * @param cause
     */
    @Override
    public void connectException(Throwable cause) {
        onConnectException(cause);
    }

    @Override
    public void sendCarInfoSuccess() {
        onSendCarInfoSuccess();
    }

    /**
     * 接口活跃，在这里获取 ChannelHandlerContext
     *
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        this.channelHandlerContext = ctx;
    }

    @SuppressLint("CheckResult")
    private void reConnect() {
        //开启定时器操作，只执行一次
        if (disposable != null) return;
        disposable = Observable.interval(3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        if (isConnnected) return;
                        Log.d(TAG, "accept: ");
                        myNettyThread = new MyNettyThread(BaseObuService.this, BaseObuService.this, serverIp, serverPoint);
                        myNettyThread.start();
                    }
                });
    }

    protected abstract void onConnect(boolean isSuccess, Throwable cause);

    protected abstract void onObuMessageReceive(String res);

    protected abstract void onDisConnect();

    protected abstract void onConnectException(Throwable cause);

    protected abstract void onSendCarInfoSuccess();

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        if (myNettyThread != null) {
            myNettyThread.close();
            myNettyThread.setNettyConnectListener(null);
            myNettyThread.setNettyMsgListener(null);
            myNettyThread = null;
        }
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
        isAutoConnect = false;
        super.onDestroy();
    }
}
