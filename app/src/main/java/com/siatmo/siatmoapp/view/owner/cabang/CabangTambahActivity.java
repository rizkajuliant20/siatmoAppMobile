package com.siatmo.siatmoapp.view.owner.cabang;

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
import com.siatmo.siatmoapp.modul.CabangDAO;
import com.siatmo.siatmoapp.modul.SupplierDAO;
import com.siatmo.siatmoapp.view.customerService.MainActivityCS;
import com.siatmo.siatmoapp.view.owner.MainActivity;
import com.siatmo.siatmoapp.view.owner.supplier.SupplierTambahActivity;
import com.siatmo.siatmoapp.view.owner.supplier.SupplierTampilSemuaActivity;
import com.siatmo.siatmoapp.view.owner.tipeMotor.TipeMotorTambahActivity;
import com.siatmo.siatmoapp.view.owner.tipeMotor.TipeMotorTampilSemuaActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CabangTambahActivity extends AppCompatActivity {

    EditText cabNama, cabAlamat, cabTelp;
    ImageView simpan;
    private ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cabang_tambah);

        cabNama = findViewById(R.id.inputNamaTambahCabang);
        cabAlamat = findViewById(R.id.inputAlamatTambahCabang);
        cabTelp = findViewById(R.id.inputNomorTelpTambahCabang);
        simpan= findViewById(R.id.save_imgCabSimpan);

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

        final String NAMACAB = this.cabNama.getText().toString().trim();
        final String ALAMATCAB = this.cabAlamat.getText().toString().trim();
        final String TELPCAB = this.cabTelp.getText().toString().trim();

        if(NAMACAB.isEmpty() ||ALAMATCAB.isEmpty() ||TELPCAB.isEmpty()){
            progress.dismiss();
            Toast.makeText(this,"Field Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
        }else{
            apiInterface = RetrofitClient.getApiClient().create(ApiInterface.class);
            Call<CabangDAO> call = apiInterface.createDataCabang(NAMACAB,ALAMATCAB,TELPCAB);

            call.enqueue(new Callback<CabangDAO>() {
                @Override
                public void onResponse(Call<CabangDAO> call, Response<CabangDAO> response) {
                    progress.dismiss();
                    Toast.makeText(CabangTambahActivity.this, "Supplier Saved!", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onFailure(Call<CabangDAO> call, Throwable t) {
                    t.printStackTrace();
                    progress.dismiss();
                    Toast.makeText(CabangTambahActivity.this, "Supplier Saved!", Toast.LENGTH_SHORT).show();
                }
            });
            startActivity(new Intent(CabangTambahActivity.this, CabangTampilSemuaActivity.class));
        }
    }

    private void VoidCancel() {
        startActivity(new Intent(CabangTambahActivity.this, MainActivity.class));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), CabangTampilSemuaActivity.class));
    }
}
