package com.siatmo.siatmoapp.customFilter;

import android.widget.Filter;

import com.siatmo.siatmoapp.adapter.AdapterCabang;
import com.siatmo.siatmoapp.modul.CabangDAO;

import java.util.ArrayList;

public class CustomFilterCabang extends Filter {

    AdapterCabang adapterCabang;
    ArrayList<CabangDAO> filterList;

    public CustomFilterCabang(ArrayList<CabangDAO> filterList, AdapterCabang adapterCabang)
    {
        this.adapterCabang = adapterCabang;
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
            ArrayList<CabangDAO> filteredCab=new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getNAMA_CABANG().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredCab.add(filterList.get(i));
                }
            }

            results.count=filteredCab.size();
            results.values=filteredCab;

        }else
        {
            results.count=filterList.size();
            results.values=filterList;
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapterCabang.cabang= (ArrayList<CabangDAO>) results.values;

        //REFRESH
        adapterCabang.notifyDataSetChanged();

    }
}
