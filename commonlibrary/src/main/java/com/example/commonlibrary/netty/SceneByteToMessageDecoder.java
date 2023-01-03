package com.example.commonlibrary.netty;

import android.util.Log;

import com.example.commonlibrary.units.Utils;

import org.json.JSONObject;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * Created by cpf6006 on 2019/4/16.
 */

public class SceneByteToMessageDecoder extends ByteToMessageDecoder {

    private static final String TAG = "SceneByteToMessage";


    private static final int frameHead=2;
    private static final int frameVersion=2;
    private static final int frameType=2;
    private static final int frameId=4;
    private static final int frameDataType=2;
    private static final int frameNull=4;
    private static final int frameLength=4;
    private static final int checkSum=1;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {

        int length = frameHead + frameVersion + frameType + frameId+frameDataType+frameNull+frameLength+checkSum;
        if(buf.readableBytes()>length){
            int beginIndex = buf.readerIndex();
            buf.skipBytes(frameHead+frameVersion+frameType+frameId+frameDataType+frameNull);
            int dataLength = buf.readInt();//数据长度
//            if(dataLength>50000)
//            {
//                buf.readerIndex(buf.readableBytes());
//                return;
//            }
            if(buf.readableBytes()<dataLength){
                buf.readerIndex(beginIndex);
                Log.i(TAG,"缓冲区的数据量小于数据长度");
                Log.i(TAG,"buf.readableBytes:"+buf.readableBytes());
                Log.i(TAG,"dataLength:"+dataLength);

                if(dataLength>6000)  //正常长度
                {
                    buf.clear();
                    Log.i(TAG,"清空缓冲区的数据");
                }

                return;
            }else{
                try{   //异常处理by王科明
                    buf.readerIndex(beginIndex + length + dataLength);
                    ByteBuf otherByteBufRef = buf.slice(beginIndex , length + dataLength);
                    otherByteBufRef.retain();
                    out.add(otherByteBufRef);
                }catch (Exception e){
                    Log.e(TAG,e.getMessage());
                }

            }
        }else {
            //数据量不够,继续缓冲
            Log.i(TAG,"数据量不够,继续缓冲");
            return;
        }
    }
}
