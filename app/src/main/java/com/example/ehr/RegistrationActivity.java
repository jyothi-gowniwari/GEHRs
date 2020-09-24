package com.example.ehr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.BuildConfig;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ehr.Model.HospitalData;
import com.example.ehr.Model.Hospital_list;
import com.example.ehr.Model.LoginResponse;
import com.example.ehr.Model.Response_registration;
import com.example.ehr.Util.FileCompressor;
import com.example.ehr.Util.GenericFileProvider;
import com.example.ehr.Util.ProgressRequestBody;
import com.example.ehr.Util.UploadCallBacks;
import com.example.ehr.remote.APIService;
import com.example.ehr.remote.RetroClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.gson.Gson;
import com.ipaulpro.afilechooser.utils.FileUtils;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.mukesh.OtpView;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import fr.ganfra.materialspinner.MaterialSpinner;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Multipart;

public class RegistrationActivity extends Activity implements UploadCallBacks {

    private static final int REQUEST_PERMISSION = 1000 ;
    private static final int PICK_FILE_REQUEST = 1001;

    private TextInputEditText firstname,lastname,age,contact_no,date_birth,password_edit,confirm_edit;
    private MaterialSpinner UID,Country,state,city,district,emergency_relation,hospitalname;
    private LinearLayout date_births,otp_verify;
    private TextView gender,upload,fingerprints,doc,verification_code,verification_code_subtext;
    OtpView pinFromUser;
    private ArrayList<Hospital_list.HospitalData> hospitalDataList=new ArrayList<>();
    File file;
    private String hosp_id,hosp_name;
    ProgressRequestBody progressRequestBody;
    private Button upload_adhar;
    private DatePickerDialog datePickerDialog;
    private int year,month,dayOfMonth;
    private RelativeLayout relativeLayout2,relativeLayout3,relativeLayout4,relativeLayout5;
    private ArrayAdapter<String> adapter;
    private Button submit,validate_button;
    private LinearLayout upload_linear,upload_linear1;
    private boolean otpsucess;
    private ProgressDialog progressDialog;
    String codeBySystem;
    Calendar calendar;
    private TextView preferred;
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_GALLERY_PHOTO = 2;
    File mPhotoFile;
    private LinearLayout linear_marital;
    FileCompressor mCompressor;
    private String strDate;
    private ImageView calendar1;
    private CheckBox terms;
    Uri selectedFileUri;
    MultipartBody.Part body;
    private ArrayList<String> hospitalNames;
    private String _getUserEnteredPhoneNumber;
    private String ParentHospitalId,ParentHospitalName;
    private RadioGroup gendergroup,marital_status;
    private RadioButton radioButton_gender,radioButton_marital;
    private RelativeLayout page1,page2,page3,page4,page5;
    private RadioButton female,male,other,radioSexButton;
    private ImageView profile,fingerprint,signup_back_button;
    private TextInputLayout countrys,firstnames,lastnames,Uids,contact_nos,genders,ages,emergency_connums,emergency_relations,emergency_contacts,email_ids,
            weights,heights,hospital_names,citys,states,districts,password,confirm_password;
    private Button next,nextscreen,next1,previous5,previous,previous1,previous2,next2,next3,previous3;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            },REQUEST_PERMISSION);
        }
        verification_code=findViewById(R.id.verify_code1);
        nextscreen=findViewById(R.id.nextscreen);
        verification_code_subtext=findViewById(R.id.verify_code);
        pinFromUser=findViewById(R.id.pin_view);
        preferred=findViewById(R.id.preferred);
        calendar1=findViewById(R.id.calendarGridView);
        linear_marital=findViewById(R.id.linear_marital);
        signup_back_button=findViewById(R.id.signup_back_button);
        upload_linear=findViewById(R.id.upload_linear);
        upload_linear1=findViewById(R.id.upload_linear1);
        page1=findViewById(R.id.page1);
        hospitalNames=new ArrayList<>();
        upload_adhar=findViewById(R.id.upload_adhar);
        validate_button=findViewById(R.id.validate_button);
        page2=findViewById(R.id.page2);
        password=findViewById(R.id.etPasswordLayout);
        confirm_password=findViewById(R.id.etConfirmPasswordLayout);
        password_edit=findViewById(R.id.editTextPassword);
        confirm_edit=findViewById(R.id.editTextConfirmPassword);
        page3=findViewById(R.id.page3);
        page4=findViewById(R.id.page4);
        date_birth=findViewById(R.id.date_birth);
        page5=findViewById(R.id.page5);
        next3=findViewById(R.id.next3);
        firstname=findViewById(R.id.firstname);
        upload=findViewById(R.id.upload);
        gendergroup=findViewById(R.id.gender_group);
        marital_status=findViewById(R.id.marital_status);
        doc=findViewById(R.id.doc);

        firstname.setHint(Html.fromHtml ( firstname.getHint()+" "+"<font color=\"#ff0000\">" + "* " + "</font>"));
        password_edit.setHint(Html.fromHtml ( password_edit.getHint()+" "+"<font color=\"#ff0000\">" + "* " + "</font>"));
        confirm_edit.setHint(Html.fromHtml ( confirm_edit.getHint()+" "+"<font color=\"#ff0000\">" + "* " + "</font>"));
        terms=findViewById(R.id.term_conditio);
        lastname=findViewById(R.id.lastname);
        lastname.setHint(Html.fromHtml ( lastname.getHint()+" "+"<font color=\"#ff0000\">" + "* " + "</font>"));
        UID=findViewById(R.id.Uid);
        UID.setHint(Html.fromHtml ( UID.getHint()+" "+"<font color=\"#ff0000\">" + "* " + "</font>"));
        gender=findViewById(R.id.gender);
        gender.setHint(Html.fromHtml ( gender.getHint()+" "+"<font color=\"#ff0000\">" + "* " + "</font>"));
        profile=findViewById(R.id.profile);
        contact_no=findViewById(R.id.contact_no);
        contact_no.setHint(Html.fromHtml ( contact_no.getHint()+" "+"<font color=\"#ff0000\">" + "* " + "</font>"));
        Country=findViewById(R.id.country);
        Country.setHint(Html.fromHtml ( Country.getHint()+" "+"<font color=\"#ff0000\">" + "* " + "</font>"));
        state=findViewById(R.id.state);
        state.setHint(Html.fromHtml ( state.getHint()+" "+"<font color=\"#ff0000\">" + "* " + "</font>"));
        relativeLayout2=findViewById(R.id.relativeLayout2);
        relativeLayout3=findViewById(R.id.relativeLayout3);
        relativeLayout4=findViewById(R.id.relativeLayout4);
        relativeLayout5=findViewById(R.id.relativeLayout5);
        previous2=findViewById(R.id.previous2);
        city=findViewById(R.id.city);
        city.setHint(Html.fromHtml ( city.getHint()+" "+"<font color=\"#ff0000\">" + "* " + "</font>"));
        district=findViewById(R.id.district);
        district.setHint(Html.fromHtml ( district.getHint()+" "+"<font color=\"#ff0000\">" + "* " + "</font>"));
        hospitalname=findViewById(R.id.hospital_name);
        emergency_relation=findViewById(R.id.emergency_relation);
        next=findViewById(R.id.next);
        previous=findViewById(R.id.previous);
        next1=findViewById(R.id.next1);
        next2=findViewById(R.id.next2);
        mCompressor = new FileCompressor(this);
        submit=findViewById(R.id.submit);
        previous1=findViewById(R.id.previous1);
        previous5=findViewById(R.id.previous5);
        countrys=findViewById(R.id.countrys);
        firstnames=findViewById(R.id.firstnames);
        lastnames=findViewById(R.id.lastnames);
        Uids=findViewById(R.id.Uids);
        contact_nos=findViewById(R.id.contact_nos);
        ages=findViewById(R.id.ages);
        previous3=findViewById(R.id.previous3);
        age=findViewById(R.id.age);
        age.setHint(Html.fromHtml ( age.getHint()+" "+"<font color=\"#ff0000\">" + "* " + "</font>"));
        emergency_connums=findViewById(R.id.emergency_connums);
        emergency_relations=findViewById(R.id.emergency_relations);
        emergency_contacts=findViewById(R.id.emergency_contacts);
        email_ids=findViewById(R.id.email_ids);
        weights=findViewById(R.id.weights);
        heights=findViewById(R.id.heights);
        date_births=findViewById(R.id.date_births);
        hospital_names=findViewById(R.id.hospital_names);
        citys=findViewById(R.id.citys);
        states=findViewById(R.id.states);
        districts=findViewById(R.id.districts);

        int selectedId = gendergroup.getCheckedRadioButtonId();

        Log.d("radio", String.valueOf(selectedId));



        fetchHospitalNames();

        String[] ITEMS = {"Aadhar Card", "Voter ID Card", "PAN Card", "Passport", "Driving License"};
        String[] EM_RELATION = {"FATHER", "MOTHER", "FRIEND", "SON", "SISTER"};
        String[] STATES = {"Maharashtra", "Delhi", "Karnataka", "Gujarat", "Telangana", "Tamil Nadu"};
        String[] CITYS = {"Mumbai","Delhi","Bengaluru","Ahmedabad","Hyderabad","Chennai","Kolkata"};
        String[] DISTRICTS = {"Mumbai City","Central Delhi","Bangalore Rural","Ahmedabad","Hyderabad","Chennai","Kolkata"};
      //  String[] HOSPITALS = {"Appollo","Fortis","Appollo Z","AppolloB","AppolloC","B Appllo","Confirm Invoice"};
        String[] COUNTRYS={"India"};

        ArrayAdapter<String> emadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, EM_RELATION);
        emadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> statteadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, STATES);
        statteadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> cityadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, CITYS);
        cityadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> districtadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, DISTRICTS);
        districtadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

       // ArrayAdapter<String> hospitaladapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, HOSPITALS);
       // hospitaladapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> countryadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, COUNTRYS);
        countryadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        UID.setAdapter(adapter);
        state.setAdapter(statteadapter);
        Country.setAdapter(countryadapter);

        city.setAdapter(cityadapter);
        district.setAdapter(districtadapter);
      //  hospitalname.setAdapter(hospitaladapter);
        emergency_relation.setAdapter(emadapter);
