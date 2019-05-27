package com.siatmo.siatmoapp.modul;

import com.google.gson.annotations.SerializedName;

public class TipeMotorDAO {

    @SerializedName("ID_MOTOR")
    private int ID_MOTOR;

    @SerializedName("TIPE_MOTOR")
    private String TIPE_MOTOR;

    @SerializedName("MERK_MOTOR")
    private String MERK_MOTOR;

    @SerializedName("VALUE")
    private String VALUE;

    public String getVALUE() {
        return VALUE;
    }

    public void setVALUE(String VALUE) {
        this.VALUE = VALUE;
    }

    public String getMESSAGE() {
        return MESSAGE;
    }

    public void setMESSAGE(String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }

    @SerializedName("MESSAGE")
    private String MESSAGE;

    public TipeMotorDAO(int ID_MOTOR, String TIPE_MOTOR, String MERK_MOTOR) {
        this.ID_MOTOR = ID_MOTOR;
        this.TIPE_MOTOR = TIPE_MOTOR;
        this.MERK_MOTOR = MERK_MOTOR;
    }

    public int getID_MOTOR() {
        return ID_MOTOR;
    }

    public void setID_MOTOR(int ID_MOTOR) {
        this.ID_MOTOR = ID_MOTOR;
    }

    public String getTIPE_MOTOR() {
        return TIPE_MOTOR;
    }

    public void setTIPE_MOTOR(String TIPE_MOTOR) {
        this.TIPE_MOTOR = TIPE_MOTOR;
    }

    public String getMERK_MOTOR() {
        return MERK_MOTOR;
    }

    public void setMERK_MOTOR(String MERK_MOTOR) {
        this.MERK_MOTOR = MERK_MOTOR;
    }
}
