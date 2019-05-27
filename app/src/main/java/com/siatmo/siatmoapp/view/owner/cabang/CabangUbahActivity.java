package com.siatmo.siatmoapp.view.owner.cabang;

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
import com.siatmo.siatmoapp.modul.CabangDAO;
import com.siatmo.siatmoapp.view.owner.MainActivity;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CabangUbahActivity extends AppCompatActivity {

    private EditText mcabNama, mcabAlamat, mcabTelepon, mcabId;

    private ApiInterface apiInterface;
    private String cabNama, cabAlamat, cabTelepon;
    private int cabId;

    ImageView img_save, img_del, img_edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cabang_ubah);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mcabId=findViewById(R.id.inputIDUbahCabang);
        mcabNama = findViewById(R.id.inputNamaUbahCabang);
        mcabAlamat = findViewById(R.id.inputAlamatUbahCabang);
        mcabTelepon =findViewById(R.id.inputNomorTelpUbahCabang);

        img_del=findViewById(R.id.del_imgCabUbah);
        img_edit=findViewById(R.id.edit_imgCabUbah);
        img_save=findViewById(R.id.save_imgCabUbah);

        Intent intent=getIntent();
        cabId = intent.getIntExtra("ID_CABANG",0);
        cabNama = intent.getStringExtra("NAMA_CABANG");
        cabAlamat = intent.getStringExtra("ALAMAT_CABANG");
        cabTelepon = intent.getStringExtra("TELEPON_CABANG");
        setDataFromIntentExtra();

        img_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(CabangUbahActivity.this);
                dialog.setMessage("Delete this Branch?");
                dialog.setPositiveButton("Yes" ,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        deleteData(cabId);
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
                imm.showSoftInput(mcabNama, InputMethodManager.SHOW_IMPLICIT);

                img_edit.setVisibility(View.GONE);
                img_save.setVisibility(View.VISIBLE);
                img_del.setVisibility(View.GONE);
            }
        });

        img_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cabId == 0) {
                    if (TextUtils.isEmpty(mcabNama.getText().toString()) ||
                            TextUtils.isEmpty(mcabAlamat.getText().toString()) ||
                            TextUtils.isEmpty(mcabTelepon.getText().toString())){
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CabangUbahActivity.this);
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
                    updateData(cabId);
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
            getSupportActionBar().setTitle("Edit Cabang");

            mcabId.setText(Integer.toString(cabId));
            mcabNama.setText(cabNama);
            mcabAlamat.setText(cabAlamat);
            mcabTelepon.setText(cabTelepon);
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

    void readMode(){
        mcabId.setFocusableInTouchMode(false);
        mcabNama.setFocusableInTouchMode(false);
        mcabAlamat.setFocusableInTouchMode(false);
        mcabTelepon.setFocusableInTouchMode(false);

        mcabId.setFocusable(false);
        mcabNama.setFocusable(false);
        mcabAlamat.setFocusable(false);
        mcabTelepon.setFocusable(false);
    }

    private void editMode(){
        mcabNama.setFocusableInTouchMode(true);
        mcabAlamat.setFocusableInTouchMode(true);
        mcabTelepon.setFocusableInTouchMode(true);
    }

    private void updateData(final int cabId) {

        final ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Updating...");
        progress.show();

        readMode();

        String NAMACABANG = mcabNama.getText().toString().trim();
        String ALAMATCABANG = mcabAlamat.getText().toString().trim();
        String TELPCABANG = mcabTelepon.getText().toString().trim();

        if(NAMACABANG.isEmpty() ||ALAMATCABANG.isEmpty() ||TELPCABANG.isEmpty()){
            progress.dismiss();
            Toast.makeText(this,"Field Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
        }else {
            apiInterface = RetrofitClient.getApiClient().create(ApiInterface.class);
            Call<CabangDAO> call = apiInterface.editDataCabang(cabId, NAMACABANG, ALAMATCABANG, TELPCABANG);

            call.enqueue(new Callback<CabangDAO>() {
                @Override
                public void onResponse(Call<CabangDAO> call, Response<CabangDAO> response) {
                    progress.dismiss();
                    Toast.makeText(CabangUbahActivity.this, "Branch Saved!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<CabangDAO> call, Throwable t) {
                    t.printStackTrace();
                    progress.dismiss();
                    Toast.makeText(CabangUbahActivity.this, "Branch Saved!", Toast.LENGTH_SHORT).show();
                }
            });
            startActivity(new Intent(CabangUbahActivity.this, CabangTampilSemuaActivity.class));
        }
    }

    private void deleteData(int cabId) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Deleting...");
        progressDialog.show();
        readMode();
        apiInterface = RetrofitClient.getApiClient().create(ApiInterface.class);

        Call<CabangDAO> call = apiInterface.deleteDataCabang(cabId);
        call.enqueue(new Callback<CabangDAO>() {
            @Override
            public void onResponse(Call<CabangDAO> call, Response<CabangDAO> response) {
                progressDialog.dismiss();
                Log.i(CabangUbahActivity.class.getSimpleName(), response.toString());
                Toast.makeText(CabangUbahActivity.this, "Success Deleting", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<CabangDAO> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(CabangUbahActivity.this, "Success Deleting", Toast.LENGTH_SHORT).show();
            }
        });
        startActivity(new Intent(CabangUbahActivity.this, CabangTampilSemuaActivity.class));
    }
}
