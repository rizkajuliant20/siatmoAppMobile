package com.siatmo.siatmoapp.modul;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class SparepartDAO {

    @SerializedName("ID_SPAREPARTS")
    @Expose
    private String ID_SPAREPARTS;

    @SerializedName("KODE_PENEMPATAN")
    @Expose
    private String KODE_PENEMPATAN;

    @SerializedName("NAMA_SPAREPART")
    @Expose
    private String NAMA_SPAREPART;

    @SerializedName("HARGA_BELI")
    @Expose
    private Double HARGA_BELI;

    @SerializedName("HARGA_JUAL")
    @Expose
    private Double HARGA_JUAL;

    @SerializedName("STOK_MINIMAL")
    @Expose
    private int STOK_MINIMAL;

    @SerializedName("STOK_BARANG")
    @Expose
    private int STOK_BARANG;

    public String getGAMBAR() {
        return GAMBAR;
    }

    public void setGAMBAR(String GAMBAR) {
        this.GAMBAR = GAMBAR;
    }

    @SerializedName("GAMBAR")
    @Expose
    private String GAMBAR;

    @SerializedName("TIPE")
    @Expose
    private String TIPE;

    public SparepartDAO(String ID_SPAREPARTS, String KODE_PENEMPATAN, String NAMA_SPAREPART, Double HARGA_BELI, Double HARGA_JUAL, int STOK_MINIMAL, int STOK_BARANG, String GAMBAR, String TIPE) {
        this.ID_SPAREPARTS = ID_SPAREPARTS;
        this.KODE_PENEMPATAN = KODE_PENEMPATAN;
        this.NAMA_SPAREPART = NAMA_SPAREPART;
        this.HARGA_BELI = HARGA_BELI;
        this.HARGA_JUAL = HARGA_JUAL;
        this.STOK_MINIMAL = STOK_MINIMAL;
        this.STOK_BARANG = STOK_BARANG;
        this.GAMBAR = GAMBAR;
        this.TIPE = TIPE;
    }


    public String getID_SPAREPARTS() {
        return ID_SPAREPARTS;
    }

    public void setID_SPAREPARTS(String ID_SPAREPARTS) {
        this.ID_SPAREPARTS = ID_SPAREPARTS;
    }

    public String getKODE_PENEMPATAN() {
        return KODE_PENEMPATAN;
    }

    public void setKODE_PENEMPATAN(String KODE_PENEMPATAN) {
        this.KODE_PENEMPATAN = KODE_PENEMPATAN;
    }

    public String getNAMA_SPAREPART() {
        return NAMA_SPAREPART;
    }

    public void setNAMA_SPAREPART(String NAMA_SPAREPART) {
        this.NAMA_SPAREPART = NAMA_SPAREPART;
    }

    public Double getHARGA_BELI() {
        return HARGA_BELI;
    }

    public void setHARGA_BELI(Double HARGA_BELI) {
        this.HARGA_BELI = HARGA_BELI;
    }

    public Double getHARGA_JUAL() {
        return HARGA_JUAL;
    }

    public void setHARGA_JUAL(Double HARGA_JUAL) {
        this.HARGA_JUAL = HARGA_JUAL;
    }

    public int getSTOK_MINIMAL() {
        return STOK_MINIMAL;
    }

    public void setSTOK_MINIMAL(int STOK_MINIMAL) {
        this.STOK_MINIMAL = STOK_MINIMAL;
    }

    public int getSTOK_BARANG() {
        return STOK_BARANG;
    }

    public void setSTOK_BARANG(int STOK_BARANG) {
        this.STOK_BARANG = STOK_BARANG;
    }


    public String getTIPE() {
        return TIPE;
    }

    public void setTIPE(String TIPE) {
        this.TIPE = TIPE;
    }
}
