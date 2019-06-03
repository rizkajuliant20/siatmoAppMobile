package com.siatmo.siatmoapp.view.owner.pemesananBarang;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PemesananUbahActivity extends AppCompatActivity {
    private Button imgDatePicker;
    private ImageView save, addSpa, delete, edit;
    private Spinner spinnerSupplier, spinnerSparepart;
    private LinearLayout container;
    private TextView showDate, grandtotal, showSupplier;
    private ImageView cekUdah,cekBelom;
    private EditText jumlahSparepart, satuan;
    List<SparepartDAO> spinnerArraySpa = new ArrayList<>();
    List<SupplierDAO> spinnerArraySup = new ArrayList<>();
    List<PemesananSparepartDAO> ArrayPemesanan = new ArrayList<>();
    List<DetailPemesananSpaDAO> ArrayDetailPemesanan = new ArrayList<>();
    List<DetailPemesananSpaDAO> detailPemesananList = new ArrayList<>();
    List<DetailPemesananSpaDAO> detailPemesananListBaru = new ArrayList<>();
    private int tampung;
    private int jumlah;
    private String supplier;
    private String satuann, fusion;
    private double hargaJual, subtotal;
    List<String> namaSpinnerSpa= new ArrayList<>();
    List<String> namaSpinnerSup= new ArrayList<>();
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    ApiInterface apiInterface;
    private String date;
    double hargaBeliPemesanan;
    List<DetailPemesananSpaDAO> lalala = new ArrayList<>();
    String tampungNamaSupplier;
    String orderTgl, orderStatus;
    Double orderSubtotal;
    Integer orderId, orderSupplier;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemesanan_ubah);

        imgDatePicker=findViewById(R.id.tanggalPemesananUbah);
        subtotal = 0;
        tampung = 0;
        addSpa = findViewById(R.id.addSpaPemesananUbah);
        save = findViewById(R.id.save_imgOrderUbah);
        delete=findViewById(R.id.del_imgOrderUbah);
        edit=findViewById(R.id.edit_imgOrderUbah);
        spinnerSparepart = findViewById(R.id.spinnerSparepartPemesananUbah);
        spinnerSupplier = findViewById(R.id.spinnerSupplierPemesananUbah);
        showDate = findViewById(R.id.showTanggalUbah);
        jumlahSparepart = findViewById(R.id.jumlahSpaUbah);
        satuan = findViewById(R.id.satuanUbah);
        apiInterface = RetrofitClient.getApiClient().create(ApiInterface.class);
        grandtotal=findViewById(R.id.tvGrandTotalUbah);
        container=findViewById(R.id.containerPesananUbah);

        jumlahSparepart.setFocusableInTouchMode(false);
        satuan.setFocusableInTouchMode(false);

        Intent intent=getIntent();
        orderId = intent.getIntExtra("ID_PEMESANAN",0);
        orderTgl = intent.getStringExtra("TGL_PEMESANAN");
        orderSupplier = intent.getIntExtra("ID_SUPPLIER",0);
        orderStatus = intent.getStringExtra("STATUS_PEMESANAN");
        orderSubtotal= intent.getDoubleExtra("GRANDTOTAL_PEMESANAN",0);
        setDataFromIntentExtra();


        for (int i = 0; i < detailPemesananList.size(); i++) {
            detailPemesananList.remove(i);
        }

        for (int i = 0; i < detailPemesananListBaru.size(); i++) {
            detailPemesananListBaru.remove(i);
        }

        for (int i = 0; i < ArrayDetailPemesanan.size(); i++) {
            detailPemesananListBaru.remove(i);
        }

        //Detail Pemesanan==========================================================================================================

        Call<List<DetailPemesananSpaDAO>> callDet=apiInterface.getDetailPemesanan();
        callDet.enqueue(new Callback<List<DetailPemesananSpaDAO>>() {
            @Override
            public void onResponse(final Call<List<DetailPemesananSpaDAO>> call, Response<List<DetailPemesananSpaDAO>> response) {
                lalala=response.body();
                for (int i=0;i<lalala.size();i++){
                    LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View addView = layoutInflater.inflate(R.layout.row_pemesanan_ubah, null);
                    final TextView textOut = addView.findViewById(R.id.textoutKetSpaUbah);
                    final TextView textOut2 = addView.findViewById(R.id.textoutJumlahSpaUbah);
                    final TextView textOut3 = addView.findViewById(R.id.textoutHargaTotPerSpaUbah);
                    if(lalala.get(i).getID_PEMESANAN()==orderId){
                        detailPemesananList.add(new DetailPemesananSpaDAO(lalala.get(i).getID_SPAREPARTS(),
                                lalala.get(i).getJUMLAH_PEMESANAN(),
                                lalala.get(i).getHARGA_BELI_PEMESANAN(),
                                lalala.get(i).getSATUAN()));
                        textOut.setText(lalala.get(i).getID_SPAREPARTS());
                        textOut2.setText(String.valueOf(lalala.get(i).getJUMLAH_PEMESANAN()));
                        textOut3.setText(String.valueOf(lalala.get(i).getHARGA_BELI_PEMESANAN()));
                        subtotal=subtotal+lalala.get(i).getHARGA_BELI_PEMESANAN();
                        container.addView(addView);
                        final Button buttonRemove = addView.findViewById(R.id.removePemesananSpaUbah);
                        buttonRemove.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final String to= textOut.getText().toString();
                                final Double to3=Double.parseDouble(textOut3.getText().toString());
                                Call<DetailPemesananSpaDAO> callDeleteDetail=apiInterface.deleteDataDetailTertentu(orderId,to);
                                callDeleteDetail.enqueue(new Callback<DetailPemesananSpaDAO>() {
                                    @Override
                                    public void onResponse(Call<DetailPemesananSpaDAO> call, Response<DetailPemesananSpaDAO> response) {
                                        for (int i = 0; i < detailPemesananList.size(); i++) {
                                            if(detailPemesananList.get(i).getID_SPAREPARTS().equals(to))
                                            {
                                                detailPemesananList.remove(i);
                                                subtotal=subtotal-to3;
                                                grandtotal.setText("Rp "+String.valueOf(subtotal));
                                                ((LinearLayout)addView.getParent()).removeView(addView);
                                                Toast.makeText(PemesananUbahActivity.this,"Success",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<DetailPemesananSpaDAO> call, Throwable t) {
                                        for (int i = 0; i < detailPemesananList.size(); i++) {
                                            if(detailPemesananList.get(i).getID_SPAREPARTS().equals(to))
                                            {
                                                detailPemesananList.remove(i);
                                                subtotal=subtotal-to3;
                                                grandtotal.setText("Rp "+String.valueOf(subtotal));
                                                ((LinearLayout)addView.getParent()).removeView(addView);
                                                Toast.makeText(PemesananUbahActivity.this,"Success",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                });
                            }
                        });
                    }
                }
                grandtotal.setText("Rp "+String.valueOf(subtotal));
            }

            @Override
            public void onFailure(Call<List<DetailPemesananSpaDAO>> call, Throwable t) {

            }
        });

        //=======================================================================================================================


        Call<List<SupplierDAO>> callSupplier = apiInterface.getSupplier();
        callSupplier.enqueue(new Callback<List<SupplierDAO>>() {
            @Override
            public void onResponse(Call<List<SupplierDAO>> call, Response<List<SupplierDAO>> response) {
                spinnerArraySup = response.body();

                for (int i = 0; i < spinnerArraySup.size(); i++) {
                    namaSpinnerSup.add(String.valueOf(spinnerArraySup.get(i).getID_SUPPLIER()) + "-" + spinnerArraySup.get(i).getNAMA_SUPPLIER());
                    if(spinnerArraySup.get(i).getID_SUPPLIER()==orderSupplier){
                        tampungNamaSupplier=spinnerArraySup.get(i).getNAMA_SUPPLIER();
                    }
                }

                ArrayAdapter<String> adapterSupplier = new ArrayAdapter<>(PemesananUbahActivity.this, R.layout.spinner_supplier_layout, R.id.txtSupplier, namaSpinnerSup);
                spinnerSupplier.setAdapter(adapterSupplier);

                int spinnerPosition = adapterSupplier.getPosition(String.valueOf(orderSupplier)+"-"+tampungNamaSupplier);
                spinnerSupplier.setSelection(spinnerPosition);
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
                ArrayAdapter<String> adapterCust = new ArrayAdapter<>(PemesananUbahActivity.this, R.layout.spinner_sparepart_layout, R.id.txtSparepart, namaSpinnerSpa);
                spinnerSparepart.setAdapter(adapterCust);
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {

            }
        });
        readMode();

        //====================================SAVE
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateData(orderId);
                edit.setVisibility(View.VISIBLE);
                save.setVisibility(View.GONE);
                delete.setVisibility(View.VISIBLE);

                readMode();
            }
        });

        //====================================DELETE
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(PemesananUbahActivity.this);
                dialog.setMessage("Delete this Supplier?");
                dialog.setPositiveButton("Yes" ,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        deleteData(orderId);
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        cekUdah=findViewById(R.id.cekUdah);
        cekBelom=findViewById(R.id.cekBelom);
        //====================================EDIT
        if(orderStatus.equals("Arrived")){
            edit.setVisibility(View.GONE);
            cekBelom.setVisibility(View.GONE);
            cekUdah.setVisibility(View.VISIBLE);
        }else {
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editMode();
                    jumlahSparepart.setFocusableInTouchMode(true);
                    satuan.setFocusableInTouchMode(true);
                    addDetailPemesananBaru();

                    //DATE==========================================================
                    imgDatePicker.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Calendar calendar = Calendar.getInstance();
                            int year = calendar.get(Calendar.YEAR);
                            int month = calendar.get(Calendar.MONTH);
                            int day = calendar.get(Calendar.DAY_OF_MONTH);

                            DatePickerDialog dialog = new DatePickerDialog(PemesananUbahActivity.this,
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

                    //=========================================================================================================
                }
            });
        }

        cekBelom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiInterface=RetrofitClient.getApiClient().create(ApiInterface.class);
                Call<PemesananSparepartDAO> editStatus=apiInterface.editDataPesanan(orderId,orderId,orderSupplier,orderTgl,orderSubtotal,"Arrived");
                editStatus.enqueue(new Callback<PemesananSparepartDAO>() {
                    @Override
                    public void onResponse(Call<PemesananSparepartDAO> call, Response<PemesananSparepartDAO> response) {
                        edit.setVisibility(View.GONE);
                        cekBelom.setVisibility(View.GONE);
                        cekUdah.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<PemesananSparepartDAO> call, Throwable t) {
                        edit.setVisibility(View.GONE);
                        cekBelom.setVisibility(View.GONE);
                        cekUdah.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void setDataFromIntentExtra() {
        showDate.setText(orderTgl);
        getSupportActionBar().setTitle("Edit Pemesanan");

    }

    private void addDetailPemesananBaru() {
        addSpa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(jumlahSparepart.getText().toString().equals("") || satuan.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Field can't be empty!",Toast.LENGTH_SHORT).show();
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
                    detailPemesananListBaru.add(new DetailPemesananSpaDAO(fusion, jumlah, hargaBeliPemesanan, satuann));
                    Toast.makeText(PemesananUbahActivity.this, String.valueOf(detailPemesananList.size()), Toast.LENGTH_SHORT).show();

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
                                    detailPemesananListBaru.remove(i);
                                    subtotal=subtotal-to3;
                                    grandtotal.setText("Rp "+String.valueOf(subtotal));
                                    ((LinearLayout)addView.getParent()).removeView(addView);
                                }
                            }
                        }});
                    container.addView(addView);
                }
            }
        });

    }

    private void readMode() {
        jumlahSparepart.setFocusableInTouchMode(false);
        satuan.setFocusableInTouchMode(false);

        jumlahSparepart.setFocusable(false);
        satuan.setFocusable(false);
    }

    private void editMode() {
        jumlahSparepart.setFocusableInTouchMode(true);
        satuan.setFocusableInTouchMode(true);

        edit.setVisibility(View.GONE);
        save.setVisibility(View.VISIBLE);
        delete.setVisibility(View.GONE);
    }

    private void UpdateData(final int orderId){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating...");
        progressDialog.show();
        readMode();
        apiInterface=RetrofitClient.getApiClient().create(ApiInterface.class);
        String[] sup = spinnerSupplier.getSelectedItem().toString().split("-");
        supplier = sup[0];
        date=showDate.getText().toString();
        String statusss = orderStatus;
        String[] subtotalss= grandtotal.getText().toString().split(" ");
        Toast.makeText(PemesananUbahActivity.this, date, Toast.LENGTH_SHORT).show();
        Call<PemesananSparepartDAO> callPem = apiInterface.editDataPesanan(orderId, orderId, Integer.parseInt(supplier), date, Double.parseDouble(subtotalss[1]), statusss);
        callPem.enqueue(new Callback<PemesananSparepartDAO>() {
            @Override
            public void onResponse(Call<PemesananSparepartDAO> call, Response<PemesananSparepartDAO> response) {
                Call<List<PemesananSparepartDAO>> calll = apiInterface.getPemesanan();
                calll.enqueue(new Callback<List<PemesananSparepartDAO>>() {
                    @Override
                    public void onResponse(Call<List<PemesananSparepartDAO>> call, Response<List<PemesananSparepartDAO>> response) {
                        for (int i=0;i<detailPemesananListBaru.size();i++){
                            Toast.makeText(PemesananUbahActivity.this,String.valueOf(orderId),Toast.LENGTH_SHORT).show();
                            Call<DetailPemesananSpaDAO> calDet=apiInterface.createDataDetailPesanan(detailPemesananListBaru.get(i).getID_SPAREPARTS(),
                                    orderId,
                                    detailPemesananListBaru.get(i).getJUMLAH_PEMESANAN(),
                                    detailPemesananListBaru.get(i).getHARGA_BELI_PEMESANAN(),
                                    detailPemesananListBaru.get(i).getSATUAN());
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
                    }
                });
            }
            @Override
            public void onFailure(Call<PemesananSparepartDAO> call, Throwable t) {
                Call<List<PemesananSparepartDAO>> calll=apiInterface.getPemesanan();
                calll.enqueue(new Callback<List<PemesananSparepartDAO>>() {
                    @Override
                    public void onResponse(Call<List<PemesananSparepartDAO>> call, Response<List<PemesananSparepartDAO>> response) {
                        for (int i=0;i<detailPemesananListBaru.size();i++){
                            Toast.makeText(PemesananUbahActivity.this,String.valueOf(orderId),Toast.LENGTH_SHORT).show();
                            Call<DetailPemesananSpaDAO> calDet=apiInterface.createDataDetailPesanan(detailPemesananListBaru.get(i).getID_SPAREPARTS(),
                                    orderId,
                                    detailPemesananListBaru.get(i).getJUMLAH_PEMESANAN(),
                                    detailPemesananListBaru.get(i).getHARGA_BELI_PEMESANAN(),
                                    detailPemesananListBaru.get(i).getSATUAN());
                            calDet.enqueue(new Callback<DetailPemesananSpaDAO>() {
                                @Override
                                public void onResponse(Call<DetailPemesananSpaDAO> call, Response<DetailPemesananSpaDAO> response) {
                                    Toast.makeText(PemesananUbahActivity.this,"Success:)",Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(Call<DetailPemesananSpaDAO> call, Throwable t) {

                                }
                            });
                        }
                    }
                    @Override
                    public void onFailure(Call<List<PemesananSparepartDAO>> call, Throwable t) {
                    }
                });
           }
 });
        startActivity(new Intent(PemesananUbahActivity.this, MainActivity.class));

    }

    private void deleteData(final int orderId) {
        final ProgressDialog progressDialog = new ProgressDialog(PemesananUbahActivity.this);
        progressDialog.setMessage("Deleting...");
        progressDialog.show();
        readMode();
        apiInterface = RetrofitClient.getApiClient().create(ApiInterface.class);

        Call<List<DetailPemesananSpaDAO>> call=apiInterface.getDetailPemesanan();
        call.enqueue(new Callback<List<DetailPemesananSpaDAO>>() {
            @Override
            public void onResponse(Call<List<DetailPemesananSpaDAO>> call, Response<List<DetailPemesananSpaDAO>> response) {
                ArrayDetailPemesanan=response.body();
                for (int i=0;i<ArrayDetailPemesanan.size();i++){
                    if(ArrayDetailPemesanan.get(i).getID_PEMESANAN()==orderId){
                        Call<DetailPemesananSpaDAO> calDel=apiInterface.deleteDataDetailPesanan(ArrayDetailPemesanan.get(i).getID_DETAIL_PEMESANAN());
                        calDel.enqueue(new Callback<DetailPemesananSpaDAO>() {
                            @Override
                            public void onResponse(Call<DetailPemesananSpaDAO> call, Response<DetailPemesananSpaDAO> response) {

                            }

                            @Override
                            public void onFailure(Call<DetailPemesananSpaDAO> call, Throwable t) {

                            }
                        });
                    }
                }

                Call<PemesananSparepartDAO> calDelPem=apiInterface.deleteDataPesanan(orderId);
                calDelPem.enqueue(new Callback<PemesananSparepartDAO>() {
                    @Override
                    public void onResponse(Call<PemesananSparepartDAO> call, Response<PemesananSparepartDAO> response) {
                        Toast.makeText(PemesananUbahActivity.this,"Success",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<PemesananSparepartDAO> call, Throwable t) {
                        Toast.makeText(PemesananUbahActivity.this,"Success",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<List<DetailPemesananSpaDAO>> call, Throwable t) {

            }
        });
        startActivity(new Intent(PemesananUbahActivity.this,PemesananTampilSemuaActivity.class));
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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), PemesananTampilSemuaActivity.class));
    }
}
