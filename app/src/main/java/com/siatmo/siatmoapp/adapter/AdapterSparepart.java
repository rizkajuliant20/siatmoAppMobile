package com.siatmo.siatmoapp.adapter;

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

import com.siatmo.siatmoapp.R;
import com.siatmo.siatmoapp.customFilter.CustomFilterSparepart;
import com.siatmo.siatmoapp.modul.SparepartDAO;

import java.util.ArrayList;
import java.util.List;

public class AdapterSparepart extends RecyclerView.Adapter<AdapterSparepart.MyViewHolder> implements Filterable {

    public List<SparepartDAO> spaFilter, sparepart;
    private Context context;
    private AdapterSparepart.RecyclerViewClickListener mListener;
    CustomFilterSparepart filter;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_sparepart, parent, false);
        return new MyViewHolder(view, mListener);
    }

    public AdapterSparepart(List<SparepartDAO> sparepart, Context context, RecyclerViewClickListener listener) {
        this.sparepart = sparepart;
        this.spaFilter = sparepart;
        this.context = context;
        this.mListener = listener;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.mNamaspa.setText(sparepart.get(position).getNAMA_SPAREPART());
        holder.mIDspa.setText("Id : "+sparepart.get(position).getID_SPAREPARTS());
        holder.mTipespa.setText("Type : "+sparepart.get(position).getTIPE());
        holder.mStokspa.setText("Remain Stock : "+String.valueOf(sparepart.get(position).getSTOK_BARANG()));
    }

    @Override
    public int getItemCount() {

        return sparepart.size();
    }

    @Override
    public Filter getFilter() {
        if (filter==null) {
            filter=new CustomFilterSparepart((ArrayList<SparepartDAO>) spaFilter,this);

        }
        return filter;
    }

    public interface RecyclerViewClickListener {
        void onRowClick(View view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private AdapterSparepart.RecyclerViewClickListener mListener;
        protected TextView mIDspa, mNamaspa, mTipespa, mStokspa;
        private RelativeLayout mRowContainer;


        public MyViewHolder(View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            mIDspa = itemView.findViewById(R.id.idSpa);
            mNamaspa =itemView.findViewById(R.id.namaSparepart);
            mStokspa = itemView.findViewById(R.id.stokSpa);
            mTipespa = itemView.findViewById(R.id.TipeSpa);
            mRowContainer=itemView.findViewById(R.id.row_containerSpa);

            mListener = listener;
            mRowContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.row_containerSpa:
                    mListener.onRowClick(mRowContainer, getAdapterPosition());
                    break;
                default:
                    break;
            }
        }
    }
}
