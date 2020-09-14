package com.app.cashfree.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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