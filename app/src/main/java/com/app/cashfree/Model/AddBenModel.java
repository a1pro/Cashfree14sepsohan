package com.app.cashfree.Model;

import com.google.gson.annotations.SerializedName;

public class AddBenModel {

    @SerializedName("status")
    String status;

    @SerializedName("subCode")
    String subCode;

    @SerializedName("message")
    String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
