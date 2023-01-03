package com.example.commonlibrary.units;


import com.example.commonlibrary.R;
import com.example.commonlibrary.base.BaseApplication;

import es.dmoral.toasty.Toasty;

/**
 * author: ChenShengDan .
 * date:   2021/4/28
 * sence:
 */
public class ToastUtils {
    public static void showSuccess(String text) {
        Toasty.success(BaseApplication.getInstance().getApplicationContext(), text).show();
    }

    public static void showInfo(String text) {
        Toasty.info(BaseApplication.getInstance().getApplicationContext(), text).show();
    }

    public static void showError(String text) {
        Toasty.error(BaseApplication.getInstance().getApplicationContext(), text).show();
    }
}
