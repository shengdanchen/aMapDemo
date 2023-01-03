package com.example.commonlibrary.interf;
/**
 * author: ChenShengDan .
 * date:   2021/4/26
 * sence:
 */
public interface ServiceMsgListener {
    /**
     * 服务器返回的数据处理
     * @param  res
     */
    void resData(String res);

    /**
     * 连接断开
     */
    void disConnect();

    /**
     * 连接成功
     */
    void connectSuccess();

    /**
     * 连接失败
     */
    void connectError(Throwable cause);

    /**
     * 链接异常
     * @param cause
     */
    void connectException(Throwable cause);

    /**
     * 车辆信息提交成功
     */
    void sendCarInfoSuccess();
}
