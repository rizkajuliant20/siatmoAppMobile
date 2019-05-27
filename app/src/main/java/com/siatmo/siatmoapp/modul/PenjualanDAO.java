package com.siatmo.siatmoapp.modul;

import com.google.gson.annotations.SerializedName;

public class PenjualanDAO {

    @SerializedName("ID_TRANSAKSI")
    private String ID_TRANSAKSI;

    @SerializedName("ID_CABANG")
    private int ID_CABANG;

    @SerializedName("ID_PELANGGAN")
    private int ID_PELANGGAN;

    @SerializedName("TGL_TRANSAKSI")
    private String TGL_TRANSAKSI;

    @SerializedName("SUBTOTAL")
    private Double SUBTOTAL;

    @SerializedName("DISKON")
    private Double DISKON;

    @SerializedName("GRANDTOTAL")
    private Double GRANDTOTAL;

    @SerializedName("STATUS_TRANSAKSI")
    private String STATUS_TRANSAKSI;

    @SerializedName("JENIS_TRANSAKSI")
    private String JENIS_TRANSAKSI;

    public PenjualanDAO(String ID_TRANSAKSI, int ID_CABANG, int ID_PELANGGAN, String TGL_TRANSAKSI, Double SUBTOTAL, Double DISKON, Double GRANDTOTAL, String STATUS_TRANSAKSI, String JENIS_TRANSAKSI) {
        this.ID_TRANSAKSI = ID_TRANSAKSI;
        this.ID_CABANG = ID_CABANG;
        this.ID_PELANGGAN = ID_PELANGGAN;
        this.TGL_TRANSAKSI = TGL_TRANSAKSI;
        this.SUBTOTAL = SUBTOTAL;
        this.DISKON = DISKON;
        this.GRANDTOTAL = GRANDTOTAL;
        this.STATUS_TRANSAKSI = STATUS_TRANSAKSI;
        this.JENIS_TRANSAKSI = JENIS_TRANSAKSI;
    }

    public String getID_TRANSAKSI() {
        return ID_TRANSAKSI;
    }

    public void setID_TRANSAKSI(String ID_TRANSAKSI) {
        this.ID_TRANSAKSI = ID_TRANSAKSI;
    }

    public int getID_CABANG() {
        return ID_CABANG;
    }

    public void setID_CABANG(int ID_CABANG) {
        this.ID_CABANG = ID_CABANG;
    }

    public int getID_PELANGGAN() {
        return ID_PELANGGAN;
    }

    public void setID_PELANGGAN(int ID_PELANGGAN) {
        this.ID_PELANGGAN = ID_PELANGGAN;
    }

    public String getTGL_TRANSAKSI() {
        return TGL_TRANSAKSI;
    }

    public void setTGL_TRANSAKSI(String TGL_TRANSAKSI) {
        this.TGL_TRANSAKSI = TGL_TRANSAKSI;
    }

    public Double getSUBTOTAL() {
        return SUBTOTAL;
    }

    public void setSUBTOTAL(Double SUBTOTAL) {
        this.SUBTOTAL = SUBTOTAL;
    }

    public Double getDISKON() {
        return DISKON;
    }

    public void setDISKON(Double DISKON) {
        this.DISKON = DISKON;
    }

    public Double getGRANDTOTAL() {
        return GRANDTOTAL;
    }

    public void setGRANDTOTAL(Double GRANDTOTAL) {
        this.GRANDTOTAL = GRANDTOTAL;
    }

    public String getSTATUS_TRANSAKSI() {
        return STATUS_TRANSAKSI;
    }

    public void setSTATUS_TRANSAKSI(String STATUS_TRANSAKSI) {
        this.STATUS_TRANSAKSI = STATUS_TRANSAKSI;
    }

    public String getJENIS_TRANSAKSI() {
        return JENIS_TRANSAKSI;
    }

    public void setJENIS_TRANSAKSI(String JENIS_TRANSAKSI) {
        this.JENIS_TRANSAKSI = JENIS_TRANSAKSI;
    }
}
