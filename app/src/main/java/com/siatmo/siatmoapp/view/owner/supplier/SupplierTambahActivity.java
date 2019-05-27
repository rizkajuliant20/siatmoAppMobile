package com.siatmo.siatmoapp.view.owner.supplier;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.siatmo.siatmoapp.R;
import com.siatmo.siatmoapp.api.ApiInterface;
import com.siatmo.siatmoapp.api.RetrofitClient;
import com.siatmo.siatmoapp.modul.SupplierDAO;
import com.siatmo.siatmoapp.view.owner.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupplierTambahActivity extends AppCompatActivity {

    EditText supNama, supAlamat, supTelp, salNama, salTelp;
    ImageView simpan;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_tambah);
        supNama = findViewById(R.id.inputNamaTambahSup);
        supAlamat = findViewById(R.id.inputAlamatTambahSup);
        supTelp = findViewById(R.id.inputNomorTeleponSupTambahSup);
        salNama = findViewById(R.id.inputNamaSalesTambahSup);
        salTelp = findViewById(R.id.inputNomorTeleponSalesTambahSup);
        simpan= findViewById(R.id.SimpanTambahSup);

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save();
            }
        });

    }

    private void Save() {
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Saving...");
        progress.show();

        final String NAMASUP = this.supNama.getText().toString().trim();
        final String ALAMATSUP = this.supAlamat.getText().toString().trim();
        final String TELPSUP = this.supTelp.getText().toString().trim();
        final String NAMASAL = this.salNama.getText().toString().trim();
        final String TELPSAL = this.salTelp.getText().toString().trim();

        if(NAMASUP.isEmpty() ||ALAMATSUP.isEmpty() ||TELPSUP.isEmpty() ||ALAMATSUP.isEmpty() ||NAMASAL.isEmpty()||TELPSAL.isEmpty()){
            progress.dismiss();
            Toast.makeText(this,"Field Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
        }else{
            apiInterface = RetrofitClient.getApiClient().create(ApiInterface.class);
            Call<SupplierDAO> call = apiInterface.createData(NAMASUP,ALAMATSUP,TELPSUP,NAMASAL,TELPSAL);

            call.enqueue(new Callback<SupplierDAO>() {
                @Override
                public void onResponse(Call<SupplierDAO> call, Response<SupplierDAO> response) {
                    String value = response.body().getVALUE();
                    String message = response.body().getMESSAGE();
                    progress.dismiss();
                    if (value.equals("1")) {
                        Toast.makeText(SupplierTambahActivity.this, message, Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(SupplierTambahActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<SupplierDAO> call, Throwable t) {
                    t.printStackTrace();
                    progress.dismiss();
                    Toast.makeText(SupplierTambahActivity.this, "Supplier Saved!", Toast.LENGTH_SHORT).show();
                }
            });
            startActivity(new Intent(SupplierTambahActivity.this, SupplierTampilSemuaActivity.class));
        }
    }

    private void VoidCancel() {
        startActivity(new Intent(SupplierTambahActivity.this, SupplierTampilSemuaActivity.class));
    }

}
