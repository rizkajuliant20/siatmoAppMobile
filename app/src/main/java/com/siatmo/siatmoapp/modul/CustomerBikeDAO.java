package com.siatmo.siatmoapp.modul;

import com.google.gson.annotations.SerializedName;

public class CustomerBikeDAO {

    //ID_MOTOR
    @SerializedName("ID_KENDARAAN_PEL")
    private int ID_KENDARAAN_PEL;

    @SerializedName("ID_MOTOR")
    private int ID_MOTOR;

    @SerializedName("ID_PELANGGAN")
    private int ID_PELANGGAN;

    @SerializedName("NO_PLAT")
    private String NO_PLAT;

    @SerializedName("VALUE")
    private String VALUE;

    @SerializedName("MESSAGE")
    private String MESSAGE;

    public CustomerBikeDAO(int ID_KENDARAAN_PEL, int ID_MOTOR, int ID_PELANGGAN, String NO_PLAT) {
        this.ID_KENDARAAN_PEL = ID_KENDARAAN_PEL;
        this.ID_MOTOR = ID_MOTOR;
        this.ID_PELANGGAN = ID_PELANGGAN;
        this.NO_PLAT = NO_PLAT;
    }

    public int getID_KENDARAAN_PEL() {
        return ID_KENDARAAN_PEL;
    }

    public void setID_KENDARAAN_PEL(int ID_KENDARAAN_PEL) {
        this.ID_KENDARAAN_PEL = ID_KENDARAAN_PEL;
    }

    public int getID_MOTOR() {
        return ID_MOTOR;
    }

    public void setID_MOTOR(int ID_MOTOR) {
        this.ID_MOTOR = ID_MOTOR;
    }

    public int getID_PELANGGAN() {
        return ID_PELANGGAN;
    }

    public void setID_PELANGGAN(int ID_PELANGGAN) {
        this.ID_PELANGGAN = ID_PELANGGAN;
    }

    public String getNO_PLAT() {
        return NO_PLAT;
    }

    public void setNO_PLAT(String NO_PLAT) {
        this.NO_PLAT = NO_PLAT;
    }
}