//        if(_otpsucess.equals("true")){
//            page1.setVisibility(View.GONE);
//        }else {
//            page1.setVisibility(View.VISIBLE);
//        }
        contact_no.setText("9999999999");

        marital_status.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton_marital=(RadioButton)findViewById(checkedId);
                Toast.makeText(getApplicationContext(),radioButton_marital.getText(),Toast.LENGTH_LONG).show();
            }
        });
        gendergroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton_gender=(RadioButton)findViewById(checkedId);

            }
        });
        upload_adhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                chooseFile();
            }
        });
        nextscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //profile.setVisibility(View.GONE);
                countrys.setVisibility(View.GONE);
                firstnames.setVisibility(View.GONE);
                lastnames.setVisibility(View.GONE);
                password.setVisibility(View.GONE);
                preferred.setVisibility(View.GONE);
                confirm_password.setVisibility(View.GONE);
                Uids.setVisibility(View.GONE);
                contact_nos.setVisibility(View.GONE);
                gendergroup.setVisibility(View.GONE);
                marital_status.setVisibility(View.GONE);
                ages.setVisibility(View.GONE);
                previous.setVisibility(View.GONE);
                states.setVisibility(View.GONE);
                citys.setVisibility(View.GONE);
                districts.setVisibility(View.GONE);
                hospital_names.setVisibility(View.GONE);
                date_births.setVisibility(View.GONE);
                nextscreen.setVisibility(View.GONE);
                previous.setVisibility(View.GONE);
                previous1.setVisibility(View.GONE);
                next1.setVisibility(View.GONE);
                terms.setVisibility(View.GONE);
                relativeLayout2.setVisibility(View.GONE);
                next2.setVisibility(View.GONE);
                profile.setVisibility(View.GONE);
                page1.setVisibility(View.GONE);
                page2.setVisibility(View.GONE);
                page3.setVisibility(View.GONE);
                page4.setVisibility(View.GONE);
                upload_linear.setVisibility(View.GONE);
                signup_back_button.setVisibility(View.GONE);
                verification_code.setVisibility(View.VISIBLE);
                verification_code_subtext.setVisibility(View.VISIBLE);
                pinFromUser.setVisibility(View.VISIBLE);
                validate_button.setVisibility(View.VISIBLE);
                _getUserEnteredPhoneNumber=contact_nos.getEditText().getText().toString().trim();
                String _phoneNo="+"+"91"+_getUserEnteredPhoneNumber;
                verification_code_subtext.setText(verification_code_subtext.getText()+_phoneNo);
                sendVerficationCodeToUser(_phoneNo);


