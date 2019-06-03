package com.siatmo.siatmoapp.view.owner.sparepart;

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
import com.siatmo.siatmoapp.adapter.AdapterSparepart;
import com.siatmo.siatmoapp.api.ApiInterface;
import com.siatmo.siatmoapp.modul.JSONResponse;
import com.siatmo.siatmoapp.api.RetrofitClient;
import com.siatmo.siatmoapp.modul.SparepartDAO;
import com.siatmo.siatmoapp.view.owner.MainActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SparepartTampilSemuaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private AdapterSparepart adapterSparepart;
    private List<SparepartDAO> spaList;
    ApiInterface apiInterface;
    AdapterSparepart.RecyclerViewClickListener listener;
    ProgressBar progressBar;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sparepart_tampil_semua);

        apiInterface = RetrofitClient.getApiClient().create(ApiInterface.class);

        progressBar = findViewById(R.id.progressSpa);
        recyclerView = findViewById(R.id.recyclerViewSpa);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        listener = new AdapterSparepart.RecyclerViewClickListener(){
            @Override
            public void onRowClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), SparepartUbahActivity.class);
                intent.putExtra("ID_SPAREPARTS", spaList.get(position).getID_SPAREPARTS());
                intent.putExtra("KODE_PENEMPATAN", spaList.get(position).getKODE_PENEMPATAN());
                intent.putExtra("NAMA_SPAREPART", spaList.get(position).getNAMA_SPAREPART());
                intent.putExtra("HARGA_BELI", spaList.get(position).getHARGA_BELI());
                intent.putExtra("HARGA_JUAL", spaList.get(position).getHARGA_JUAL());
                intent.putExtra("STOK_MINIMAL", spaList.get(position).getSTOK_MINIMAL());
                intent.putExtra("STOK_BARANG", spaList.get(position).getSTOK_BARANG());
                intent.putExtra("GAMBAR", spaList.get(position).getGAMBAR());
                intent.putExtra("TIPE", spaList.get(position).getTIPE());
                startActivity(intent);
            }
        };

        fab=findViewById(R.id.fabSpa);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SparepartTampilSemuaActivity.this, SparepartTambahActivity.class));
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
        searchView.setQueryHint("Search Sparepart...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {

                adapterSparepart.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapterSparepart.getFilter().filter(newText);
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

    public void getSparepart(){
        Call<JSONResponse> call = apiInterface.getSpareparts();
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                progressBar.setVisibility(View.GONE);
                spaList = response.body().getData();
                Log.i(SparepartTampilSemuaActivity.class.getSimpleName(), response.body().toString());
                adapterSparepart = new AdapterSparepart(spaList, SparepartTampilSemuaActivity.this, listener);
                recyclerView.setAdapter(adapterSparepart);
                adapterSparepart.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Toast.makeText(SparepartTampilSemuaActivity.this, "rp :"+ t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSparepart();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}
