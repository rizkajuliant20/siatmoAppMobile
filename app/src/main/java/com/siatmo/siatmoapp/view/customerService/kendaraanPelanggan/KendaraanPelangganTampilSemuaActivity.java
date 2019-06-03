package com.siatmo.siatmoapp.view.customerService.kendaraanPelanggan;

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
import com.siatmo.siatmoapp.adapter.AdapterCustomerBike;
import com.siatmo.siatmoapp.api.ApiInterface;
import com.siatmo.siatmoapp.api.RetrofitClient;
import com.siatmo.siatmoapp.modul.CustomerBikeDAO;
import com.siatmo.siatmoapp.view.customerService.MainActivityCS;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KendaraanPelangganTampilSemuaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private AdapterCustomerBike adapterCustomerBike;
    private List<CustomerBikeDAO> custBikeList;
    ApiInterface apiInterface;
    AdapterCustomerBike.RecyclerViewClickListener listener;
    ProgressBar progressBar;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kendaraan_pelanggan_tampil_semua);

        apiInterface = RetrofitClient.getApiClient().create(ApiInterface.class);

        progressBar = findViewById(R.id.progressCustBike);
        recyclerView = findViewById(R.id.recyclerViewCustBike);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        listener = new AdapterCustomerBike.RecyclerViewClickListener() {
            @Override
            public void onRowClick(View view, final int position) {
                Intent intent = new Intent(getApplicationContext(), KendaraanPelangganUbahActivity.class);
                intent.putExtra("ID_KENDARAAN_PEL", custBikeList.get(position).getID_KENDARAAN_PEL());
                intent.putExtra("ID_PELANGGAN", custBikeList.get(position).getID_PELANGGAN());
                intent.putExtra("ID_MOTOR", custBikeList.get(position).getID_MOTOR());
                intent.putExtra("NO_PLAT", custBikeList.get(position).getNO_PLAT());
                startActivity(intent);
            }
        };

        fab=findViewById(R.id.fabCustBike);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(KendaraanPelangganTampilSemuaActivity.this, KendaraanPelangganTambahActivity.class));
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
        searchView.setQueryHint("Search Plate Number...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {

                adapterCustomerBike.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapterCustomerBike.getFilter().filter(newText);
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

    public void getCustomerBike(){
        Call<List<CustomerBikeDAO>> call = apiInterface.getCustomerBike();
        call.enqueue(new Callback<List<CustomerBikeDAO>>() {
            @Override
            public void onResponse(Call<List<CustomerBikeDAO>> call, Response<List<CustomerBikeDAO>> response) {
                progressBar.setVisibility(View.GONE);
                custBikeList = response.body();
                Log.i(KendaraanPelangganTampilSemuaActivity.class.getSimpleName(), response.body().toString());
                adapterCustomerBike = new AdapterCustomerBike(custBikeList, KendaraanPelangganTampilSemuaActivity.this, listener);
                recyclerView.setAdapter(adapterCustomerBike);
                adapterCustomerBike.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<CustomerBikeDAO>> call, Throwable t) {
                Toast.makeText(KendaraanPelangganTampilSemuaActivity.this, "rp :"+ t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCustomerBike();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivityCS.class));
    }
}
