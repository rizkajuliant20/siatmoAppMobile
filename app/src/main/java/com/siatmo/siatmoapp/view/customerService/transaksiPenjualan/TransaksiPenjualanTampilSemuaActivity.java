package com.siatmo.siatmoapp.view.customerService.transaksiPenjualan;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.siatmo.siatmoapp.R;
import com.siatmo.siatmoapp.adapter.AdapterTransaksiPenjualan;
import com.siatmo.siatmoapp.api.ApiInterface;
import com.siatmo.siatmoapp.api.RetrofitClient;
import com.siatmo.siatmoapp.modul.PenjualanDAO;
import com.siatmo.siatmoapp.view.customerService.kendaraanPelanggan.KendaraanPelangganTampilSemuaActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransaksiPenjualanTampilSemuaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private AdapterTransaksiPenjualan adapterTransaksiPenjualan;
    private List<PenjualanDAO> transList;
    ApiInterface apiInterface;
    AdapterTransaksiPenjualan.RecyclerViewClickListener listener;
    ProgressBar progressBar;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_penjualan_tampil_semua);

        apiInterface = RetrofitClient.getApiClient().create(ApiInterface.class);

        progressBar = findViewById(R.id.progressTransaksi);
        recyclerView = findViewById(R.id.recyclerViewTransaksi);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        listener = new AdapterTransaksiPenjualan.RecyclerViewClickListener() {
            @Override
            public void onRowClick(View view, final int position) {
                Intent intent = new Intent(getApplicationContext(), TransaksiPenjualanUbahActivity.class);
                intent.putExtra("ID_TRANSAKSI", transList.get(position).getID_TRANSAKSI());
                intent.putExtra("ID_CABANG", transList.get(position).getID_CABANG());
                intent.putExtra("ID_PELANGGAN", transList.get(position).getID_PELANGGAN());
                intent.putExtra("TGL_TRANSAKSI", transList.get(position).getTGL_TRANSAKSI());
                intent.putExtra("SUBTOTAL", transList.get(position).getSUBTOTAL());
                intent.putExtra("DISKON", transList.get(position).getDISKON());
                intent.putExtra("GRANDTOTAL", transList.get(position).getGRANDTOTAL());
                intent.putExtra("STATUS_TRANSAKSI", transList.get(position).getSTATUS_TRANSAKSI());
                intent.putExtra("JENIS_TRANSAKSI", transList.get(position).getJENIS_TRANSAKSI());
                startActivity(intent);
            }
        };

        fab=findViewById(R.id.fabTransaksi);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TransaksiPenjualanTampilSemuaActivity.this, TransaksiPenjualanTambahActivity.class));
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_supplier, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName())
        );
        searchView.setQueryHint("Search Transaction...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {

                adapterTransaksiPenjualan.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapterTransaksiPenjualan.getFilter().filter(newText);
                return false;
            }
        });

        searchMenuItem.getIcon().setVisible(false, false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getTransaction(){
        Call<List<PenjualanDAO>> call = apiInterface.getTransaksiPenjualan();
        call.enqueue(new Callback<List<PenjualanDAO>>() {
            @Override
            public void onResponse(Call<List<PenjualanDAO>> call, Response<List<PenjualanDAO>> response) {
                progressBar.setVisibility(View.GONE);
                transList = response.body();
                Log.i(KendaraanPelangganTampilSemuaActivity.class.getSimpleName(), response.body().toString());
                adapterTransaksiPenjualan = new AdapterTransaksiPenjualan(transList, TransaksiPenjualanTampilSemuaActivity.this, listener);
                recyclerView.setAdapter(adapterTransaksiPenjualan);
                adapterTransaksiPenjualan.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<PenjualanDAO>> call, Throwable t) {
                Toast.makeText(TransaksiPenjualanTampilSemuaActivity.this, "rp :"+ t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getTransaction();
    }
}
