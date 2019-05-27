package com.siatmo.siatmoapp.modul;

import com.google.gson.annotations.SerializedName;

public class DetailPenjualanSparepart {

    @SerializedName("ID_PENJUALAN_SPAREPART")
    private int ID_PENJUALAN_SPAREPART;

    @SerializedName("ID_TRANSAKSI")
    private String ID_TRANSAKSI;

    @SerializedName("ID_SPAREPARTS")
    private String ID_SPAREPARTS;

    @SerializedName("JUMLAH_SPAREPART")
    private int JUMLAH_SPAREPART;

    @SerializedName("SUBTOTAL_SPAREPART")
    private Double SUBTOTAL_SPAREPART;

    @SerializedName("HARGA_TAMPUNG_JUAL")
    private Double HARGA_TAMPUNG_JUAL;

    public DetailPenjualanSparepart(int ID_PENJUALAN_SPAREPART, String ID_TRANSAKSI, String ID_SPAREPARTS, int JUMLAH_SPAREPART, Double SUBTOTAL_SPAREPART, Double HARGA_TAMPUNG_JUAL) {
        this.ID_PENJUALAN_SPAREPART = ID_PENJUALAN_SPAREPART;
        this.ID_TRANSAKSI = ID_TRANSAKSI;
        this.ID_SPAREPARTS = ID_SPAREPARTS;
        this.JUMLAH_SPAREPART = JUMLAH_SPAREPART;
        this.SUBTOTAL_SPAREPART = SUBTOTAL_SPAREPART;
        this.HARGA_TAMPUNG_JUAL = HARGA_TAMPUNG_JUAL;
    }
    public DetailPenjualanSparepart(String ID_TRANSAKSI, String ID_SPAREPARTS, int JUMLAH_SPAREPART, Double SUBTOTAL_SPAREPART, Double HARGA_TAMPUNG_JUAL) {
        this.ID_TRANSAKSI = ID_TRANSAKSI;
        this.ID_SPAREPARTS = ID_SPAREPARTS;
        this.JUMLAH_SPAREPART = JUMLAH_SPAREPART;
        this.SUBTOTAL_SPAREPART = SUBTOTAL_SPAREPART;
        this.HARGA_TAMPUNG_JUAL = HARGA_TAMPUNG_JUAL;
    }

    public int getID_PENJUALAN_SPAREPART() {
        return ID_PENJUALAN_SPAREPART;
    }

    public void setID_PENJUALAN_SPAREPART(int ID_PENJUALAN_SPAREPART) {
        this.ID_PENJUALAN_SPAREPART = ID_PENJUALAN_SPAREPART;
    }

    public String getID_TRANSAKSI() {
        return ID_TRANSAKSI;
    }

    public void setID_TRANSAKSI(String ID_TRANSAKSI) {
        this.ID_TRANSAKSI = ID_TRANSAKSI;
    }

    public String getID_SPAREPARTS() {
        return ID_SPAREPARTS;
    }

    public void setID_SPAREPARTS(String ID_SPAREPARTS) {
        this.ID_SPAREPARTS = ID_SPAREPARTS;
    }

    public int getJUMLAH_SPAREPART() {
        return JUMLAH_SPAREPART;
    }

    public void setJUMLAH_SPAREPART(int JUMLAH_SPAREPART) {
        this.JUMLAH_SPAREPART = JUMLAH_SPAREPART;
    }

    public Double getSUBTOTAL_SPAREPART() {
        return SUBTOTAL_SPAREPART;
    }

    public void setSUBTOTAL_SPAREPART(Double SUBTOTAL_SPAREPART) {
        this.SUBTOTAL_SPAREPART = SUBTOTAL_SPAREPART;
    }

    public Double getHARGA_TAMPUNG_JUAL() {
        return HARGA_TAMPUNG_JUAL;
    }

    public void setHARGA_TAMPUNG_JUAL(Double HARGA_TAMPUNG_JUAL) {
        this.HARGA_TAMPUNG_JUAL = HARGA_TAMPUNG_JUAL;
    }
}
