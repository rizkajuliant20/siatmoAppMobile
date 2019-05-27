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
import com.siatmo.siatmoapp.customFilter.CustomFilterPemesanan;
import com.siatmo.siatmoapp.modul.PemesananSparepartDAO;

import java.util.ArrayList;
import java.util.List;

public class AdapterPemesanan extends RecyclerView.Adapter<AdapterPemesanan.MyViewHolder> implements Filterable {
    public List<PemesananSparepartDAO> orderFilter, order;
    private Context context;
    private AdapterPemesanan.RecyclerViewClickListener mListener;
    CustomFilterPemesanan filter;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_pemesanan, parent, false);
        return new MyViewHolder(view, mListener);
    }


    public AdapterPemesanan(List<PemesananSparepartDAO> order, Context context, AdapterPemesanan.RecyclerViewClickListener listener) {
        this.order = order;
        this.orderFilter = order;
        this.context = context;
        this.mListener = listener;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(final AdapterPemesanan.MyViewHolder holder, int position) {
        holder.morderTgl.setText(order.get(position).getTGL_PEMESANAN()+"--"+String.valueOf(order.get(position).getID_PEMESANAN()));
        holder.morderIdSupplier.setText("Supplier : "+String.valueOf(order.get(position).getID_SUPPLIER()));
        holder.morderStatus.setText("Status : "+order.get(position).getSTATUS_PEMESANAN());
        holder.morderSubtotal.setText("Invoice : "+String.valueOf(order.get(position).getGRANDTOTAL_PEMESANAN()));
    }

    @Override
    public int getItemCount() {
        return order.size();
    }

    @Override
    public Filter getFilter() {
        if (filter==null) {
            filter=new CustomFilterPemesanan((ArrayList<PemesananSparepartDAO>) orderFilter, this);
        }
        return filter;
    }

    public interface RecyclerViewClickListener {
        void onRowClick(View view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private AdapterPemesanan.RecyclerViewClickListener mListener;
        protected TextView morderTgl, morderSubtotal, morderIdSupplier, morderStatus;
        private RelativeLayout mRowContainer;

        public MyViewHolder(View itemView, AdapterPemesanan.RecyclerViewClickListener listener) {
            super(itemView);
            morderTgl =itemView.findViewById(R.id.orderTgl);
            morderSubtotal = itemView.findViewById(R.id.orderSubtotal);
            morderIdSupplier = itemView.findViewById(R.id.orderIdSupplier);
            morderStatus = itemView.findViewById(R.id.orderStatus);
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
