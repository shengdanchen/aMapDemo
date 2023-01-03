package com.example.commonlibrary.units;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * author : ChenShengDan
 * date   : 2021/8/19
 * desc   :
 */
public class TimerUtils {


    private static TimerUtils instance;
    private Observable<Long> observable;

    public static TimerUtils getInstance() {
        if (instance == null) instance = new TimerUtils();
        return instance;
    }

    public TimerUtils() {
        observable = Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Long> getObservable() {
        return observable;
    }

}
