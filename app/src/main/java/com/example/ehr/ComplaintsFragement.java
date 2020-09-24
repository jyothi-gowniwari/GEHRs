package com.example.ehr;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.fragment.app.Fragment;

import fr.ganfra.materialspinner.MaterialSpinner;

public class ComplaintsFragement extends Fragment {
    public ComplaintsFragement(){

    }
    private MaterialSpinner doctor_help,complaints,duration;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.complaints, container, false);

        doctor_help=rootView.findViewById(R.id.doctor_help);
        complaints=rootView.findViewById(R.id.complaint);
        duration=rootView.findViewById(R.id.duration);

        String[] doctor_helps={" Share details on your symptoms","concerns"," how you'd like the doctor to help you"," Try to be as descriptive as possible"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, doctor_helps);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        doctor_help.setAdapter(adapter);

        String[] durations={" Hours","Days","Months","Years"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, durations);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        duration.setAdapter(adapter1);

        String[] spec={"Gynecologist","Surgeon","Psychiatrist","Cardiologist","Dermatologist","Endocrinologist"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, spec);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        complaints.setAdapter(adapter);
        doctor_help.setHint(Html.fromHtml ( doctor_help.getHint()+" "));
        duration.setHint(Html.fromHtml ( duration.getHint()+" "+"<font color=\"#ff0000\">" + "* " + "</font>"));
        complaints.setHint(Html.fromHtml ( complaints.getHint()+" "));
        return rootView;
    }
}
