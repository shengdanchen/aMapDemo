package com.example.commonlibrary.mqtt;

import java.io.Serializable;

/**
 * author : ChenShengDan
 * date   : 2021/6/21
 * desc   :
 */
public class MqttInfoEntity implements Serializable {
    private String clinetId, host, USERNAME, PASSWORD;

    public String getClinetId() {
        return clinetId;
    }

    public void setClinetId(String clinetId) {
        this.clinetId = clinetId;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }
}
