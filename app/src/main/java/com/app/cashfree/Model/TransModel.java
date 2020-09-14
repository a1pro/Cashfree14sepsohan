package com.app.cashfree.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TransModel {
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<Data> data = null;

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

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data {

        @SerializedName("transactionId")
        @Expose
        private String transactionId;
        @SerializedName("trnasType")
        @Expose
        private String trnasType;
        @SerializedName("senderId")
        @Expose
        private String senderId;
        @SerializedName("sWalletId")
        @Expose
        private String sWalletId;
        @SerializedName("receiverId")
        @Expose
        private String receiverId;
        @SerializedName("rWalletId")
        @Expose
        private String rWalletId;
        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("senderName")
        @Expose
        private String senderName;
        @SerializedName("receiverName")
        @Expose
        private String receiverName;
        @SerializedName("recevierNames")
        @Expose
        private String recevierNames;


        public String getRecevierNames() {
            return recevierNames;
        }

        public void setRecevierNames(String recevierNames) {
            this.recevierNames = recevierNames;
        }

        public String getSenderName() {
            return senderName;
        }

        public void setSenderName(String senderName) {
            this.senderName = senderName;
        }

        public String getReceiverName() {
            return receiverName;
        }

        public void setReceiverName(String receiverName) {
            this.receiverName = receiverName;
        }

        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

        public String getTrnasType() {
            return trnasType;
        }

        public void setTrnasType(String trnasType) {
            this.trnasType = trnasType;
        }

        public String getSenderId() {
            return senderId;
        }

        public void setSenderId(String senderId) {
            this.senderId = senderId;
        }

        public String getsWalletId() {
            return sWalletId;
        }

        public void setsWalletId(String sWalletId) {
            this.sWalletId = sWalletId;
        }

        public String getReceiverId() {
            return receiverId;
        }

        public void setReceiverId(String receiverId) {
            this.receiverId = receiverId;
        }

        public String getrWalletId() {
            return rWalletId;
        }

        public void setrWalletId(String rWalletId) {
            this.rWalletId = rWalletId;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }
    }


}
