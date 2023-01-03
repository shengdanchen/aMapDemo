package com.example.commonlibrary.units;

import android.content.Context;
import android.widget.ImageView;

/**
 * author : ChenShengDan
 * date   : 2022/12/15
 * desc   :
 */
public class ImageUtils {
    /**
     * 根据图片文件名设置资源文件
     * @param context
     * @param imgName
     * @param imageView
     */
    public static void loadImageByName(Context context, String imgName, ImageView imageView){
        int icon = context.getResources().getIdentifier(imgName, "mipmap", context.getPackageName());
        imageView.setImageResource(icon);
    }


    public static int findImageByName(Context context, String imgName){
        return context.getResources().getIdentifier(imgName, "mipmap", context.getPackageName());
    }

    public static int findImageByRaw(Context context, String imgName){
        return context.getResources().getIdentifier(imgName, "raw", context.getPackageName());
    }
}
