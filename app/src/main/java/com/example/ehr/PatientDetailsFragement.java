package com.example.ehr;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.fragment.app.Fragment;

import fr.ganfra.materialspinner.MaterialSpinner;

public class PatientDetailsFragement extends Fragment {

    public PatientDetailsFragement(){

    }
    private MaterialSpinner pre_existing_condition,current_medication,allergies;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.patient_details, container, false);

        pre_existing_condition=rootView.findViewById(R.id.pre_existing);
        current_medication=rootView.findViewById(R.id.current_medications);
        allergies=rootView.findViewById(R.id.allergies);

        String[] pre_existing={"Yes","No"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, pre_existing);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pre_existing_condition.setAdapter(adapter);
        pre_existing_condition.setHint(Html.fromHtml ( pre_existing_condition.getHint()+" "));

        String[] current_medications={"Qty","Frequency","Status"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, current_medications);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        current_medication.setAdapter(adapter1);
        current_medication.setHint(Html.fromHtml ( current_medication.getHint()+" "));

        String[] allergie={"Yes","No"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, allergie);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        allergies.setAdapter(adapter2);
        allergies.setHint(Html.fromHtml ( allergies.getHint()+" "));

        return rootView;
    }
}
