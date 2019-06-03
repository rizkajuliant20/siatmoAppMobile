package com.siatmo.siatmoapp.view.customerService.pelanggan;

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
import com.siatmo.siatmoapp.adapter.AdapterCustomer;
import com.siatmo.siatmoapp.api.ApiInterface;
import com.siatmo.siatmoapp.api.RetrofitClient;
import com.siatmo.siatmoapp.modul.CustomerDAO;
import com.siatmo.siatmoapp.view.customerService.MainActivityCS;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PelangganTampilSemuaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private AdapterCustomer adapterCustomer;
    private List<CustomerDAO> custList;
    ApiInterface apiInterface;
    AdapterCustomer.RecyclerViewClickListener listener;
    ProgressBar progressBar;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelanggan_tampil_semua);

        apiInterface = RetrofitClient.getApiClient().create(ApiInterface.class);

        progressBar = findViewById(R.id.progressPelanggan);
        recyclerView = findViewById(R.id.recyclerViewPelanggan);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        listener = new AdapterCustomer.RecyclerViewClickListener() {
            @Override
            public void onRowClick(View view, final int position) {
                Intent intent = new Intent(getApplicationContext(), PelangganUbahActivity.class);
                intent.putExtra("ID_PELANGGAN", custList.get(position).getID_PELANGGAN());
                intent.putExtra("NAMA_PELANGGAN", custList.get(position).getNAMA_PELANGGAN());
                intent.putExtra("ALAMAT_PELANGGAN", custList.get(position).getALAMAT_PELANGGAN());
                intent.putExtra("TELEPON_PELANGGAN", custList.get(position).getTELEPON_PELANGGAN());
                startActivity(intent);

            }
        };

        fab=findViewById(R.id.fabPelanggan);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PelangganTampilSemuaActivity.this, PelangganTambahActivity.class));
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
        searchView.setQueryHint("Search Customer...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {

                adapterCustomer.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapterCustomer.getFilter().filter(newText);
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

    public void getCustomer(){
        Call<List<CustomerDAO>> call = apiInterface.getCustomer();
        call.enqueue(new Callback<List<CustomerDAO>>() {
            @Override
            public void onResponse(Call<List<CustomerDAO>> call, Response<List<CustomerDAO>> response) {
                progressBar.setVisibility(View.GONE);
                custList = response.body();
                Log.i(PelangganTampilSemuaActivity.class.getSimpleName(), response.body().toString());
                adapterCustomer = new AdapterCustomer(custList, PelangganTampilSemuaActivity.this, listener);
                recyclerView.setAdapter(adapterCustomer);
                adapterCustomer.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<CustomerDAO>> call, Throwable t) {
                Toast.makeText(PelangganTampilSemuaActivity.this, "rp :"+ t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCustomer();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivityCS.class));
    }
}
