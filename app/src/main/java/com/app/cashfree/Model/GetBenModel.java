package com.app.cashfree.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetBenModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("subCode")
    @Expose
    private String subCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        @SerializedName("beneId")
        @Expose
        private String beneId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("address1")
        @Expose
        private String address1;
        @SerializedName("address2")
        @Expose
        private String address2;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("pincode")
        @Expose
        private String pincode;
        @SerializedName("bankAccount")
        @Expose
        private String bankAccount;
        @SerializedName("ifsc")
        @Expose
        private String ifsc;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("maskedCard")
        @Expose
        private Object maskedCard;
        @SerializedName("vpa")
        @Expose
        private String vpa;
        @SerializedName("addedOn")
        @Expose
        private String addedOn;

        public String getBeneId() {
            return beneId;
        }

        public void setBeneId(String beneId) {
            this.beneId = beneId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress1() {
            return address1;
        }

        public void setAddress1(String address1) {
            this.address1 = address1;
        }

        public String getAddress2() {
            return address2;
        }

        public void setAddress2(String address2) {
            this.address2 = address2;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getPincode() {
            return pincode;
        }

        public void setPincode(String pincode) {
            this.pincode = pincode;
        }

        public String getBankAccount() {
            return bankAccount;
        }

        public void setBankAccount(String bankAccount) {
            this.bankAccount = bankAccount;
        }

        public String getIfsc() {
            return ifsc;
        }

        public void setIfsc(String ifsc) {
            this.ifsc = ifsc;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Object getMaskedCard() {
            return maskedCard;
        }

        public void setMaskedCard(Object maskedCard) {
            this.maskedCard = maskedCard;
        }

        public String getVpa() {
            return vpa;
        }

        public void setVpa(String vpa) {
            this.vpa = vpa;
        }

        public String getAddedOn() {
            return addedOn;
        }

        public void setAddedOn(String addedOn) {
            this.addedOn = addedOn;
        }

    }


}
