package com.siatmo.siatmoapp.view.customerService.kendaraanPelanggan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.siatmo.siatmoapp.R;
import com.siatmo.siatmoapp.api.ApiInterface;
import com.siatmo.siatmoapp.api.RetrofitClient;
import com.siatmo.siatmoapp.modul.CustomerBikeDAO;
import com.siatmo.siatmoapp.modul.CustomerDAO;
import com.siatmo.siatmoapp.modul.TipeMotorDAO;
import com.siatmo.siatmoapp.view.customerService.MainActivityCS;
import com.siatmo.siatmoapp.view.owner.MainActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KendaraanPelangganTambahActivity extends AppCompatActivity {
    Spinner spinnerTipeMotor, spinnerCustomer;
    ApiInterface apiInterface;
    List<TipeMotorDAO> spinnerArray =  new ArrayList<>();
    List<CustomerDAO> spinnerArrayCust =new ArrayList<>();
    List<String> namaSpinnerTipe= new ArrayList<>();
    List<String> namaSpinnerCust= new ArrayList<>();
    Integer tampungIDCustomer, tampungJenisMotor;
    EditText noPlat;
    String mNoPlat;
    ImageView save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kendaraan_pelanggan_tambah);

        apiInterface = RetrofitClient.getApiClient().create(ApiInterface.class);
        spinnerCustomer=findViewById(R.id.spinnerIdCustomer);
        spinnerTipeMotor = findViewById(R.id.spinnerTipeKendaraan);
        noPlat=findViewById(R.id.inputNomorPolisiTambahKendaraanPel);
        save= findViewById(R.id.save_imgTipeTambahKendaraanPelanggan);

        Call<List<TipeMotorDAO>> callTipeMotor = apiInterface.getMotors();
        callTipeMotor.enqueue(new Callback<List<TipeMotorDAO>>() {
            @Override
            public void onResponse(Call<List<TipeMotorDAO>> callTipeMotor, Response<List<TipeMotorDAO>> response) {

                spinnerArray=response.body();
                for(int i=0; i<spinnerArray.size();i++){
                    namaSpinnerTipe.add(String.format("%03d",spinnerArray.get(i).getID_MOTOR())+"-"+spinnerArray.get(i).getTIPE_MOTOR());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(KendaraanPelangganTambahActivity.this, R.layout.spinner_tipemotor_layout,R.id.txtTipe, namaSpinnerTipe);
                spinnerTipeMotor.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<TipeMotorDAO>> call, Throwable t) {

            }
        });

        Call<List<CustomerDAO>> callCustomer = apiInterface.getCustomer();
        callCustomer.enqueue(new Callback<List<CustomerDAO>>() {
            @Override
            public void onResponse(Call<List<CustomerDAO>> callCustomer, Response<List<CustomerDAO>> response) {
                spinnerArrayCust=response.body();
                for(int i=0; i<spinnerArrayCust.size();i++){
                    namaSpinnerCust.add(String.format("%03d",spinnerArrayCust.get(i).getID_PELANGGAN())+"-"+spinnerArrayCust.get(i).getNAMA_PELANGGAN());
                }
                ArrayAdapter<String> adapterCust = new ArrayAdapter<>(KendaraanPelangganTambahActivity.this, R.layout.spinner_customer_layout,R.id.txtCustomer, namaSpinnerCust);
                spinnerCustomer.setAdapter(adapterCust);
            }

            @Override
            public void onFailure(Call<List<CustomerDAO>> call, Throwable t) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] sprteMotor=spinnerTipeMotor.getSelectedItem().toString().split("-");
                String[] sprteCust=spinnerCustomer.getSelectedItem().toString().split("-");
                tampungJenisMotor = Integer.parseInt(sprteMotor[0]);
                tampungIDCustomer = Integer.parseInt(sprteCust[0]);
                mNoPlat = noPlat.getText().toString().trim();
                Call<CustomerBikeDAO> call = apiInterface.createDataCustomerBike(tampungJenisMotor, tampungIDCustomer, mNoPlat);

                call.enqueue(new Callback<CustomerBikeDAO>() {
                    @Override
                    public void onResponse(Call<CustomerBikeDAO> call, Response<CustomerBikeDAO> response) {
                        Toast.makeText(getApplicationContext(), "Customer's Bike Saved", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<CustomerBikeDAO> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Customer's Bike Saved", Toast.LENGTH_SHORT).show();
                    }
                });
                startActivity(new Intent(KendaraanPelangganTambahActivity.this, KendaraanPelangganTampilSemuaActivity.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), KendaraanPelangganTampilSemuaActivity.class));
    }
}