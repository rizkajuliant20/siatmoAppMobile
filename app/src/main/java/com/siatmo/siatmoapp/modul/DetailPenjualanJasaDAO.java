package com.siatmo.siatmoapp.modul;

import com.google.gson.annotations.SerializedName;

public class DetailPenjualanJasaDAO {

    @SerializedName("ID_DETAIL_PENJUALAN_JASA")
    private int ID_DETAIL_PENJUALAN_JASA;

    @SerializedName("ID_TRANSAKSI")
    private String ID_TRANSAKSI;

    @SerializedName("ID_JASA")
    private int ID_JASA;

    @SerializedName("ID_MONTIR_ONDUTY")
    private int ID_MONTIR_ONDUTY;

    @SerializedName("SUBTOTAL_JASA")
    private Double SUBTOTAL_JASA;

    @SerializedName("STATUS_JASA")
    private String STATUS_JASA;

    public DetailPenjualanJasaDAO(int ID_DETAIL_PENJUALAN_JASA, String ID_TRANSAKSI, int ID_JASA, int ID_MONTIR_ONDUTY, Double SUBTOTAL_JASA, String STATUS_JASA) {
        this.ID_DETAIL_PENJUALAN_JASA = ID_DETAIL_PENJUALAN_JASA;
        this.ID_TRANSAKSI = ID_TRANSAKSI;
        this.ID_JASA = ID_JASA;
        this.ID_MONTIR_ONDUTY = ID_MONTIR_ONDUTY;
        this.SUBTOTAL_JASA = SUBTOTAL_JASA;
        this.STATUS_JASA = STATUS_JASA;
    }

    public DetailPenjualanJasaDAO(String ID_TRANSAKSI, int ID_JASA, int ID_MONTIR_ONDUTY, Double SUBTOTAL_JASA, String STATUS_JASA) {
        this.ID_TRANSAKSI = ID_TRANSAKSI;
        this.ID_JASA = ID_JASA;
        this.ID_MONTIR_ONDUTY = ID_MONTIR_ONDUTY;
        this.SUBTOTAL_JASA = SUBTOTAL_JASA;
        this.STATUS_JASA = STATUS_JASA;
    }

    public int getID_DETAIL_PENJUALAN_JASA() {
        return ID_DETAIL_PENJUALAN_JASA;
    }

    public void setID_DETAIL_PENJUALAN_JASA(int ID_DETAIL_PENJUALAN_JASA) {
        this.ID_DETAIL_PENJUALAN_JASA = ID_DETAIL_PENJUALAN_JASA;
    }

    public String getID_TRANSAKSI() {
        return ID_TRANSAKSI;
    }

    public void setID_TRANSAKSI(String ID_TRANSAKSI) {
        this.ID_TRANSAKSI = ID_TRANSAKSI;
    }

    public int getID_JASA() {
        return ID_JASA;
    }

    public void setID_JASA(int ID_JASA) {
        this.ID_JASA = ID_JASA;
    }

    public int getID_MONTIR_ONDUTY() {
        return ID_MONTIR_ONDUTY;
    }

    public void setID_MONTIR_ONDUTY(int ID_MONTIR_ONDUTY) {
        this.ID_MONTIR_ONDUTY = ID_MONTIR_ONDUTY;
    }

    public Double getSUBTOTAL_JASA() {
        return SUBTOTAL_JASA;
    }

    public void setSUBTOTAL_JASA(Double SUBTOTAL_JASA) {
        this.SUBTOTAL_JASA = SUBTOTAL_JASA;
    }

    public String getSTATUS_JASA() {
        return STATUS_JASA;
    }

    public void setSTATUS_JASA(String STATUS_JASA) {
        this.STATUS_JASA = STATUS_JASA;
    }
}
