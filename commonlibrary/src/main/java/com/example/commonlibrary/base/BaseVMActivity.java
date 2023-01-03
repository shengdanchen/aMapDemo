package com.example.commonlibrary.base;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;


import com.example.commonlibrary.http.model.ResErrorEntity;
import com.example.commonlibrary.units.FixMemLeak;


/**
 * author : ChenShengDan
 * date   : 2021/8/16
 * desc   : 使用vm的Activity基类
 */
public abstract class BaseVMActivity<VM extends BaseViewModel, DB extends ViewDataBinding> extends AppCompatActivity {
    protected DB mViewDataBinding;
    protected VM mViewModel;
    protected String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        mViewModel = getViewModel();
        if (mViewModel != null) mViewModel.getResErrorEntityMutableLiveData().observe(this, this::resErrorMessage);
        getIntentData(getIntent());
        init(savedInstanceState);
    }

    /**
     * 初始化VM
     *
     * @return
     */
    protected abstract VM getViewModel();

    /**
     * 初始化
     */
    protected abstract void init(Bundle savedInstanceState);

    /**
     * 获取资源ID
     *
     * @return 布局资源ID
     */
    protected abstract int getLayoutId();

    /**
     * 返回的错误信息
     *
     * @param resErrorEntity
     */
    protected abstract void resErrorMessage(ResErrorEntity resErrorEntity);

    /**
     * 提取intent数据
     * @param intent
     */
    protected void getIntentData(Intent intent) {

    }

    @Override
    protected void onDestroy() {
        FixMemLeak.fixLeak(this);
        super.onDestroy();
    }
}
