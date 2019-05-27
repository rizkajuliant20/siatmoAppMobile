package com.siatmo.siatmoapp.customFilter;

import android.widget.Filter;

import com.siatmo.siatmoapp.adapter.AdapterSupplier;
import com.siatmo.siatmoapp.modul.SupplierDAO;

import java.util.ArrayList;


public class CustomFilterSupplier extends Filter {


    AdapterSupplier adapterSupplier;
    ArrayList<SupplierDAO> filterList;

    public CustomFilterSupplier(ArrayList<SupplierDAO> filterList, AdapterSupplier adapterSupplier)
    {
        this.adapterSupplier = adapterSupplier;
        this.filterList=filterList;

    }

    //FILTERING OCCURS
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();

        if(constraint != null && constraint.length() > 0)
        {
            constraint=constraint.toString().toUpperCase();

            ArrayList<SupplierDAO> filteredSup=new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                if(filterList.get(i).getNAMA_SUPPLIER().toUpperCase().contains(constraint))
                {
                    filteredSup.add(filterList.get(i));
                }
            }

            results.count=filteredSup.size();
            results.values=filteredSup;

        }else
        {
            results.count=filterList.size();
            results.values=filterList;
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapterSupplier.supplier= (ArrayList<SupplierDAO>) results.values;

        //REFRESH
        adapterSupplier.notifyDataSetChanged();

    }
}
