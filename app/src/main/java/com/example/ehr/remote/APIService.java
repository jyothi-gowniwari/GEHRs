package com.example.ehr.remote;

import android.icu.text.UFormat;

import com.example.ehr.Model.DoctorListModel;
import com.example.ehr.Model.HospitalData;
import com.example.ehr.Model.Hospital_list;
import com.example.ehr.Model.LoginResponse;
import com.example.ehr.Model.Response_registration;


import org.json.JSONObject;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface APIService {


    //Registration Api
    @Multipart
    @POST("ApiPatientRegistration/AddNewPatient")
    Call<Response_registration> postData(
                                        @Part("FName") RequestBody firstName,
                                        @Part("LName") RequestBody lastName,
                                        @Part("Gender") RequestBody gender,
                                        @Part("State") RequestBody state,
                                        @Part("Country") RequestBody country,
                                        @Part("District") RequestBody district,
                                        @Part("City") RequestBody city,
                                        @Part("Mobile") RequestBody mobile,
                                        @Part("EmailId") RequestBody emailId,
                                        @Part("Age") RequestBody age,
                                        @Part("DOB") RequestBody dateBirth,
                                         @Part("Height") RequestBody height,
                                         @Part("Weight") RequestBody weight,
                                         @Part("MaritalStatus") RequestBody maritalStatus,
                                        @Part("EmergencyContactPersonRelation") RequestBody emergencyContactPersonRelation,
                                        @Part("EmergencyContactNumber") RequestBody emergencyContactNumber,
                                         @Part("EmergencyContactPerson") RequestBody EmergencyContactPerson,
                                        @Part("HospitalId") RequestBody hospitalId,
                                        @Part MultipartBody.Part AdharCard,
                                         @Part("password") RequestBody password);

 //   Call<Response_registration> postdata(@PartMap Map<String, RequestBody> params);

    @POST("PLogin/PatientLogin")
    @FormUrlEncoded
    Call<LoginResponse> GetLogin(@Field("Mobile") String mobile,
                                     @Field("Password") String password,
                                     @Field("Otp") String otp);

    @POST("ApiDoctorschedule/GetDoctorList")
    @FormUrlEncoded
    Call<DoctorListModel> GetDoctorList(@Field("HospitalID") String hospitalId);

    //Hospital Names
    @GET("gethospital/Get")
    Call<Hospital_list> GetHospitalNames();
}

