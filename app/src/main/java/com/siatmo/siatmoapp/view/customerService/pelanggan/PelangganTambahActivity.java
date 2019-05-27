package com.siatmo.siatmoapp.view.customerService.pelanggan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class PelangganTambahActivity extends AppCompatActivity {

    EditText custNama, custAlamat, custTelp;
    ImageView simpan;
    private ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelanggan_tambah);

        custNama = findViewById(R.id.inputNamaPelanggan);
        custAlamat = findViewById(R.id.inputAddressPelanggan);
        custTelp = findViewById(R.id.inputTelpPelanggan);
        simpan= findViewById(R.id.save_imgPelangganTambah);

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

        final String NAMACUSTOMER= this.custNama.getText().toString().trim();
        final String ALAMATCUSTOMER = this.custAlamat.getText().toString().trim();
        final String TELPCUSTOMER = this.custTelp.getText().toString().trim();

        if(NAMACUSTOMER.isEmpty() ||ALAMATCUSTOMER.isEmpty() ||TELPCUSTOMER.isEmpty()){
            progress.dismiss();
            Toast.makeText(this,"Field Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
        }else{
            apiInterface = RetrofitClient.getApiClient().create(ApiInterface.class);
            Call<CustomerDAO> call = apiInterface.createDataCustomer(NAMACUSTOMER, ALAMATCUSTOMER, TELPCUSTOMER);

            call.enqueue(new Callback<CustomerDAO>() {
                @Override
                public void onResponse(Call<CustomerDAO> call, Response<CustomerDAO> response) {
                    progress.dismiss();
                    Toast.makeText(PelangganTambahActivity.this, "Customer Saved!", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onFailure(Call<CustomerDAO> call, Throwable t) {
                    t.printStackTrace();
                    progress.dismiss();
                    Toast.makeText(PelangganTambahActivity.this, "Customer Saved!", Toast.LENGTH_SHORT).show();
                }
            });
            startActivity(new Intent(PelangganTambahActivity.this, PelangganTampilSemuaActivity.class));
        }
    }

    private void VoidCancel() {
        startActivity(new Intent(PelangganTambahActivity.this, PelangganTampilSemuaActivity.class));
    }
}
