package com.siatmo.siatmoapp.view.owner.supplier;

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
import com.siatmo.siatmoapp.adapter.AdapterSupplier;
import com.siatmo.siatmoapp.api.ApiInterface;
import com.siatmo.siatmoapp.api.RetrofitClient;
import com.siatmo.siatmoapp.modul.SupplierDAO;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupplierTampilSemuaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private AdapterSupplier adapterSupplier;
    private List<SupplierDAO> supList;
    ApiInterface apiInterface;
    AdapterSupplier.RecyclerViewClickListener listener;
    ProgressBar progressBar;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_tampil_semua);

        apiInterface = RetrofitClient.getApiClient().create(ApiInterface.class);

        progressBar = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        listener = new AdapterSupplier.RecyclerViewClickListener() {
            @Override
            public void onRowClick(View view, final int position) {
                Intent intent = new Intent(getApplicationContext(), SupplierUbahActivity.class);
                intent.putExtra("ID_SUPPLIER", supList.get(position).getID_SUPPLIER());
                intent.putExtra("NAMA_SUPPLIER", supList.get(position).getNAMA_SUPPLIER());
                intent.putExtra("ALAMAT_SUPPLIER", supList.get(position).getALAMAT_SUPPLIER());
                intent.putExtra("TELEPON_SUPPLIER", supList.get(position).getTELEPON_SUPPLIER());
                intent.putExtra("NAMA_SALES", supList.get(position).getNAMA_SALES());
                intent.putExtra("TELEPON_SALES", supList.get(position).getTELEPON_SALES());
                startActivity(intent);

            }
        };

        fab=findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SupplierTampilSemuaActivity.this, SupplierTambahActivity.class));
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
        searchView.setQueryHint("Search Supplier...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {

                adapterSupplier.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapterSupplier.getFilter().filter(newText);
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

    public void getSupplier(){
        Call<List<SupplierDAO>> call = apiInterface.getSupplier();
        call.enqueue(new Callback<List<SupplierDAO>>() {
            @Override
            public void onResponse(Call<List<SupplierDAO>> call, Response<List<SupplierDAO>> response) {
                progressBar.setVisibility(View.GONE);
                supList = response.body();
                Log.i(SupplierTampilSemuaActivity.class.getSimpleName(), response.body().toString());
                adapterSupplier = new AdapterSupplier(supList, SupplierTampilSemuaActivity.this, listener);
                recyclerView.setAdapter(adapterSupplier);
                adapterSupplier.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<SupplierDAO>> call, Throwable t) {
                Toast.makeText(SupplierTampilSemuaActivity.this, "rp :"+ t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupplier();
    }
}
