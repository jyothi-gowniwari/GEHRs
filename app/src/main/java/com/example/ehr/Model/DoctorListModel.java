package com.example.ehr.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DoctorListModel {
    public ArrayList<DataList> getDataLists() {
        return dataLists;
    }

    public void setDataLists(ArrayList<DataList> dataLists) {
        this.dataLists = dataLists;
    }

    @SerializedName("Data")
    @Expose
    private ArrayList<DataList> dataLists;

}
