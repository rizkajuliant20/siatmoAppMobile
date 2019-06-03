package com.siatmo.siatmoapp.view.owner.supplier;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.siatmo.siatmoapp.R;
import com.siatmo.siatmoapp.api.ApiInterface;
import com.siatmo.siatmoapp.api.RetrofitClient;
import com.siatmo.siatmoapp.modul.SupplierDAO;
import com.siatmo.siatmoapp.view.owner.MainActivity;
import com.siatmo.siatmoapp.view.owner.cabang.CabangTampilSemuaActivity;
import com.siatmo.siatmoapp.view.owner.cabang.CabangUbahActivity;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupplierUbahActivity extends AppCompatActivity {

    private EditText msupNama, msupAlamat, msupTelepon, msalNama, msalTelepon;

    private ApiInterface apiInterface;
    private String supNama, supAlamat, supTelepon, salNama, salTelepon;
    private int supId;

    ImageView img_save, img_del, img_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_ubah);


        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        msupNama = findViewById(R.id.inputNamaUbahSup);
        msupAlamat = findViewById(R.id.inputAlamatUbahSup);
        msupTelepon =findViewById(R.id.inputNomorSupUbahSup);
        msalNama = findViewById(R.id.inputNamaSalesUbahSup);
        msalTelepon=findViewById(R.id.inputNomorSalesUbahSup);

        img_del=findViewById(R.id.del_img);
        img_edit=findViewById(R.id.edit_img);
        img_save=findViewById(R.id.save_img);

        Intent intent=getIntent();
        supId = intent.getIntExtra("ID_SUPPLIER",0);
        supNama = intent.getStringExtra("NAMA_SUPPLIER");
        supAlamat = intent.getStringExtra("ALAMAT_SUPPLIER");
        supTelepon = intent.getStringExtra("TELEPON_SUPPLIER");
        salNama = intent.getStringExtra("NAMA_SALES");
        salTelepon = intent.getStringExtra("TELEPON_SALES");
        setDataFromIntentExtra();

//        Log.i(SupplierUbahActivity.class.getName(), supNama);


        //DELETE
        img_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(SupplierUbahActivity.this);
                dialog.setMessage("Delete this Supplier?");
                dialog.setPositiveButton("Yes" ,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        deleteData(supId);
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


        //EDIT

        img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editMode();

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(msupNama, InputMethodManager.SHOW_IMPLICIT);

                img_edit.setVisibility(View.GONE);
                img_save.setVisibility(View.VISIBLE);
                img_del.setVisibility(View.GONE);
            }
        });

        //SAVE
        img_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (supId == 0) {
                    if (TextUtils.isEmpty(msupNama.getText().toString()) ||
                            TextUtils.isEmpty(msupAlamat.getText().toString()) ||
                            TextUtils.isEmpty(msupTelepon.getText().toString()) ||
                            TextUtils.isEmpty(msalNama.getText().toString()) ||
                            TextUtils.isEmpty(msalTelepon.getText().toString())){
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SupplierUbahActivity.this);
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
//                        postData();
                        img_edit.setVisibility(View.VISIBLE);
                        img_save.setVisibility(View.GONE);
                        img_del.setVisibility(View.VISIBLE);
                        readMode();
                    }
                } else {
                    updateData(supId);
                    img_edit.setVisibility(View.VISIBLE);
                    img_save.setVisibility(View.GONE);
                    img_del.setVisibility(View.VISIBLE);

                    readMode();
                }
            }
        });
    }

    private void setDataFromIntentExtra() {
        if (supId != 0) {
            readMode();
            getSupportActionBar().setTitle("Edit Supplier");
            //+ supNama.toString()

            msupNama.setText(supNama);
            msupAlamat.setText(supAlamat);
            msupTelepon.setText(supTelepon);
            msalNama.setText(salNama);
            msalTelepon.setText(salTelepon);
        } else {
            getSupportActionBar().setTitle("Tambah Supplier");
        }
    }

