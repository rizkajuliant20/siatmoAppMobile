package com.siatmo.siatmoapp.customFilter;

import android.widget.Filter;

import com.siatmo.siatmoapp.adapter.AdapterTransaksiPenjualan;
import com.siatmo.siatmoapp.modul.PenjualanDAO;

import java.util.ArrayList;

public class CustomFilterTransaksiPenjualan extends Filter {

    AdapterTransaksiPenjualan adapterTransaksiPenjualan;
    ArrayList<PenjualanDAO> filterList;

    public CustomFilterTransaksiPenjualan(ArrayList<PenjualanDAO> filterList, AdapterTransaksiPenjualan adapterTransaksiPenjualan)
    {
        this.adapterTransaksiPenjualan = adapterTransaksiPenjualan;
        this.filterList=filterList;

    }

    //FILTERING OCCURS
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();
        //CHECK CONSTRAINT VALIDITY
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            ArrayList<PenjualanDAO> filteredTrans=new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getID_TRANSAKSI().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredTrans.add(filterList.get(i));
                }
            }

            results.count=filteredTrans.size();
            results.values=filteredTrans;

        }else
        {
            results.count=filterList.size();
            results.values=filterList;
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapterTransaksiPenjualan.trans= (ArrayList<PenjualanDAO>) results.values;

        //REFRESH
        adapterTransaksiPenjualan.notifyDataSetChanged();

    }
}
