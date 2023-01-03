package com.example.commonlibrary.http.intercept;

import android.util.Log;

import com.example.commonlibrary.http.Exception.CustomException;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;


import static okhttp3.internal.Util.UTF_8;

public class LoggerInterceptor implements Interceptor {

    public static final String TAG = "NetWorkLogger";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        printRequestMessage(request);
        Response response = chain.proceed(request);
        printResponseMessage(response);

        //验签操作
//        if (verifySign(response)){
            return response;
//        }else{
//            return null;
//        }
    }

    /**
     * 打印请求消息
     *
     * @param request 请求的对象
     */
    private void printRequestMessage(Request request) {
        if (request == null) {
            return;
        }
        Log.d(TAG, "Url   : " + request.url().url().toString());
        Log.d(TAG, "Method: " + request.method());
        Log.d(TAG, "req:Header x-sign-data: "+request.header("x-sign-data"));
        Log.d(TAG, "req:Header x-sign-timestamp: "+request.header("x-sign-timestamp"));
        Log.d(TAG, "req:Header x-sign-type: "+request.header("x-sign-type"));
        RequestBody requestBody = request.body();
        if (requestBody == null) {
            return;
        }
        try {
            Buffer bufferedSink = new Buffer();
            requestBody.writeTo(bufferedSink);
            Charset charset = requestBody.contentType().charset();
            charset = charset == null ? Charset.forName("utf-8") : charset;
            Log.i(TAG, "Params: " + bufferedSink.readString(charset));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印返回消息
     *
     * @param response 返回的对象
     */
    private void printResponseMessage(Response response) {
        if (response == null || !response.isSuccessful()) {
            return;
        }
        Log.d(TAG, "res:Header x-sign-data: "+response.header("x-sign-data"));
        Log.d(TAG, "res:Header x-sign-timestamp: "+response.header("x-sign-timestamp"));
        Log.d(TAG, "res:Header x-sign-type: "+response.header("x-sign-type"));
        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();
        BufferedSource source = responseBody.source();
        try {
            source.request(Long.MAX_VALUE); // Buffer the entire body.
        } catch (IOException e) {
            e.printStackTrace();
        }
        Buffer buffer = source.buffer();
        Charset charset = UTF_8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset();
        }
        if (contentLength != 0) {
            String result = buffer.clone().readString(charset);
            Log.i(TAG, "Response: " + result);
        }
    }

    /**
     * 验签
     *
     * @return
     */
    private boolean verifySign(Response response) {
        if (response == null || !response.isSuccessful()) {
            return false;
        }
        //第二个参数放封装好的CA认证工具类方法
        if (response.header("x-sign-data") != null && true) {
            return true;
        }else{
            return false;
        }
    }
}
