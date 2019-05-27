package com.siatmo.siatmoapp.modul;

import com.google.gson.annotations.SerializedName;

public class SparepartMotorDAO {
    @SerializedName("ID_SPAREPART_MOTOR")
    private int ID_SPAREPART_MOTOR;

    @SerializedName("ID_SPAREPARTS")
    private String ID_SPAREPARTS;

    @SerializedName("ID_MOTOR")
    private int ID_MOTOR;

    public int getID_SPAREPART_MOTOR() {
        return ID_SPAREPART_MOTOR;
    }

    public void setID_SPAREPART_MOTOR(int ID_SPAREPART_MOTOR) {
        this.ID_SPAREPART_MOTOR = ID_SPAREPART_MOTOR;
    }

    public String getID_SPAREPARTS() {
        return ID_SPAREPARTS;
    }

    public void setID_SPAREPARTS(String ID_SPAREPARTS) {
        this.ID_SPAREPARTS = ID_SPAREPARTS;
    }

    public int getID_MOTOR() {
        return ID_MOTOR;
    }

    public void setID_MOTOR(int ID_MOTOR) {
        this.ID_MOTOR = ID_MOTOR;
    }

    public SparepartMotorDAO(int ID_SPAREPART_MOTOR, String ID_SPAREPARTS, int ID_MOTOR) {
        this.ID_SPAREPART_MOTOR = ID_SPAREPART_MOTOR;
        this.ID_SPAREPARTS = ID_SPAREPARTS;
        this.ID_MOTOR = ID_MOTOR;
    }
}
