package com.example.commonlibrary.interf;

/**
 * author: ChenShengDan .
 * date:   2021/4/26
 * sence:
 */
public interface NettyConnectListener {
    void connectSuccess();
    void connectError(Throwable cause);
}
