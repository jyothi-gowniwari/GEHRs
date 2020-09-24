package com.example.ehr.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ehr.Model.DataList;
import com.example.ehr.Model.DoctorListModel;
import com.example.ehr.Model.Doctorlist;
import com.example.ehr.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<Doctorlist> doctorListModels;
    private DoctorAdapter.SelectIemClickListner selectIemClickListner;
    public DoctorAdapter(Context context,ArrayList<Doctorlist> doctorListModels,DoctorAdapter.SelectIemClickListner selectIemClickListner){
    mContext=context;
    this.doctorListModels=doctorListModels;
    this.selectIemClickListner=selectIemClickListner;
    }
    @Override
    public DoctorAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.appointment_booking, parent, false);
        DoctorAdapter.MyViewHolder holder = new DoctorAdapter.MyViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull DoctorAdapter.MyViewHolder holder, final int position) {

            holder.doctor_name.setText(doctorListModels.get(position).getDoctorName());
            holder.specialization.setText(doctorListModels.get(position).getSpecialization());
            holder.qualification.setText(doctorListModels.get(position).getQualification());
            holder.experience.setText(doctorListModels.get(position).getExperience());
            holder.languageknown.setText(doctorListModels.get(position).getLanguagesKnown());
            holder.hospital.setText(doctorListModels.get(position).getHospital());
            holder.consultationfee.setText(doctorListModels.get(position).getConsultationFees());
           // holder.doctor_profile.setImageResource(Integer.parseInt(doctorListModels.get(position).getProfllePhoto()));
        Picasso.with(mContext)
                .load(doctorListModels.get(position).getProfllePhoto())
                .placeholder(R.drawable.ic_round_account_circle_24)
                .into(holder.doctor_profile);
        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectIemClickListner.mySelectIemListner(position);
            }
        });

    }
    @Override
    public int getItemCount() {
        return doctorListModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView doctor_profile;
        TextView doctor_name,specialization,qualification,experience,languageknown,hospital,consultationfee;
        Button select;
        RatingBar ratingBar;
        public MyViewHolder(View itemView) {
            super(itemView);
            doctor_profile=itemView.findViewById(R.id.doctor_profile);
            doctor_name=itemView.findViewById(R.id.doctor_name);
            specialization=itemView.findViewById(R.id.specialization);
            qualification=itemView.findViewById(R.id.qualification);
            experience=itemView.findViewById(R.id.experience);
            languageknown=itemView.findViewById(R.id.languagesKnown);
            hospital=itemView.findViewById(R.id.hospital);
            consultationfee=itemView.findViewById(R.id.consultationFees);
            select=itemView.findViewById(R.id.select);

        }
    }
    public interface SelectIemClickListner{
        void mySelectIemListner(int position);
    }
}
