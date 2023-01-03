package com.example.commonlibrary.netty;

import android.text.TextUtils;
import android.util.Log;

import com.example.commonlibrary.interf.DeBugNettyConnectListener;
import com.example.commonlibrary.interf.NettyMsgListener;
import com.example.commonlibrary.units.LogToFile;
import com.example.commonlibrary.units.SharedPreferenceUtils;
import com.example.commonlibrary.units.Utils;

import org.json.JSONObject;

import java.net.InetSocketAddress;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by cpf6006 on 2019/4/10.
 */

public class NlinkClientHandler extends ChannelInboundHandlerAdapter {

    private static final int frameHead = 2;
    private static final int frameVersion = 2;
    private static final int frameType = 2;
    private static final int frameId = 4;
    private static final int frameDataType = 2;
    private static final int frameNull = 4;
    private static final int frameLength = 4;
    private static final int checkSum = 1;

    private String TAG = "NlinkClientHandler";
    private NettyMsgListener nettyMsgListener;
    public static boolean isSend = true;
    private DeBugNettyConnectListener deBugNettyConnectListener;

    public NlinkClientHandler(NettyMsgListener nettyMsgListener) {
        this.nettyMsgListener = nettyMsgListener;
//        this.msgListener = msgListener;
    }

    public NlinkClientHandler(DeBugNettyConnectListener deBugNettyConnectListener) {
        TAG = "NlinkClientHandler 远程调试";
        this.deBugNettyConnectListener = deBugNettyConnectListener;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        Log.d(TAG, "channelRegistered: 注册中");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
        if (deBugNettyConnectListener != null) deBugNettyConnectListener.connectError();
        Log.i(TAG, "channelUnregistered 注册失败");

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().localAddress();
        String ip = insocket.getAddress().getHostAddress();
        int port = insocket.getPort();
        Log.d(TAG, "channelActive: 连接成功 " + ip + "  " + port);
        if (nettyMsgListener != null) nettyMsgListener.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        Log.i(TAG, "channelInactive 断开链接");
        if (deBugNettyConnectListener != null) deBugNettyConnectListener.channelInactive();
        if (nettyMsgListener != null) nettyMsgListener.disConnect();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        int length = frameHead + frameVersion + frameType + frameId + frameDataType + frameNull + frameLength + checkSum;
        if (buf.readableBytes() < length) {
            //数据量不够,继续缓冲
            Log.i(TAG, "数据量不够,继续缓冲");
            return;
        } else {
//                    判断是否是帧头
            if (BytesUtils.isFrameHead(buf)) {
                buf.skipBytes(frameVersion + frameType + frameId + frameDataType + frameNull);
                int dataLength = buf.readInt();//数据长度
                if (dataLength > 50000) {
                    ByteBuf byteBuf = buf.readBytes(dataLength);
                    return;
                }
                ByteBuf byteBuf = buf.readBytes(dataLength);
                String message = new String(byteBuf.array(), "UTF-8").trim();
//                BaseJson baseJson = new BaseJson();
//                baseJson.setData(message);
                Log.i(TAG, "channelRead: " + message);
//                LogToFile.i("",message);
                if (nettyMsgListener != null) nettyMsgListener.resMsg(message);
//                String carName = SharedPreferenceUtils.getInstance().getSharePreferenceString("carName");
//                String carNum = SharedPreferenceUtils.getInstance().getSharePreferenceString("carNum");
//                int carType = SharedPreferenceUtils.getInstance().getSharePreferenceInt("carType", 8);
                //发送车辆信息,只传一次
//                if (carName != null && !TextUtils.isEmpty(carName)
//                        && carNum != null && !carNum.isEmpty()
//                        && isSend) {
//                    JSONObject jsonObject = new JSONObject();
//                    jsonObject.put("carName", carName);
//                    jsonObject.put("carNum", carNum);
//                    jsonObject.put("carType", carType);
//                    ByteBuf returnBuf = Utils.byteBufSend(jsonObject.toString());
//                    ctx.writeAndFlush(returnBuf);
//                    if (nettyMsgListener != null) nettyMsgListener.sendCarInfoSuccess();
//                    isSend = false;
//                }
//                        Log.i(TAG, "channelRead" + message);

            }
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
//        Log.i(TAG, "channelReadComplete" );
    }


    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        super.channelWritabilityChanged(ctx);
        Log.i(TAG, "channelWritabilityChanged");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        Log.i(TAG, "exceptionCaught 连接异常" + cause.getLocalizedMessage());
        if (ctx != null) ctx.close();
        if (nettyMsgListener != null) nettyMsgListener.connectException(cause);
    }


}
