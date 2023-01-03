package com.example.commonlibrary.http.response;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * author : ChenShengDan
 * date   : 2021/7/5
 * desc   :
 */
public class RxTransformerHelper {
    @org.jetbrains.annotations.Contract(" -> new")
    @NotNull
    public static <T> ObservableTransformer<T, T> observableIO2Main() {
        return new ObservableTransformer<T, T>() {
            @NotNull
            @Override
            public ObservableSource<T> apply(@NotNull Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
