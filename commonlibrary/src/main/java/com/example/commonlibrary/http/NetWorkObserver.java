package com.example.commonlibrary.http;

import android.accounts.NetworkErrorException;
import android.util.Log;


import com.example.commonlibrary.http.model.ResEntity;
import com.example.commonlibrary.units.NetworkInfoUtils;

import org.jetbrains.annotations.NotNull;

import java.net.ConnectException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static com.example.commonlibrary.http.ServerCode.SERVIER_SUCCED_STATUS;


/**
 * author : ChenShengDan
 * date   : 2021/7/5
 * desc   :
 */
public abstract class NetWorkObserver<T> implements Observer<ResEntity<T>> {
    @Override
    public void onSubscribe(@NotNull Disposable d) {
        onRequestStart(d);
    }


    @Override
    public void onNext(@NotNull ResEntity<T> tResEntity) {
        if (tResEntity.code == SERVIER_SUCCED_STATUS) {
            try {
                onSuccess(tResEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                onErrorCode(tResEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onError(@NotNull Throwable e) {
        try {
            if (NetworkInfoUtils.isNetworkConnected()) {
                onErrorConnect(e);
            } else {
                onErrorNetwork();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void onComplete() {

    }

    /**
     * 请求成功
     *
     * @param tResEntity
     */
    protected abstract void onSuccess(ResEntity<T> tResEntity);

    /**
     * 请求开始
     *
     * @param d
     */
    protected abstract void onRequestStart(Disposable d);

    /**
     * 请求错误，服务器返回不同的code
     *
     * @param tResEntity
     */
    protected abstract void onErrorCode(ResEntity<T> tResEntity);

    /**
     * 网络错误
     */
    protected abstract void onErrorNetwork();

    /**
     * 连接错误
     *
     * @param e
     */
    protected void onErrorConnect(Throwable e) {
    }
}
