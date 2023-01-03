package com.hikailink.gdmap.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.example.commonlibrary.base.BaseViewModel;

/**
 * author: ChenShengDan .
 * date:   2021/4/28
 * sence:
 */
public abstract class BaseFragment<DB extends ViewDataBinding, VM extends BaseViewModel>  extends Fragment {
    protected Bundle savedInstanceState;
    protected DB db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutId = getLayoutId();
        if (layoutId != 0) {
            this.savedInstanceState = savedInstanceState;
            db = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
            View view = db.getRoot();

            getDataFromArgument();
            initView(view);
            initEvent();
            initData();
            return view;
        }
        return null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    protected abstract int getLayoutId();

    protected void getDataFromArgument() {
    }

    protected abstract void initView(View view);

    protected void initEvent() {

    }

    protected abstract void initData();

    public Bundle getSavedInstanceState() {
        return savedInstanceState;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
