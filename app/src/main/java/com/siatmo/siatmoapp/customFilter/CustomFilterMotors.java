package com.siatmo.siatmoapp.customFilter;

import android.widget.Filter;

import com.siatmo.siatmoapp.adapter.AdapterMotor;
import com.siatmo.siatmoapp.modul.TipeMotorDAO;

import java.util.ArrayList;

public class CustomFilterMotors extends Filter{

    AdapterMotor adapterTipeMotor;
    ArrayList<TipeMotorDAO> filterList;

    public CustomFilterMotors(ArrayList<TipeMotorDAO> filterList, AdapterMotor adapterMotor)
    {
        this.adapterTipeMotor = adapterMotor;
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
            ArrayList<TipeMotorDAO> filteredTipe=new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getTIPE_MOTOR().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredTipe.add(filterList.get(i));
                }
            }

            results.count=filteredTipe.size();
            results.values=filteredTipe;

        }else
        {
            results.count=filterList.size();
            results.values=filterList;
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapterTipeMotor.tipeMotor= (ArrayList<TipeMotorDAO>) results.values;
        //REFRESH
        adapterTipeMotor.notifyDataSetChanged();

    }
}
