package com.example.ehr.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DataList {


    public String getSpecialization() {
        return Specialization;
    }

    public DataList(String specialization) {
        Specialization = specialization;
    }

    public void setSpecialization(String specialization) {
        Specialization = specialization;
    }

    @SerializedName("Specialization")
    @Expose
    private String Specialization;

    public ArrayList<Doctorlist> getData() {
        return Data;
    }

    public void setData(ArrayList<Doctorlist> data) {
        Data = data;
    }

    @SerializedName("Data")
    @Expose
    private ArrayList<Doctorlist> Data;


}
