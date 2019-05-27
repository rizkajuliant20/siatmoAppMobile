package com.siatmo.siatmoapp.modul;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PegawaiDAO {
    @SerializedName("ID_PEGAWAI")
    @Expose
    private int ID_PEGAWAI;

    @SerializedName("NAMA_PEGAWAI")
    @Expose
    private String NAMA_PEGAWAI;

    @SerializedName("ALAMAT_PEGAWAI")
    @Expose
    private String ALAMAT_PEGAWAI;

    @SerializedName("TELEPON_PEGAWAI")
    @Expose
    private String TELEPON_PEGAWAI;

    @SerializedName("GAJI_PEGAWAI")
    @Expose
    private double GAJI_PEGAWAI;

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("ID_CABANG")
    @Expose
    private int ID_CABANG;

    @SerializedName("ROLE")
    @Expose
    private String ROLE;

    public PegawaiDAO(int ID_PEGAWAI, String NAMA_PEGAWAI, String ALAMAT_PEGAWAI, String TELEPON_PEGAWAI, double GAJI_PEGAWAI, int id, int ID_CABANG, String ROLE) {
        this.ID_PEGAWAI = ID_PEGAWAI;
        this.NAMA_PEGAWAI = NAMA_PEGAWAI;
        this.ALAMAT_PEGAWAI = ALAMAT_PEGAWAI;
        this.TELEPON_PEGAWAI = TELEPON_PEGAWAI;
        this.GAJI_PEGAWAI = GAJI_PEGAWAI;
        this.id = id;
        this.ID_CABANG = ID_CABANG;
        this.ROLE = ROLE;
    }

    public int getID_PEGAWAI() {
        return ID_PEGAWAI;
    }

    public void setID_PEGAWAI(int ID_PEGAWAI) {
        this.ID_PEGAWAI = ID_PEGAWAI;
    }

    public String getNAMA_PEGAWAI() {
        return NAMA_PEGAWAI;
    }

    public void setNAMA_PEGAWAI(String NAMA_PEGAWAI) {
        this.NAMA_PEGAWAI = NAMA_PEGAWAI;
    }

    public String getALAMAT_PEGAWAI() {
        return ALAMAT_PEGAWAI;
    }

    public void setALAMAT_PEGAWAI(String ALAMAT_PEGAWAI) {
        this.ALAMAT_PEGAWAI = ALAMAT_PEGAWAI;
    }

    public String getTELEPON_PEGAWAI() {
        return TELEPON_PEGAWAI;
    }

    public void setTELEPON_PEGAWAI(String TELEPON_PEGAWAI) {
        this.TELEPON_PEGAWAI = TELEPON_PEGAWAI;
    }

    public double getGAJI_PEGAWAI() {
        return GAJI_PEGAWAI;
    }

    public void setGAJI_PEGAWAI(double GAJI_PEGAWAI) {
        this.GAJI_PEGAWAI = GAJI_PEGAWAI;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getID_CABANG() {
        return ID_CABANG;
    }

    public void setID_CABANG(int ID_CABANG) {
        this.ID_CABANG = ID_CABANG;
    }

    public String getROLE() {
        return ROLE;
    }

    public void setROLE(String ROLE) {
        this.ROLE = ROLE;
    }
}
