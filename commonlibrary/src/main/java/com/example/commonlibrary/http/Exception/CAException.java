package com.example.commonlibrary.http.Exception;

/**
 * Created by Zaifeng on 2018/2/28.
 * 异常处理
 */

public class CAException extends Exception {
    private String code;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public CAException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CAException(String code, String message, String msg) {
        super(message);
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }



}
