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
import com.siatmo.siatmoapp.customFilter.CustomFilterMotors;
import com.siatmo.siatmoapp.modul.TipeMotorDAO;

import java.util.ArrayList;
import java.util.List;

public class AdapterMotor extends RecyclerView.Adapter<AdapterMotor.MyViewHolder> implements Filterable {

    public List<TipeMotorDAO> tipeMotorFilter, tipeMotor;
    private Context context;
    private RecyclerViewClickListener mListener;
    CustomFilterMotors filter;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_tipe_motor, parent, false);
        return new MyViewHolder(view, mListener);
    }

    public AdapterMotor(List<TipeMotorDAO> tipeMotordao, Context context, RecyclerViewClickListener listener) {
        this.tipeMotor = tipeMotordao;
        this.tipeMotorFilter = tipeMotordao;
        this.context = context;
        this.mListener = listener;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.mIDMotor.setText("Id : "+"MTR-"+Integer.toString(tipeMotor.get(position).getID_MOTOR()));
        holder.mTipeMotor.setText(tipeMotor.get(position).getTIPE_MOTOR());
        holder.mMerkMotor.setText("Brand : "+tipeMotor.get(position).getMERK_MOTOR());
    }

    @Override
    public int getItemCount() {
        return tipeMotor.size();
    }

    @Override
    public Filter getFilter() {
        if (filter==null) {
            filter=new CustomFilterMotors((ArrayList<TipeMotorDAO>) tipeMotor, this);
        }
        return filter;
    }

    public interface RecyclerViewClickListener {
        void onRowClick(View view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private AdapterMotor.RecyclerViewClickListener mListener;
        protected TextView mIDMotor, mMerkMotor, mTipeMotor;
        private RelativeLayout mRowContainer;

        public MyViewHolder(View itemView, AdapterMotor.RecyclerViewClickListener listener) {
            super(itemView);
            mTipeMotor=itemView.findViewById(R.id.TipeMotorTitle);
            mIDMotor = itemView.findViewById(R.id.IDMotor);
            mMerkMotor = itemView.findViewById(R.id.MerkMotor);
            mRowContainer = itemView.findViewById(R.id.row_container);

            mListener=listener;
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
