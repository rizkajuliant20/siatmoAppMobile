package com.siatmo.siatmoapp.modul;

import com.google.gson.annotations.SerializedName;

public class PosisiDAO {
    @SerializedName("KODE_PENEMPATAN")
    private String KODE_PENEMPATAN;

    @SerializedName("KETERANGAN")
    private String KETERANGAN;

    public PosisiDAO(String KODE_PENEMPATAN, String KETERANGAN) {
        this.KODE_PENEMPATAN = KODE_PENEMPATAN;
        this.KETERANGAN = KETERANGAN;
    }

    public String getKODE_PENEMPATAN() {
        return KODE_PENEMPATAN;
    }

    public void setKODE_PENEMPATAN(String KODE_PENEMPATAN) {
        this.KODE_PENEMPATAN = KODE_PENEMPATAN;
    }

    public String getKETERANGAN() {
        return KETERANGAN;
    }

    public void setKETERANGAN(String KETERANGAN) {
        this.KETERANGAN = KETERANGAN;
    }
}
