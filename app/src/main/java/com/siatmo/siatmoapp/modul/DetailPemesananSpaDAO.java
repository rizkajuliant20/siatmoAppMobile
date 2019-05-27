package com.siatmo.siatmoapp.modul;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailPemesananSpaDAO {

    @SerializedName("ID_DETAIL_PEMESANAN")
    @Expose
    private int ID_DETAIL_PEMESANAN;

    @SerializedName("ID_SPAREPARTS")
    @Expose
    private String ID_SPAREPARTS;
    //lalala

    @SerializedName("ID_PEMESANAN")
    @Expose
    private int ID_PEMESANAN;

    @SerializedName("JUMLAH_PEMESANAN")
    @Expose
    private int JUMLAH_PEMESANAN;

    @SerializedName("HARGA_BELI_PEMESANAN")
    @Expose
    private double HARGA_BELI_PEMESANAN;

    @SerializedName("SATUAN")
    @Expose
    private String SATUAN;

    public DetailPemesananSpaDAO(int ID_DETAIL_PEMESANAN, String ID_SPAREPARTS, int ID_PEMESANAN, int JUMLAH_PEMESANAN, double HARGA_BELI_PEMESANAN, String SATUAN) {
        this.ID_DETAIL_PEMESANAN = ID_DETAIL_PEMESANAN;
        this.ID_SPAREPARTS = ID_SPAREPARTS;
        this.ID_PEMESANAN = ID_PEMESANAN;
        this.JUMLAH_PEMESANAN = JUMLAH_PEMESANAN;
        this.HARGA_BELI_PEMESANAN = HARGA_BELI_PEMESANAN;
        this.SATUAN = SATUAN;
    }

    public DetailPemesananSpaDAO(String ID_SPAREPARTS,int JUMLAH_PEMESANAN, double HARGA_BELI_PEMESANAN, String SATUAN) {
        this.ID_SPAREPARTS = ID_SPAREPARTS;
        this.JUMLAH_PEMESANAN = JUMLAH_PEMESANAN;
        this.HARGA_BELI_PEMESANAN = HARGA_BELI_PEMESANAN;
        this.SATUAN = SATUAN;
    }

    public int getID_DETAIL_PEMESANAN() {
        return ID_DETAIL_PEMESANAN;
    }

    public void setID_DETAIL_PEMESANAN(int ID_DETAIL_PEMESANAN) {
        this.ID_DETAIL_PEMESANAN = ID_DETAIL_PEMESANAN;
    }

    public String getID_SPAREPARTS() {
        return ID_SPAREPARTS;
    }

    public void setID_SPAREPARTS(String ID_SPAREPARTS) {
        this.ID_SPAREPARTS = ID_SPAREPARTS;
    }

    public int getID_PEMESANAN() {
        return ID_PEMESANAN;
    }

    public void setID_PEMESANAN(int ID_PEMESANAN) {
        this.ID_PEMESANAN = ID_PEMESANAN;
    }

    public int getJUMLAH_PEMESANAN() {
        return JUMLAH_PEMESANAN;
    }

    public void setJUMLAH_PEMESANAN(int JUMLAH_PEMESANAN) {
        this.JUMLAH_PEMESANAN = JUMLAH_PEMESANAN;
    }

    public double getHARGA_BELI_PEMESANAN() {
        return HARGA_BELI_PEMESANAN;
    }

    public void setHARGA_BELI_PEMESANAN(double HARGA_BELI_PEMESANAN) {
        this.HARGA_BELI_PEMESANAN = HARGA_BELI_PEMESANAN;
    }

    public String getSATUAN() {
        return SATUAN;
    }

    public void setSATUAN(String SATUAN) {
        this.SATUAN = SATUAN;
    }
}
