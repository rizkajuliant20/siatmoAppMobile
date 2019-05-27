package com.siatmo.siatmoapp.modul;

import com.google.gson.annotations.SerializedName;

public class MontirOnDutyDAO {
    @SerializedName("ID_MONTIR_ONDUTY")
    private int ID_MONTIR_ONDUTY;

    @SerializedName("ID_PEGAWAI")
    private int ID_PEGAWAI;

    @SerializedName("ID_KENDARAAN_PEL")
    private int ID_KENDARAAN_PEL;

    public MontirOnDutyDAO(int ID_MONTIR_ONDUTY, int ID_PEGAWAI, int ID_KENDARAAN_PEL) {
        this.ID_MONTIR_ONDUTY = ID_MONTIR_ONDUTY;
        this.ID_PEGAWAI = ID_PEGAWAI;
        this.ID_KENDARAAN_PEL = ID_KENDARAAN_PEL;
    }

    public int getID_MONTIR_ONDUTY() {
        return ID_MONTIR_ONDUTY;
    }

    public void setID_MONTIR_ONDUTY(int ID_MONTIR_ONDUTY) {
        this.ID_MONTIR_ONDUTY = ID_MONTIR_ONDUTY;
    }

    public int getID_PEGAWAI() {
        return ID_PEGAWAI;
    }

    public void setID_PEGAWAI(int ID_PEGAWAI) {
        this.ID_PEGAWAI = ID_PEGAWAI;
    }

    public int getID_KENDARAAN_PEL() {
        return ID_KENDARAAN_PEL;
    }

    public void setID_KENDARAAN_PEL(int ID_KENDARAAN_PEL) {
        this.ID_KENDARAAN_PEL = ID_KENDARAAN_PEL;
    }
}
