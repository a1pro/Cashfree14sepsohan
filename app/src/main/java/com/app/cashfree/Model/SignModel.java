package com.app.cashfree.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SignModel {
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Data> data = null;
    @SerializedName("cardInfo")
    @Expose
    private List<CardInfo> cardInfo = null;

    public String getCode() {
        return code;
    }

    public List<CardInfo> getCardInfo() {
        return cardInfo;
    }

    public void setCardInfo(List<CardInfo> cardInfo) {
        this.cardInfo = cardInfo;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data {

        @SerializedName("varifiedStatus")
        @Expose
        private String varifiedStatus;
        @SerializedName("beneficiaryId")
        @Expose
        private String beneficiaryId;
        @SerializedName("beneficiaryName")
        @Expose
        private String beneficiaryName;

        @SerializedName("walletId")
        @Expose
        private String walletId;

        @SerializedName("qrCode")
        @Expose
        private String qrCode;

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("firstName")
        @Expose
        private String firstName;
        @SerializedName("lastName")
        @Expose
        private String lastName;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("userType")
        @Expose
        private String userType;
        @SerializedName("deviceId")
        @Expose
        private Object deviceId;
        @SerializedName("deviceType")
        @Expose
        private Object deviceType;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("pinNumber")
        @Expose
        private String pinNumber;

        public String getPinNumber() {
            return pinNumber;
        }

        public void setPinNumber(String pinNumber) {
            this.pinNumber = pinNumber;
        }

        public String getVarifiedStatus() {
            return varifiedStatus;
        }

        public void setVarifiedStatus(String varifiedStatus) {
            this.varifiedStatus = varifiedStatus;
        }

        public String getBeneficiaryId() {
            return beneficiaryId;
        }

        public void setBeneficiaryId(String beneficiaryId) {
            this.beneficiaryId = beneficiaryId;
        }

        public String getWalletId() {
            return walletId;
        }

        public void setWalletId(String walletId) {
            this.walletId = walletId;
        }

        public String getQrCode() {
            return qrCode;
        }

        public void setQrCode(String qrCode) {
            this.qrCode = qrCode;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhone() {
            return phone;
        }

        public String getBeneficiaryName() {
            return beneficiaryName;
        }

        public void setBeneficiaryName(String beneficiaryName) {
            this.beneficiaryName = beneficiaryName;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public Object getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(Object deviceId) {
            this.deviceId = deviceId;
        }

        public Object getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(Object deviceType) {
            this.deviceType = deviceType;
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


    public class CardInfo {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("userId")
        @Expose
        private String userId;
        @SerializedName("ifscCode")
        @Expose
        private String ifscCode;
        @SerializedName("account_no")
        @Expose
        private String accountNo;
        @SerializedName("cardHolderName")
        @Expose
        private String cardHolderName;
        @SerializedName("cardNo")
        @Expose
        private String cardNo;
        @SerializedName("cardCvvNo")
        @Expose
        private String cardCvvNo;
        @SerializedName("cardExpiryDate")
        @Expose
        private String cardExpiryDate;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getIfscCode() {
            return ifscCode;
        }

        public void setIfscCode(String ifscCode) {
            this.ifscCode = ifscCode;
        }

        public String getAccountNo() {
            return accountNo;
        }

        public void setAccountNo(String accountNo) {
            this.accountNo = accountNo;
        }

        public String getCardHolderName() {
            return cardHolderName;
        }

        public void setCardHolderName(String cardHolderName) {
            this.cardHolderName = cardHolderName;
        }

        public String getCardNo() {
            return cardNo;
        }

        public void setCardNo(String cardNo) {
            this.cardNo = cardNo;
        }

        public String getCardCvvNo() {
            return cardCvvNo;
        }

        public void setCardCvvNo(String cardCvvNo) {
            this.cardCvvNo = cardCvvNo;
        }

        public String getCardExpiryDate() {
            return cardExpiryDate;
        }

        public void setCardExpiryDate(String cardExpiryDate) {
            this.cardExpiryDate = cardExpiryDate;
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
