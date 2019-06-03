package com.siatmo.siatmoapp.view.customerService.pelanggan;

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
import com.siatmo.siatmoapp.modul.CustomerDAO;
import com.siatmo.siatmoapp.view.customerService.MainActivityCS;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PelangganUbahActivity extends AppCompatActivity {

    private EditText mcustNama, mcustAddress, mCustTelp, mCustId;
    private ApiInterface apiInterface;
    private String custNama, custAddress, custTelp;
    private int custId;

    ImageView img_save, img_del, img_edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelanggan_ubah);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mCustId =findViewById(R.id.inputIDUbahPelanggan);
        mcustNama=findViewById(R.id.inputNamaUbahPelanggan);
        mcustAddress = findViewById(R.id.inputAddressUbahPelanggan);
        mCustTelp = findViewById(R.id.inputTelpUbahPelanggan);


        img_del=findViewById(R.id.del_imgPelangganUbah);
        img_edit=findViewById(R.id.edit_imgPelangganUbah);
        img_save=findViewById(R.id.save_imgPelangganUbah);

        Intent intent=getIntent();
        custId = intent.getIntExtra("ID_PELANGGAN",0);
        custNama = intent.getStringExtra("NAMA_PELANGGAN");
        custAddress = intent.getStringExtra("ALAMAT_PELANGGAN");
        custTelp = intent.getStringExtra("TELEPON_PELANGGAN");
        setDataFromIntentExtra();

        img_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(PelangganUbahActivity.this);
                dialog.setMessage("Delete this Customer?");
                dialog.setPositiveButton("Yes" ,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        deleteData(custId);
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
                imm.showSoftInput(mcustNama, InputMethodManager.SHOW_IMPLICIT);

                img_edit.setVisibility(View.GONE);
                img_save.setVisibility(View.VISIBLE);
                img_del.setVisibility(View.GONE);
            }
        });

        img_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (custId == 0) {
                    if (TextUtils.isEmpty(mcustNama.getText().toString()) ||
                            TextUtils.isEmpty(mcustAddress.getText().toString()) ||
                            TextUtils.isEmpty(mCustTelp.getText().toString())){
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(PelangganUbahActivity.this);
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
                    updateData(custId);
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
        getSupportActionBar().setTitle("Edit Pelanggan");

        mCustId.setText(Integer.toString(custId));
        mcustNama.setText(custNama);
        mcustAddress.setText(custAddress);
        mCustTelp.setText(custTelp);
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
        mCustId.setFocusableInTouchMode(false);
        mcustNama.setFocusableInTouchMode(false);
        mcustAddress.setFocusableInTouchMode(false);
        mCustTelp.setFocusableInTouchMode(false);

        mCustId.setFocusable(false);
        mcustNama.setFocusable(false);
        mcustAddress.setFocusable(false);
        mCustTelp.setFocusable(false);
    }

    private void editMode(){
        mcustNama.setFocusableInTouchMode(true);
        mcustAddress.setFocusableInTouchMode(true);
        mCustTelp.setFocusableInTouchMode(true);
    }

    private void updateData(final int custId) {

        final ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Updating...");
        progress.show();

        readMode();

        String NAMAPELANGGAN = mcustNama.getText().toString().trim();
        String ALAMATPELANGGAN = mcustAddress.getText().toString().trim();
        String TELPPELANGGAN = mCustTelp.getText().toString().trim();

        if(NAMAPELANGGAN.isEmpty() ||ALAMATPELANGGAN.isEmpty() ||TELPPELANGGAN.isEmpty()){
            progress.dismiss();
            Toast.makeText(this,"Field Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
        }else {
            apiInterface = RetrofitClient.getApiClient().create(ApiInterface.class);
            Call<CustomerDAO> call = apiInterface.editDataCustomer(custId, NAMAPELANGGAN, ALAMATPELANGGAN, TELPPELANGGAN);

            call.enqueue(new Callback<CustomerDAO>() {
                @Override
                public void onResponse(Call<CustomerDAO> call, Response<CustomerDAO> response) {
                    progress.dismiss();
                    Toast.makeText(PelangganUbahActivity.this, "Customer Saved!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<CustomerDAO> call, Throwable t) {
                    t.printStackTrace();
                    progress.dismiss();
                    Toast.makeText(PelangganUbahActivity.this, "Customer Saved!", Toast.LENGTH_SHORT).show();
                }
            });
            startActivity(new Intent(PelangganUbahActivity.this, PelangganTampilSemuaActivity.class));
        }
    }

    private void deleteData(int custId) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Deleting...");
        progressDialog.show();
        readMode();
        apiInterface = RetrofitClient.getApiClient().create(ApiInterface.class);

        Call<CustomerDAO> call = apiInterface.deleteDataCustomer(custId);
        call.enqueue(new Callback<CustomerDAO>() {
            @Override
            public void onResponse(Call<CustomerDAO> call, Response<CustomerDAO> response) {
                progressDialog.dismiss();
                Log.i(PelangganUbahActivity.class.getSimpleName(), response.toString());
                Toast.makeText(PelangganUbahActivity.this, "Success Deleting", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<CustomerDAO> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(PelangganUbahActivity.this, "Success Deleting", Toast.LENGTH_SHORT).show();
            }
        });
        startActivity(new Intent(PelangganUbahActivity.this, PelangganTampilSemuaActivity.class));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), PelangganTampilSemuaActivity.class));
    }
}
