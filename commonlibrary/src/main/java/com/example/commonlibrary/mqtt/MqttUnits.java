package com.example.commonlibrary.mqtt;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.example.commonlibrary.R;
import com.example.commonlibrary.units.SslUtil;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

/**
 * author : ChenShengDan
 * date   : 2021/6/18
 * desc   :
 */
public class MqttUnits {
    private MqttAndroidClient mqttAndroidClient;
    private Context mContext;
    private MqttConnectOptions mMqttConnectOptions;
    private String TAG = "MqttManager";


    public void init(Context context, MqttInfoEntity mqttInfoEntity) {
        if (mContext != null) return;
        mContext = context;
        mqttAndroidClient = new MqttAndroidClient(mContext, mqttInfoEntity.getHost(), mqttInfoEntity.getClinetId());
        mMqttConnectOptions = new MqttConnectOptions();
        mMqttConnectOptions.setCleanSession(true); //设置是否清除缓存
        mMqttConnectOptions.setConnectionTimeout(0); //设置超时时间，单位：秒
        mMqttConnectOptions.setKeepAliveInterval(20); //设置心跳包发送间隔，单位：秒
        mMqttConnectOptions.setUserName(mqttInfoEntity.getUSERNAME()); //设置用户名
        mMqttConnectOptions.setPassword(mqttInfoEntity.getPASSWORD().toCharArray()); //设置密码
        if (mqttInfoEntity.getHost().contains("ssl"))mMqttConnectOptions.setSocketFactory(getFactory(context));


    }

    private SocketFactory getFactory(Context context) {
//        try {
//            InputStream caCrtFile = context.getResources().openRawResource(R.raw.root_ca);
//            InputStream crtFile = context.getResources().openRawResource(R.raw.cert);
//            InputStream keyFile = context.getResources().openRawResource(R.raw.key);
//            return SslUtil.getSocketFactory(caCrtFile,
//                    crtFile, keyFile, "");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return null;
    }

    /**
     * 连接
     *
     * @param callback
     */
    public void connect(IMqttActionListener callback) {
        try {
            mqttAndroidClient.connect(mMqttConnectOptions, mContext, callback);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "连接出错");
        }
    }

    /**
     * 订阅消息
     *
     * @param topic_subscribe
     * @param qos
     */
    public void subscribeTopic(String topic_subscribe, int qos) {
        try {
            mqttAndroidClient.subscribe(topic_subscribe, qos);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 断开连接
     */
    public void disConnnect() {
        try {
            mqttAndroidClient.disconnect(); //断开连接
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发布 （模拟其他客户端发布消息）
     *
     * @param message
     * @param topic
     * @param qos
     */
    public void publish(String message, String topic, Integer qos) {
        Boolean retained = false;
        try {
            //参数分别为：主题、消息的字节数组、服务质量、是否在服务器保留断开连接后的最后一条消息
            mqttAndroidClient.publish(topic, message.getBytes(), qos.intValue(), retained.booleanValue());
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void setMessagesMqttCallback(MqttCallback mqttCallback) {
        if (mqttAndroidClient == null) return;
        mqttAndroidClient.setCallback(mqttCallback);
    }

}
