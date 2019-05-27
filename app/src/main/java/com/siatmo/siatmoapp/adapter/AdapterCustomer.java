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

import com.siatmo.siatmoapp.customFilter.CustomFilterCustomer;
import com.siatmo.siatmoapp.R;
import com.siatmo.siatmoapp.modul.CustomerDAO;

import java.util.ArrayList;
import java.util.List;

public class AdapterCustomer extends RecyclerView.Adapter<AdapterCustomer.MyViewHolder> implements Filterable {
    public List<CustomerDAO> custFilter, customer;
    private Context context;
    private AdapterCustomer.RecyclerViewClickListener mListener;
    CustomFilterCustomer filter;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_customer, parent, false);
        return new MyViewHolder(view, mListener);
    }


    public AdapterCustomer(List<CustomerDAO> customer, Context context, AdapterCustomer.RecyclerViewClickListener listener) {
        this.customer = customer;
        this.custFilter = customer;
        this.context = context;
        this.mListener = listener;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(final AdapterCustomer.MyViewHolder holder, int position) {
        holder.mcustNama.setText(customer.get(position).getNAMA_PELANGGAN());
        holder.mcustAddress.setText("Address : "+customer.get(position).getALAMAT_PELANGGAN());
        holder.mcustTelp.setText("Phone : "+customer.get(position).getTELEPON_PELANGGAN());
    }

    @Override
    public int getItemCount() {
        return customer.size();
    }

    @Override
    public Filter getFilter() {
        if (filter==null) {
            filter=new CustomFilterCustomer((ArrayList<CustomerDAO>) custFilter, this);
        }
        return filter;
    }

    public interface RecyclerViewClickListener {
        void onRowClick(View view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private AdapterCustomer.RecyclerViewClickListener mListener;
        protected TextView mcustNama, mcustTelp, mcustAddress;
        private RelativeLayout mRowContainer;


        public MyViewHolder(View itemView, AdapterCustomer.RecyclerViewClickListener listener) {
            super(itemView);
            mcustNama =itemView.findViewById(R.id.custNama);
            mcustAddress = itemView.findViewById(R.id.custAddress);
            mcustTelp = itemView.findViewById(R.id.custTelp);
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
