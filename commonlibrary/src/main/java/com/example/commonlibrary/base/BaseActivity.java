package com.example.commonlibrary.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.commonlibrary.units.FixMemLeak;

/**
 * author : ChenShengDan
 * date   : 2021/6/29
 * desc   :
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {
    public P presenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        presenter = getPresenter();
        if (presenter != null) {
            presenter.setView(this);
        }

        init(savedInstanceState);

    }

    protected abstract void init(Bundle savedInstanceState);

    protected abstract int getLayoutId();
    protected abstract P getPresenter();

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.detttch();
            presenter = null;
        }
        FixMemLeak.fixLeak(this);
        super.onDestroy();
    }
}
