package com.siatmo.siatmoapp.modul;

import com.google.gson.annotations.SerializedName;

public class JasaServiceDAO {

    @SerializedName("ID_JASA")
    private int ID_JASA;

    @SerializedName("NAMA_JASA")
    private String NAMA_JASA;

    @SerializedName("HARGA_JASA")
    private Double HARGA_JASA;

    public JasaServiceDAO(int ID_JASA, String NAMA_JASA, Double HARGA_JASA) {
        this.ID_JASA = ID_JASA;
        this.NAMA_JASA = NAMA_JASA;
        this.HARGA_JASA = HARGA_JASA;
    }

    public int getID_JASA() {
        return ID_JASA;
    }

    public void setID_JASA(int ID_JASA) {
        this.ID_JASA = ID_JASA;
    }

    public String getNAMA_JASA() {
        return NAMA_JASA;
    }

    public void setNAMA_JASA(String NAMA_JASA) {
        this.NAMA_JASA = NAMA_JASA;
    }

    public Double getHARGA_JASA() {
        return HARGA_JASA;
    }

    public void setHARGA_JASA(Double HARGA_JASA) {
        this.HARGA_JASA = HARGA_JASA;
    }
}
