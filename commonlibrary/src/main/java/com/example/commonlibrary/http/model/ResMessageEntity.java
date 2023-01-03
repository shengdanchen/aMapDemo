package com.example.commonlibrary.http.model;

import com.google.gson.annotations.SerializedName;

/**
 * 修改备注：
 */
public class ResMessageEntity extends ResCodeEntity {
    private static final String FIELD_MESSAGE = "resultMsg";

    @SerializedName(value = FIELD_MESSAGE, alternate = {"msg", "error_description"})
    public String message;


    @Override
    public String toString() {
        return "ResMessageEntity{" + "message='" + message + '\'' + ", code=" + code + '}';
    }
}
