package com.example.commonlibrary.mqtt;

import android.util.Log;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * author : ChenShengDan
 * date   : 2021/6/21
 * desc   :
 */
public class MqttMessageService extends BaseMqttService {
    private String TAG = "MqttMessageService";

    @Override
    protected void messageReceived(String topic, MqttMessage message) {
        Log.d(TAG, "messageReceived: " + new String(message.getPayload()));
    }

    @Override
    protected void messageComplete(IMqttDeliveryToken token) {

    }

    @Override
    protected void onDisconnect(Throwable cause) {
        Log.d(TAG, "onDisconnect: " + Log.getStackTraceString(cause));
    }

    @Override
    protected void onRegistered(IMqttToken asyncActionToken, Throwable exception, boolean isSuccess) {
        Log.d(TAG, "onRegistered: " + isSuccess);
        if (isSuccess) {
            subscribeTopic("app/clzx/log");
        }else{
            Log.e(TAG, "onRegistered: ", exception);
        }
    }

}
