package com.example.ehr;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ehr.Adapter.AppointmentAdapter;
import com.example.ehr.Adapter.DoctorAdapter;
import com.example.ehr.Adapter.SpeclializationAdapter;
import com.example.ehr.Model.DataList;
import com.example.ehr.Model.DoctorListModel;
import com.example.ehr.Model.Doctorlist;
import com.example.ehr.Model.LoginResponse;
import com.example.ehr.remote.APIService;
import com.example.ehr.remote.RetroClass;
import com.google.gson.Gson;

import java.util.ArrayList;

import fr.ganfra.materialspinner.MaterialSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookAppointmentFragement extends Fragment implements DoctorAdapter.SelectIemClickListner,SpeclializationAdapter.MyItemClickListener{
    public BookAppointmentFragement() {
        // Required empty public constructor
    }
    private ProgressDialog progressDialog = null;
    private MaterialSpinner reason_visit,who_appointment;
    private AppointmentAdapter appointmentadapter;
    private RecyclerView recyclerView;
    private ArrayList<String> appointmentModels;
    private RatingBar ratingBar,ratingBar1;
    private Button select;
    private String specilization;
    private RecyclerView recyclerViewDoctor;
    private ArrayList<DataList> specilaistlist;
    private ArrayList<Doctorlist> doctorlists_spe;
    private ArrayList<Doctorlist> doctorlists_spe1;
    private SpeclializationAdapter speclializationAdapter;
    private DoctorAdapter doctorAdapter;
    private String specliality,first_name,Specialization,DoctorName, Qualification, Experience, LanguagesKnown, ProfllePhoto, ConsultationFees, Hospital, HospitalID, DoctorID;
    private GridView gridLayout;
    private ArrayList<DataList> dataLists;
    private Doctorlist doctorlist2;
    private DataList dataList1;
    private TextView name,doctortext;
    int position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.doctor_list, container, false);

        String Fname=getActivity().getIntent().getStringExtra("FName");
        String Lname=getActivity().getIntent().getStringExtra("LName");
        name=rootView.findViewById(R.id.name);
        recyclerViewDoctor=rootView.findViewById(R.id.recyclerDoctor);
        name.setText(Fname.concat(" ").concat(Lname));
        gridLayout=rootView.findViewById(R.id.gridlayout);
        doctortext=rootView.findViewById(R.id.doctortext);
        specilaistlist=new ArrayList<>();
        doctorlists_spe=new ArrayList<>();
        doctorlists_spe1=new ArrayList<>();
        speclializationAdapter = new SpeclializationAdapter(requireContext(), specilaistlist,this);
        doctorAdapter=new DoctorAdapter(requireContext(),doctorlists_spe,this);
        recyclerViewDoctor.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerViewDoctor.setAdapter(doctorAdapter);
        getDoctorsList();
        return rootView;
    }

    private void getDoctorsList() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
        String hospital_id="PH-193";
        final APIService service = RetroClass.getRetrofitInstance().create(APIService.class);
        Call<DoctorListModel> call = service.GetDoctorList(hospital_id);
        Log.wtf("URL Called", call.request().url() + "");
        call.enqueue(new Callback<DoctorListModel>() {
            @Override
            public void onResponse(Call<DoctorListModel> call, Response<DoctorListModel> response) {
                Log.e("response", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    Log.e("response", new Gson().toJson(response.body()));
                    DoctorListModel doctorListModel = response.body();
                    dataLists=doctorListModel.getDataLists();
                    for (DataList dataList : dataLists) {
                    String specilization=dataList.getSpecialization();
                    Log.d("specilization",specilization);
                    DataList dataList1=new DataList(specilization);
                    specilaistlist.add(dataList1);
                    gridLayout.setAdapter(speclializationAdapter);
                    speclializationAdapter.notifyDataSetChanged();
                    }
                }
                progressDialog.dismiss();
            }
            @Override
            public void onFailure(Call<DoctorListModel> call, Throwable t) {
                Log.d("error", t.getMessage());
                progressDialog.dismiss();
            }
        });
    }
    private void loadFragment(Fragment fragment) {
        // load fragment
        androidx.fragment.app.FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.dynamic_frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    @Override
    public void mySelectIemListner(int position) {
        Fragment fragment = new DateTimeFragment();
        String doctor_name=doctorlists_spe.get(position).getDoctorName();
        String doctor_profile=doctorlists_spe.get(position).getProfllePhoto();
        String specilization=doctorlists_spe.get(position).getSpecialization();
        String qualification=doctorlists_spe.get(position).getQualification();
        String experience=doctorlists_spe.get(position).getExperience();
        String languagesKnown=doctorlists_spe.get(position).getLanguagesKnown();
        String hospitalname=doctorlists_spe.get(position).getHospital();
        String consultationfee=doctorlists_spe.get(position).getConsultationFees();
        Bundle args = new Bundle();
        args.putString("doctor_name", String.valueOf(doctor_name));
        args.putString("doctor_profile", String.valueOf(doctor_profile));
        args.putString("specilization", String.valueOf(specilization));
        args.putString("qualification", String.valueOf(qualification));
        args.putString("experience", String.valueOf(experience));
        args.putString("languagesKnown", String.valueOf(languagesKnown));
        args.putString("hospitalname", String.valueOf(hospitalname));
        args.putString("consultationfee", String.valueOf(consultationfee));
        fragment.setArguments(args);
        loadFragment(fragment);
    }

    @Override
    public void myItemClick(final int position) {
        doctorlists_spe.clear();
        final String speclname=specilaistlist.get(position).getSpecialization();
        String hospital_id="PH-193";
        final APIService service = RetroClass.getRetrofitInstance().create(APIService.class);
        Call<DoctorListModel> call = service.GetDoctorList(hospital_id);
        Log.wtf("URL Called", call.request().url() + "");
        call.enqueue(new Callback<DoctorListModel>() {
            @Override
            public void onResponse(Call<DoctorListModel> call, Response<DoctorListModel> response) {
                Log.e("response", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    Log.e("response", new Gson().toJson(response.body()));
                    DoctorListModel doctorListModel = response.body();
                    dataLists = doctorListModel.getDataLists();
                    for (DataList dataList : dataLists) {
                        specilization = dataList.getSpecialization();
                        dataList1 = new DataList(specilization);
                        ArrayList<Doctorlist> doctorlists = dataList.getData();
                        boolean istrue = false;
                        for (Doctorlist doctorlist : doctorlists) {
                            Specialization = doctorlist.getSpecialization();
                            DoctorName = doctorlist.getDoctorName();
                            Qualification = doctorlist.getQualification();
                            Experience = doctorlist.getExperience();
                            LanguagesKnown = doctorlist.getLanguagesKnown();
                            Hospital = doctorlist.getHospital();
                            ConsultationFees = doctorlist.getConsultationFees();
                            ProfllePhoto = doctorlist.getProfllePhoto();
                            HospitalID = doctorlist.getHospitalID();
                           DoctorID = doctorlist.getDoctorID();
                           String rupee = getResources().getString(R.string.Rs);
                        Doctorlist doctorlist2 = new Doctorlist(Specialization, DoctorName, Qualification, Experience, LanguagesKnown, ProfllePhoto, rupee.concat(ConsultationFees), Hospital, HospitalID, DoctorID);
                        doctortext.setVisibility(View.VISIBLE);
                        if (dataList1.getSpecialization().equals(speclname)) {
                            doctorlists_spe.add(doctorlist2);
                            doctorAdapter.notifyDataSetChanged();
                        }
                        else {

                        }
                        }
                    }
                    }

                progressDialog.dismiss();
            }
            @Override
            public void onFailure(Call<DoctorListModel> call, Throwable t) {
                Log.d("error", t.getMessage());
                progressDialog.dismiss();
            }
        });



    }
    public void update(View view){
        doctorAdapter.notifyDataSetChanged();
    }
}
