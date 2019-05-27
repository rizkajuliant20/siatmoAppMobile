package com.siatmo.siatmoapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.siatmo.siatmoapp.R;
import com.siatmo.siatmoapp.customFilter.CustomFilterCabang;
import com.siatmo.siatmoapp.modul.CabangDAO;

import java.util.ArrayList;
import java.util.List;

public class AdapterCabang extends RecyclerView.Adapter<AdapterCabang.MyViewHolder> implements Filterable {

    public List<CabangDAO> cabFilter, cabang;
    private Context context;
    private AdapterCabang.RecyclerViewClickListener mListener;
    CustomFilterCabang filter;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_cabang, parent, false);
        return new MyViewHolder(view, mListener);
    }


    public AdapterCabang(List<CabangDAO> cabang, Context context, RecyclerViewClickListener listener) {
        this.cabang = cabang;
        this.cabFilter = cabang;
        this.context = context;
        this.mListener = listener;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.mcabName.setText(cabang.get(position).getNAMA_CABANG());
        holder.mcabAddress.setText("Address : "+cabang.get(position).getALAMAT_CABANG());
        holder.mcabPhone.setText("Phone : "+cabang.get(position).getTELEPON_CABANG());
    }

    @Override
    public int getItemCount() {
        return cabang.size();
    }

    @Override
    public Filter getFilter() {
        if (filter==null) {
            filter=new CustomFilterCabang((ArrayList<CabangDAO>) cabFilter, this);
        }
        return filter;
    }

    public interface RecyclerViewClickListener {
        void onRowClick(View view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private AdapterCabang.RecyclerViewClickListener mListener;
        protected TextView mcabAddress, mcabPhone, mcabName;
        private RelativeLayout mRowContainer;


        public MyViewHolder(View itemView, AdapterCabang.RecyclerViewClickListener listener) {
            super(itemView);
            mcabName =itemView.findViewById(R.id.namaCabang);
            mcabAddress = itemView.findViewById(R.id.cabAddress);
            mcabPhone = itemView.findViewById(R.id.cabPhone);
            mRowContainer = itemView.findViewById(R.id.row_container);

            mListener = listener;
            mRowContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.row_container:
                    mListener.onRowClick(mRowContainer, getAdapterPosition());
                    break;
                default:
                    break;
            }
        }
    }
}
