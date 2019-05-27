package com.siatmo.siatmoapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.siatmo.siatmoapp.R;
import com.siatmo.siatmoapp.modul.SparepartDAO;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder>{

    public List<SparepartDAO> sparepartDAOS;
    private Context context;

    public RecycleAdapter(Context context, List<SparepartDAO> sparepartDAOS) {
        this.sparepartDAOS=sparepartDAOS;
        this.context=context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView Nama, Jumlah, Harga;
        private ImageView Gambar;
        private View itemView;
        private Context context;
        private List<SparepartDAO> SparepartItem;

        public MyViewHolder(View itemView, Context context, List<SparepartDAO> sparepartDAOS)
        {
            super(itemView);
            this.context = context;
            this.SparepartItem = sparepartDAOS;
            this.itemView = itemView;

            Nama = itemView.findViewById(R.id.tvNamaSparepart);
            Jumlah= itemView.findViewById(R.id.tvJumlah);
            Harga= itemView.findViewById(R.id.tvHarga);
            Gambar = itemView.findViewById(R.id.ivGambarSparepart);
        }
    }

    @Override
    public RecycleAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card_view, parent, false);
        return new RecycleAdapter.MyViewHolder(view, context, sparepartDAOS);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final SparepartDAO sparepartDAO = sparepartDAOS.get(position);
        holder.Nama.setText("  "+sparepartDAO.getNAMA_SPAREPART());
        holder.Jumlah.setText("  Jumlah    : "+String.valueOf(sparepartDAO.getSTOK_BARANG()));
        holder.Harga.setText("  Harga      : Rp "+String.valueOf(sparepartDAO.getHARGA_JUAL()));
        Picasso.get().load("http://p3l.pillowfaceid.com"+sparepartDAO.getGAMBAR()).fit().into(holder.Gambar);
//        Picasso.get().load("http://192.168.19.140/8926/public"+sparepartDAO.getGAMBAR()).fit().into(holder.Gambar);//http://10.53.14.216:8000>
    }

    public int getItemCount()
    {
        return sparepartDAOS.size();
    }
}
