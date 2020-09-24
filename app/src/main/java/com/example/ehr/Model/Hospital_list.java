package com.example.ehr.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Hospital_list {

    @SerializedName("Data")
    public List<HospitalData> data = null;

    public static class HospitalData {

        @SerializedName("ParentHospitalId")
        private String ParentHospitalId;
        @SerializedName("ParentHospitalName")
        private String ParentHospitalName;

        public HospitalData(String parentHospitalId, String parentHospitalName) {
            ParentHospitalId = parentHospitalId;
            ParentHospitalName = parentHospitalName;
        }

        public String getParentHospitalId() {
            return ParentHospitalId;
        }

        public void setParentHospitalId(String parentHospitalId) {
            ParentHospitalId = parentHospitalId;
        }

        public String getParentHospitalName() {
            return ParentHospitalName;
        }

        public void setParentHospitalName(String parentHospitalName) {
            ParentHospitalName = parentHospitalName;
        }
    }
}
