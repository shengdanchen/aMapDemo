package com.example.commonlibrary.interf;

import io.netty.channel.ChannelHandlerContext;

/**
 * author : ChenShengDan
 * date   : 2021/6/24
 * desc   :
 */
public interface DeBugNettyConnectListener {
    void channelActive(ChannelHandlerContext ctx);
    void channelInactive();
    void connectError();
}
