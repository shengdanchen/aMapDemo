package com.example.commonlibrary.netty;


import io.netty.buffer.ByteBuf;

/**
 * Created by cpf6006 on 2019/4/15.
 */

public class BytesUtils {

    /**
     * 判断帧头
     * @param in
     * @return
     */
    public static boolean isFrameHead(ByteBuf in) {
        byte da = in.readByte();
        byte db = in.readByte();
        if(da == 90 && db == -91){
            return true;
        }else{
            return false;
        }
    }

 /*   public static String  getJsonFromBytes(ByteBuf in){

    }*/
}
