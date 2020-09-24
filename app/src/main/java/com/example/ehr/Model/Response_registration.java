package com.example.ehr.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response_registration {
    @SerializedName("Message")
    @Expose
    private String Message;
    @SerializedName("RegistrationNo")
    @Expose
    private String RegistrationNo;

    public String getRegistrationNo() {
        return RegistrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        RegistrationNo = registrationNo;
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
    @SerializedName("Status")
    @Expose
    private String Status;
}
