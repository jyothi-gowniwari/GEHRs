package com.example.ehr.Model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("LoginId")
    private String LoginId;
    @SerializedName("Type")
    private String Type;
    @SerializedName("Message")
    private String Message;
    @SerializedName("Status")
    private String Status;
    @SerializedName("FName")
    private String FName;

    public String getLoginId() {
        return LoginId;
    }

    public void setLoginId(String loginId) {
        LoginId = loginId;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public String getLName() {
        return LName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    @SerializedName("LName")
    private String LName;
    @SerializedName("Mobile")
    private String Mobile;
    @SerializedName("Token")
    private String Token;
}
