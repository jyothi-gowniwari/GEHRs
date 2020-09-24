package com.example.ehr;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;

import androidx.fragment.app.Fragment;

public class ViewDoctorProfileFragment extends Fragment {

    public ViewDoctorProfileFragment(){

    }
    private RatingBar ratingBar;
    private Button book_appointment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.select_doctor, container, false);
        book_appointment=rootView.findViewById(R.id.book_appointment);
        ratingBar=rootView.findViewById(R.id.ratingBar);
        ratingBar.setNumStars(5);
        ratingBar.setRating(4);

        book_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new DateTimeFragment();
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
