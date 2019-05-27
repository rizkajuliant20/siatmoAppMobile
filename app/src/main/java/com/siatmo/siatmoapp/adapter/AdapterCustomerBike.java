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
import com.siatmo.siatmoapp.customFilter.CustomFilterCustomer;
import com.siatmo.siatmoapp.customFilter.CustomFilterCustomerBike;
import com.siatmo.siatmoapp.modul.CustomerBikeDAO;
import com.siatmo.siatmoapp.modul.CustomerDAO;

import java.util.ArrayList;
import java.util.List;

public class AdapterCustomerBike extends RecyclerView.Adapter<AdapterCustomerBike.MyViewHolder> implements Filterable {
    public List<CustomerBikeDAO> custBikeFilter, customerBike;
    private Context context;
    private AdapterCustomerBike.RecyclerViewClickListener mListener;
    CustomFilterCustomerBike filter;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_customer_bike, parent, false);
        return new MyViewHolder(view, mListener);
    }


    public AdapterCustomerBike(List<CustomerBikeDAO> customerBike, Context context, AdapterCustomerBike.RecyclerViewClickListener listener) {
        this.customerBike = customerBike;
        this.custBikeFilter = customerBike;
        this.context = context;
        this.mListener = listener;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.mIdBikeType.setText("Type : "+"MTR-"+String.valueOf(customerBike.get(position).getID_MOTOR()));
        holder.mIdCustomer.setText("Customer : "+"PLG-"+String.valueOf(customerBike.get(position).getID_PELANGGAN()));
        holder.mNoPlat.setText(customerBike.get(position).getNO_PLAT());
    }

    @Override
    public int getItemCount() {
        return customerBike.size();
    }

    @Override
    public Filter getFilter() {
        if (filter==null) {
            filter=new CustomFilterCustomerBike((ArrayList<CustomerBikeDAO>) custBikeFilter, this);
        }
        return filter;
    }

    public interface RecyclerViewClickListener {
        void onRowClick(View view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private AdapterCustomerBike.RecyclerViewClickListener mListener;
        protected TextView mIdCustomer, mIdBikeType, mNoPlat;
        private RelativeLayout mRowContainer;


        public MyViewHolder(View itemView, AdapterCustomerBike.RecyclerViewClickListener listener) {
            super(itemView);
            mIdCustomer =itemView.findViewById(R.id.CustSpace);
            mIdBikeType = itemView.findViewById(R.id.BikeTypeSpace);
            mNoPlat = itemView.findViewById(R.id.NomorPlat);
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
