package com.example.commonlibrary.base;

import android.content.Context;

import androidx.annotation.NonNull;


import java.util.HashMap;


import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class BasePresenter<T> {
    protected CompositeDisposable netWorkDisposable;
    protected CompositeDisposable signDisposable;
    T view;

    public T getView() {
        return view;
    }

    public void setView(T view) {
        this.view = view;
    }

    public void detttch() {  //销毁的
        if (view != null) {
            view = null;
        }
        if (netWorkDisposable != null) netWorkDisposable.dispose();
        if (signDisposable != null) signDisposable.dispose();
    }

}
