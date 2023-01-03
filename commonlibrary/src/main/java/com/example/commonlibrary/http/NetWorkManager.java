package com.example.commonlibrary.http;


import com.example.commonlibrary.http.intercept.LoggerInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Zaifeng on 2018/2/28.
 * API初始化类
 */

public class NetWorkManager {

    private static NetWorkManager mInstance;
    private static Retrofit retrofit;

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public static NetWorkManager getInstance() {
        if (mInstance == null) {
            synchronized (NetWorkManager.class) {
                if (mInstance == null) {
                    mInstance = new NetWorkManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化必要对象和参数
     *
     * @param baseUrl    域名
     */
    public void init(String baseUrl) {
        // 初始化Retrofit
        retrofit = new Retrofit.Builder()
                .client(getOkHttpClient())
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private OkHttpClient getOkHttpClient() {
//        OkHttpClient client;
//        client = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor())
//                .connectTimeout(30, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .writeTimeout(30, TimeUnit.SECONDS)
//                .build();

        return new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor())
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
    }
}
