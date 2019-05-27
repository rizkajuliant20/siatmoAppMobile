package com.siatmo.siatmoapp.modul;

import com.google.gson.annotations.SerializedName;

public class CustomerDAO {

    @SerializedName("ID_PELANGGAN")
    private int ID_PELANGGAN;

    @SerializedName("NAMA_PELANGGAN")
    private String NAMA_PELANGGAN;

    @SerializedName("TELEPON_PELANGGAN")
    private String TELEPON_PELANGGAN;

    @SerializedName("ALAMAT_PELANGGAN")
    private String ALAMAT_PELANGGAN;

    @SerializedName("VALUE")
    private String VALUE;

    @SerializedName("MESSAGE")
    private String MESSAGE;

    public CustomerDAO(int ID_PELANGGAN, String NAMA_PELANGGAN, String TELEPON_PELANGGAN, String ALAMAT_PELANGGAN) {
        this.ID_PELANGGAN = ID_PELANGGAN;
        this.NAMA_PELANGGAN = NAMA_PELANGGAN;
        this.TELEPON_PELANGGAN = TELEPON_PELANGGAN;
        this.ALAMAT_PELANGGAN = ALAMAT_PELANGGAN;
    }

    public int getID_PELANGGAN() {
        return ID_PELANGGAN;
    }

    public void setID_PELANGGAN(int ID_PELANGGAN) {
        this.ID_PELANGGAN = ID_PELANGGAN;
    }

    public String getNAMA_PELANGGAN() {
        return NAMA_PELANGGAN;
    }

    public void setNAMA_PELANGGAN(String NAMA_PELANGGAN) {
        this.NAMA_PELANGGAN = NAMA_PELANGGAN;
    }

    public String getTELEPON_PELANGGAN() {
        return TELEPON_PELANGGAN;
    }

    public void setTELEPON_PELANGGAN(String TELEPON_PELANGGAN) {
        this.TELEPON_PELANGGAN = TELEPON_PELANGGAN;
    }

    public String getALAMAT_PELANGGAN() {
        return ALAMAT_PELANGGAN;
    }

    public void setALAMAT_PELANGGAN(String ALAMAT_PELANGGAN) {
        this.ALAMAT_PELANGGAN = ALAMAT_PELANGGAN;
    }

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
}
