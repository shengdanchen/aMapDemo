package com.example.commonlibrary.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.commonlibrary.GlobalException;
import com.example.commonlibrary.mqtt.MqttInfoEntity;
import com.example.commonlibrary.mqtt.MqttMessageService;
import com.example.commonlibrary.netty.NettyManager;
import com.example.commonlibrary.units.SharedPreferenceUtils;
import com.example.commonlibrary.units.Utils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * author : ChenShengDan
 * date   : 2021/6/9
 * desc   :
 */
public class BaseApplication extends Application {
    private static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        SharedPreferenceUtils.initialize(this);
//        NettyManager.init(this);
        initBToast();
        initLogger();
    }

    /**
     * 初始化mqtt，与崩溃日志一起调用
     *
     * @param clientID
     * @param host
     * @param userName
     * @param passWrod
     */
    protected void initMqtt(String clientID, String host, String userName, String passWrod, boolean isAutoReConnect) {
        if (isAppInBackground(this)) return;
        Intent intent = new Intent(this, MqttMessageService.class);
        MqttInfoEntity mqttInfoEntity = new MqttInfoEntity();
        mqttInfoEntity.setClinetId(clientID);
        mqttInfoEntity.setHost(host);
        mqttInfoEntity.setUSERNAME(userName);
        mqttInfoEntity.setPASSWORD(passWrod);
        intent.putExtra("MqttInfo", mqttInfoEntity);
        intent.putExtra("isAutoReConnect", isAutoReConnect);
        startService(intent);
        //崩溃日志上传
        GlobalException handler = GlobalException.getInstance(this);
        Thread.setDefaultUncaughtExceptionHandler(handler);
    }

    /**
     * Toast提示初始化
     */
    private void initBToast() {
        Toasty.Config.getInstance()
                .apply(); // required
    }

    /**
     * 初始化比较友好的日志工具
     */
    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(0)         // (Optional) How many method line to show. Default 2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
                .tag("")                // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {

            @Override
            public boolean isLoggable(int priority, String tag) {
                return true;
            }
        });
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    /**
     * 判断app是否在后台
     *
     * @param context
     * @return
     */
    private boolean isAppInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) { // Android5.0及以后的检测方法
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                //前台程序
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }
        return isInBackground;
    }

}