//----------------------------------------------------------------------------------------
    private void postData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.show();

        readMode();

        String supNama = msupNama.getText().toString().trim();
        String supAlamat = msupAlamat.getText().toString().trim();
        String supTelepon = msupTelepon.getText().toString().trim();
        String salNama = msalNama.getText().toString().trim();
        String salTelepon = msalTelepon.getText().toString().trim();


        apiInterface = RetrofitClient.getApiClient().create(ApiInterface.class);

        Call<SupplierDAO> call = apiInterface.createData(supNama,supAlamat,supTelepon,salNama,salTelepon);

        call.enqueue(new Callback<SupplierDAO>() {
            @Override
            public void onResponse(Call<SupplierDAO> call, Response<SupplierDAO> response) {

                progressDialog.dismiss();

                Log.i(SupplierUbahActivity.class.getSimpleName(), response.toString());

                int value = response.body().getID_SUPPLIER();

                if (value!=0){
                    finish();
                } else {
                    Toast.makeText(SupplierUbahActivity.this, "Failed to Save", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SupplierDAO> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(SupplierUbahActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }

        });

    }
//============================================================================================================================
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

    private void updateData(final int supId) {

        final ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Updating...");
        progress.show();

        readMode();

        String NAMASUP = msupNama.getText().toString().trim();
        String ALAMATSUP = msupAlamat.getText().toString().trim();
        String TELPSUP = msupTelepon.getText().toString().trim();
        String NAMASAL = msalNama.getText().toString().trim();
        String TELPSAL = msalTelepon.getText().toString().trim();

        if(NAMASUP.isEmpty() ||ALAMATSUP.isEmpty() ||TELPSUP.isEmpty() ||ALAMATSUP.isEmpty() ||NAMASAL.isEmpty()||TELPSAL.isEmpty()){
            progress.dismiss();
            Toast.makeText(this,"Field Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
        }else {
            apiInterface = RetrofitClient.getApiClient().create(ApiInterface.class);
            Call<SupplierDAO> call = apiInterface.editData(supId, NAMASUP, ALAMATSUP, TELPSUP, NAMASAL, TELPSAL);

            call.enqueue(new Callback<SupplierDAO>() {
                @Override
                public void onResponse(Call<SupplierDAO> call, Response<SupplierDAO> response) {
                    String value = response.body().getVALUE();
                    String message = response.body().getMESSAGE();
                    progress.dismiss();
                    if (value.equals("1")) {
                        Toast.makeText(SupplierUbahActivity.this, message, Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(SupplierUbahActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<SupplierDAO> call, Throwable t) {
                    t.printStackTrace();
                    progress.dismiss();
                    Toast.makeText(SupplierUbahActivity.this, "Supplier Saved!", Toast.LENGTH_SHORT).show();
                }
            });
            startActivity(new Intent(SupplierUbahActivity.this, SupplierTampilSemuaActivity.class));
        }
    }

    private void deleteData(int supId) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Deleting...");
        progressDialog.show();
        readMode();
        apiInterface = RetrofitClient.getApiClient().create(ApiInterface.class);

        Call<SupplierDAO> call = apiInterface.deleteData(supId);
        call.enqueue(new Callback<SupplierDAO>() {
            @Override
            public void onResponse(Call<SupplierDAO> call, Response<SupplierDAO> response) {
                progressDialog.dismiss();

                Log.i(SupplierUbahActivity.class.getSimpleName(), response.toString());
                String value = response.body().getVALUE();
                String message = response.body().getMESSAGE();
                if (value.equals("1")){
                    Toast.makeText(SupplierUbahActivity.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(SupplierUbahActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SupplierDAO> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(SupplierUbahActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        startActivity(new Intent(SupplierUbahActivity.this, SupplierTampilSemuaActivity.class));
    }

    void readMode(){
        msupNama.setFocusableInTouchMode(false);
        msupAlamat.setFocusableInTouchMode(false);
        msupTelepon.setFocusableInTouchMode(false);
        msalNama.setFocusableInTouchMode(false);
        msalTelepon.setFocusableInTouchMode(false);

        msupNama.setFocusable(false);
        msupAlamat.setFocusable(false);
        msupTelepon.setFocusable(false);
        msalNama.setFocusable(false);
        msalTelepon.setFocusable(false);
    }

    private void editMode(){
        msupNama.setFocusableInTouchMode(true);
        msupAlamat.setFocusableInTouchMode(true);
        msupTelepon.setFocusableInTouchMode(true);
        msalTelepon.setFocusableInTouchMode(true);
        msalNama.setFocusableInTouchMode(true);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), SupplierTampilSemuaActivity.class));
    }
}
