package com.example.commonlibrary;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.commonlibrary.base.BaseApplication;
import com.example.commonlibrary.mqtt.MqttMessageService;
import com.example.commonlibrary.units.LogUtils;
import com.example.commonlibrary.units.Utils;


/**
 * 构造方法私有化，获取得到一个单例
 * Created by ljtyzhr on 2015/4/16.
 */
public class GlobalException implements Thread.UncaughtExceptionHandler {
    public static final String TAG = GlobalException.class.getSimpleName();
    private static GlobalException myCrashHandler = null;
    private static Context mContext;

    private GlobalException(Context context) {
        mContext = context;
    }

    public static synchronized GlobalException getInstance(Context context) {
        if (myCrashHandler == null) myCrashHandler = new GlobalException(context);
        return myCrashHandler;
    }

    public void uncaughtException(Thread arg0, Throwable arg1) {
        boolean isSend = Utils.isServiceRunning(BaseApplication.getInstance(),
                "com.example.commonlibrary" + ".mqtt.MqttMessageService");
        if (isSend) MqttMessageService.publish(Log.getStackTraceString(arg1), "app/clzx/log");

//崩溃后自动重启
//        Intent i = mContext.getPackageManager().getLaunchIntentForPackage(mContext.getPackageName());
//        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        mContext.startActivity(i);

    }

}
