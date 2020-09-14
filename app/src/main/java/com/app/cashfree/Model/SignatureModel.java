package com.app.cashfree.Model;

import com.google.gson.annotations.SerializedName;

public class SignatureModel {

    @SerializedName("code")
    String code;

    @SerializedName("status")
    String status;

    @SerializedName("data")
    String data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}



