package com.example.commonlibrary.units;

import android.text.TextUtils;
import android.util.Log;


import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


public class LogUtils {
    private static final String TAG = LogUtils.class.getSimpleName();


    private LogUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }


    public static void debug(String tag, String content) {
        if (TextUtils.isEmpty(content)) {
            return;
        }



        if (TextUtils.isEmpty(tag)) {
            Log.d(TAG, content);
        } else {
            Log.d(tag, content);
        }
    }


    public static void info(String tag, String content) {


        infoMemory(tag, content);
        LogToFile.i(tag, content);
    }


    /**
     * 需要在LogView页面显示的日志
     */
    public static void infoLogView(String tag, String content) {


        infoMemory(tag, content);
        LogToFile.i(tag, content, LogToFile.getLogFileLoopRQPath(), LogToFile
                .getLogFileNamePerHour());
    }


    public static void infoMemory(String tag, String content) {
        if (TextUtils.isEmpty(content)) {
            return;
        }



        if (TextUtils.isEmpty(tag)) {
            Log.i(TAG, content);
        } else {
            //Log.i(tag, content);
            infoMemoryJSON(tag, content);
        }
    }


    public static void infoMemoryJSON(String tag, String content) {
        if (TextUtils.isEmpty(content)) {
            return;
        }


        if (TextUtils.isEmpty(tag)) {
            Log.i(TAG, content);
        } else {
            Object o = null;
            try {
                o = new JSONTokener(content).nextValue();
            } catch (JSONException e) {
                //                    e.printStackTrace();
                Log.i(tag, content);
            }

            if (o instanceof JSONObject || o instanceof JSONArray) {
                Logger.t(tag).json(content);
            } else {
                Log.i(tag, content);
            }
        }
    }


    public static void error(String tag, String content) {
        errorMemory(tag, content);
        LogToFile.e(tag, content);
    }


    public static void errorMemory(String tag, String content) {
        if (TextUtils.isEmpty(content)) {
            return;
        }

        if (TextUtils.isEmpty(tag)) {
            Log.e(TAG, content);
        } else {
            Log.e(tag, content);
        }
    }
}
