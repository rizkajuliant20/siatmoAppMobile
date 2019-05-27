package com.siatmo.siatmoapp.modul;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PemesananSparepartDAO {

    @SerializedName("ID_PEMESANAN")
    private int ID_PEMESANAN;

    @SerializedName("ID_SUPPLIER")
    private int ID_SUPPLIER;

    @SerializedName("TGL_PEMESANAN")
    private String TGL_PEMESANAN;

    @SerializedName("GRANDTOTAL_PEMESANAN")
    private double GRANDTOTAL_PEMESANAN;

    @SerializedName("STATUS_PEMESANAN")
    private String STATUS_PEMESANAN;

    public PemesananSparepartDAO(int ID_PEMESANAN, int ID_SUPPLIER, String TGL_PEMESANAN, double GRANDTOTAL_PEMESANAN, String STATUS_PEMESANAN) {
        this.ID_PEMESANAN = ID_PEMESANAN;
        this.ID_SUPPLIER = ID_SUPPLIER;
        this.TGL_PEMESANAN = TGL_PEMESANAN;
        this.GRANDTOTAL_PEMESANAN = GRANDTOTAL_PEMESANAN;
        this.STATUS_PEMESANAN = STATUS_PEMESANAN;
    }

    public int getID_PEMESANAN() {
        return ID_PEMESANAN;
    }

    public void setID_PEMESANAN(int ID_PEMESANAN) {
        this.ID_PEMESANAN = ID_PEMESANAN;
    }

    public int getID_SUPPLIER() {
        return ID_SUPPLIER;
    }

    public void setID_SUPPLIER(int ID_SUPPLIER) {
        this.ID_SUPPLIER = ID_SUPPLIER;
    }

    public String getTGL_PEMESANAN() {
        return TGL_PEMESANAN;
    }

    public void setTGL_PEMESANAN(String TGL_PEMESANAN) {
        this.TGL_PEMESANAN = TGL_PEMESANAN;
    }

    public double getGRANDTOTAL_PEMESANAN() {
        return GRANDTOTAL_PEMESANAN;
    }

    public void setGRANDTOTAL_PEMESANAN(double GRANDTOTAL_PEMESANAN) {
        this.GRANDTOTAL_PEMESANAN = GRANDTOTAL_PEMESANAN;
    }

    public String getSTATUS_PEMESANAN() {
        return STATUS_PEMESANAN;
    }

    public void setSTATUS_PEMESANAN(String STATUS_PEMESANAN) {
        this.STATUS_PEMESANAN = STATUS_PEMESANAN;
    }
}
