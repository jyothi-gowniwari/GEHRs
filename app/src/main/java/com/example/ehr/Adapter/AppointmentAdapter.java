package com.example.ehr.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ehr.Model.AppointmentModel;
import com.example.ehr.R;


import java.util.ArrayList;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.MyViewHolder> {


    private ArrayList<String> appointmentlist;
    private Context mContext;


    public AppointmentAdapter(ArrayList<String> appointmentModels) {
        this.appointmentlist=appointmentModels;
    }

    @Override
    public AppointmentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.book_app_list, parent, false);
        AppointmentAdapter.MyViewHolder holder = new AppointmentAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(AppointmentAdapter.MyViewHolder holder, final int position) {

          }

    @Override
    public int getItemCount() {
        Log.d("appointment list", String.valueOf(appointmentlist.size()));
        return appointmentlist.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        public MyViewHolder(View itemView) {
            super(itemView);
          }


    }
}
