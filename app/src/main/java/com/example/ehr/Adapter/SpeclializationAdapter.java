package com.example.ehr.Adapter;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ehr.Model.DataList;
import com.example.ehr.Model.DoctorListModel;
import com.example.ehr.Model.Doctorlist;
import com.example.ehr.R;

import java.util.ArrayList;

public class SpeclializationAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<DataList> doctorListModels;
    private SpeclializationAdapter.MyItemClickListener clickListener;
    private DoctorAdapter.SelectIemClickListner selectIemClickListner;
    public SpeclializationAdapter(Context context,ArrayList<DataList> doctorListModels,SpeclializationAdapter.MyItemClickListener clickListener){
        mContext=context;
        this.doctorListModels=doctorListModels;
        this.clickListener=clickListener;
    }



    @Override
    public int getCount() {
        return doctorListModels.size();
    }

    @Override
    public Object getItem(int position)
    {

        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView = null;
        final ImageView speclialistimage;
        final TextView specialization;
        final LinearLayout speclist;

        if (convertView == null) {
            gridView = new View(mContext);
            convertView=inflater.inflate(R.layout.speclialist, null);
            speclialistimage=convertView.findViewById(R.id.specilistimage);

            speclist=convertView.findViewById(R.id.speclist);
            specialization=convertView.findViewById(R.id.specialization);
             specialization.setText(doctorListModels.get(position).getSpecialization());
             final String specliality_name=doctorListModels.get(position).getSpecialization();
             if(specliality_name.equals("Cardiology")){
                 speclialistimage.setImageResource(R.drawable.cardiology);
             }
            if(specliality_name.equals("ENT specialist")){
                speclialistimage.setImageResource(R.drawable.neurology);
            }
            if(specliality_name.equals("Orthopedics")){
                speclialistimage.setImageResource(R.drawable.orthopedic);
            }

//            speclialistimage.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    speclialistimage.setBackgroundResource(R.drawable.like_button);
//                    clickListener.myItemClick(position);
//                }
//            });

            final View finalGridView = gridView;
            convertView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    speclialistimage.setBackgroundResource(R.drawable.like_button);
                    clickListener.myItemClick(position);
                }
            });
        }
        return convertView;
    }

    public interface MyItemClickListener{
        void myItemClick(int position);


    }

}

