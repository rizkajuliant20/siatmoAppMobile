package com.siatmo.siatmoapp.view.customerService.transaksiPenjualan;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.siatmo.siatmoapp.modul.DetailPemesananSpaDAO;
import com.siatmo.siatmoapp.modul.DetailPenjualanJasaDAO;
import com.siatmo.siatmoapp.modul.DetailPenjualanSparepart;
import com.siatmo.siatmoapp.modul.JSONResponse;
import com.siatmo.siatmoapp.modul.JasaServiceDAO;
import com.siatmo.siatmoapp.modul.MontirOnDutyDAO;
import com.siatmo.siatmoapp.modul.PegawaiDAO;
import com.siatmo.siatmoapp.modul.PenjualanDAO;
import com.siatmo.siatmoapp.modul.SparepartDAO;
import com.siatmo.siatmoapp.view.customerService.MainActivityCS;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Integer.parseInt;

public class TransaksiPenjualanTambahActivity extends AppCompatActivity{

    private Spinner spinnerSSVP, spinnerJasa, spinnerSparepart, spinnerCabang, spinnerCustomer, spinnerPlatNomor, spinnerMontir;
    private Button dateButton, process, process2;
    private TextView showDate, grandtotal, subtotal, tvmontir;
    private ImageView save, addJasa, addSpa;
    private String JenisTransaksi;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private ApiInterface apiInterface;
    private String date;
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
    private List<PenjualanDAO> ArrayPenjualan= new ArrayList<>();
    private List<DetailPenjualanJasaDAO> ArrayPenjualanJasa= new ArrayList<>();
    private List<DetailPenjualanSparepart> ArrayPenjualanSpa= new ArrayList<>();
    private LinearLayout layoutJasa, layoutSparepart;
    private RelativeLayout laysJasa, laysSpa;
    private String Status;
    private EditText jumlahSparepart;
    double subtots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_penjualan_tambah);

        apiInterface = RetrofitClient.getApiClient().create(ApiInterface.class);
        spinnerCabang= findViewById(R.id.CabangTransaksi);
        spinnerSparepart= findViewById(R.id.spinnerSparepartTransaksi);
        spinnerCustomer= findViewById(R.id.CustomerTransaksi);
        spinnerJasa= findViewById(R.id.spinnerJasaServiceTransaksi);
        spinnerSSVP= findViewById(R.id.jenisTransaksi);
        spinnerPlatNomor= findViewById(R.id.PlatNomorCustomer);
        showDate= findViewById(R.id.showTanggalTransaksi);
        subtotal= findViewById(R.id.hargaSebelumDiskon);
        grandtotal= findViewById(R.id.grandtotalTransaksi);
        dateButton= findViewById(R.id.tanggalTransaksi);
        layoutJasa= findViewById(R.id.layoutJasaServiceAdd);
        layoutSparepart= findViewById(R.id.layoutSparepartAdd);
        process= findViewById(R.id.processUI);
        laysJasa= findViewById(R.id.laysJasa);
        laysSpa= findViewById(R.id.laysSparepart);
        save= findViewById(R.id.saveTransaksi);
        addJasa= findViewById(R.id.addJasaServiceTransaksi);
        addSpa= findViewById(R.id.addPenjualanSparepartTransaksi);
        jumlahSparepart= findViewById(R.id.jumlahSparepartTransaksi);
        spinnerMontir= findViewById(R.id.MontirTransaksi);
        process2= findViewById(R.id.MontirOnDutyProcess);
        tvmontir= findViewById(R.id.tvMontirTrans);
        Status="Not Paid";



        for (int i=0;i<ArrayPenjualanJasa.size();i++){
            ArrayPenjualanJasa.remove(i);
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
                ArrayAdapter<String> adapterMontir= new ArrayAdapter<>(TransaksiPenjualanTambahActivity.this, R.layout.spinner_sparepart_layout, R.id.txtSparepart, namaSpinnerMontir);
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
                }
                ArrayAdapter<String> adapterBranch = new ArrayAdapter<>(TransaksiPenjualanTambahActivity.this, R.layout.spinner_sparepart_layout, R.id.txtSparepart, namaSpinnerBranch);
                spinnerCabang.setAdapter(adapterBranch);
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
                ArrayAdapter<String> adapterServices = new ArrayAdapter<>(TransaksiPenjualanTambahActivity.this, R.layout.spinner_sparepart_layout, R.id.txtSparepart, namaSpinnerServices);
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
                ArrayAdapter<String> adapterSparepart = new ArrayAdapter<>(TransaksiPenjualanTambahActivity.this, R.layout.spinner_sparepart_layout, R.id.txtSparepart, namaSpinnerSparepart);
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
                }
                ArrayAdapter<String> adapterCustomer = new ArrayAdapter<>(TransaksiPenjualanTambahActivity.this, R.layout.spinner_sparepart_layout, R.id.txtSparepart, namaSpinnerCustomer);
                spinnerCustomer.setAdapter(adapterCustomer);
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
//                String[] cust=spinnerCustomer.getSelectedItem().toString().split("-");
//                int customerId=Integer.parseInt(cust[0]);
                for (int i = 0; i < spinnerArrayPlatNomor.size(); i++) {
//                    if(spinnerArrayPlatNomor.get(i).getID_PELANGGAN()==customerId){
                    namaSpinnerPlatNomor.add(String.valueOf(spinnerArrayPlatNomor.get(i).getID_KENDARAAN_PEL()) + "-" + spinnerArrayPlatNomor.get(i).getNO_PLAT());
//                    }
                }
                ArrayAdapter<String> adapterPlat = new ArrayAdapter<>(TransaksiPenjualanTambahActivity.this, R.layout.spinner_sparepart_layout, R.id.txtSparepart, namaSpinnerPlatNomor);
                spinnerPlatNomor.setAdapter(adapterPlat);
            }

            @Override
            public void onFailure(Call<List<CustomerBikeDAO>> call, Throwable t) {

            }
        });

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(TransaksiPenjualanTambahActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                date = year + "-" + String.format("%02d",month) + "-" + String.format("%02d",dayOfMonth);
                showDate.setText(date);
            }
        };

        process.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JenisTransaksi= spinnerSSVP.getSelectedItem().toString();
                if (JenisTransaksi.equals("SS")){
                    laysSpa.setVisibility(View.VISIBLE);
                    laysJasa.setVisibility(View.VISIBLE);
                    layoutSparepart.setVisibility(View.VISIBLE);
                    layoutJasa.setVisibility(View.VISIBLE);
                }else if (JenisTransaksi.equals("SV")){
                    laysSpa.setVisibility(View.GONE);
                    laysJasa.setVisibility(View.VISIBLE);
                    layoutSparepart.setVisibility(View.GONE);
                    layoutJasa.setVisibility(View.VISIBLE);
                }else{
                    laysJasa.setVisibility(View.GONE);
                    laysSpa.setVisibility(View.VISIBLE);
                    layoutJasa.setVisibility(View.GONE);
                    layoutSparepart.setVisibility(View.VISIBLE);
                    tvmontir.setVisibility(View.GONE);
                    spinnerMontir.setVisibility(View.GONE);
                }
            }
        });

        process2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (date==null){
                    Toast.makeText(getApplicationContext(),"You haven't pick the date!", Toast.LENGTH_SHORT).show();
                }else{
                    String[] montir=spinnerMontir.getSelectedItem().toString().split("-");
                    int montirs=Integer.parseInt(montir[0]);
                    String[] plat= spinnerPlatNomor.getSelectedItem().toString().split("-");
                    int plats=Integer.parseInt(plat[0]);
                    String dateID=showDate.getText().toString().substring(2,4);
                    if (showDate.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(),"Date can't be empty!",Toast.LENGTH_SHORT).show();
                    }else{
                        String[] dateID2=showDate.getText().toString().split("-");
                        final String dateIDfin=dateID2[2]+dateID2[1]+dateID;
                        Call<MontirOnDutyDAO> makeMontir=apiInterface.createDataMontirOnDuty(montirs,plats);
                        makeMontir.enqueue(new Callback<MontirOnDutyDAO>() {
                            @Override
                            public void onResponse(Call<MontirOnDutyDAO> call, final Response<MontirOnDutyDAO> response) {

                            }

                            @Override
                            public void onFailure(Call<MontirOnDutyDAO> call, Throwable t) {
                                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                                Call<List<MontirOnDutyDAO>> getMontir=apiInterface.getDetailMontirOnDuty();
                                getMontir.enqueue(new Callback<List<MontirOnDutyDAO>>() {
                                    @Override
                                    public void onResponse(Call<List<MontirOnDutyDAO>> call, Response<List<MontirOnDutyDAO>> response) {
                                        final int idd=response.body().get(response.body().size()-1).getID_MONTIR_ONDUTY();
                                        addJasa.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Call<List<PenjualanDAO>> getPenj=apiInterface.getTransaksiPenjualan();
                                                getPenj.enqueue(new Callback<List<PenjualanDAO>>() {
                                                    @Override
                                                    public void onResponse(Call<List<PenjualanDAO>> call, Response<List<PenjualanDAO>> response) {
                                                        ArrayPenjualan=response.body();
                                                        int Nomor;
                                                        if (ArrayPenjualan.size()==0){
                                                            Nomor=1;
                                                        }else{
                                                            String[] IdTerakhir=ArrayPenjualan.get(ArrayPenjualan.size()-1).getID_TRANSAKSI().toString().split("-");
                                                            int noUruts=Integer.parseInt(IdTerakhir[1]);
                                                            Nomor=noUruts+1;
                                                        }
                                                        String gabung=dateIDfin+"-"+String.format("%02d",Nomor);
                                                        String[] idJasa=spinnerJasa.getSelectedItem().toString().split("-");
                                                        String[] hargaJasa=spinnerJasa.getSelectedItem().toString().split(": ");
                                                        subtots=subtots+Double.parseDouble(hargaJasa[1]);
                                                        subtotal.setText("Rp "+String.valueOf(subtots));
                                                        ArrayPenjualanJasa.add(new DetailPenjualanJasaDAO(gabung, Integer.parseInt(idJasa[0]),idd,Double.parseDouble(hargaJasa[1]),"On Process"));

                                                        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                                        final View addView = layoutInflater.inflate(R.layout.layout_row_jasa, null);
                                                        final TextView textOut = addView.findViewById(R.id.textoutKetJasa);
                                                        final TextView textOut2 = addView.findViewById(R.id.textoutHargaJasa);
                                                        textOut.setText(hargaJasa[0]);
                                                        textOut2.setText(String.valueOf(hargaJasa[1]));
                                                        Button buttonRemove = addView.findViewById(R.id.removeJasa);
                                                        buttonRemove.setOnClickListener(new View.OnClickListener(){
                                                            @Override
                                                            public void onClick(View v) {
                                                                String[] to= textOut.getText().toString().split("-");
                                                                int too=Integer.parseInt(to[0]);
                                                                double to3=Double.parseDouble(textOut2.getText().toString());
                                                                for (int i = 0; i < ArrayPenjualanJasa.size(); i++) {
                                                                    if(ArrayPenjualanJasa.get(i).getID_JASA()==(too))
                                                                    {
                                                                        ArrayPenjualanJasa.remove(i);
                                                                        subtots=subtots-to3;
                                                                        subtotal.setText("Rp "+String.valueOf(subtots));
                                                                        ((LinearLayout)addView.getParent()).removeView(addView);
                                                                        Toast.makeText(getApplicationContext(),String.valueOf(ArrayPenjualanJasa.size()),Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }
                                                            }});
                                                        layoutJasa.addView(addView);
                                                    }

                                                    @Override
                                                    public void onFailure(Call<List<PenjualanDAO>> call, Throwable t) {

                                                    }
                                                });

                                            }
                                        });

                                    }

                                    @Override
                                    public void onFailure(Call<List<MontirOnDutyDAO>> call, Throwable t) {

                                    }
                                });
                            }
                        });
                    }
                }
            }
        });

        addSpa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (date==null){
                    Toast.makeText(getApplicationContext(),"You haven't pick the date!", Toast.LENGTH_SHORT).show();
                }else if (jumlahSparepart.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"You haven't set the total!", Toast.LENGTH_SHORT).show();
                }else{
                    String dateID=showDate.getText().toString().substring(2,4);
                    String[] dateID2=showDate.getText().toString().split("-");
                    final String dateIDfin=dateID2[2]+dateID2[1]+dateID;
                    Call<List<PenjualanDAO>> getDataPenjualan=apiInterface.getTransaksiPenjualan();
                    getDataPenjualan.enqueue(new Callback<List<PenjualanDAO>>() {
                        @Override
                        public void onResponse(Call<List<PenjualanDAO>> call, Response<List<PenjualanDAO>> response) {
                            ArrayPenjualan=response.body();
                            int Nomor;
                            if (ArrayPenjualan.size()==0){
                                Nomor=1;
                            }else{
                                String[] IdTerakhir=ArrayPenjualan.get(ArrayPenjualan.size()-1).getID_TRANSAKSI().toString().split("-");
                                int noUruts=Integer.parseInt(IdTerakhir[1]);
                                Nomor=noUruts+1;
                            }
                            String gabung=dateIDfin+"-"+String.format("%02d",Nomor);
                            String[] spare=spinnerSparepart.getSelectedItem().toString().split("-");
                            String[] spare2=spinnerSparepart.getSelectedItem().toString().split(": ");
                            Integer jumlah=Integer.parseInt(jumlahSparepart.getText().toString());
                            Double subs=jumlah*Double.parseDouble(spare2[1]);
                            String sparepart=spare[0]+"-"+spare[1];
                            subtots=subtots+subs;
                            subtotal.setText("Rp "+String.valueOf(subtots));
                            ArrayPenjualanSpa.add(new DetailPenjualanSparepart(gabung,sparepart,jumlah,subs,Double.parseDouble(spare2[1])));

                            LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            final View addView = layoutInflater.inflate(R.layout.row_pemesanan, null);
                            final TextView textOut = addView.findViewById(R.id.textoutKetSpa);
                            final TextView textOut2 = addView.findViewById(R.id.textoutJumlahSpa);
                            final TextView textOut3 = addView.findViewById(R.id.textoutHargaTotPerSpa);
                            textOut.setText(sparepart);
                            textOut2.setText(String.valueOf(jumlah));
                            textOut3.setText(String.valueOf(subs));
                            Button buttonRemove = addView.findViewById(R.id.removePemesananSpa);
                            buttonRemove.setOnClickListener(new View.OnClickListener(){
                                @Override
                                public void onClick(View v) {
                                    String to= textOut.getText().toString();
                                    double to3=Double.parseDouble(textOut3.getText().toString());
                                    for (int i = 0; i < ArrayPenjualanSpa.size(); i++) {
                                        if(ArrayPenjualanSpa.get(i).getID_SPAREPARTS().equals(to))
                                        {
                                            ArrayPenjualanSpa.remove(i);
                                            subtots=subtots-to3;
                                            subtotal.setText("Rp "+String.valueOf(subtots));
                                            ((LinearLayout)addView.getParent()).removeView(addView);
                                        }
                                    }
                                }});
                            layoutSparepart.addView(addView);
                        }

                        @Override
                        public void onFailure(Call<List<PenjualanDAO>> call, Throwable t) {

                        }
                    });
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (date==null){
                    Toast.makeText(getApplicationContext(),"You haven't pick the date!", Toast.LENGTH_SHORT).show();
                }else if (subtots==0){
                    Toast.makeText(getApplicationContext(),"You haven't buy a thing!", Toast.LENGTH_SHORT).show();
                }else{
                    String dateID=showDate.getText().toString().substring(2,4);
                    String[] dateID2=showDate.getText().toString().split("-");
                    final String dateIDfin=dateID2[2]+dateID2[1]+dateID;
                    Call<List<PenjualanDAO>> callPenj=apiInterface.getTransaksiPenjualan();
                    callPenj.enqueue(new Callback<List<PenjualanDAO>>() {
                        @Override
                        public void onResponse(Call<List<PenjualanDAO>> call, Response<List<PenjualanDAO>> response) {
                            ArrayPenjualan=response.body();
                            final int nooo;
                            if (ArrayPenjualan.size()==0){
                                nooo=1;
                            }else{
                                String no=ArrayPenjualan.get(ArrayPenjualan.size()-1).getID_TRANSAKSI();
                                String[] noo=no.split("-");
                                nooo=Integer.parseInt(noo[1])+1;
                            }
                            String[] cabang = spinnerCabang.getSelectedItem().toString().split("-");
                            int cabs=Integer.parseInt(cabang[0]);
                            String[] customer = spinnerCustomer.getSelectedItem().toString().split("-");
                            int custs=Integer.parseInt(customer[0]);
                            String gabung=dateIDfin+"-"+String.format("%02d",nooo);
                            String jenis=spinnerSSVP.getSelectedItem().toString();
                            String tanggalan=showDate.getText().toString();
                            Call<PenjualanDAO> createTransaksi=apiInterface.createDataTransaksiPenjualan(gabung, cabs, custs,tanggalan,subtots,0,subtots,Status,jenis);
                            createTransaksi.enqueue(new Callback<PenjualanDAO>() {
                                @Override
                                public void onResponse(Call<PenjualanDAO> call, Response<PenjualanDAO> response) {
                                    Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                                    if (spinnerSSVP.getSelectedItem().toString().equals("SS") || spinnerSSVP.getSelectedItem().toString().equals("SV")){
                                        for(int j=0;j<ArrayPenjualanJasa.size();j++){
                                            Call<DetailPenjualanJasaDAO> createJasa=apiInterface.createDataDetailJasa(ArrayPenjualanJasa.get(j).getID_TRANSAKSI(),
                                                    ArrayPenjualanJasa.get(j).getID_JASA(),
                                                    ArrayPenjualanJasa.get(j).getID_MONTIR_ONDUTY(),
                                                    ArrayPenjualanJasa.get(j).getSUBTOTAL_JASA(),
                                                    ArrayPenjualanJasa.get(j).getSTATUS_JASA());
                                            createJasa.enqueue(new Callback<DetailPenjualanJasaDAO>() {
                                                @Override
                                                public void onResponse(Call<DetailPenjualanJasaDAO> call, Response<DetailPenjualanJasaDAO> response) {

                                                }

                                                @Override
                                                public void onFailure(Call<DetailPenjualanJasaDAO> call, Throwable t) {

                                                }
                                            });
                                        }
                                    }

                                    if(spinnerSSVP.getSelectedItem().toString().equals("SS") || spinnerSSVP.getSelectedItem().toString().equals("SP")){
                                        for (int k=0;k<ArrayPenjualanSpa.size();k++){
                                            Call<DetailPenjualanSparepart> createSpa=apiInterface.createDataDetailSparepart(ArrayPenjualanSpa.get(k).getID_TRANSAKSI(),
                                                    ArrayPenjualanSpa.get(k).getID_SPAREPARTS(),
                                                    ArrayPenjualanSpa.get(k).getJUMLAH_SPAREPART(),
                                                    ArrayPenjualanSpa.get(k).getSUBTOTAL_SPAREPART(),
                                                    ArrayPenjualanSpa.get(k).getHARGA_TAMPUNG_JUAL());

                                            createSpa.enqueue(new Callback<DetailPenjualanSparepart>() {
                                                @Override
                                                public void onResponse(Call<DetailPenjualanSparepart> call, Response<DetailPenjualanSparepart> response) {

                                                }

                                                @Override
                                                public void onFailure(Call<DetailPenjualanSparepart> call, Throwable t) {

                                                }
                                            });
                                        }
                                    }
                                    startActivity(new Intent(TransaksiPenjualanTambahActivity.this, MainActivityCS.class));
                                }

                                @Override
                                public void onFailure(Call<PenjualanDAO> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                                    if (spinnerSSVP.getSelectedItem().toString().equals("SS") || spinnerSSVP.getSelectedItem().toString().equals("SV")){
                                        for(int j=0;j<ArrayPenjualanJasa.size();j++){
                                            Call<DetailPenjualanJasaDAO> createJasa=apiInterface.createDataDetailJasa(ArrayPenjualanJasa.get(j).getID_TRANSAKSI(),
                                                    ArrayPenjualanJasa.get(j).getID_JASA(),
                                                    ArrayPenjualanJasa.get(j).getID_MONTIR_ONDUTY(),
                                                    ArrayPenjualanJasa.get(j).getSUBTOTAL_JASA(),
                                                    ArrayPenjualanJasa.get(j).getSTATUS_JASA());
                                            createJasa.enqueue(new Callback<DetailPenjualanJasaDAO>() {
                                                @Override
                                                public void onResponse(Call<DetailPenjualanJasaDAO> call, Response<DetailPenjualanJasaDAO> response) {

                                                }

                                                @Override
                                                public void onFailure(Call<DetailPenjualanJasaDAO> call, Throwable t) {

                                                }
                                            });
                                        }
                                    }

                                    if(spinnerSSVP.getSelectedItem().toString().equals("SS") || spinnerSSVP.getSelectedItem().toString().equals("SP")){
                                        for (int k=0;k<ArrayPenjualanSpa.size();k++){
                                            Call<DetailPenjualanSparepart> createSpa=apiInterface.createDataDetailSparepart(ArrayPenjualanSpa.get(k).getID_TRANSAKSI(),
                                                    ArrayPenjualanSpa.get(k).getID_SPAREPARTS(),
                                                    ArrayPenjualanSpa.get(k).getJUMLAH_SPAREPART(),
                                                    ArrayPenjualanSpa.get(k).getSUBTOTAL_SPAREPART(),
                                                    ArrayPenjualanSpa.get(k).getHARGA_TAMPUNG_JUAL());

                                            createSpa.enqueue(new Callback<DetailPenjualanSparepart>() {
                                                @Override
                                                public void onResponse(Call<DetailPenjualanSparepart> call, Response<DetailPenjualanSparepart> response) {

                                                }

                                                @Override
                                                public void onFailure(Call<DetailPenjualanSparepart> call, Throwable t) {

                                                }
                                            });
                                        }
                                    }
                                    startActivity(new Intent(TransaksiPenjualanTambahActivity.this, MainActivityCS.class));
                                }
                            });
                        }

                        @Override
                        public void onFailure(Call<List<PenjualanDAO>> call, Throwable t) {

                        }
                    });
                }
            }
        });

    }
}
