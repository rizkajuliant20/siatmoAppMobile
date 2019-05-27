package com.siatmo.siatmoapp.customFilter;

import android.widget.Filter;
import com.siatmo.siatmoapp.adapter.AdapterCustomerBike;
import com.siatmo.siatmoapp.modul.CustomerBikeDAO;

import java.util.ArrayList;

public class CustomFilterCustomerBike extends Filter {
    AdapterCustomerBike adapterCustomerBike;
    ArrayList<CustomerBikeDAO> filterList;

    public CustomFilterCustomerBike(ArrayList<CustomerBikeDAO> filterList, AdapterCustomerBike adapterCustomerBike)
    {
        this.adapterCustomerBike = adapterCustomerBike;
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
            ArrayList<CustomerBikeDAO> filteredCustBike=new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getNO_PLAT().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredCustBike.add(filterList.get(i));
                }
            }

            results.count=filteredCustBike.size();
            results.values=filteredCustBike;

        }else
        {
            results.count=filterList.size();
            results.values=filterList;
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapterCustomerBike.customerBike= (ArrayList<CustomerBikeDAO>) results.values;

        //REFRESH
        adapterCustomerBike.notifyDataSetChanged();

    }
}
