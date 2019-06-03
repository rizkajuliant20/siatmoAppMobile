package com.siatmo.siatmoapp.view.customerService;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.siatmo.siatmoapp.R;
import com.siatmo.siatmoapp.api.ApiInterface;
import com.siatmo.siatmoapp.session.SharedPrefManager;
import com.siatmo.siatmoapp.view.LandingPageMainActivity;
import com.siatmo.siatmoapp.view.customerService.kendaraanPelanggan.KendaraanPelangganTampilSemuaActivity;
import com.siatmo.siatmoapp.view.customerService.pelanggan.PelangganTampilSemuaActivity;
import com.siatmo.siatmoapp.view.customerService.transaksiPenjualan.TransaksiPenjualanTambahActivity;
import com.siatmo.siatmoapp.view.customerService.transaksiPenjualan.TransaksiPenjualanTampilSemuaActivity;

public class MainActivityCS extends AppCompatActivity {

    private SharedPrefManager sharedPrefManager;
    private TextView txtNamaAkses, txtNamaRole;
    private String pengakses, pengaksesTest;
    private ApiInterface apiInterface;
    private boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cs);
        txtNamaAkses=findViewById(R.id.txtNamaAksesPegawai);
        txtNamaRole=findViewById(R.id.txtRole);
        //LOGOUT WITH SESSION
        sharedPrefManager = new SharedPrefManager(this);
        findViewById(R.id.layoutlogoutCS).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
                startActivity(new Intent(MainActivityCS.this, LandingPageMainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });
        pengaksesTest= sharedPrefManager.getSPNama().toString();
        String splitRole[]= pengaksesTest.split("-");
        txtNamaAkses.setText(splitRole[1]);
        if (splitRole[0].equals("CS")){
            txtNamaRole.setText("Customer Service");
        }else{
            txtNamaRole.setText(splitRole[0]);
        }

    }

    public void kendaraanPelanggan(View view){
        Intent i= new Intent(MainActivityCS.this,KendaraanPelangganTampilSemuaActivity.class);
        startActivity(i);
    }

    public void pelanggan(View view){
        Intent i= new Intent(MainActivityCS.this,PelangganTampilSemuaActivity.class);
        startActivity(i);
    }

    public void transaksiPenjualan(View view){
        Intent i= new Intent(MainActivityCS.this,TransaksiPenjualanTampilSemuaActivity.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click back again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        },2000);
    }
}
