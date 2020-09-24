package com.example.ehr.Model;

public class AppointmentModel {

    private String Imageview;
    private String doctor_name;

    public String getImageview() {
        return Imageview;
    }

    public void setImageview(String imageview) {
        Imageview = imageview;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getSpeclist() {
        return speclist;
    }

    public void setSpeclist(String speclist) {
        this.speclist = speclist;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getBook_doctor() {
        return book_doctor;
    }

    public void setBook_doctor(String book_doctor) {
        this.book_doctor = book_doctor;
    }

    public String getContact_hospital() {
        return contact_hospital;
    }

    public void setContact_hospital(String contact_hospital) {
        this.contact_hospital = contact_hospital;
    }

    private String speclist;
    private String experience;
    private String book_doctor;
    private String contact_hospital;

}
