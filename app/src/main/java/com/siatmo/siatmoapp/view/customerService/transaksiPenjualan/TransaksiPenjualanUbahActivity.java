package com.siatmo.siatmoapp.view.customerService.transaksiPenjualan;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.siatmo.siatmoapp.R;
import com.siatmo.siatmoapp.api.ApiInterface;
import com.siatmo.siatmoapp.api.RetrofitClient;
import com.siatmo.siatmoapp.modul.CabangDAO;
import com.siatmo.siatmoapp.modul.CustomerBikeDAO;
import com.siatmo.siatmoapp.modul.CustomerDAO;
import com.siatmo.siatmoapp.modul.DetailPenjualanJasaDAO;
import com.siatmo.siatmoapp.modul.DetailPenjualanSparepart;
import com.siatmo.siatmoapp.modul.JSONResponse;
import com.siatmo.siatmoapp.modul.JasaServiceDAO;
import com.siatmo.siatmoapp.modul.MontirOnDutyDAO;
import com.siatmo.siatmoapp.modul.PegawaiDAO;
import com.siatmo.siatmoapp.modul.PenjualanDAO;
import com.siatmo.siatmoapp.modul.SparepartDAO;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransaksiPenjualanUbahActivity extends AppCompatActivity {

    private Spinner spinnerSSVP, spinnerJasa, spinnerSparepart, spinnerCabang, spinnerCustomer, spinnerPlatNomor, spinnerMontir;
    private Button process, process2;
    private TextView showDate, subtotal, jenis;
    private ImageView save, delete, edit, addJasa, addSpa;
    private ApiInterface apiInterface;
    private List<SparepartDAO> spinnerArraySparepart = new ArrayList<>();
    private List<CustomerDAO> spinnerArrayCustomer= new ArrayList<>();
    private List<CabangDAO> spinnerArrayBranch= new ArrayList<>();
    private List<JasaServiceDAO> spinnerArrayServices= new ArrayList<>();
    private List<CustomerBikeDAO> spinnerArrayPlatNomor= new ArrayList<>();
    private List<PegawaiDAO> spinnerArrayMontir= new ArrayList<>();
    private List<String> namaSpinnerMontir= new ArrayList<>();
    private List<String> namaSpinnerSparepart= new ArrayList<>();
    private List<String> namaSpinnerCustomer= new ArrayList<>();
    private List<String> namaSpinnerBranch= new ArrayList<>();
    private List<String> namaSpinnerServices= new ArrayList<>();
    private List<String> namaSpinnerPlatNomor= new ArrayList<>();
    private List<DetailPenjualanJasaDAO> ArrayPenjualanJasa= new ArrayList<>();
    private LinearLayout layoutJasa, layoutSparepart;
    private RelativeLayout laysJasa, laysSpa;
    private String Status;
    private EditText jumlahSparepart;
    private double subtots;
    private String JenisTransaksi;
    private String midTrans, mtglTransaksi, mjenisTransaksi, mstatusTransaksi;
    private int midCabang, midPelanggan;
    private Double msubtotal, mgrantotal, mdiskon;

    private TextView tvMontir;
    private String tampungNamaCabang, tampungNamaPelanggan;
    private List<DetailPenjualanJasaDAO> ArrayDetailJasa = new ArrayList<>();
    private List<DetailPenjualanSparepart> ArrayDetailSpare = new ArrayList<>();
    private List<DetailPenjualanJasaDAO> ArrayDetailJasa2 = new ArrayList<>();
    private List<DetailPenjualanSparepart> ArrayDetailSpare2 = new ArrayList<>();
    private List<DetailPenjualanSparepart> TampungSparepartBaru= new ArrayList<>();
    private List<DetailPenjualanJasaDAO> TampungJasaLama= new ArrayList<>();
    private int tampungIdMontirOnDuty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_penjualan_ubah);

        getSupportActionBar().setTitle("Edit Penjualan");

        apiInterface = RetrofitClient.getApiClient().create(ApiInterface.class);
        spinnerCabang= findViewById(R.id.CabangTransaksiUbah);
        spinnerSparepart= findViewById(R.id.spinnerSparepartTransaksiUbah);
        spinnerCustomer= findViewById(R.id.CustomerTransaksiUbah);
        spinnerJasa= findViewById(R.id.spinnerJasaServiceTransaksiUbah);
        spinnerSSVP= findViewById(R.id.jenisTransaksiUbah);
        spinnerPlatNomor= findViewById(R.id.PlatNomorCustomerUbah);
        showDate= findViewById(R.id.showTanggalTransaksiUbah);
        subtotal= findViewById(R.id.hargaSebelumDiskonUbah);
        layoutJasa= findViewById(R.id.layoutJasaServiceAddUbah);
        layoutSparepart= findViewById(R.id.layoutSparepartAddUbah);
        process= findViewById(R.id.processUIUbah);
        laysJasa= findViewById(R.id.laysJasaUbah);
        laysSpa= findViewById(R.id.laysSparepartUbah);
        save= findViewById(R.id.save_imgTransUbah);
        edit= findViewById(R.id.edit_imgTransUbah);
        delete= findViewById(R.id.del_imgTransUbah);
        addJasa= findViewById(R.id.addJasaServiceTransaksiUbah);
        addSpa= findViewById(R.id.addPenjualanSparepartTransaksiUbah);
        jumlahSparepart= findViewById(R.id.jumlahSparepartTransaksiUbah);
        spinnerMontir= findViewById(R.id.MontirTransaksiUbah);
        process2= findViewById(R.id.MontirOnDutyProcessUbah);
        jenis=findViewById(R.id.tvJenis);
        tvMontir=findViewById(R.id.tvMontirUbahTransaksi);
        Status="Not Paid";

        for (int i=0;i<ArrayPenjualanJasa.size();i++){
            ArrayPenjualanJasa.remove(i);
        }

        Intent intent=getIntent();
        midTrans = intent.getStringExtra("ID_TRANSAKSI");
        midPelanggan = intent.getIntExtra("ID_CABANG",0);
        midCabang = intent.getIntExtra("ID_PELANGGAN",0);
        mtglTransaksi = intent.getStringExtra("TGL_TRANSAKSI");
        msubtotal = intent.getDoubleExtra("SUBTOTAL",0);
        mdiskon = intent.getDoubleExtra("DISKON",0);
        mgrantotal = intent.getDoubleExtra("GRANDTOTAL",0);
        mstatusTransaksi = intent.getStringExtra("STATUS_TRANSAKSI");
        mjenisTransaksi=intent.getStringExtra("JENIS_TRANSAKSI");

        subtots=msubtotal;
        showDate.setText(mtglTransaksi);
        subtotal.setText(String.valueOf(msubtotal));
        jenis.setText(mjenisTransaksi);

        if (mjenisTransaksi.equals("SS")){
            laysSpa.setVisibility(View.VISIBLE);
            laysJasa.setVisibility(View.VISIBLE);
            layoutSparepart.setVisibility(View.VISIBLE);
            layoutJasa.setVisibility(View.VISIBLE);
        }else if (mjenisTransaksi.equals("SV")){
            laysSpa.setVisibility(View.GONE);
            laysJasa.setVisibility(View.VISIBLE);
            layoutSparepart.setVisibility(View.GONE);
            layoutJasa.setVisibility(View.VISIBLE);
        }else{
            laysJasa.setVisibility(View.GONE);
            laysSpa.setVisibility(View.VISIBLE);
            layoutJasa.setVisibility(View.GONE);
            layoutSparepart.setVisibility(View.VISIBLE);
            tvMontir.setVisibility(View.GONE);
            spinnerMontir.setVisibility(View.GONE);
        }

        Call<List<PegawaiDAO>> callMontir= apiInterface.getPegawai();
        callMontir.enqueue(new Callback<List<PegawaiDAO>>() {
            @Override
            public void onResponse(Call<List<PegawaiDAO>> call, Response<List<PegawaiDAO>> response) {
                spinnerArrayMontir= response.body();
                for (int i = 0; i < spinnerArrayMontir.size(); i++) {
                    if(spinnerArrayMontir.get(i).getROLE().equals("Montir")){
                        namaSpinnerMontir.add(String.valueOf(spinnerArrayMontir.get(i).getID_PEGAWAI())+"-"+spinnerArrayMontir.get(i).getNAMA_PEGAWAI());
                    }
                }
                ArrayAdapter<String> adapterMontir= new ArrayAdapter<>(TransaksiPenjualanUbahActivity.this, R.layout.spinner_sparepart_layout, R.id.txtSparepart, namaSpinnerMontir);
                spinnerMontir.setAdapter(adapterMontir);

            }

            @Override
            public void onFailure(Call<List<PegawaiDAO>> call, Throwable t) {

            }
        });

        Call<List<CabangDAO>> callBranch=apiInterface.getCabangs();
        callBranch.enqueue(new Callback<List<CabangDAO>>() {
            @Override
            public void onResponse(Call<List<CabangDAO>> call, Response<List<CabangDAO>> response) {
                spinnerArrayBranch= response.body();
                for (int i = 0; i < spinnerArrayBranch.size(); i++) {
                    namaSpinnerBranch.add(String.valueOf(spinnerArrayBranch.get(i).getID_CABANG()) + "-" + spinnerArrayBranch.get(i).getNAMA_CABANG());
                    if(spinnerArrayBranch.get(i).getID_CABANG()==midCabang){
                        tampungNamaCabang=spinnerArrayBranch.get(i).getNAMA_CABANG();
                    }
                }
                ArrayAdapter<String> adapterBranch = new ArrayAdapter<>(TransaksiPenjualanUbahActivity.this, R.layout.spinner_sparepart_layout, R.id.txtSparepart, namaSpinnerBranch);
                spinnerCabang.setAdapter(adapterBranch);

                int spinnerPosition = adapterBranch.getPosition(String.valueOf(midCabang)+"-"+tampungNamaCabang);
                spinnerCabang.setSelection(spinnerPosition);
            }

            @Override
            public void onFailure(Call<List<CabangDAO>> call, Throwable t) {

            }
        });

        Call<List<JasaServiceDAO>> callService= apiInterface.getService();
        callService.enqueue(new Callback<List<JasaServiceDAO>>() {
            @Override
            public void onResponse(Call<List<JasaServiceDAO>> call, Response<List<JasaServiceDAO>> response) {
                spinnerArrayServices= response.body();
                for (int i = 0; i < spinnerArrayServices.size(); i++) {
                    namaSpinnerServices.add(String.valueOf(spinnerArrayServices.get(i).getID_JASA()) + "-" + spinnerArrayServices.get(i).getNAMA_JASA() + ": " + String.valueOf(spinnerArrayServices.get(i).getHARGA_JASA()));
                }
                ArrayAdapter<String> adapterServices = new ArrayAdapter<>(TransaksiPenjualanUbahActivity.this, R.layout.spinner_sparepart_layout, R.id.txtSparepart, namaSpinnerServices);
                spinnerJasa.setAdapter(adapterServices);
            }

            @Override
            public void onFailure(Call<List<JasaServiceDAO>> call, Throwable t) {

            }
        });

        Call<JSONResponse> callSparepart= apiInterface.getSpareparts();
        callSparepart.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                spinnerArraySparepart = response.body().getData();
                for (int i = 0; i < spinnerArraySparepart.size(); i++) {
                    namaSpinnerSparepart.add(spinnerArraySparepart.get(i).getID_SPAREPARTS() + "-" + spinnerArraySparepart.get(i).getNAMA_SPAREPART() + ": " + String.valueOf(spinnerArraySparepart.get(i).getHARGA_BELI()));
                }
                ArrayAdapter<String> adapterSparepart = new ArrayAdapter<>(TransaksiPenjualanUbahActivity.this, R.layout.spinner_sparepart_layout, R.id.txtSparepart, namaSpinnerSparepart);
                spinnerSparepart.setAdapter(adapterSparepart);
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {

            }
        });

        Call<List<CustomerDAO>> callCustomer= apiInterface.getCustomer();
        callCustomer.enqueue(new Callback<List<CustomerDAO>>() {
            @Override
            public void onResponse(Call<List<CustomerDAO>> call, Response<List<CustomerDAO>> response) {
                spinnerArrayCustomer= response.body();
                for (int i = 0; i < spinnerArrayCustomer.size(); i++) {
                    namaSpinnerCustomer.add(String.valueOf(spinnerArrayCustomer.get(i).getID_PELANGGAN()) + "-" + spinnerArrayCustomer.get(i).getNAMA_PELANGGAN());
                    if(spinnerArrayCustomer.get(i).getID_PELANGGAN()==midPelanggan){
                        tampungNamaPelanggan=spinnerArrayCustomer.get(i).getNAMA_PELANGGAN();
                    }
                }
                ArrayAdapter<String> adapterCustomer = new ArrayAdapter<>(TransaksiPenjualanUbahActivity.this, R.layout.spinner_sparepart_layout, R.id.txtSparepart, namaSpinnerCustomer);
                spinnerCustomer.setAdapter(adapterCustomer);

                int spinnerPosition = adapterCustomer.getPosition(String.valueOf(midPelanggan)+"-"+tampungNamaPelanggan);
                spinnerCustomer.setSelection(spinnerPosition);
            }

            @Override
            public void onFailure(Call<List<CustomerDAO>> call, Throwable t) {

            }
        });

        Call<List<CustomerBikeDAO>> callPlatNomor= apiInterface.getCustomerBike();
        callPlatNomor.enqueue(new Callback<List<CustomerBikeDAO>>() {
            @Override
            public void onResponse(Call<List<CustomerBikeDAO>> call, Response<List<CustomerBikeDAO>> response) {
                spinnerArrayPlatNomor= response.body();
                for (int i = 0; i < spinnerArrayPlatNomor.size(); i++) {
                    namaSpinnerPlatNomor.add(String.valueOf(spinnerArrayPlatNomor.get(i).getID_KENDARAAN_PEL()) + "-" + spinnerArrayPlatNomor.get(i).getNO_PLAT());
                }
                ArrayAdapter<String> adapterPlat = new ArrayAdapter<>(TransaksiPenjualanUbahActivity.this, R.layout.spinner_sparepart_layout, R.id.txtSparepart, namaSpinnerPlatNomor);
                spinnerPlatNomor.setAdapter(adapterPlat);
            }

            @Override
            public void onFailure(Call<List<CustomerBikeDAO>> call, Throwable t) {

            }
        });

        //====================================================================================DETAIL JASA===========================================================
        Call<List<DetailPenjualanJasaDAO>> callDetailJasa= apiInterface.getDetailJasa();
        callDetailJasa.enqueue(new Callback<List<DetailPenjualanJasaDAO>>() {
            @Override
            public void onResponse(Call<List<DetailPenjualanJasaDAO>> call, final Response<List<DetailPenjualanJasaDAO>> response) {
                ArrayDetailJasa=response.body();
                LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                for (int i=0; i<ArrayDetailJasa.size(); i++){
                    if (ArrayDetailJasa.get(i).getID_TRANSAKSI().equals(midTrans)){
                        final View addView = layoutInflater.inflate(R.layout.layout_row_jasa_ubah, null);
                        final TextView textOut = addView.findViewById(R.id.textoutKetJasa);
                        final TextView textOut2 = addView.findViewById(R.id.textoutHargaJasa);
                        final ImageView statusJasa= addView.findViewById(R.id.checkBox);
                        final ImageView statusJasaSelesai= addView.findViewById(R.id.checkBoxDone);
                        final int idTransaksiDetailJasa= ArrayDetailJasa.get(i).getID_JASA();
                        final int idDetail= ArrayDetailJasa.get(i).getID_DETAIL_PENJUALAN_JASA();
                        final int montirOnDuty= ArrayDetailJasa.get(i).getID_MONTIR_ONDUTY();
                        final Double subtotalss= ArrayDetailJasa.get(i).getSUBTOTAL_JASA();
                        final String statusJasaaaa= "Done";
                        final String AmbilStatus= ArrayDetailJasa.get(i).getSTATUS_JASA();

                        if (AmbilStatus.equals("Done")){
                            statusJasaSelesai.setVisibility(View.VISIBLE);
                            statusJasa.setVisibility(View.GONE);
                        }else{
                            statusJasa.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //ISI CUK
                                    Call<DetailPenjualanJasaDAO> ubahStatus= apiInterface.editDataDetailJasa(idDetail,midTrans,idTransaksiDetailJasa,montirOnDuty,subtotalss,statusJasaaaa);
                                    ubahStatus.enqueue(new Callback<DetailPenjualanJasaDAO>() {
                                        @Override
                                        public void onResponse(Call<DetailPenjualanJasaDAO> call, Response<DetailPenjualanJasaDAO> response) {
                                            statusJasaSelesai.setVisibility(View.VISIBLE);
                                            statusJasa.setVisibility(View.GONE);
                                        }

                                        @Override
                                        public void onFailure(Call<DetailPenjualanJasaDAO> call, Throwable t) {
                                            statusJasaSelesai.setVisibility(View.VISIBLE);
                                            statusJasa.setVisibility(View.GONE);
                                            Toast.makeText(getApplicationContext(),"Nice",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                        }

                        Call<List<JasaServiceDAO>> callJasa= apiInterface.getService();
                        callJasa.enqueue(new Callback<List<JasaServiceDAO>>() {
                            @Override
                            public void onResponse(Call<List<JasaServiceDAO>> call, Response<List<JasaServiceDAO>> response) {
                                for (int j=0;j<response.body().size();j++){
                                    if (response.body().get(j).getID_JASA()==idTransaksiDetailJasa){
                                        textOut.setText(String.valueOf(idTransaksiDetailJasa)+"-"+response.body().get(j).getNAMA_JASA());
                                        textOut2.setText(String.valueOf(response.body().get(j).getHARGA_JASA()));
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<List<JasaServiceDAO>> call, Throwable t) {

                            }
                        });

                        Button buttonRemove = addView.findViewById(R.id.removeJasa);
                        buttonRemove.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                Call<DetailPenjualanJasaDAO> delDetailJasa= apiInterface.deleteDataDetailJasa(idDetail);
                                delDetailJasa.enqueue(new Callback<DetailPenjualanJasaDAO>() {
                                    @Override
                                    public void onResponse(Call<DetailPenjualanJasaDAO> call, Response<DetailPenjualanJasaDAO> response) {
                                        for (int i=0;i<ArrayDetailJasa.size();i++){
                                            if (ArrayDetailJasa.get(i).getID_DETAIL_PENJUALAN_JASA()==idDetail){
                                                ArrayDetailJasa.remove(i);
                                                subtots=subtots-Double.parseDouble(textOut2.getText().toString());
                                                subtotal.setText("Rp "+String.valueOf(subtots));
                                                ((LinearLayout)addView.getParent()).removeView(addView);
                                            }
                                        }
                                        final String[] custBaru= spinnerCustomer.getSelectedItem().toString().split("-");
                                        final String[] cabBaru= spinnerCabang.getSelectedItem().toString().split("-");
                                        final String jenisTransaksiBaru= jenis.getText().toString();
                                        Call<PenjualanDAO> editTransaksiPenjualan= apiInterface.editDataTransaksiPenjualan(midTrans,Integer.parseInt(cabBaru[0]),
                                                Integer.parseInt(custBaru[0]),mtglTransaksi,subtots,0,subtots,mstatusTransaksi,jenisTransaksiBaru);
                                        editTransaksiPenjualan.enqueue(new Callback<PenjualanDAO>() {
                                            @Override
                                            public void onResponse(Call<PenjualanDAO> call, Response<PenjualanDAO> response) {
                                                Toast.makeText(getApplicationContext(), "Success3",Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onFailure(Call<PenjualanDAO> call, Throwable t) {
                                                Toast.makeText(getApplicationContext(), "Success4",Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }

                                    @Override
                                    public void onFailure(Call<DetailPenjualanJasaDAO> call, Throwable t) {
                                        for (int i=0;i<ArrayDetailJasa.size();i++){
                                            if (ArrayDetailJasa.get(i).getID_DETAIL_PENJUALAN_JASA()==idDetail){
                                                ArrayDetailJasa.remove(i);
                                                subtots=subtots-Double.parseDouble(textOut2.getText().toString());
                                                subtotal.setText("Rp "+String.valueOf(subtots));
                                                ((LinearLayout)addView.getParent()).removeView(addView);
                                            }
                                        }
                                        final String[] custBaru= spinnerCustomer.getSelectedItem().toString().split("-");
                                        final String[] cabBaru= spinnerCabang.getSelectedItem().toString().split("-");
                                        final String jenisTransaksiBaru= jenis.getText().toString();
                                        Call<PenjualanDAO> editTransaksiPenjualan= apiInterface.editDataTransaksiPenjualan(midTrans,Integer.parseInt(cabBaru[0]),
                                                Integer.parseInt(custBaru[0]),mtglTransaksi,subtots,0,subtots,mstatusTransaksi,jenisTransaksiBaru);
                                        editTransaksiPenjualan.enqueue(new Callback<PenjualanDAO>() {
                                            @Override
                                            public void onResponse(Call<PenjualanDAO> call, Response<PenjualanDAO> response) {
                                                Toast.makeText(getApplicationContext(), "Success3",Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onFailure(Call<PenjualanDAO> call, Throwable t) {
                                                Toast.makeText(getApplicationContext(), "Success4",Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                });
                            }});
                        layoutJasa.addView(addView);
                    }

                }
            }

            @Override
            public void onFailure(Call<List<DetailPenjualanJasaDAO>> call, Throwable t) {

            }
        });

        //================================================DETAIL SPAREPART=====================================================================
        Call<List<DetailPenjualanSparepart>> callDetailSparepart= apiInterface.getDetailSparepart();
        callDetailSparepart.enqueue(new Callback<List<DetailPenjualanSparepart>>() {
            @Override
            public void onResponse(Call<List<DetailPenjualanSparepart>> call, Response<List<DetailPenjualanSparepart>> response) {
                ArrayDetailSpare=response.body();
                for (int k=0; k<ArrayDetailSpare.size();k++){
                    if (ArrayDetailSpare.get(k).getID_TRANSAKSI().equals(midTrans)){
                        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        final View addView = layoutInflater.inflate(R.layout.row_pemesanan, null);
                        final TextView textOut = addView.findViewById(R.id.textoutKetSpa);
                        final TextView textOut2 = addView.findViewById(R.id.textoutJumlahSpa);
                        final TextView textOut3 = addView.findViewById(R.id.textoutHargaTotPerSpa);
                        final String IdSparepart= ArrayDetailSpare.get(k).getID_SPAREPARTS();
                        final int jumlah=ArrayDetailSpare.get(k).getJUMLAH_SPAREPART();
                        final double subs=ArrayDetailSpare.get(k).getSUBTOTAL_SPAREPART();
                        final int idDetail2=ArrayDetailSpare.get(k).getID_PENJUALAN_SPAREPART();

                        Call<JSONResponse> callSpa=apiInterface.getSpareparts();
                        callSpa.enqueue(new Callback<JSONResponse>() {
                            @Override
                            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                                for (int y=0;y<response.body().getData().size();y++) {
                                    if (response.body().getData().get(y).getID_SPAREPARTS().equals(IdSparepart)) {
                                        textOut.setText(IdSparepart + "-" + response.body().getData().get(y).getNAMA_SPAREPART());
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<JSONResponse> call, Throwable t) {

                            }
                        });
                        textOut2.setText(String.valueOf(jumlah));
                        textOut3.setText(String.valueOf(subs));
                        Button buttonRemove = addView.findViewById(R.id.removePemesananSpa);
                        buttonRemove.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                Call<DetailPenjualanSparepart> delDetail=apiInterface.deleteDataDetailSparepart(idDetail2);
                                delDetail.enqueue(new Callback<DetailPenjualanSparepart>() {
                                    @Override
                                    public void onResponse(Call<DetailPenjualanSparepart> call, Response<DetailPenjualanSparepart> response) {
                                        double to3=Double.parseDouble(textOut3.getText().toString());
                                        for (int i = 0; i < ArrayDetailSpare.size(); i++) {
                                            if(ArrayDetailSpare.get(i).getID_PENJUALAN_SPAREPART()==idDetail2)
                                            {
                                                ArrayDetailSpare.remove(i);
                                                subtots=subtots-to3;
                                                subtotal.setText("Rp "+String.valueOf(subtots));
                                                ((LinearLayout)addView.getParent()).removeView(addView);
                                            }
                                        }
                                        final String[] custBaru= spinnerCustomer.getSelectedItem().toString().split("-");
                                        final String[] cabBaru= spinnerCabang.getSelectedItem().toString().split("-");
                                        final String jenisTransaksiBaru= jenis.getText().toString();
                                        Call<PenjualanDAO> editTransaksiPenjualan= apiInterface.editDataTransaksiPenjualan(midTrans,Integer.parseInt(cabBaru[0]),
                                                Integer.parseInt(custBaru[0]),mtglTransaksi,subtots,0,subtots,mstatusTransaksi,jenisTransaksiBaru);
                                        editTransaksiPenjualan.enqueue(new Callback<PenjualanDAO>() {
                                            @Override
                                            public void onResponse(Call<PenjualanDAO> call, Response<PenjualanDAO> response) {
                                                Toast.makeText(getApplicationContext(), "Success1",Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onFailure(Call<PenjualanDAO> call, Throwable t) {
                                                Toast.makeText(getApplicationContext(), "Success2",Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }

                                    @Override
                                    public void onFailure(Call<DetailPenjualanSparepart> call, Throwable t) {
                                        double to3=Double.parseDouble(textOut3.getText().toString());
                                        for (int i = 0; i < ArrayDetailSpare.size(); i++) {
                                            if(ArrayDetailSpare.get(i).getID_PENJUALAN_SPAREPART()==idDetail2)
                                            {
                                                ArrayDetailSpare.remove(i);
                                                subtots=subtots-to3;
                                                subtotal.setText("Rp "+String.valueOf(subtots));
                                                ((LinearLayout)addView.getParent()).removeView(addView);
                                            }
                                        }
                                        final String[] custBaru= spinnerCustomer.getSelectedItem().toString().split("-");
                                        final String[] cabBaru= spinnerCabang.getSelectedItem().toString().split("-");
                                        final String jenisTransaksiBaru= jenis.getText().toString();
                                        Call<PenjualanDAO> editTransaksiPenjualan= apiInterface.editDataTransaksiPenjualan(midTrans,Integer.parseInt(cabBaru[0]),
                                                Integer.parseInt(custBaru[0]),mtglTransaksi,subtots,0,subtots,mstatusTransaksi,jenisTransaksiBaru);
                                        editTransaksiPenjualan.enqueue(new Callback<PenjualanDAO>() {
                                            @Override
                                            public void onResponse(Call<PenjualanDAO> call, Response<PenjualanDAO> response) {
                                                Toast.makeText(getApplicationContext(), "Success3",Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onFailure(Call<PenjualanDAO> call, Throwable t) {
                                                Toast.makeText(getApplicationContext(), "Success4",Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                });
                            }});
                        layoutSparepart.addView(addView);
                    }
                }

            }

            @Override
            public void onFailure(Call<List<DetailPenjualanSparepart>> call, Throwable t) {

            }
        });

        //DELETE========================================================================================================================
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mjenisTransaksi.equals("SS") || mjenisTransaksi.equals("SV")){
                    Call<List<DetailPenjualanJasaDAO>> getDetailService=apiInterface.getDetailJasa();
                    getDetailService.enqueue(new Callback<List<DetailPenjualanJasaDAO>>() {
                        @Override
                        public void onResponse(Call<List<DetailPenjualanJasaDAO>> call, Response<List<DetailPenjualanJasaDAO>> response) {
                            ArrayDetailJasa2=response.body();
                            for (int i=0;i<ArrayDetailJasa2.size();i++){
                                if (ArrayDetailJasa2.get(i).getID_TRANSAKSI().equals(midTrans)){
                                    Call<DetailPenjualanJasaDAO> delDetailJasa= apiInterface.deleteDataDetailJasa(ArrayDetailJasa2.get(i).getID_DETAIL_PENJUALAN_JASA());
                                    delDetailJasa.enqueue(new Callback<DetailPenjualanJasaDAO>() {
                                        @Override
                                        public void onResponse(Call<DetailPenjualanJasaDAO> call, Response<DetailPenjualanJasaDAO> response) {

                                        }

                                        @Override
                                        public void onFailure(Call<DetailPenjualanJasaDAO> call, Throwable t) {

                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<DetailPenjualanJasaDAO>> call, Throwable t) {

                        }
                    });
                }

                if (mjenisTransaksi.equals("SS") || mjenisTransaksi.equals("SP")){
                    Call<List<DetailPenjualanSparepart>> getDetailSpaa=apiInterface.getDetailSparepart();
                    getDetailSpaa.enqueue(new Callback<List<DetailPenjualanSparepart>>() {
                        @Override
                        public void onResponse(Call<List<DetailPenjualanSparepart>> call, Response<List<DetailPenjualanSparepart>> response) {
                            ArrayDetailSpare2=response.body();
                            for (int i=0;i<ArrayDetailSpare2.size();i++){
                                if (ArrayDetailSpare2.get(i).getID_TRANSAKSI().equals(midTrans)){
                                    Call<DetailPenjualanSparepart> delDetailSpare=apiInterface.deleteDataDetailSparepart(ArrayDetailSpare2.get(i).getID_PENJUALAN_SPAREPART());
                                    delDetailSpare.enqueue(new Callback<DetailPenjualanSparepart>() {
                                        @Override
                                        public void onResponse(Call<DetailPenjualanSparepart> call, Response<DetailPenjualanSparepart> response) {

                                        }

                                        @Override
                                        public void onFailure(Call<DetailPenjualanSparepart> call, Throwable t) {

                                        }
                                    });

                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<DetailPenjualanSparepart>> call, Throwable t) {

                        }
                    });
                }

                Call<PenjualanDAO> delPenjualan=apiInterface.deleteDataTransaksiPenjualan(midTrans);
                delPenjualan.enqueue(new Callback<PenjualanDAO>() {
                    @Override
                    public void onResponse(Call<PenjualanDAO> call, Response<PenjualanDAO> response) {
                        Toast.makeText(getApplicationContext(), "Success1", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(TransaksiPenjualanUbahActivity.this, TransaksiPenjualanTampilSemuaActivity.class));
                    }

                    @Override
                    public void onFailure(Call<PenjualanDAO> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Success2", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(TransaksiPenjualanUbahActivity.this, TransaksiPenjualanTampilSemuaActivity.class));
                    }
                });
            }
        });

        //EDIT========================================================================================================================
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        JenisTransaksi= spinnerSSVP.getSelectedItem().toString();
                        if (JenisTransaksi.equals("SS")){
                            laysSpa.setVisibility(View.VISIBLE);
                            laysJasa.setVisibility(View.VISIBLE);
                            layoutSparepart.setVisibility(View.VISIBLE);
                            layoutJasa.setVisibility(View.VISIBLE);
                            jenis.setText("SS");
                        }else if (JenisTransaksi.equals("SV")){
                            laysSpa.setVisibility(View.GONE);
                            laysJasa.setVisibility(View.VISIBLE);
                            layoutSparepart.setVisibility(View.GONE);
                            layoutJasa.setVisibility(View.VISIBLE);
                            jenis.setText("SV");
                        }else{
                            laysJasa.setVisibility(View.GONE);
                            laysSpa.setVisibility(View.VISIBLE);
                            layoutJasa.setVisibility(View.GONE);
                            layoutSparepart.setVisibility(View.VISIBLE);
                            jenis.setText("SP");
                        }
                    }
                });

                edit.setVisibility(View.GONE);
                save.setVisibility(View.VISIBLE);
                delete.setVisibility(View.GONE);
            }
        });

        //SAVE========================================================================================================================
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] custBaru= spinnerCustomer.getSelectedItem().toString().split("-");
                final String[] cabBaru= spinnerCabang.getSelectedItem().toString().split("-");
                final String jenisTransaksiBaru= jenis.getText().toString();
                Call<PenjualanDAO> editTransaksiPenjualan= apiInterface.editDataTransaksiPenjualan(midTrans,Integer.parseInt(cabBaru[0]),
                        Integer.parseInt(custBaru[0]),mtglTransaksi,subtots,0,subtots,mstatusTransaksi,jenisTransaksiBaru);
                editTransaksiPenjualan.enqueue(new Callback<PenjualanDAO>() {
                    @Override
                    public void onResponse(Call<PenjualanDAO> call, Response<PenjualanDAO> response) {
                        Toast.makeText(getApplicationContext(), "Success",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<PenjualanDAO> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Success",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        //ADD SPAREPART BARU========================================================================================================================
        addSpa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (jumlahSparepart.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "You haven't set the total!",Toast.LENGTH_SHORT).show();
                }else{
                    String[] spare=spinnerSparepart.getSelectedItem().toString().split("-");
                    String[] spare2=spinnerSparepart.getSelectedItem().toString().split(": ");
                    final Integer jumlah=Integer.parseInt(jumlahSparepart.getText().toString());
                    final Double subs=jumlah*Double.parseDouble(spare2[1]);
                    final String sparepart=spare[0]+"-"+spare[1];
                    subtots=subtots+subs;
                    subtotal.setText("Rp "+String.valueOf(subtots));
                    TampungSparepartBaru.add(new DetailPenjualanSparepart(midTrans,sparepart,jumlah,subs,Double.parseDouble(spare2[1])));

                    Call<DetailPenjualanSparepart> makeSparepart= apiInterface.createDataDetailSparepart(midTrans,sparepart,jumlah,subs,Double.parseDouble(spare2[1]));
                    makeSparepart.enqueue(new Callback<DetailPenjualanSparepart>() {
                        @Override
                        public void onResponse(Call<DetailPenjualanSparepart> call, Response<DetailPenjualanSparepart> response) {
                            LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            final View addView = layoutInflater.inflate(R.layout.row_pemesanan, null);
                            final TextView textOut = addView.findViewById(R.id.textoutKetSpa);
                            final TextView textOut2 = addView.findViewById(R.id.textoutJumlahSpa);
                            final TextView textOut3 = addView.findViewById(R.id.textoutHargaTotPerSpa);

                            Call<JSONResponse> panggilSpare=apiInterface.getSpareparts();
                            panggilSpare.enqueue(new Callback<JSONResponse>() {
                                @Override
                                public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                                    for (int i=0;i<response.body().getData().size();i++){
                                        if (response.body().getData().get(i).getID_SPAREPARTS().equals(sparepart)){
                                            textOut.setText(sparepart+response.body().getData().get(i).getNAMA_SPAREPART());
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<JSONResponse> call, Throwable t) {

                                }
                            });
                            textOut2.setText(String.valueOf(jumlah));
                            textOut3.setText(String.valueOf(subs));
                            Button buttonRemove = addView.findViewById(R.id.removePemesananSpa);
                            buttonRemove.setOnClickListener(new View.OnClickListener(){
                                @Override
                                public void onClick(View v) {
                                    String to= textOut.getText().toString();
                                    double to3=Double.parseDouble(textOut3.getText().toString());
                                    for (int i = 0; i < TampungSparepartBaru.size(); i++) {
                                        if(TampungSparepartBaru.get(i).getID_SPAREPARTS().equals(to))
                                        {
                                            TampungSparepartBaru.remove(i);
                                            subtots=subtots-to3;
                                            subtotal.setText("Rp "+String.valueOf(subtots));
                                            ((LinearLayout)addView.getParent()).removeView(addView);
                                        }
                                    }
                                }});
                            layoutSparepart.addView(addView);
                            Toast.makeText(getApplicationContext(), "Success1",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<DetailPenjualanSparepart> call, Throwable t) {
                            LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            final View addView = layoutInflater.inflate(R.layout.row_pemesanan, null);
                            final TextView textOut = addView.findViewById(R.id.textoutKetSpa);
                            final TextView textOut2 = addView.findViewById(R.id.textoutJumlahSpa);
                            final TextView textOut3 = addView.findViewById(R.id.textoutHargaTotPerSpa);
                            Call<JSONResponse> panggilSpare=apiInterface.getSpareparts();
                            panggilSpare.enqueue(new Callback<JSONResponse>() {
                                @Override
                                public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                                    for (int i=0;i<response.body().getData().size();i++){
                                        if (response.body().getData().get(i).getID_SPAREPARTS().equals(sparepart)){
                                            textOut.setText(sparepart+response.body().getData().get(i).getNAMA_SPAREPART());
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<JSONResponse> call, Throwable t) {

                                }
                            });
                            textOut2.setText(String.valueOf(jumlah));
                            textOut3.setText(String.valueOf(subs));
                            Button buttonRemove = addView.findViewById(R.id.removePemesananSpa);
                            buttonRemove.setOnClickListener(new View.OnClickListener(){
                                @Override
                                public void onClick(View v) {
                                    String to= textOut.getText().toString();
                                    double to3=Double.parseDouble(textOut3.getText().toString());
                                    for (int i = 0; i < TampungSparepartBaru.size(); i++) {
                                        if(TampungSparepartBaru.get(i).getID_SPAREPARTS().equals(to))
                                        {
                                            TampungSparepartBaru.remove(i);
                                            subtots=subtots-to3;
                                            subtotal.setText("Rp "+String.valueOf(subtots));
                                            ((LinearLayout)addView.getParent()).removeView(addView);
                                        }
                                    }
                                }});
                            layoutSparepart.addView(addView);
                            final String[] custBaru= spinnerCustomer.getSelectedItem().toString().split("-");
                            final String[] cabBaru= spinnerCabang.getSelectedItem().toString().split("-");
                            final String jenisTransaksiBaru= jenis.getText().toString();
                            Call<PenjualanDAO> editTransaksiPenjualan= apiInterface.editDataTransaksiPenjualan(midTrans,Integer.parseInt(cabBaru[0]),
                                    Integer.parseInt(custBaru[0]),mtglTransaksi,subtots,0,subtots,mstatusTransaksi,jenisTransaksiBaru);
                            editTransaksiPenjualan.enqueue(new Callback<PenjualanDAO>() {
                                @Override
                                public void onResponse(Call<PenjualanDAO> call, Response<PenjualanDAO> response) {
                                    Toast.makeText(getApplicationContext(), "Success1",Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(Call<PenjualanDAO> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(), "Success2",Toast.LENGTH_SHORT).show();
                                }
                            });
                            Toast.makeText(getApplicationContext(), "Success2",Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

        //ADD JASA BARU========================================================================================================================
        addJasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] jasaBaru=spinnerJasa.getSelectedItem().toString().split("-");
                final String[] montirr=spinnerMontir.getSelectedItem().toString().split("-");
                final String[] idKendaraan=spinnerPlatNomor.getSelectedItem().toString().split("-");
                final String[] harga=spinnerJasa.getSelectedItem().toString().split(": ");
                Call<List<DetailPenjualanJasaDAO>> getDetailPenjualanJasa=apiInterface.getDetailJasa();
                getDetailPenjualanJasa.enqueue(new Callback<List<DetailPenjualanJasaDAO>>() {
                    @Override
                    public void onResponse(Call<List<DetailPenjualanJasaDAO>> call, Response<List<DetailPenjualanJasaDAO>> response) {
                        TampungJasaLama=response.body();
                        int tampung = 0;
                        for (int i=0;i<TampungJasaLama.size();i++){
                            if (TampungJasaLama.get(i).getID_TRANSAKSI().equals(midTrans)){
                                tampung++;
                            }
                        }
                        if(tampung==0){
                            Call<MontirOnDutyDAO> makeMontirOnDutyBaru=apiInterface.createDataMontirOnDuty(Integer.parseInt(montirr[0]),Integer.parseInt(idKendaraan[0]));
                            makeMontirOnDutyBaru.enqueue(new Callback<MontirOnDutyDAO>() {
                                @Override
                                public void onResponse(Call<MontirOnDutyDAO> call, Response<MontirOnDutyDAO> response) {
                                    Call<DetailPenjualanJasaDAO> makeJasaBaru=apiInterface.createDataDetailJasa(midTrans,Integer.parseInt(jasaBaru[0]),tampungIdMontirOnDuty,Double.parseDouble(harga[1]),"On Process");
                                    makeJasaBaru.enqueue(new Callback<DetailPenjualanJasaDAO>() {
                                        @Override
                                        public void onResponse(Call<DetailPenjualanJasaDAO> call, Response<DetailPenjualanJasaDAO> response) {

                                        }

                                        @Override
                                        public void onFailure(Call<DetailPenjualanJasaDAO> call, Throwable t) {
                                            Toast.makeText(getApplicationContext(), "Success",Toast.LENGTH_SHORT).show();

                                        }
                                    });
                                }

                                @Override
                                public void onFailure(Call<MontirOnDutyDAO> call, Throwable t) {

                                }
                            });
                        }else{
                            for (int i=0; i<TampungJasaLama.size();i++){
                                if(TampungJasaLama.get(i).getID_TRANSAKSI().equals(midTrans)){
                                    tampungIdMontirOnDuty = TampungJasaLama.get(i).getID_MONTIR_ONDUTY();
                                }
                            }
                            Call<DetailPenjualanJasaDAO> makeJasaBaru=apiInterface.createDataDetailJasa(midTrans,Integer.parseInt(jasaBaru[0]),tampungIdMontirOnDuty,Double.parseDouble(harga[1]),"On Process");
                            makeJasaBaru.enqueue(new Callback<DetailPenjualanJasaDAO>() {
                                @Override
                                public void onResponse(Call<DetailPenjualanJasaDAO> call, Response<DetailPenjualanJasaDAO> response) {

                                }

                                @Override
                                public void onFailure(Call<DetailPenjualanJasaDAO> call, Throwable t) {
                                    LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    final View addView = layoutInflater.inflate(R.layout.layout_row_jasa_ubah, null);
                                    final TextView textOut = addView.findViewById(R.id.textoutKetJasa);
                                    final TextView textOut2 = addView.findViewById(R.id.textoutHargaJasa);
                                    final ImageView statusJasa= addView.findViewById(R.id.checkBox);
                                    final ImageView statusJasaSelesai= addView.findViewById(R.id.checkBoxDone);

                                    statusJasa.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
//ISI CUK
                                            statusJasaSelesai.setVisibility(View.GONE);
                                            statusJasa.setVisibility(View.VISIBLE);
                                        }
                                    });
                                    textOut.setText(harga[0]);
                                    textOut2.setText(String.valueOf(harga[1]));
                                    subtots=subtots+Double.parseDouble(harga[1]);
                                    subtotal.setText(String.valueOf(subtots));
                                    Button buttonRemove = addView.findViewById(R.id.removeJasa);
                                    buttonRemove.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
//DELETE
                                        }
                                    });
                                    final String[] custBaru= spinnerCustomer.getSelectedItem().toString().split("-");
                                    final String[] cabBaru= spinnerCabang.getSelectedItem().toString().split("-");
                                    final String jenisTransaksiBaru= jenis.getText().toString();
                                    Call<PenjualanDAO> editTransaksiPenjualan= apiInterface.editDataTransaksiPenjualan(midTrans,Integer.parseInt(cabBaru[0]),
                                            Integer.parseInt(custBaru[0]),mtglTransaksi,Double.parseDouble(subtotal.getText().toString()),0,Double.parseDouble(subtotal.getText().toString()),mstatusTransaksi,jenisTransaksiBaru);
                                    editTransaksiPenjualan.enqueue(new Callback<PenjualanDAO>() {
                                        @Override
                                        public void onResponse(Call<PenjualanDAO> call, Response<PenjualanDAO> response) {}

                                        @Override
                                        public void onFailure(Call<PenjualanDAO> call, Throwable t) {}
                                    });
                                    Toast.makeText(getApplicationContext(), "Success",Toast.LENGTH_SHORT).show();
                                    layoutJasa.addView(addView);
                                }
                            });

                        }
                    }

                    @Override
                    public void onFailure(Call<List<DetailPenjualanJasaDAO>> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
