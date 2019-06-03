package com.siatmo.siatmoapp.view.owner;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.siatmo.siatmoapp.R;
import com.siatmo.siatmoapp.adapter.AdapterSparepart;
import com.siatmo.siatmoapp.api.ApiInterface;
import com.siatmo.siatmoapp.api.RetrofitClient;
import com.siatmo.siatmoapp.modul.JSONResponse;
import com.siatmo.siatmoapp.modul.SparepartDAO;
import com.siatmo.siatmoapp.session.SharedPrefManager;
import com.siatmo.siatmoapp.view.LandingPageMainActivity;
import com.siatmo.siatmoapp.view.owner.pemesananBarang.HistoriBarangMasukActivity;
import com.siatmo.siatmoapp.view.owner.pemesananBarang.PemesananTambahActivity;
import com.siatmo.siatmoapp.view.owner.cabang.CabangTampilSemuaActivity;
import com.siatmo.siatmoapp.view.owner.pemesananBarang.PemesananTampilSemuaActivity;
import com.siatmo.siatmoapp.view.owner.sparepart.SparepartTampilSemuaActivity;
import com.siatmo.siatmoapp.view.owner.sparepart.StokTakOptimalActivity;
import com.siatmo.siatmoapp.view.owner.supplier.SupplierTampilSemuaActivity;
import com.siatmo.siatmoapp.view.owner.tipeMotor.TipeMotorTampilSemuaActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ApiInterface apiInterface;
    private List<SparepartDAO> spaList;
    private boolean doubleBackToExitPressedOnce = false;
    SharedPrefManager sharedPrefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannel();
        apiInterface = RetrofitClient.getApiClient().create(ApiInterface.class);


        //LOGOUT WITH SESSION
        sharedPrefManager = new SharedPrefManager(this);
        findViewById(R.id.layoutlogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
                startActivity(new Intent(MainActivity.this, LandingPageMainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });

        notifikasiSpareparts();

    }

    public void supplier(View view){
        Intent i= new Intent(MainActivity.this,SupplierTampilSemuaActivity.class);
        startActivity(i);
    }

    public void sparepart(View view){
        Intent i= new Intent(MainActivity.this,SparepartTampilSemuaActivity.class);
        startActivity(i);
    }

    public void branch(View view){
        Intent i= new Intent(MainActivity.this,CabangTampilSemuaActivity.class);
        startActivity(i);
    }

    public void pemesananBarang(View view){
        Intent i= new Intent(MainActivity.this,PemesananTampilSemuaActivity.class);
        startActivity(i);
    }

    public void tipeMotor(View view){
        Intent i= new Intent(MainActivity.this,TipeMotorTampilSemuaActivity.class);
        startActivity(i);
    }

    public void notif(View view){
        Intent i= new Intent(MainActivity.this,StokTakOptimalActivity.class);
        startActivity(i);
    }

    public void logout(View view){
        Intent i= new Intent(MainActivity.this,LandingPageMainActivity.class);
        startActivity(i);
    }

    public void historiBarangMasuk(View view){
        Intent i=new Intent(MainActivity.this, HistoriBarangMasukActivity.class);
        startActivity(i);
    }


    /////Notifikasai


    private void notifikasiSpareparts() {
        Call<JSONResponse> call = apiInterface.getSpareparts();
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                spaList = response.body().getData();
                notifikasikurangdariStokOp((ArrayList) spaList);

            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "rp :"+ t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "ASU";
            String description = "OKE";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("1", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void notifikasikurangdariStokOp(ArrayList<SparepartDAO> sparepartList) {
        for(int i=0; i<sparepartList.size(); i++){
            SparepartDAO data = sparepartList.get(i);
            if(data.getSTOK_BARANG()<data.getSTOK_MINIMAL())
                makeNotifikasi(data);
        }
    }

    private void makeNotifikasi(SparepartDAO data) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "ASU";
            String description = "OKE";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("1", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }


        Intent intent = new Intent(this, StokTakOptimalActivity.class);


        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.drawable.ic_arrow_drop_down_black_24dp)
                .setContentTitle("Pemberitahuan Stok Kurang Dari Optimal")
                .setContentText("WARNING! STOK HARUS DI RESTOCK!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        notificationManager.notify(0, builder.build());

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
