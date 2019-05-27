package com.siatmo.siatmoapp.modul;

import com.google.gson.annotations.SerializedName;

public class CabangDAO {
    @SerializedName("ID_CABANG")
    private int ID_CABANG;

    @SerializedName("NAMA_CABANG")
    private String NAMA_CABANG;

    @SerializedName("ALAMAT_CABANG")
    private String ALAMAT_CABANG;

    @SerializedName("TELEPON_CABANG")
    private String TELEPON_CABANG;

    @SerializedName("VALUE")
    private String VALUE;

    public String getVALUE() {
        return VALUE;
    }

    public void setVALUE(String VALUE) {
        this.VALUE = VALUE;
    }

    public String getMESSAGE() {
        return MESSAGE;
    }

    public void setMESSAGE(String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }

    @SerializedName("MESSAGE")
    private String MESSAGE;

    public CabangDAO(int ID_CABANG, String NAMA_CABANG, String ALAMAT_CABANG, String TELEPON_CABANG) {
        this.ID_CABANG = ID_CABANG;
        this.NAMA_CABANG = NAMA_CABANG;
        this.ALAMAT_CABANG = ALAMAT_CABANG;
        this.TELEPON_CABANG = TELEPON_CABANG;
    }

    public int getID_CABANG() {
        return ID_CABANG;
    }

    public void setID_CABANG(int ID_CABANG) {
        this.ID_CABANG = ID_CABANG;
    }

    public String getNAMA_CABANG() {
        return NAMA_CABANG;
    }

    public void setNAMA_CABANG(String NAMA_CABANG) {
        this.NAMA_CABANG = NAMA_CABANG;
    }

    public String getALAMAT_CABANG() {
        return ALAMAT_CABANG;
    }

    public void setALAMAT_CABANG(String ALAMAT_CABANG) {
        this.ALAMAT_CABANG = ALAMAT_CABANG;
    }

    public String getTELEPON_CABANG() {
        return TELEPON_CABANG;
    }

    public void setTELEPON_CABANG(String TELEPON_CABANG) {
        this.TELEPON_CABANG = TELEPON_CABANG;
    }
}
