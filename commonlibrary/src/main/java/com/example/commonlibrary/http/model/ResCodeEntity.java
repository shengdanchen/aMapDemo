package com.example.commonlibrary.http.model;

import com.google.gson.annotations.SerializedName;

/**
 * 不要轻易修改
 */
public class ResCodeEntity {
    private static final String FIELD_CODE = "resultCode";

//    @JsonAdapter(BooleanTypeAdapter.class)
    @SerializedName(value = FIELD_CODE, alternate = {"error_code", "code", "result_code"})
    public Integer code;


    @Override
    public String toString() {
        return "ResCodeEntity{" + "code=" + code + '}';
    }
}
