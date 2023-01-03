package com.example.commonlibrary.netty;

import android.util.Log;

import com.example.commonlibrary.interf.DeBugNettyConnectListener;
import com.example.commonlibrary.interf.NettyConnectListener;
import com.example.commonlibrary.interf.NettyMsgListener;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.oio.OioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * Created by cpf6006 on 2019/4/18.
 */

public class DeBugNettyThread extends Thread {

    private static final String TAG = "MyNettyThread";

    private String serverIp;
    private int serverPoint;
    private ChannelFuture sync;
    private NlinkClientHandler nlinkClientHandler;

    private DeBugNettyConnectListener deBugNettyConnectListener;
    public DeBugNettyThread(DeBugNettyConnectListener deBugNettyConnectListener, String ip, int port) {
        this.serverIp = ip;
        this.serverPoint = port;
        nlinkClientHandler = new NlinkClientHandler(deBugNettyConnectListener);
    }
    @Override
    public void run() {
        EventLoopGroup eventLoopGroup = new OioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup).option(ChannelOption.TCP_NODELAY, true)//屏蔽Nagle算法试图
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                //.channel(NioSocketChannel.class)
                .channel(OioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new IdleStateHandler(-1, 0, 0, TimeUnit.SECONDS));
                        pipeline.addLast(new HeartbeatServerHandler());
                        pipeline.addLast(new SceneByteToMessageDecoder());
                        pipeline.addLast(nlinkClientHandler);
                    }
                });
        sync = bootstrap.connect(new InetSocketAddress(serverIp, serverPoint));
        sync.channel().closeFuture();
        sync.addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                Log.d(TAG, "连接成功: ");
//               if (nettyConnectListener != null)nettyConnectListener.connectSuccess();
            } else {
                Log.d(TAG, "连接失败: " + future.cause().getMessage());
//                if (nettyConnectListener != null)nettyConnectListener.connectError(future.cause());
                // 这里一定要关闭，不然一直重试会引发OOM
                future.channel().close();
                eventLoopGroup.shutdownGracefully();
            }
        });
    }

    //关闭通道 葛明
    public void close() {
        if (sync != null) {
            if (sync.channel() != null && sync.channel().isActive()) {
                try {
                    sync.channel().close();
                } catch (Exception b) {
                    Log.i(TAG, "连接断开异常: " + b.getMessage());
                    //Utils.postMsg("连接断开异常!", MsgType.CONN_SERVER_FAIL);
                }

            }
        }

    }
}
