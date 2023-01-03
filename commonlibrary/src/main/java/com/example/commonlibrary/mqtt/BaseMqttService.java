package com.example.commonlibrary.mqtt;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * author : ChenShengDan
 * date   : 2021/6/18
 * desc   :
 */
public abstract class BaseMqttService extends Service implements MqttCallback, IMqttActionListener {
    private static MqttUnits mqttUnits;
    private MqttInfoEntity mqttInfoEntity;
    private String TAG = "MqttService";
    private boolean isAutoReConnect;
    private Disposable disposable;

    @Override
    public void onCreate() {
        super.onCreate();
        mqttUnits = new MqttUnits();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) return super.onStartCommand(intent, flags, startId);
        mqttInfoEntity = (MqttInfoEntity) intent.getSerializableExtra("MqttInfo");
        isAutoReConnect = intent.getBooleanExtra("isAutoReConnect", false);
        if (mqttInfoEntity != null) {
            mqttUnits.init(this, mqttInfoEntity);
            mqttUnits.connect(this);
            mqttUnits.setMessagesMqttCallback(this);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void connectionLost(Throwable cause) {
        if (isAutoReConnect) startAutoReConnect();
        onDisconnect(cause);
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        messageReceived(topic, message);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        messageComplete(token);
    }

    @Override
    public void onSuccess(IMqttToken asyncActionToken) {
         closeAutoReConnect();
        onRegistered(asyncActionToken, null, true);
    }

    @Override
    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
        onRegistered(asyncActionToken, exception, false);

    }

    /**
     * 开启重连
     */
    @SuppressLint("CheckResult")
    private void startAutoReConnect() {
        //开启定时器操作，只执行一次
        if (disposable != null) return;
        disposable = Observable.interval(5, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        mqttUnits.connect(BaseMqttService.this);
                        Log.d(TAG, "accept: ");
                    }
                });
    }

    /**
     * 关闭自动重连
     */
    private void closeAutoReConnect() {
        if (disposable == null) return;
        disposable.dispose();
        disposable = null;
    }

    /**
     * 先这么封装，后续可以拆分多个不同情况下的抽象方法
     *
     * @param topic
     * @param message
     */
    protected abstract void messageReceived(String topic, MqttMessage message);

    protected abstract void messageComplete(IMqttDeliveryToken token);

    protected abstract void onDisconnect(Throwable cause);

    protected abstract void onRegistered(IMqttToken asyncActionToken, Throwable exception, boolean isSuccess);


    /**
     * 订阅消息
     *
     * @param topic
     * @param qos
     */
    public static void subscribeTopic(String topic, Integer qos) {
        mqttUnits.subscribeTopic(topic, qos);
    }

    /**
     * 订阅消息 qos2
     *
     * @param topic
     */
    public static void subscribeTopic(String topic) {
        subscribeTopic(topic, 2);
    }

    /**
     * 推送消息
     *
     * @param message
     * @param topic
     * @param qos
     */
    public static void publish(String message, String topic, Integer qos) {
        mqttUnits.publish(message, topic, qos);
    }

    /**
     * 推送消息 qos2
     *
     * @param message
     * @param topic
     */
    public static void publish(String message, String topic) {
        mqttUnits.publish(message, topic, 2);
    }

    /**
     * 断开连接
     */
    public static void disConnnect() {
        mqttUnits.disConnnect();
    }

    @Override
    public void onDestroy() {
        closeAutoReConnect();
        super.onDestroy();
    }
}
