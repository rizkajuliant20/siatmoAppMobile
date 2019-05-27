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
import com.siatmo.siatmoapp.customFilter.CustomFilterTransaksiPenjualan;
import com.siatmo.siatmoapp.modul.PenjualanDAO;

import java.util.ArrayList;
import java.util.List;

public class AdapterTransaksiPenjualan extends RecyclerView.Adapter<AdapterTransaksiPenjualan.MyViewHolder> implements Filterable {

    public List<PenjualanDAO> transFilter, trans;
    private Context context;
    private AdapterTransaksiPenjualan.RecyclerViewClickListener mListener;
    CustomFilterTransaksiPenjualan filter;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_transaksi_penjualan, parent, false);
        return new MyViewHolder(view, mListener);
    }


    public AdapterTransaksiPenjualan(List<PenjualanDAO> trans, Context context, RecyclerViewClickListener listener) {
        this.trans = trans;
        this.transFilter = trans;
        this.context = context;
        this.mListener = listener;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.mtransID.setText(trans.get(position).getJENIS_TRANSAKSI()+"-"+trans.get(position).getID_TRANSAKSI());
        holder.mtransCabang.setText("Cabang : "+trans.get(position).getID_CABANG());
    }

    @Override
    public int getItemCount() {
        return trans.size();
    }

    @Override
    public Filter getFilter() {
        if (filter==null) {
            filter=new CustomFilterTransaksiPenjualan((ArrayList<PenjualanDAO>) transFilter, this);
        }
        return filter;
    }

    public interface RecyclerViewClickListener {
        void onRowClick(View view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private AdapterTransaksiPenjualan.RecyclerViewClickListener mListener;
        protected TextView mtransID, mtransCabang;
        private RelativeLayout mRowContainer;


        public MyViewHolder(View itemView, AdapterTransaksiPenjualan.RecyclerViewClickListener listener) {
            super(itemView);
            mtransID =itemView.findViewById(R.id.idTransaksi);
            mtransCabang = itemView.findViewById(R.id.cabangTrans);
            mRowContainer = itemView.findViewById(R.id.row_containerTrans);

            mListener = listener;
            mRowContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.row_containerTrans:
                    mListener.onRowClick(mRowContainer, getAdapterPosition());
                    break;
                default:
                    break;
            }
        }
    }
}