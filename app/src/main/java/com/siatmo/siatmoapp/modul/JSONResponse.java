package com.siatmo.siatmoapp.modul;

import com.google.gson.annotations.SerializedName;
import com.siatmo.siatmoapp.modul.SparepartDAO;

import java.util.List;

public class JSONResponse {
    String value;
    String message;

    @SerializedName("data")
    List<SparepartDAO> data;

    public List<SparepartDAO> getData() {
        return data;
    }

    public void setData(List<SparepartDAO> data) {
        this.data = data;
    }

    public String getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }
}
