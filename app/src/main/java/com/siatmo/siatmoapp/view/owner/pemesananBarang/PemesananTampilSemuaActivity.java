package com.siatmo.siatmoapp.view.owner.pemesananBarang;

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
import com.siatmo.siatmoapp.adapter.AdapterPemesanan;
import com.siatmo.siatmoapp.api.ApiInterface;
import com.siatmo.siatmoapp.api.RetrofitClient;
import com.siatmo.siatmoapp.modul.PemesananSparepartDAO;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PemesananTampilSemuaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private AdapterPemesanan adapterPemesanan;
    private List<PemesananSparepartDAO> orderList;
    ApiInterface apiInterface;
    AdapterPemesanan.RecyclerViewClickListener listener;
    ProgressBar progressBar;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemesanan_tampil_semua);

        apiInterface = RetrofitClient.getApiClient().create(ApiInterface.class);

        progressBar = findViewById(R.id.progressOrder);
        recyclerView = findViewById(R.id.recyclerViewOrder);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

            listener = new AdapterPemesanan.RecyclerViewClickListener() {
            @Override
            public void onRowClick(View view, final int position) {
                Intent intent = new Intent(getApplicationContext(), PemesananUbahActivity.class);
                intent.putExtra("ID_PEMESANAN", orderList.get(position).getID_PEMESANAN());
                intent.putExtra("ID_SUPPLIER", orderList.get(position).getID_SUPPLIER());
                intent.putExtra("TGL_PEMESANAN", orderList.get(position).getTGL_PEMESANAN());
                intent.putExtra("GRANDTOTAL_PEMESANAN", orderList.get(position).getGRANDTOTAL_PEMESANAN());
                intent.putExtra("STATUS_PEMESANAN", orderList.get(position).getSTATUS_PEMESANAN());
                startActivity(intent);

            }
        };

        fab=findViewById(R.id.fabOrder);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PemesananTampilSemuaActivity.this, PemesananTambahActivity.class));
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
        searchView.setQueryHint("Search Date of Order...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {

                adapterPemesanan.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapterPemesanan.getFilter().filter(newText);
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

    public void getOrder(){
        Call<List<PemesananSparepartDAO>> call = apiInterface.getPemesanan();
        call.enqueue(new Callback<List<PemesananSparepartDAO>>() {
            @Override
            public void onResponse(Call<List<PemesananSparepartDAO>> call, Response<List<PemesananSparepartDAO>> response) {
                progressBar.setVisibility(View.GONE);
                orderList = response.body();
                Log.i(PemesananTampilSemuaActivity.class.getSimpleName(), response.body().toString());
                adapterPemesanan = new AdapterPemesanan(orderList, PemesananTampilSemuaActivity.this, listener);
                recyclerView.setAdapter(adapterPemesanan);
                adapterPemesanan.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<PemesananSparepartDAO>> call, Throwable t) {
                Toast.makeText(PemesananTampilSemuaActivity.this, "rp :"+ t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getOrder();
    }
}
