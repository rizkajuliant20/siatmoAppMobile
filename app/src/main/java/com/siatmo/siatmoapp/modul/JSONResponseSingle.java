package com.siatmo.siatmoapp.modul;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JSONResponseSingle {

    @SerializedName("data")
    @Expose
    private SparepartDAO data;

    public SparepartDAO getData() {
        return data;
    }

    public void setData(SparepartDAO data) {
        this.data = data;
    }

}
