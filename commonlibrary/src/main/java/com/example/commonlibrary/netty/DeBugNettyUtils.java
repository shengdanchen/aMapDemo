package com.example.commonlibrary.netty;

import com.example.commonlibrary.interf.DeBugNettyConnectListener;

/**
 * author : ChenShengDan
 * date   : 2021/6/24
 * desc   :
 */
public class DeBugNettyUtils {
    private static DeBugNettyThread deBugNettyThread;

    public static void startDeBugNetty(String ip, int port, DeBugNettyConnectListener deBugNettyConnectListener) {
        if (deBugNettyThread != null)return;
            deBugNettyThread = new DeBugNettyThread(deBugNettyConnectListener, ip, port);
        deBugNettyThread.start();
    }

    public static void clostDeBug() {
        if (deBugNettyThread != null) {
            deBugNettyThread.close();
            deBugNettyThread = null;
        }

    }
}
