package com.example.commonlibrary.netty;

import android.util.Log;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * Created by cpf6006 on 2019/4/15.
 */

public class HeartbeatServerHandler extends ChannelInboundHandlerAdapter {

    private static final String TAG = "HeartbeatServerHandler";

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // super.userEventTriggered(ctx, evt);
        Log.i(TAG, "userEventTriggered" );
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
//            if (idleStateEvent.state() == IdleState.ALL_IDLE) {
//                ReferenceCountUtil.release(ctx.alloc().buffer());
//                ctx.channel().close();
//                Utils.postMsg("监测不到服务器心跳",MsgType.CONN_AGAIN);
//            }
            //没有收到来自服务端的任何数据，我们就向服务端写数据，如果写失败了，那么就会去触发我们的channelInactive方法，就会去重连了
            if(idleStateEvent.state() == IdleState.READER_IDLE)
            {
                Log.i(TAG, "监测不到服务器心跳");
                ctx.channel().close();
                //ctx.writeAndFlush(Utils.byteBufSend("heartbeat test"));
                //ctx.fireChannelInactive();
//                ReferenceCountUtil.release(ctx.alloc().buffer());
                //NewMainActivity.myNettyThread.close();
                //ctx.writeAndFlush(ctx.channel().alloc().buffer().writeBytes(new byte[]{11}));
            }
        }

        //super.userEventTriggered(ctx, evt);
    }
}
