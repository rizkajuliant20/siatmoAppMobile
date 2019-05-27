package com.siatmo.siatmoapp.adapter;

import com.siatmo.siatmoapp.R;
import com.siatmo.siatmoapp.customFilter.CustomFilterSupplier;
import com.siatmo.siatmoapp.modul.SupplierDAO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;


public class AdapterSupplier extends RecyclerView.Adapter<AdapterSupplier.MyViewHolder> implements Filterable {

    public List<SupplierDAO> supFilter, supplier;
    private Context context;
    private RecyclerViewClickListener mListener;
    CustomFilterSupplier filter;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_supplier, parent, false);
        return new MyViewHolder(view, mListener);
    }

    public AdapterSupplier(List<SupplierDAO> supplier, Context context, RecyclerViewClickListener listener) {
        this.supplier = supplier;
        this.supFilter = supplier;
        this.context = context;
        this.mListener = listener;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.msupName.setText(supplier.get(position).getNAMA_SUPPLIER());
        holder.msupAddress.setText("Address : "+supplier.get(position).getALAMAT_SUPPLIER());
        holder.msupPhone.setText("Phone : "+supplier.get(position).getTELEPON_SUPPLIER());
        holder.msalName.setText("Sales Address : "+supplier.get(position).getNAMA_SALES());
        holder.msalPhone.setText("Sales Phone : "+supplier.get(position).getTELEPON_SALES());
    }

    @Override
    public int getItemCount() {

        return supplier.size();
    }

    @Override
    public Filter getFilter() {
        if (filter==null) {
            filter=new CustomFilterSupplier((ArrayList<SupplierDAO>) supFilter, this);

        }
        return filter;
    }

    public interface RecyclerViewClickListener {
        void onRowClick(View view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private AdapterSupplier.RecyclerViewClickListener mListener;
        protected TextView msupAddress, msupPhone, msalName, msalPhone;
        protected TextView msupName;
        private RelativeLayout mRowContainer;


        public MyViewHolder(View itemView, AdapterSupplier.RecyclerViewClickListener listener) {
            super(itemView);
            msupName =itemView.findViewById(R.id.namaSupplier);
            msupAddress = itemView.findViewById(R.id.supAddress);
            msupPhone = itemView.findViewById(R.id.supPhone);
            msalName = itemView.findViewById(R.id.salName);
            msalPhone = itemView.findViewById(R.id.salPhone);
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