//                String _getUserEnteredPhoneNumber=contact_nos.getEditText().getText().toString().trim();
//
//                String _phoneNo="+"+"91"+_getUserEnteredPhoneNumber;
//                //  Toast.makeText(getApplicationContext(), radioSexButton.getText().toString().trim(), Toast.LENGTH_SHORT).show();
//
//                Intent intent=new Intent(getApplicationContext(),LoginWithOTP.class);
//                intent.putExtra("phoneNo",_phoneNo);
//                startActivity(intent);

            }
        });



      //
        next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next1.setVisibility(View.GONE);
                profile.setVisibility(View.GONE);
                previous1.setVisibility(View.GONE);
                submit.setVisibility(View.GONE);
                terms.setVisibility(View.VISIBLE);
                emergency_connums.setVisibility(View.GONE);
                email_ids.setVisibility(View.GONE);
                emergency_contacts.setVisibility(View.GONE);
                emergency_relations.setVisibility(View.GONE);
                countrys.setVisibility(View.VISIBLE);
                preferred.setVisibility(View.VISIBLE);
                states.setVisibility(View.VISIBLE);
                citys.setVisibility(View.VISIBLE);
                districts.setVisibility(View.VISIBLE);
                hospital_names.setVisibility(View.VISIBLE);
                date_births.setVisibility(View.VISIBLE);
                heights.setVisibility(View.GONE);
                linear_marital.setVisibility(View.GONE);
                terms.setVisibility(View.GONE);
                weights.setVisibility(View.GONE);
                relativeLayout3.setVisibility(View.VISIBLE);
                previous2.setVisibility(View.VISIBLE);
                next2.setVisibility(View.VISIBLE);
                page1.setVisibility(View.GONE);
                page2.setVisibility(View.GONE);
                page3.setVisibility(View.VISIBLE);
                page4.setVisibility(View.GONE);
                upload_linear.setVisibility(View.GONE);
                signup_back_button.setVisibility(View.GONE);
                firstnames.setVisibility(View.GONE);
                lastnames.setVisibility(View.GONE);
                ages.setVisibility(View.GONE);
                password.setVisibility(View.GONE);
                confirm_password.setVisibility(View.GONE);
                gendergroup.setVisibility(View.GONE);
                Uids.setVisibility(View.GONE);

            }
        });
        previous2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                upload.setVisibility(View.GONE);
                doc.setVisibility(View.GONE);
                countrys.setVisibility(View.GONE);
                firstnames.setVisibility(View.VISIBLE);
                lastnames.setVisibility(View.VISIBLE);
                Uids.setVisibility(View.VISIBLE);
                password.setVisibility(View.VISIBLE);
                confirm_password.setVisibility(View.VISIBLE);
                profile.setVisibility(View.GONE);
                contact_nos.setVisibility(View.GONE);
                gendergroup.setVisibility(View.VISIBLE);
                ages.setVisibility(View.VISIBLE);
                previous.setVisibility(View.VISIBLE);
                states.setVisibility(View.GONE);
                citys.setVisibility(View.GONE);
                heights.setVisibility(View.GONE);
                linear_marital.setVisibility(View.GONE);
                weights.setVisibility(View.GONE);
                districts.setVisibility(View.GONE);
                hospital_names.setVisibility(View.GONE);
                preferred.setVisibility(View.GONE);
                date_births.setVisibility(View.GONE);
                next.setVisibility(View.GONE);
                emergency_connums.setVisibility(View.GONE);
                email_ids.setVisibility(View.GONE);
                emergency_contacts.setVisibility(View.GONE);
                emergency_relations.setVisibility(View.GONE);
                previous.setVisibility(View.GONE);
                previous2.setVisibility(View.GONE);
                previous1.setVisibility(View.VISIBLE);
                next1.setVisibility(View.VISIBLE);
                submit.setVisibility(View.GONE);
                terms.setVisibility(View.GONE);
                next2.setVisibility(View.GONE);
                previous3.setVisibility(View.GONE);
                page1.setVisibility(View.GONE);
                page2.setVisibility(View.VISIBLE);
                page3.setVisibility(View.GONE);
                page4.setVisibility(View.GONE);
                page5.setVisibility(View.GONE);
                upload_linear.setVisibility(View.GONE);
                signup_back_button.setVisibility(View.GONE);
            }
        });
        previous1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                upload.setVisibility(View.GONE);
                doc.setVisibility(View.GONE);
                countrys.setVisibility(View.GONE);
                profile.setVisibility(View.GONE);
                terms.setVisibility(View.GONE);
                countrys.setVisibility(View.GONE);
                firstnames.setVisibility(View.GONE);
                lastnames.setVisibility(View.GONE);
                Uids.setVisibility(View.GONE);
                password.setVisibility(View.GONE);
                confirm_password.setVisibility(View.GONE);
                contact_nos.setVisibility(View.VISIBLE);
                gendergroup.setVisibility(View.GONE);
                ages.setVisibility(View.GONE);
                previous.setVisibility(View.GONE);
                states.setVisibility(View.GONE);
                citys.setVisibility(View.GONE);
                heights.setVisibility(View.GONE);
                linear_marital.setVisibility(View.GONE);
                weights.setVisibility(View.GONE);
                districts.setVisibility(View.GONE);
                hospital_names.setVisibility(View.GONE);
                date_births.setVisibility(View.GONE);
                next.setVisibility(View.GONE);
                nextscreen.setVisibility(View.VISIBLE);
                emergency_connums.setVisibility(View.GONE);
                email_ids.setVisibility(View.GONE);
                emergency_contacts.setVisibility(View.GONE);
                emergency_relations.setVisibility(View.GONE);
                previous.setVisibility(View.GONE);
                previous2.setVisibility(View.GONE);
                previous1.setVisibility(View.GONE);
                next1.setVisibility(View.GONE);
                submit.setVisibility(View.GONE);
                terms.setVisibility(View.GONE);
                previous3.setVisibility(View.GONE);
                page1.setVisibility(View.VISIBLE);
                page2.setVisibility(View.GONE);
                page3.setVisibility(View.GONE);
                page4.setVisibility(View.GONE);
                page5.setVisibility(View.GONE);
                upload_linear.setVisibility(View.GONE);
                signup_back_button.setVisibility(View.VISIBLE);
            }
        });

