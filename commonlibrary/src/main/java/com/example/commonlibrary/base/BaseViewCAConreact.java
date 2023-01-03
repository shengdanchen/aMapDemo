package com.example.commonlibrary.base;

import android.content.Context;

/**
 * author : ChenShengDan
 * date   : 2021/7/2
 * desc   : 带CA的Conreact
 */
public interface BaseViewCAConreact {
    Context getContext();

    /**
     * CA签名错误
     * @param errorCode 错误码
     * @param errorMsg  错误信息
     */
    void signError(String errorCode,String errorMsg);
}
