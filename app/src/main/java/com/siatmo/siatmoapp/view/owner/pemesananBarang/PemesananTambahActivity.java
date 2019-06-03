package com.siatmo.siatmoapp.view.owner.pemesananBarang;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.siatmo.siatmoapp.R;
import com.siatmo.siatmoapp.api.ApiInterface;
import com.siatmo.siatmoapp.api.RetrofitClient;
import com.siatmo.siatmoapp.modul.DetailPemesananSpaDAO;
import com.siatmo.siatmoapp.modul.JSONResponse;
import com.siatmo.siatmoapp.modul.PemesananSparepartDAO;
import com.siatmo.siatmoapp.modul.SparepartDAO;
import com.siatmo.siatmoapp.modul.SupplierDAO;
import com.siatmo.siatmoapp.view.owner.MainActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PemesananTambahActivity extends AppCompatActivity {

    private Button imgDatePicker;
    private ImageView save, addSpa;
    private Spinner spinnerSupplier, spinnerSparepart;
    private LinearLayout container;
    private TextView showDate, grandtotal;
    private EditText jumlahSparepart, satuan;
    List<SparepartDAO> spinnerArraySpa = new ArrayList<>();
    List<SupplierDAO> spinnerArraySup = new ArrayList<>();
    List<PemesananSparepartDAO> ArrayPemesanan = new ArrayList<>();
    List<DetailPemesananSpaDAO> detailPemesananList = new ArrayList<>();
    private int tampung, jumlah, supplier;
    private String satuann, fusion;
    private double hargaJual, subtotal;
    List<String> namaSpinnerSpa= new ArrayList<>();
    List<String> namaSpinnerSup= new ArrayList<>();
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    ApiInterface apiInterface;
    private String date;
    double hargaBeliPemesanan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemesanan_tambah);
        subtotal = 0;
        tampung = 0;
        imgDatePicker = findViewById(R.id.tanggalPemesanan);
        addSpa = findViewById(R.id.addSpaPemesanan);
        save = findViewById(R.id.savePemesanan);
        spinnerSparepart = findViewById(R.id.spinnerSparepartPemesanan);
        spinnerSupplier = findViewById(R.id.spinnerSupplierPemesanan);
        showDate = findViewById(R.id.showTanggal);
        jumlahSparepart = findViewById(R.id.jumlahSpa);
        satuan = findViewById(R.id.satuan);
        apiInterface = RetrofitClient.getApiClient().create(ApiInterface.class);
        grandtotal=findViewById(R.id.tvGrandTotal);
        container=findViewById(R.id.containerPesanan);

        for (int i = 0; i < detailPemesananList.size(); i++) {
            detailPemesananList.remove(i);
        }

        Call<List<SupplierDAO>> callSupplier = apiInterface.getSupplier();
        callSupplier.enqueue(new Callback<List<SupplierDAO>>() {
            @Override
            public void onResponse(Call<List<SupplierDAO>> call, Response<List<SupplierDAO>> response) {
                spinnerArraySup = response.body();
                for (int i = 0; i < spinnerArraySup.size(); i++) {
                    namaSpinnerSup.add(String.valueOf(spinnerArraySup.get(i).getID_SUPPLIER()) + "-" + spinnerArraySup.get(i).getNAMA_SUPPLIER());
                }
                ArrayAdapter<String> adapterSupplier = new ArrayAdapter<>(PemesananTambahActivity.this, R.layout.spinner_supplier_layout, R.id.txtSupplier, namaSpinnerSup);
                spinnerSupplier.setAdapter(adapterSupplier);
            }

            @Override
            public void onFailure(Call<List<SupplierDAO>> call, Throwable t) {

            }
        });

        Call<JSONResponse> callSparepart = apiInterface.getSpareparts();
        callSparepart.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                spinnerArraySpa = response.body().getData();
                for (int i = 0; i < spinnerArraySpa.size(); i++) {
                    namaSpinnerSpa.add(spinnerArraySpa.get(i).getID_SPAREPARTS() + "-" + spinnerArraySpa.get(i).getNAMA_SPAREPART() + ": " + String.valueOf(spinnerArraySpa.get(i).getHARGA_BELI()));
                }
                ArrayAdapter<String> adapterCust = new ArrayAdapter<>(PemesananTambahActivity.this, R.layout.spinner_sparepart_layout, R.id.txtSparepart, namaSpinnerSpa);
                spinnerSparepart.setAdapter(adapterCust);
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {

            }
        });

        imgDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(PemesananTambahActivity.this,
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
                date = year + "-" + month + "-" + dayOfMonth;
                showDate.setText(date);
            }
        };

        addSpa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (date==null){
                    Toast.makeText(getApplicationContext(),"Check your date!", Toast.LENGTH_SHORT).show();
                }else if(jumlahSparepart.getText().toString().equals("") || satuan.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Field can't be empty",Toast.LENGTH_SHORT).show();
                }else{
                    String[] sparepart = spinnerSparepart.getSelectedItem().toString().split("-");
                    String[] spa = spinnerSparepart.getSelectedItem().toString().split(": ");
                    fusion = sparepart[0] + "-" + sparepart[1];
                    jumlah = Integer.parseInt(jumlahSparepart.getText().toString().trim());
                    hargaJual = Double.parseDouble(spa[1]);
                    hargaBeliPemesanan = jumlah * hargaJual;
                    satuann = satuan.getText().toString().trim();
                    subtotal = (hargaJual * jumlah) + subtotal;
                    grandtotal.setText("Rp "+String.valueOf(subtotal));
                    detailPemesananList.add(new DetailPemesananSpaDAO(fusion, jumlah, hargaBeliPemesanan, satuann));
                    Toast.makeText(PemesananTambahActivity.this, String.valueOf(detailPemesananList.size()), Toast.LENGTH_SHORT).show();

                    LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View addView = layoutInflater.inflate(R.layout.row_pemesanan, null);
                    final TextView textOut = addView.findViewById(R.id.textoutKetSpa);
                    final TextView textOut2 = addView.findViewById(R.id.textoutJumlahSpa);
                    final TextView textOut3 = addView.findViewById(R.id.textoutHargaTotPerSpa);
                    textOut.setText(fusion);
                    textOut2.setText(String.valueOf(jumlah));
                    textOut3.setText(String.valueOf(hargaBeliPemesanan));
                    Button buttonRemove = addView.findViewById(R.id.removePemesananSpa);
                    buttonRemove.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            String to= textOut.getText().toString();
                            double to3=Double.parseDouble(textOut3.getText().toString());
                            for (int i = 0; i < detailPemesananList.size(); i++) {
                                if(detailPemesananList.get(i).getID_SPAREPARTS().equals(to))
                                {
                                    detailPemesananList.remove(i);
                                    subtotal=subtotal-to3;
                                    grandtotal.setText(String.valueOf(subtotal));
                                    ((LinearLayout)addView.getParent()).removeView(addView);
                                }
                            }
                        }});
                    container.addView(addView);
                }
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (date==null){
                    Toast.makeText(getApplicationContext(),"Check your date!", Toast.LENGTH_SHORT).show();
                }else if (subtotal==0){
                    Toast.makeText(getApplicationContext(),"You have not choose a thing!", Toast.LENGTH_SHORT).show();
                }else{
                    String[] sup = spinnerSupplier.getSelectedItem().toString().split("-");
                    supplier = Integer.parseInt(sup[0]);
                    final Call<PemesananSparepartDAO> callPem = apiInterface.createDataPesanan(supplier, date, subtotal, "On Process");
                    callPem.enqueue(new Callback<PemesananSparepartDAO>() {
                        @Override
                        public void onResponse(Call<PemesananSparepartDAO> call, Response<PemesananSparepartDAO> response) {
                            Call<List<PemesananSparepartDAO>> calll=apiInterface.getPemesanan();
                            calll.enqueue(new Callback<List<PemesananSparepartDAO>>() {
                                @Override
                                public void onResponse(Call<List<PemesananSparepartDAO>> call, Response<List<PemesananSparepartDAO>> response) {
                                    ArrayPemesanan=response.body();
                                    int ukuran=ArrayPemesanan.size();
                                    int recent=ArrayPemesanan.get(ukuran-1).getID_PEMESANAN();
                                    for (int i=0;i<detailPemesananList.size();i++){
                                        Toast.makeText(PemesananTambahActivity.this,String.valueOf(recent),Toast.LENGTH_SHORT).show();
                                        Call<DetailPemesananSpaDAO> calDet=apiInterface.createDataDetailPesanan(detailPemesananList.get(i).getID_SPAREPARTS(),
                                                recent,
                                                detailPemesananList.get(i).getJUMLAH_PEMESANAN(),
                                                detailPemesananList.get(i).getHARGA_BELI_PEMESANAN(),
                                                detailPemesananList.get(i).getSATUAN());
                                        calDet.enqueue(new Callback<DetailPemesananSpaDAO>() {
                                            @Override
                                            public void onResponse(Call<DetailPemesananSpaDAO> call, Response<DetailPemesananSpaDAO> response) {

                                            }

                                            @Override
                                            public void onFailure(Call<DetailPemesananSpaDAO> call, Throwable t) {
                                            }
                                        });
                                    }
                                }
                                @Override
                                public void onFailure(Call<List<PemesananSparepartDAO>> call, Throwable t) {
//                                Toast.makeText(PemesananTambahActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        @Override
                        public void onFailure(Call<PemesananSparepartDAO> call, Throwable t) {
                            Call<List<PemesananSparepartDAO>> calll=apiInterface.getPemesanan();
                            calll.enqueue(new Callback<List<PemesananSparepartDAO>>() {
                                @Override
                                public void onResponse(Call<List<PemesananSparepartDAO>> call, Response<List<PemesananSparepartDAO>> response) {
                                    ArrayPemesanan=response.body();
                                    int ukuran=ArrayPemesanan.size();
                                    int recent=ArrayPemesanan.get(ukuran-1).getID_PEMESANAN();
                                    Toast.makeText(PemesananTambahActivity.this,String.valueOf(subtotal),Toast.LENGTH_SHORT).show();
                                    for (int i=0;i<detailPemesananList.size();i++){
                                        Toast.makeText(PemesananTambahActivity.this,String.valueOf(recent),Toast.LENGTH_SHORT).show();
                                        Call<DetailPemesananSpaDAO> calDet=apiInterface.createDataDetailPesanan(detailPemesananList.get(i).getID_SPAREPARTS(),
                                                recent,
                                                detailPemesananList.get(i).getJUMLAH_PEMESANAN(),
                                                detailPemesananList.get(i).getHARGA_BELI_PEMESANAN(),
                                                detailPemesananList.get(i).getSATUAN());
                                        calDet.enqueue(new Callback<DetailPemesananSpaDAO>() {
                                            @Override
                                            public void onResponse(Call<DetailPemesananSpaDAO> call, Response<DetailPemesananSpaDAO> response) {

                                            }

                                            @Override
                                            public void onFailure(Call<DetailPemesananSpaDAO> call, Throwable t) {
                                                Toast.makeText(PemesananTambahActivity.this,"Success",Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<PemesananSparepartDAO>> call, Throwable t) {
//                                Toast.makeText(PemesananTambahActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                    startActivity(new Intent(PemesananTambahActivity.this, MainActivity.class));
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), PemesananTampilSemuaActivity.class));
    }
}