//        previous.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fingerprint.setVisibility(View.GONE);
//                upload.setVisibility(View.GONE);
//                doc.setVisibility(View.GONE);
//                fingerprints.setVisibility(View.GONE);
//                countrys.setVisibility(View.GONE);
//                firstnames.setVisibility(View.GONE);
//                lastnames.setVisibility(View.GONE);
//                Uids.setVisibility(View.GONE);
//                contact_nos.setVisibility(View.VISIBLE);
//                gendergroup.setVisibility(View.GONE);
//                ages.setVisibility(View.GONE);
//                next.setVisibility(View.GONE);
//                next1.setVisibility(View.VISIBLE);
//                previous.setVisibility(View.GONE);
//                states.setVisibility(View.GONE);
//                citys.setVisibility(View.GONE);
//                hospital_names.setVisibility(View.GONE);
//                date_births.setVisibility(View.GONE);
//                heights.setVisibility(View.GONE);
//                weights.setVisibility(View.GONE);
//                previous3.setVisibility(View.GONE);
//                page1.setVisibility(View.VISIBLE);
//                page2.setVisibility(View.GONE);
//                page3.setVisibility(View.GONE);
//                page4.setVisibility(View.GONE);
//                page5.setVisibility(View.GONE);
//                upload_linear.setVisibility(View.GONE);
//                signup_back_button.setVisibility(View.GONE);
//            }
//        });
        next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile.setVisibility(View.GONE);
                upload.setVisibility(View.GONE);
                doc.setVisibility(View.GONE);
                heights.setVisibility(View.VISIBLE);
                linear_marital.setVisibility(View.VISIBLE);
                submit.setVisibility(View.GONE);
                preferred.setVisibility(View.GONE);
                weights.setVisibility(View.VISIBLE);
                email_ids.setVisibility(View.VISIBLE);
                marital_status.setVisibility(View.VISIBLE);
                emergency_connums.setVisibility(View.VISIBLE);
                emergency_contacts.setVisibility(View.VISIBLE);
                emergency_relations.setVisibility(View.VISIBLE);
                terms.setVisibility(View.GONE);
                countrys.setVisibility(View.GONE);
                states.setVisibility(View.GONE);
                citys.setVisibility(View.GONE);
                districts.setVisibility(View.GONE);
                hospital_names.setVisibility(View.GONE);
                date_births.setVisibility(View.GONE);
                next2.setVisibility(View.GONE);
                next3.setVisibility(View.VISIBLE);
                relativeLayout4.setVisibility(View.VISIBLE);
                previous3.setVisibility(View.VISIBLE);
                previous.setVisibility(View.GONE);
                previous1.setVisibility(View.GONE);
                previous2.setVisibility(View.GONE);
                page1.setVisibility(View.GONE);
                page2.setVisibility(View.GONE);
                page3.setVisibility(View.GONE);
                page4.setVisibility(View.VISIBLE);
                upload_linear.setVisibility(View.GONE);
                signup_back_button.setVisibility(View.GONE);
            }
        });

        next3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile.setVisibility(View.VISIBLE);
                upload.setVisibility(View.VISIBLE);
                preferred.setVisibility(View.GONE);
                doc.setVisibility(View.VISIBLE);
                heights.setVisibility(View.GONE);
                linear_marital.setVisibility(View.GONE);
                page5.setVisibility(View.VISIBLE);
                submit.setVisibility(View.VISIBLE);
                weights.setVisibility(View.GONE);
                email_ids.setVisibility(View.GONE);
                emergency_connums.setVisibility(View.GONE);
                emergency_contacts.setVisibility(View.GONE);
                emergency_relations.setVisibility(View.GONE);
                terms.setVisibility(View.VISIBLE);
                countrys.setVisibility(View.GONE);
                states.setVisibility(View.GONE);
                citys.setVisibility(View.GONE);
                districts.setVisibility(View.GONE);
                hospital_names.setVisibility(View.GONE);
                date_births.setVisibility(View.GONE);
                next2.setVisibility(View.GONE);
                relativeLayout4.setVisibility(View.GONE);
                relativeLayout5.setVisibility(View.VISIBLE);
                previous5.setVisibility(View.VISIBLE);
                previous3.setVisibility(View.VISIBLE);
                previous.setVisibility(View.GONE);
                previous1.setVisibility(View.GONE);
                previous2.setVisibility(View.GONE);
                page1.setVisibility(View.GONE);
                page2.setVisibility(View.GONE);
                page3.setVisibility(View.GONE);
                page4.setVisibility(View.GONE);
                upload_linear1.setVisibility(View.VISIBLE);
                signup_back_button.setVisibility(View.GONE);
            }
        });
        previous3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload.setVisibility(View.GONE);
                doc.setVisibility(View.GONE);
                countrys.setVisibility(View.VISIBLE);
                firstnames.setVisibility(View.GONE);
                lastnames.setVisibility(View.GONE);
                Uids.setVisibility(View.GONE);
                contact_nos.setVisibility(View.GONE);
                gendergroup.setVisibility(View.GONE);
                ages.setVisibility(View.GONE);
                next.setVisibility(View.GONE);
                states.setVisibility(View.VISIBLE);
                citys.setVisibility(View.VISIBLE);
                preferred.setVisibility(View.VISIBLE);
                hospital_names.setVisibility(View.VISIBLE);
                date_births.setVisibility(View.VISIBLE);
                heights.setVisibility(View.GONE);
                linear_marital.setVisibility(View.GONE);
                weights.setVisibility(View.GONE);
                email_ids.setVisibility(View.GONE);
                emergency_relations.setVisibility(View.GONE);
                emergency_contacts.setVisibility(View.GONE);
                terms.setVisibility(View.GONE);
                emergency_connums.setVisibility(View.GONE);
                profile.setVisibility(View.GONE);
                submit.setVisibility(View.GONE);
                relativeLayout4.setVisibility(View.VISIBLE);
                previous2.setVisibility(View.VISIBLE);
                previous3.setVisibility(View.GONE);
                previous1.setVisibility(View.GONE);
                previous.setVisibility(View.GONE);
                next2.setVisibility(View.VISIBLE);
                next3.setVisibility(View.GONE);
                page1.setVisibility(View.GONE);
                page2.setVisibility(View.GONE);
                page3.setVisibility(View.VISIBLE);
                page4.setVisibility(View.GONE);
                page5.setVisibility(View.GONE);
                upload_linear1.setVisibility(View.GONE);
                upload_linear.setVisibility(View.GONE);
                signup_back_button.setVisibility(View.GONE);
            }
        });

        previous5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page4.setVisibility(View.VISIBLE);
                page5.setVisibility(View.GONE);
                upload.setVisibility(View.GONE);
                submit.setVisibility(View.GONE);
                profile.setVisibility(View.GONE);
                doc.setVisibility(View.GONE);
                previous5.setVisibility(View.GONE);
                previous2.setVisibility(View.GONE);
                upload_linear.setVisibility(View.GONE);
                terms.setVisibility(View.GONE);
                heights.setVisibility(View.VISIBLE);
                linear_marital.setVisibility(View.VISIBLE);
                preferred.setVisibility(View.GONE);
                upload_linear1.setVisibility(View.GONE);
                weights.setVisibility(View.VISIBLE);
                email_ids.setVisibility(View.VISIBLE);
                emergency_relations.setVisibility(View.VISIBLE);
                emergency_contacts.setVisibility(View.VISIBLE);
                emergency_connums.setVisibility(View.VISIBLE);
                previous3.setVisibility(View.VISIBLE);
                relativeLayout4.setVisibility(View.VISIBLE);
                next2.setVisibility(View.GONE);
            }
        });


        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        date_birth.setText(date);
        calendar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get Current Date
                final Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                dayOfMonth = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(RegistrationActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                date_birth.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });

        signup_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitData();
            }
        });
        return;
    }

    private void fetchHospitalNames() {

//        progressDialog = new ProgressDialog(RegistrationActivity.this);
//        progressDialog.setIndeterminate(true);
//        progressDialog.setMessage("Loading...");
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.setCancelable(false);
//        progressDialog.show();
        final APIService service = RetroClass.getRetrofitInstance().create(APIService.class);
        Call<Hospital_list> call = service.GetHospitalNames();
        Log.wtf("URL Called", call.request().url() + "");
        call.enqueue(new Callback<Hospital_list>() {
            @Override
            public void onResponse(Call<Hospital_list> call, Response<Hospital_list> response) {

                Log.e("response", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    Log.e("response", new Gson().toJson(response.body()));
                    Hospital_list hospital_list = response.body();
                    final List<Hospital_list.HospitalData> hospitalList = hospital_list.data;
                    for (Hospital_list.HospitalData hospitalList1 : hospitalList) {
                        ParentHospitalId = hospitalList1.getParentHospitalId();
                        ParentHospitalName = hospitalList1.getParentHospitalName();
                        Hospital_list.HospitalData hospitalDataList1 = new Hospital_list.HospitalData(ParentHospitalId,ParentHospitalName);
                        hospitalDataList.add(hospitalDataList1);
                        hospitalNames.add(ParentHospitalName);
                    }
                    final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                            getApplicationContext(),R.layout.spinner_item,hospitalNames){// dataAdapter.setDropDownViewResource(android.R.borderdashboard.simple_spinner_dropdown_item);
                        //spinnerlocation.setAdapter(dataAdapter);


                        @Override
                        public boolean isEnabled(int position){
                            if(position == 0)
                            {
                                // Disable the first item from Spinner
                                // First item will be use for hint
                                // spinnerlocation.setPrompt("Select");

                                return false;
                            }
                            else
                            {
                                return true;
                            }
                        }
                        @Override
                        public View getDropDownView(int position, View convertView,
                                                    ViewGroup parent) {
                            View view = super.getDropDownView(position, convertView, parent);
                            TextView tv = (TextView) view;

                            if(position+1 == 0){
                                // Set the hint multipart/form-data color gray

                                tv.setTextColor(Color.GRAY);
                            }
                            else {
                                tv.setTextColor(Color.BLACK);
                            }
                            return view;
                        }
                    };
                    // ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(), android.R.borderdashboard.simple_spinner_item, taskTypes);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    hospitalname.setAdapter(spinnerArrayAdapter);
                    ArrayAdapter<String> adapter=(ArrayAdapter<String>) hospitalname.getAdapter();
                    hospitalname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            if (position + 1 > 0) {
                                Hospital_list.HospitalData hospname = hospitalDataList.get(position);
                                hosp_id = hospname.getParentHospitalId();
                                hosp_name = hospname.getParentHospitalName();

                            Toast.makeText(getApplicationContext(),hosp_id,Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<Hospital_list> call, Throwable t) {
                Log.d("error", t.getMessage());
                progressDialog.dismiss();
                //  llProgressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_PERMISSION:
            {
                 if(grantResults.length>0 &&grantResults[0]==PackageManager.PERMISSION_GRANTED){

                     Toast.makeText(this,"permission granted",Toast.LENGTH_LONG).show();
                 }else {
                     Toast.makeText(this,"permission denined",Toast.LENGTH_LONG).show();
                 }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_FILE_REQUEST) {
                if (data != null) {
                    selectedFileUri = data.getData();
                    if (selectedFileUri != null && !selectedFileUri.getPath().isEmpty()){

                    }else {
                        Toast.makeText(this,"Cannot upload file to Server",Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    private void chooseFile() {
        Intent intent= Intent.createChooser(FileUtils.createGetContentIntent(),"Select a file");
        startActivityForResult(intent,PICK_FILE_REQUEST);
    }



    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Gallery", "Cancel"};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(RegistrationActivity.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    RegistrationActivity.this.requestStoragePermission(true);
                } else if (items[item].equals("Choose from Gallery")) {
                    RegistrationActivity.this.requestStoragePermission(false);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    /**
     * Capture image from camera
     */
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
                // Error occurred while creating the File
            }
            if (photoFile != null) {
                Uri photoURI = GenericFileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", photoFile);

                mPhotoFile = photoFile;
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);

            }
        }
    }


    /**
     * Select image fro gallery
     */
    private void dispatchGalleryIntent() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO);
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            if (requestCode == REQUEST_TAKE_PHOTO) {
//                try {
//                    mPhotoFile = mCompressor.compressToFile(mPhotoFile);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                Glide.with(getApplicationContext()).load(mPhotoFile).apply(new RequestOptions().centerCrop().circleCrop().placeholder(R.drawable.profile_pic_place_holder)).into(profile);
//
//            } else if (requestCode == REQUEST_GALLERY_PHOTO) {
//                Uri selectedImage = data.getData();
//                try {
//                    mPhotoFile = mCompressor.compressToFile(new File(getRealPathFromUri(selectedImage)));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                Glide.with(getApplicationContext()).load(mPhotoFile).apply(new RequestOptions().centerCrop().circleCrop().placeholder(R.drawable.profile_pic_place_holder)).into(profile);
//
//            }
//        }
//    }

    /**
     * Requesting multiple permissions (storage and camera) at once
     * This uses multiple permission model from dexter
     * On permanent denial opens settings dialog
     */
    private void requestStoragePermission(final boolean isCamera) {
        Dexter.withActivity(this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            if (isCamera) {
                                dispatchTakePictureIntent();
                            } else {
                                dispatchGalleryIntent();
                            }
                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }


                }).withErrorListener(new PermissionRequestErrorListener() {
            @Override
            public void onError(DexterError error) {
                Toast.makeText(RegistrationActivity.this.getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
            }
        })
                .onSameThread()
                .check();
    }


    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                RegistrationActivity.this.openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    /**
     * Create file with current timestamp name
     *
     * @return
     * @throws IOException
     */
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String mFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File mFile = File.createTempFile(mFileName, ".jpg", storageDir);
        return mFile;
    }

    /**
     * Get real file path from URI
     *
     * @param contentUri
     * @return
     */
    public String getRealPathFromUri(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = getContentResolver().query(contentUri, proj, null, null, null);
            assert cursor != null;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private void submitData() {

        progressDialog = new ProgressDialog(RegistrationActivity.this);
        progressDialog.setMessage(getString(R.string.Loading));
        progressDialog.setCancelable(false);
        progressDialog.show();
        //Defining retrofit api service


        final String firstName=firstnames.getEditText().getText().toString().trim();
        final String lastName=lastnames.getEditText().getText().toString().trim();
        final String age=ages.getEditText().getText().toString().trim();
        final String selected_gender=radioButton_gender.getText().toString().trim();
        final String passwords=password.getEditText().getText().toString().trim();
        String confirm_passwords=confirm_password.getEditText().getText().toString().trim();
        String selected_country=Country.getSelectedItem().toString().trim();
        final String selected_state=state.getSelectedItem().toString().trim();
        final String selected_city=city.getSelectedItem().toString().trim();
        final String selected_district=district.getSelectedItem().toString().trim();
        final String selected_hospital=hosp_id;
        final String selected_dob=date_birth.getText().toString().trim();
        final String selected_height=heights.getEditText().getText().toString().trim();
        final String selected_weight=weights.getEditText().getText().toString().trim();
        final String selected_emailid=email_ids.getEditText().getText().toString().trim();
        String selected_emergency_contact_person=emergency_contacts.getEditText().getText().toString().trim();
        final String selected_emergency_contact_relation=emergency_relation.getSelectedItem().toString().trim();
        final String slected_emergency_contactnum=emergency_connums.getEditText().getText().toString().trim();
        final String selected_marital=radioButton_marital.getText().toString().trim();
        RequestBody firstName1 =RequestBody.create(MediaType.parse("multipart/form-data"), firstName);
        Log.d("firstname", String.valueOf(firstName1));
        RequestBody selected_dob1 =RequestBody.create(MediaType.parse("multipart/form-data"), selected_dob);
        Log.d("selected_dob1", String.valueOf(selected_dob1));
        RequestBody lastName1 =RequestBody.create(MediaType.parse("multipart/form-data"), lastName);
        Log.d("selected_dob1", String.valueOf(selected_dob1));
        RequestBody Passwords1 =RequestBody.create(MediaType.parse("multipart/form-data"), passwords);
        Log.d("selected_dob1", String.valueOf(selected_dob1));
        RequestBody selected_gender1 =RequestBody.create(MediaType.parse("multipart/form-data"), selected_gender);
        Log.d("selected_dob1", String.valueOf(selected_dob1));
        RequestBody selected_state1 =RequestBody.create(MediaType.parse("multipart/form-data"), selected_state);
        Log.d("selected_dob1", String.valueOf(selected_dob1));
        RequestBody selected_district1 =RequestBody.create(MediaType.parse("multipart/form-data"), selected_district);
        Log.d("selected_dob1", String.valueOf(selected_dob1));
        RequestBody selected_city1 =RequestBody.create(MediaType.parse("multipart/form-data"), selected_city);
        Log.d("selected_dob1", String.valueOf(selected_dob1));
        RequestBody _getUserEnteredPhoneNumber1 =RequestBody.create(MediaType.parse("multipart/form-data"), _getUserEnteredPhoneNumber);
        Log.d("Mobile", String.valueOf(_getUserEnteredPhoneNumber1));
        RequestBody selected_emailid1 =RequestBody.create(MediaType.parse("multipart/form-data"), selected_emailid);
        Log.d("selected_emailid1", String.valueOf(selected_emailid1));
        RequestBody selected_country1 =RequestBody.create(MediaType.parse("multipart/form-data"), selected_country);
        Log.d("selected_country1", String.valueOf(selected_country1));
        RequestBody age1 =RequestBody.create(MediaType.parse("multipart/form-data"), age);
        Log.d("age1", String.valueOf(age1));
        RequestBody selected_height1 =RequestBody.create(MediaType.parse("multipart/form-data"), selected_height);
        Log.d("selected_height1", String.valueOf(selected_height1));
        RequestBody selected_weight1 =RequestBody.create(MediaType.parse("multipart/form-data"), selected_weight);
        Log.d("selected_weight1", String.valueOf(selected_weight1));
        RequestBody selected_marital1 =RequestBody.create(MediaType.parse("multipart/form-data"), selected_marital);
        Log.d("selected_marital1", String.valueOf(selected_marital1));
        RequestBody slected_emergency_contactnum1 =RequestBody.create(MediaType.parse("multipart/form-data"), slected_emergency_contactnum);
        Log.d("contact_num", String.valueOf(slected_emergency_contactnum1));
        RequestBody selected_emergency_contact_relation1 =RequestBody.create(MediaType.parse("multipart/form-data"), selected_emergency_contact_relation);
        Log.d("contact_relation", String.valueOf(selected_emergency_contact_relation1));
        RequestBody selected_emergency_contact_person1 =RequestBody.create(MediaType.parse("multipart/form-data"), selected_emergency_contact_person);
        Log.d("contact_person", String.valueOf(selected_emergency_contact_person1));
        RequestBody selected_hospital1 =RequestBody.create(MediaType.parse("multipart/form-data"), selected_hospital);
        Log.d("selected_hospital1", String.valueOf(selected_hospital1));

            file=FileUtils.getFile(this,selectedFileUri);

        RequestBody requestFile=RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part requestImage= MultipartBody.Part.createFormData("AdharCard",file.getName(),requestFile);
         final APIService service = RetroClass.getRetrofitInstance().create(APIService.class);
        Call<Response_registration> call = service.postData(firstName1,lastName1,selected_gender1,selected_country1,selected_state1,selected_district1,
                selected_city1,_getUserEnteredPhoneNumber1,selected_emailid1,age1,selected_dob1,selected_height1,selected_weight1,selected_marital1,
                selected_emergency_contact_person1,selected_emergency_contact_relation1,slected_emergency_contactnum1,selected_hospital1,requestImage,Passwords1);
        Log.wtf("URL Called", call.request().url() + "");
        call.enqueue(new Callback<Response_registration>() {
            @Override
            public void onResponse(Call<Response_registration> call, Response<Response_registration> response) {

                Log.e("response", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    Log.e("response", new Gson().toJson(response.body()));
                    Response_registration response_registration = response.body();
                    String Status = response_registration.getStatus();
                    if (Status.equals("true"))
                        Toast.makeText(getApplicationContext(), response_registration.getMessage(), Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();

                   // ResponseBody login = response.body();
//                    String success = login.getStatus();
//                    if (success.equals("true")) {
//                        progressDialog.dismiss();
//                        Toast.makeText(getApplicationContext(),login.getMessage(),Toast.LENGTH_LONG).show();
//                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }else {
//                        progressDialog.dismiss();
//                        Toast.makeText(getApplicationContext(),login.getStatus(),Toast.LENGTH_LONG).show();
//
//                    }
                }
                else {
                    progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Response_registration> call, Throwable t) {

                progressDialog.dismiss();
                Log.d("error", t.getMessage());
                //  llProgressBar.setVisibility(View.GONE);
            }
        });
//        RequestBody filename = RequestBody.create(MediaType.parse("multipart/form-data"), file.getName());
//        RequestBody firstName1 =RequestBody.create(MediaType.parse("multipart/form-data"), firstName);
//        RequestBody lastName1 =RequestBody.create(MediaType.parse("multipart/form-data"), lastName);
//        RequestBody Passwords1 =RequestBody.create(MediaType.parse("multipart/form-data"), passwords);
//        RequestBody selected_gender1 =RequestBody.create(MediaType.parse("multipart/form-data"), selected_gender);
//        RequestBody selected_country1 =RequestBody.create(MediaType.parse("multipart/form-data"), selected_state);
//        RequestBody selected_district1 =RequestBody.create(MediaType.parse("multipart/form-data"), selected_district);
//        RequestBody selected_city1 =RequestBody.create(MediaType.parse("multipart/form-data"), selected_city);
//        RequestBody _getUserEnteredPhoneNumber1 =RequestBody.create(MediaType.parse("multipart/form-data"), _getUserEnteredPhoneNumber);
//        RequestBody selected_emailid1 =RequestBody.create(MediaType.parse("multipart/form-data"), selected_emailid);
//        RequestBody age1 =RequestBody.create(MediaType.parse("multipart/form-data"), age);
//        RequestBody selected_height1 =RequestBody.create(MediaType.parse("multipart/form-data"), selected_height);
//        RequestBody selected_weight1 =RequestBody.create(MediaType.parse("multipart/form-data"), selected_weight);
//        RequestBody selected_marital1 =RequestBody.create(MediaType.parse("multipart/form-data"), selected_marital);
//        RequestBody slected_emergency_contactnum1 =RequestBody.create(MediaType.parse("multipart/form-data"), slected_emergency_contactnum);
//        RequestBody selected_emergency_contact_relation1 =RequestBody.create(MediaType.parse("multipart/form-data"), selected_emergency_contact_relation);
//        RequestBody selected_emergency_contact_person1 =RequestBody.create(MediaType.parse("multipart/form-data"), selected_emergency_contact_person);
//        RequestBody selected_hospital1 =RequestBody.create(MediaType.parse("multipart/form-data"), selected_hospital);
//        RequestBody date_birth11 =RequestBody.create(MediaType.parse("multipart/form-data"), date_birth1);
//
//        Map<String, RequestBody> requestBodyMap = new HashMap<>();
//        requestBodyMap.put("FName", firstName1);
//        requestBodyMap.put("LName", lastName1);
//        requestBodyMap.put("Password", Passwords1);
//        requestBodyMap.put("Gender", selected_gender1);
//        requestBodyMap.put("State", selected_country1);
//        requestBodyMap.put("District", selected_district1);
//        requestBodyMap.put("City", selected_city1);
//        requestBodyMap.put("Mobile", _getUserEnteredPhoneNumber1);
//        requestBodyMap.put("EmailId", selected_emailid1);
//        requestBodyMap.put("Age", age1);
//        requestBodyMap.put("Height", selected_height1);
//        requestBodyMap.put("Weight", selected_weight1);
//        requestBodyMap.put("MaritalStatus", selected_marital1);
//        requestBodyMap.put("EmergencyContactPerson", selected_emergency_contact_person1);
//        requestBodyMap.put("EmergencyContactPersonRelation", selected_emergency_contact_relation1);
//        requestBodyMap.put("EmergencyContactNumber",slected_emergency_contactnum1 );
//        requestBodyMap.put("HospitalId", selected_hospital1);
//        requestBodyMap.put("DOB", date_birth11);
//        requestBodyMap.put("AdharCard", filename);
//
//
//
//       // RequestBody filename = RequestBody.create(MediaType.parse("multipart/form-data"), "json");
////        RequestBody requestBody = new MultipartBody.Builder()
////                .setType(MultipartBody.FORM)
////                .addFormDataPart("FName", firstName)
////                .addFormDataPart("LName", lastName)
////                .addFormDataPart("Password", passwords)
////                .addFormDataPart("Gender", selected_gender)
////                .addFormDataPart("Country", selected_country)
////                .addFormDataPart("State", selected_state)
////                .addFormDataPart("District", selected_district)
////                .addFormDataPart("City", selected_city)
////                .addFormDataPart("Mobile", _getUserEnteredPhoneNumber)
////                .addFormDataPart("EmailId", selected_emailid)
////                .addFormDataPart("Age", age)
////                .addFormDataPart("Height", selected_height)
////                .addFormDataPart("Weight", selected_weight)
////                .addFormDataPart("MaritalStatus", selected_marital)
////                .addFormDataPart("EmergencyContactPerson", selected_emergency_contact_person)
////                .addFormDataPart("EmergencyContactPersonRelation", selected_emergency_contact_relation)
////                .addFormDataPart("EmergencyContactNumber", slected_emergency_contactnum)
////                .addFormDataPart("HospitalId", selected_hospital)
////                .addFormDataPart("AdharCard", String.valueOf(filename))
////                .addFormDataPart("DOB", date_birth.getText().toString().trim())
//              //  .build();
//        final APIService service = RetroClass.getRetrofitInstance().create(APIService.class);
////        final Call<Response_registration> call = service.postData(firstName,lastName,age,selected_city,selected_country,selected_district,selected_dob,selected_emailid,selected_emergency_contact_person,selected_emergency_contact_relation,selected_gender,
////                selected_height,selected_hospital,selected_marital,selected_state,selected_weight,fileToUpload,filename,_getUserEnteredPhoneNumber);
////                //calling the api
//        final Call<Response_registration> call = service.postdata(requestBodyMap);
//                new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                call.enqueue(new Callback<Response_registration>() {
//                    @Override
//                    public void onResponse(Call<Response_registration> call, Response<Response_registration> response) {
//                        //hiding progress dialog
//                        progressDialog.dismiss();
//                        Log.e("response", new Gson().toJson(response.body()));
//                        if(response.isSuccessful()){
//                            Toast.makeText(getApplicationContext(), (CharSequence) response.body(), Toast.LENGTH_LONG).show();
//                            Toast.makeText(getApplicationContext(), "Successefully Registred!", Toast.LENGTH_LONG).show();
//                            Log.e("response", new Gson().toJson(response.body()));
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Response_registration> call, Throwable t) {
//                        progressDialog.dismiss();
//                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
//                    }
//                });
//            }
//        }).start();



    }

    private void sendVerficationCodeToUser(String phoneNo) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNo,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    codeBySystem=s;
                }

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    String code =phoneAuthCredential.getSmsCode();
                    if(code!=null){
                        pinFromUser.setText(code);
                        verifyCode(code);
                    }

                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {

                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            };

    private void verifyCode(String code) {

        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(codeBySystem,code);
        signInWithPhoneAuthCredential(credential);

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),"Verification Completed!",Toast.LENGTH_LONG).show();
                            countrys.setVisibility(View.GONE);
                            firstnames.setVisibility(View.VISIBLE);
                            lastnames.setVisibility(View.VISIBLE);
                            password.setVisibility(View.VISIBLE);
                            confirm_password.setVisibility(View.VISIBLE);
                            Uids.setVisibility(View.VISIBLE);
                            upload_linear.setVisibility(View.VISIBLE);
                            contact_nos.setVisibility(View.GONE);
                            gendergroup.setVisibility(View.VISIBLE);
                            ages.setVisibility(View.VISIBLE);
                            previous.setVisibility(View.GONE);
                            marital_status.setVisibility(View.GONE);
                            states.setVisibility(View.GONE);
                            citys.setVisibility(View.GONE);
                            districts.setVisibility(View.GONE);
                            hospital_names.setVisibility(View.GONE);
                            date_births.setVisibility(View.GONE);
                            next.setVisibility(View.GONE);
                            previous.setVisibility(View.GONE);
                            previous1.setVisibility(View.VISIBLE);
                            next1.setVisibility(View.VISIBLE);
                            terms.setVisibility(View.GONE);
                            relativeLayout2.setVisibility(View.VISIBLE);
                            next2.setVisibility(View.GONE);
                            profile.setVisibility(View.GONE);
                            page1.setVisibility(View.GONE);
                            page2.setVisibility(View.VISIBLE);
                            page3.setVisibility(View.GONE);
                            page4.setVisibility(View.GONE);
                            signup_back_button.setVisibility(View.GONE);
                            verification_code.setVisibility(View.GONE);
                            verification_code_subtext.setVisibility(View.GONE);
                            pinFromUser.setVisibility(View.GONE);
                            validate_button.setVisibility(View.GONE);

                        } else {

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(getApplicationContext(),"Verification Not completed! Try Again",Toast.LENGTH_LONG).show();
                                // The verification code entered was invalid


                            }
                        }
                    }
                });
    }

    public void callNextScreenFromOTP(View view){

        String code=pinFromUser.getText().toString();
        if(!code.isEmpty()){
            verifyCode(code);
        }

    }


    private Boolean validateName(){
        String val=firstnames.getEditText().getText().toString().trim();
        String _getLastName=lastnames.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            firstname.setError("Field cannot be empty");
            return false;
        }else {
            firstname.setError(null);
            return true;
        }


    }


    @Override
    public void onProgressUpdate(int percentage) {

    }
}
