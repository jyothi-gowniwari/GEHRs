package com.example.ehr.Model;

public class Doctorlist {
    private String Specialization;
    private String DoctorName;
    private String Qualification;
    private String Experience;
    private String LanguagesKnown;
    private String ProfllePhoto;
    private String ConsultationFees;

    public String getConsultationFees() {
        return ConsultationFees;
    }

    public void setConsultationFees(String consultationFees) {
        ConsultationFees = consultationFees;
    }

    public Doctorlist(String specialization, String doctorName, String qualification, String experience, String languagesKnown, String profllePhoto, String consultationFees, String hospital, String hospitalID, String doctorID) {
        Specialization = specialization;
        DoctorName = doctorName;
        Qualification = qualification;
        Experience = experience;
        LanguagesKnown = languagesKnown;
        ProfllePhoto = profllePhoto;
        ConsultationFees = consultationFees;
        Hospital = hospital;
        HospitalID = hospitalID;
        DoctorID = doctorID;
    }

    private String Hospital;
    private String HospitalID;
    private String DoctorID;


    public String getSpecialization() {
        return Specialization;
    }

    public void setSpecialization(String specialization) {
        Specialization = specialization;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String doctorName) {
        DoctorName = doctorName;
    }

    public String getQualification() {
        return Qualification;
    }

    public void setQualification(String qualification) {
        Qualification = qualification;
    }

    public String getExperience() {
        return Experience;
    }

    public void setExperience(String experience) {
        Experience = experience;
    }

    public String getLanguagesKnown() {
        return LanguagesKnown;
    }

    public void setLanguagesKnown(String languagesKnown) {
        LanguagesKnown = languagesKnown;
    }

    public String getProfllePhoto() {
        return ProfllePhoto;
    }

    public void setProfllePhoto(String profllePhoto) {
        ProfllePhoto = profllePhoto;
    }

    public String getHospital() {
        return Hospital;
    }

    public void setHospital(String hospital) {
        Hospital = hospital;
    }

    public String getHospitalID() {
        return HospitalID;
    }

    public void setHospitalID(String hospitalID) {
        HospitalID = hospitalID;
    }

    public String getDoctorID() {
        return DoctorID;
    }

    public void setDoctorID(String doctorID) {
        DoctorID = doctorID;
    }
}
