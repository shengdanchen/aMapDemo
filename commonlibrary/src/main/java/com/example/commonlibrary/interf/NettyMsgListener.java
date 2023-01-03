package com.example.commonlibrary.interf;

import io.netty.channel.ChannelHandlerContext;

/**
 * author: ChenShengDan .
 * date:   2021/4/26
 * sence:
 */
public interface NettyMsgListener {
    void resMsg(String s);
    void disConnect();
    void connectException(Throwable cause);
    void sendCarInfoSuccess();

    void channelActive(ChannelHandlerContext ctx);
}
