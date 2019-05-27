package com.siatmo.siatmoapp.customFilter;

import android.widget.Filter;

import com.siatmo.siatmoapp.adapter.AdapterSparepart;
import com.siatmo.siatmoapp.modul.SparepartDAO;

import java.util.ArrayList;

public class CustomFilterSparepart extends Filter {

    AdapterSparepart adapterSparepart;
    ArrayList<SparepartDAO> filterList;

    public CustomFilterSparepart(ArrayList<SparepartDAO> filterList, AdapterSparepart adapterSparepart)
    {
        this.adapterSparepart = adapterSparepart;
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
            ArrayList<SparepartDAO> filteredSpa=new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getNAMA_SPAREPART().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredSpa.add(filterList.get(i));
                }
            }

            results.count=filteredSpa.size();
            results.values=filteredSpa;

        }else
        {
            results.count=filterList.size();
            results.values=filterList;
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapterSparepart.sparepart= (ArrayList<SparepartDAO>) results.values;

        //REFRESH
        adapterSparepart.notifyDataSetChanged();

    }
}
