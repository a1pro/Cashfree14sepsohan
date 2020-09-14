package com.app.cashfree.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddCartModel {

@SerializedName("code")
@Expose
private String code;
@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("cardInfo")
@Expose
private List<CardInfo> cardInfo = null;

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

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public List<CardInfo> getCardInfo() {
return cardInfo;
}

public void setCardInfo(List<CardInfo> cardInfo) {
this.cardInfo = cardInfo;
}

}