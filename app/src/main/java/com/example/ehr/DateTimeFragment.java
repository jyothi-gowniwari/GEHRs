package com.example.ehr;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class DateTimeFragment  extends Fragment {
    public DateTimeFragment(){

    }
    private RatingBar ratingBar;
    private Button book_appointment;
    private String doctor_name,doctorprofile,specilist,qualification,experience,languageknown,hospitalname,consultationfee;
    private TextView doctorName,speclist,tex_qualification,tex_experience,tex_languageknown,tex_hospital,tex_consultationfee;
    private CircleImageView doctor_profile;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.selected_datetime, container, false);
        doctor_name = getArguments().getString("doctor_name");
        doctorprofile=getArguments().getString("doctor_profile");
        specilist=getArguments().getString("specilization");
        qualification=getArguments().getString("qualification");
        experience=getArguments().getString("experience");
        languageknown=getArguments().getString("languagesKnown");
        hospitalname=getArguments().getString("hospitalname");
        consultationfee=getArguments().getString("consultationfee");
        book_appointment=rootView.findViewById(R.id.book_appointment);
        doctorName=rootView.findViewById(R.id.doctor_name);
        doctor_profile=rootView.findViewById(R.id.doctor_profile);
        speclist=rootView.findViewById(R.id.speclist);
        tex_qualification=rootView.findViewById(R.id.qualification);
        tex_qualification.setText(qualification);
        tex_experience=rootView.findViewById(R.id.experience);
        tex_experience.setText(experience);
        tex_languageknown=rootView.findViewById(R.id.languagesKnown);
        tex_languageknown.setText(languageknown);
        tex_hospital=rootView.findViewById(R.id.hospital);
        tex_hospital.setText(hospitalname);
        tex_consultationfee=rootView.findViewById(R.id.consultationFees);
        tex_consultationfee.setText(consultationfee);
        speclist.setText(specilist);
        Picasso.with(getContext())
                .load(doctorprofile)
                .placeholder(R.drawable.ic_round_account_circle_24)
                .into(doctor_profile);

        doctorName.setText(doctor_name);

        book_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ConfirmedFragment();
                loadFragment(fragment);
            }
        });

        return rootView;
    }
    private void loadFragment(Fragment fragment) {
        // load fragment
        androidx.fragment.app.FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.dynamic_frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
