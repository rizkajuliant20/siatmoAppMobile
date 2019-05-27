package com.siatmo.siatmoapp.customFilter;

import android.widget.Filter;

import com.siatmo.siatmoapp.adapter.AdapterPemesanan;
import com.siatmo.siatmoapp.modul.PemesananSparepartDAO;

import java.util.ArrayList;

public class CustomFilterPemesanan extends Filter {
    AdapterPemesanan adapterPemesanan;
    ArrayList<PemesananSparepartDAO> filterList;

    public CustomFilterPemesanan(ArrayList<PemesananSparepartDAO> filterList, AdapterPemesanan adapterPemesanan)
    {
        this.adapterPemesanan = adapterPemesanan;
        this.filterList=filterList;

    }

    //FILTERING OCCURS
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();
        if(constraint != null && constraint.length() > 0)
        {
            constraint=constraint.toString().toUpperCase();

            ArrayList<PemesananSparepartDAO> filteredOrder=new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                if(filterList.get(i).getTGL_PEMESANAN().toUpperCase().contains(constraint))
                {
                    filteredOrder.add(filterList.get(i));
                }
            }

            results.count=filteredOrder.size();
            results.values=filteredOrder;

        }else
        {
            results.count=filterList.size();
            results.values=filterList;
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapterPemesanan.order= (ArrayList<PemesananSparepartDAO>) results.values;

        //REFRESH
        adapterPemesanan.notifyDataSetChanged();

    }
}
