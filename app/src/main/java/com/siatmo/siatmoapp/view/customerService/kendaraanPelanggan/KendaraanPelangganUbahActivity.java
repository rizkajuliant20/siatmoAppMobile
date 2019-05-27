package com.siatmo.siatmoapp.view.customerService.kendaraanPelanggan;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.siatmo.siatmoapp.R;
import com.siatmo.siatmoapp.api.ApiInterface;
import com.siatmo.siatmoapp.api.RetrofitClient;
import com.siatmo.siatmoapp.modul.CabangDAO;
import com.siatmo.siatmoapp.modul.CustomerBikeDAO;
import com.siatmo.siatmoapp.modul.CustomerDAO;
import com.siatmo.siatmoapp.modul.TipeMotorDAO;
import com.siatmo.siatmoapp.view.customerService.MainActivityCS;
import com.siatmo.siatmoapp.view.owner.cabang.CabangTampilSemuaActivity;
import com.siatmo.siatmoapp.view.owner.cabang.CabangUbahActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class KendaraanPelangganUbahActivity extends AppCompatActivity {

    private EditText mNoPlat;
    private TextView mIdCustTV, mIdTypeTV;
    private Spinner mIdType, mIdCust;
    private ApiInterface apiInterface = RetrofitClient.getApiClient().create(ApiInterface.class);
    private String  NoPlat;
    private String mNoPlats;
    private int IdType,IdCust,IdCB;
    private TextView tv1, tv2;
    List<TipeMotorDAO> spinnerArrayTipe =  new ArrayList<>();
    List<CustomerDAO> spinnerArrayCust =new ArrayList<>();
    List<String> namaSpinnerTipe= new ArrayList<>();
    List<String> namaSpinnerCust= new ArrayList<>();
    Integer tampungJenisMotor, tampungIDCustomer;
    ImageView img_save, img_del, img_edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kendaraan_pelanggan_ubah);

        mNoPlat=findViewById(R.id.inputNomorPolisiUbahKendaraanPel);
        mIdCust=findViewById(R.id.spinnerIdPelUbahKendaraanPel);
        mIdType=findViewById(R.id.spinnerIdTipeMotorUbahKendaraanPel);

        mIdCustTV=findViewById(R.id.TVIdPelUbahKendaraanPel);
        mIdTypeTV=findViewById(R.id.TVIdTipeUbahKendaraanPel);

        tv1=findViewById(R.id.tv1);
        tv2=findViewById(R.id.tv2);

        img_del=findViewById(R.id.del_imgUbahKend);
        img_edit=findViewById(R.id.edit_imgUbahKend);
        img_save=findViewById(R.id.save_imgUbahKend);

        Intent intent=getIntent();
        IdCB=intent.getIntExtra("ID_KENDARAAN_PEL",0);
        IdCust = intent.getIntExtra("ID_PELANGGAN",0);
        IdType = intent.getIntExtra("ID_MOTOR",0);
        NoPlat = intent.getStringExtra("NO_PLAT");
        setDataFromIntentExtra();

        img_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(KendaraanPelangganUbahActivity.this);
                dialog.setMessage("Delete this Bike?");
                dialog.setPositiveButton("Yes" ,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        deleteData(IdCB);
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

        img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editMode();

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mNoPlat, InputMethodManager.SHOW_IMPLICIT);

                img_edit.setVisibility(View.GONE);
                img_save.setVisibility(View.VISIBLE);
                img_del.setVisibility(View.GONE);
                tv1.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.VISIBLE);
            }
        });

        img_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (IdCB == 0) {
                    if (TextUtils.isEmpty(mNoPlat.getText().toString())){
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(KendaraanPelangganUbahActivity.this);
                        alertDialog.setMessage("Please complete the field!");
                        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialog.show();
                    }
                    else {
                        img_edit.setVisibility(View.VISIBLE);
                        img_save.setVisibility(View.GONE);
                        img_del.setVisibility(View.VISIBLE);
                        readMode();
                    }
                } else {
                    updateData(IdCB);
                    img_edit.setVisibility(View.VISIBLE);
                    img_save.setVisibility(View.GONE);
                    img_del.setVisibility(View.VISIBLE);

                    readMode();
                }
            }
        });
    }

    private void setDataFromIntentExtra() {
        readMode();
        getSupportActionBar().setTitle("Edit Kendaraan Pelanggan");

        mNoPlat.setText(NoPlat);
        mIdCustTV.setText("PLG-"+String.valueOf(IdCust));
        mIdTypeTV.setText("MTR-"+String.valueOf(IdType));

    }

    void readMode(){
        mNoPlat.setFocusableInTouchMode(false);
        mIdCustTV.setFocusableInTouchMode(false);
        mIdTypeTV.setFocusableInTouchMode(false);

        mNoPlat.setFocusable(false);
        mIdCustTV.setFocusable(false);
        mIdTypeTV.setFocusable(false);
    }

    private void editMode(){
        mNoPlat.setFocusableInTouchMode(true);
        mIdCustTV.setVisibility(View.GONE);
        mIdTypeTV.setVisibility(View.GONE);

        mIdCust.setVisibility(View.VISIBLE);
        mIdType.setVisibility(View.VISIBLE);

        retrofit2.Call<List<TipeMotorDAO>> call=apiInterface.getMotors();
        call.enqueue(new Callback<List<TipeMotorDAO>>() {
            @Override
            public void onResponse(retrofit2.Call<List<TipeMotorDAO>> call, Response<List<TipeMotorDAO>> response) {
                spinnerArrayTipe=response.body();
                for(int i=0; i<spinnerArrayTipe.size();i++){
                    namaSpinnerTipe.add(String.format("%03d",spinnerArrayTipe.get(i).getID_MOTOR()));
                }
                ArrayAdapter<String> adapterTipe = new ArrayAdapter<>(KendaraanPelangganUbahActivity.this, R.layout.spinner_tipemotor_layout,R.id.txtTipe, namaSpinnerTipe);
                mIdType.setAdapter(adapterTipe);
            }

            @Override
            public void onFailure(retrofit2.Call<List<TipeMotorDAO>> call, Throwable t) {

            }
        });

        retrofit2.Call<List<CustomerDAO>> callCust=apiInterface.getCustomer();
        callCust.enqueue(new Callback<List<CustomerDAO>>() {
            @Override
            public void onResponse(retrofit2.Call<List<CustomerDAO>> call, Response<List<CustomerDAO>> response) {
                spinnerArrayCust=response.body();
                for(int i=0; i<spinnerArrayCust.size();i++){
                    namaSpinnerCust.add(String.format("%03d",spinnerArrayCust.get(i).getID_PELANGGAN()));
                }
                ArrayAdapter<String> adapterCust = new ArrayAdapter<>(KendaraanPelangganUbahActivity.this, R.layout.spinner_customer_layout,R.id.txtCustomer, namaSpinnerCust);
                mIdCust.setAdapter(adapterCust);
            }

            @Override
            public void onFailure(retrofit2.Call<List<CustomerDAO>> call, Throwable t) {

            }
        });
    }

    private void updateData(final int custBikeId) {

        final ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Updating...");
        progress.show();

        readMode();

        tampungJenisMotor = Integer.parseInt(mIdType.getSelectedItem().toString());
        tampungIDCustomer = Integer.parseInt(mIdCust.getSelectedItem().toString());
        mNoPlats = mNoPlat.getText().toString().trim();

        retrofit2.Call<CustomerBikeDAO> call = apiInterface.editDataCustomerBike(custBikeId,tampungJenisMotor, tampungIDCustomer, mNoPlats);
        call.enqueue(new Callback<CustomerBikeDAO>() {
            @Override
            public void onResponse(retrofit2.Call<CustomerBikeDAO> call, Response<CustomerBikeDAO> response) {
                Toast.makeText(getApplicationContext(), "Success Updating", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(retrofit2.Call<CustomerBikeDAO> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Success Updating", Toast.LENGTH_SHORT).show();
            }
        });
        startActivity(new Intent(KendaraanPelangganUbahActivity.this, KendaraanPelangganTampilSemuaActivity.class));
    }

    private void deleteData(int custBikeId) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Deleting...");
        progressDialog.show();
        readMode();
        apiInterface = RetrofitClient.getApiClient().create(ApiInterface.class);

        retrofit2.Call<CustomerBikeDAO> call = apiInterface.deleteDataCustomerBike(custBikeId);
        call.enqueue(new Callback<CustomerBikeDAO>() {
            @Override
            public void onResponse(retrofit2.Call<CustomerBikeDAO> call, Response<CustomerBikeDAO> response) {
                progressDialog.dismiss();
                Log.i(CabangUbahActivity.class.getSimpleName(), response.toString());
                Toast.makeText(KendaraanPelangganUbahActivity.this, "Success Deleting", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(retrofit2.Call<CustomerBikeDAO> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(KendaraanPelangganUbahActivity.this, "Success Deleting", Toast.LENGTH_SHORT).show();
            }
        });
        startActivity(new Intent(KendaraanPelangganUbahActivity.this, KendaraanPelangganTampilSemuaActivity.class));
    }
}
