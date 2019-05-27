package com.siatmo.siatmoapp.modul;

import com.google.gson.annotations.SerializedName;

public class SupplierDAO {

    @SerializedName("ID_SUPPLIER")
    private int ID_SUPPLIER;

    @SerializedName("NAMA_SUPPLIER")
    private String NAMA_SUPPLIER;

    @SerializedName("ALAMAT_SUPPLIER")
    private String ALAMAT_SUPPLIER;

    @SerializedName("TELEPON_SUPPLIER")
    private String TELEPON_SUPPLIER;

    @SerializedName("NAMA_SALES")
    private String NAMA_SALES;

    @SerializedName("TELEPON_SALES")
    private String TELEPON_SALES;

    @SerializedName("VALUE")
    private String VALUE;

    @SerializedName("MESSAGE")
    private String MESSAGE;

    public SupplierDAO(int ID_SUPPLIER, String NAMA_SUPPLIER, String ALAMAT_SUPPLIER, String TELEPON_SUPPLIER, String NAMA_SALES, String TELEPON_SALES) {
        this.ID_SUPPLIER = ID_SUPPLIER;
        this.NAMA_SUPPLIER = NAMA_SUPPLIER;
        this.ALAMAT_SUPPLIER = ALAMAT_SUPPLIER;
        this.TELEPON_SUPPLIER = TELEPON_SUPPLIER;
        this.NAMA_SALES = NAMA_SALES;
        this.TELEPON_SALES = TELEPON_SALES;
    }

    public SupplierDAO(String NAMA_SUPPLIER, String ALAMAT_SUPPLIER, String TELEPON_SUPPLIER, String NAMA_SALES, String TELEPON_SALES) {
        this.NAMA_SUPPLIER = NAMA_SUPPLIER;
        this.ALAMAT_SUPPLIER = ALAMAT_SUPPLIER;
        this.TELEPON_SUPPLIER = TELEPON_SUPPLIER;
        this.NAMA_SALES = NAMA_SALES;
        this.TELEPON_SALES = TELEPON_SALES;
    }

    public int getID_SUPPLIER() {
        return ID_SUPPLIER;
    }

    public void setID_SUPPLIER(int ID_SUPPLIER) {
        this.ID_SUPPLIER = ID_SUPPLIER;
    }

    public String getNAMA_SUPPLIER() {
        return NAMA_SUPPLIER;
    }

    public void setNAMA_SUPPLIER(String NAMA_SUPPLIER) {
        this.NAMA_SUPPLIER = NAMA_SUPPLIER;
    }

    public String getALAMAT_SUPPLIER() {
        return ALAMAT_SUPPLIER;
    }

    public void setALAMAT_SUPPLIER(String ALAMAT_SUPPLIER) {
        this.ALAMAT_SUPPLIER = ALAMAT_SUPPLIER;
    }

    public String getTELEPON_SUPPLIER() {
        return TELEPON_SUPPLIER;
    }

    public void setTELEPON_SUPPLIER(String TELEPON_SUPPLIER) {
        this.TELEPON_SUPPLIER = TELEPON_SUPPLIER;
    }

    public String getNAMA_SALES() {
        return NAMA_SALES;
    }

    public void setNAMA_SALES(String NAMA_SALES) {
        this.NAMA_SALES = NAMA_SALES;
    }

    public String getTELEPON_SALES() {
        return TELEPON_SALES;
    }

    public void setTELEPON_SALES(String TELEPON_SALES) {
        this.TELEPON_SALES = TELEPON_SALES;
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
