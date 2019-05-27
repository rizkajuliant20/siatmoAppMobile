package com.siatmo.siatmoapp.view.owner.tipeMotor;

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
import com.siatmo.siatmoapp.modul.SupplierDAO;
import com.siatmo.siatmoapp.modul.TipeMotorDAO;
import com.siatmo.siatmoapp.view.owner.MainActivity;
import com.siatmo.siatmoapp.view.owner.supplier.SupplierTambahActivity;
import com.siatmo.siatmoapp.view.owner.supplier.SupplierTampilSemuaActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TipeMotorTambahActivity extends AppCompatActivity {
    EditText tipeNamaTipe, tipeMerk;
    ImageView simpan;
    private ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipe_motor_tambah);

        tipeNamaTipe = findViewById(R.id.inputTipeTambahTipeMotor);
        tipeMerk = findViewById(R.id.inputMerkTambahTipeMotor);
        simpan= findViewById(R.id.save_imgTipeTambah);

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

        final String TIPE = this.tipeNamaTipe.getText().toString().trim();
        final String MERK = this.tipeMerk.getText().toString().trim();

        if(TIPE.isEmpty() ||MERK.isEmpty()){
            progress.dismiss();
            Toast.makeText(this,"Field Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
        }else{
            apiInterface = RetrofitClient.getApiClient().create(ApiInterface.class);
            Call<TipeMotorDAO> call = apiInterface.createDataMotor(TIPE,MERK);

            call.enqueue(new Callback<TipeMotorDAO>() {
                @Override
                public void onResponse(Call<TipeMotorDAO> call, Response<TipeMotorDAO> response) {
                    progress.dismiss();
                    Toast.makeText(TipeMotorTambahActivity.this, "Bike Type Saved!", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onFailure(Call<TipeMotorDAO> call, Throwable t) {
                    t.printStackTrace();
                    progress.dismiss();
                    Toast.makeText(TipeMotorTambahActivity.this, "Bike Type Saved!", Toast.LENGTH_SHORT).show();
                }
            });
            startActivity(new Intent(TipeMotorTambahActivity.this, TipeMotorTampilSemuaActivity.class));
        }
    }

    private void VoidCancel() {
        startActivity(new Intent(TipeMotorTambahActivity.this, TipeMotorTampilSemuaActivity.class));
    }
}
