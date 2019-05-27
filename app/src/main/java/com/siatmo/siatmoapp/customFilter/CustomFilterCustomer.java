package com.siatmo.siatmoapp.customFilter;

import android.widget.Filter;

import com.siatmo.siatmoapp.adapter.AdapterCustomer;
import com.siatmo.siatmoapp.modul.CustomerDAO;

import java.util.ArrayList;

public class CustomFilterCustomer extends Filter {

    AdapterCustomer adapterCustomer;
    ArrayList<CustomerDAO> filterList;

    public CustomFilterCustomer(ArrayList<CustomerDAO> filterList, AdapterCustomer adapterCustomer)
    {
        this.adapterCustomer = adapterCustomer;
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
            ArrayList<CustomerDAO> filteredCust=new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getNAMA_PELANGGAN().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredCust.add(filterList.get(i));
                }
            }

            results.count=filteredCust.size();
            results.values=filteredCust;

        }else
        {
            results.count=filterList.size();
            results.values=filterList;
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapterCustomer.customer= (ArrayList<CustomerDAO>) results.values;

        //REFRESH
        adapterCustomer.notifyDataSetChanged();

    }
}
