package com.example.commonlibrary.http.model;

import com.example.commonlibrary.http.model.ResMessageEntity;

/**
 * author : ChenShengDan
 * date   : 2021/7/26
 * desc   :
 */
public class ResErrorEntity {
    private int code;
    private String message;

    public ResErrorEntity(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
