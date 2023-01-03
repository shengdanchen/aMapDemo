package com.example.commonlibrary.units;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreference工具类
 * @author ShengDanChen.
 */

public class SharedPreferenceUtils {
    public static final String sharedPrenceFileName = "shuttlebus_app";
    private static SharedPreferenceUtils instance;

    private static SharedPreferences mSharePreference;

    /**
     * Constructor
     */
    private SharedPreferenceUtils(Context context) {
        mSharePreference = context.getSharedPreferences(sharedPrenceFileName, Context.MODE_PRIVATE);
    }

    public static SharedPreferenceUtils initialize(Context context) {
        return instance = new SharedPreferenceUtils(context);
    }

    /**
     * 单实例
     *
     * @return Object SharePreference Util
     */
    public static SharedPreferenceUtils getInstance() {
        if (instance == null) {
            throw new NullPointerException("please initialize SharedPreferenceUtil first");
        }
        return instance;
    }

    /**
     * 保存String
     *
     * @param key
     * @param value as string dataType
     */
    public void savePreference(String key, String value) {
        SharedPreferences.Editor edit = mSharePreference.edit();
        edit.putString(key, value);
        edit.commit();
    }

    /**
     * 保存Int
     *
     * @param key
     * @param value as integer dataType
     */
    public void savePreference(String key, int value) {
        SharedPreferences.Editor edit = mSharePreference.edit();
        edit.putInt(key, value);
        edit.commit();
    }

    /**
     * 保存boolean
     *
     * @param key
     * @param value as boolean dataType (True or False)
     */
    public void savePreference(String key, boolean value) {
        SharedPreferences.Editor edit = mSharePreference.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }


    /**
     * 保存long值
     *
     * @param key
     * @param value as boolean dataType (True or False)
     */
    public void savePreference(String key, long value) {
        SharedPreferences.Editor edit = mSharePreference.edit();
        edit.putLong(key, value);
        edit.commit();
    }

    /**
     * 获取String
     *
     * @param key
     * @return String value if there is no value it return ""
     */
    public String getSharePreferenceString(String key) {
        return mSharePreference.getString(key, "");
    }

    /**
     * 获取Int
     *
     * @param key
     * @return Integer value if there is no value it return 0
     */
    public int getSharePreferenceInt(String key) {
        return mSharePreference.getInt(key, 0);
    }

    /**
     * 获取Int
     *
     * @param key
     * @param value 默认值
     * @return Integer value if there is no value it return 0
     */
    public int getSharePreferenceInt(String key, int value) {
        return mSharePreference.getInt(key, value);
    }

    /**
     * 获取boolean
     *
     * @param key
     * @return True or False Default Value is False
     */
    public boolean getSharePreferenceBoolean(String key) {
        return mSharePreference.getBoolean(key, false);
    }

    /**
     * 获取long值
     *
     * @param key
     * @return True or False Default Value is False
     */
    public long getSharePreferenceLong(String key) {
        return mSharePreference.getLong(key, 0);
    }
}
