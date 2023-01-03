package com.example.commonlibrary.http.model;

/**
 */
public class ResEntity<T> extends ResMessageEntity {
    public T data;
    public long timeStamp;
    public boolean success;

    @Override
    public String toString() {
        return "ResEntity{" + "message='" + message + '\'' + ", code=" + code +
                ", data=" + data + '}';
    }
}
