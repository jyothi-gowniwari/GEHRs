package com.example.ehr;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.fragment.app.Fragment;

import fr.ganfra.materialspinner.MaterialSpinner;

public class RescheduleAppointmentFragement extends Fragment {

    public RescheduleAppointmentFragement() {
    }
    private MaterialSpinner reschedule;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.reshedule_appointment, container, false);
        reschedule=rootView.findViewById(R.id.reschedule);
        reschedule.setHint(Html.fromHtml ( reschedule.getHint()+" "));
        String[] who_app={"You need to attend to an urgent workplace matter","You are behind schedule due to personal reasons such as transportation issues"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, who_app);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reschedule.setAdapter(adapter);
        return rootView;
    }
}
