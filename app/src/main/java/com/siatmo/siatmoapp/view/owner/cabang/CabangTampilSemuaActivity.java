package com.siatmo.siatmoapp.view.owner.cabang;

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
import com.siatmo.siatmoapp.adapter.AdapterCabang;
import com.siatmo.siatmoapp.api.ApiInterface;
import com.siatmo.siatmoapp.api.RetrofitClient;
import com.siatmo.siatmoapp.modul.CabangDAO;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CabangTampilSemuaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private AdapterCabang adapterCabang;
    private List<CabangDAO> cabList;
    ApiInterface apiInterface;
    AdapterCabang.RecyclerViewClickListener listener;
    ProgressBar progressBar;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cabang_tampil_semua);

        apiInterface = RetrofitClient.getApiClient().create(ApiInterface.class);

        progressBar = findViewById(R.id.progressCab);
        recyclerView = findViewById(R.id.recyclerViewCab);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        listener = new AdapterCabang.RecyclerViewClickListener() {
            @Override
            public void onRowClick(View view, final int position) {
                Intent intent = new Intent(getApplicationContext(), CabangUbahActivity.class);
                intent.putExtra("ID_CABANG", cabList.get(position).getID_CABANG());
                intent.putExtra("NAMA_CABANG", cabList.get(position).getNAMA_CABANG());
                intent.putExtra("ALAMAT_CABANG", cabList.get(position).getALAMAT_CABANG());
                intent.putExtra("TELEPON_CABANG", cabList.get(position).getTELEPON_CABANG());
                startActivity(intent);

            }
        };

        fab=findViewById(R.id.fabCab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CabangTampilSemuaActivity.this, CabangTambahActivity.class));
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
        searchView.setQueryHint("Search Branch...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {

                adapterCabang.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapterCabang.getFilter().filter(newText);
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

    public void getCabang(){
        Call<List<CabangDAO>> call = apiInterface.getCabangs();
        call.enqueue(new Callback<List<CabangDAO>>() {
            @Override
            public void onResponse(Call<List<CabangDAO>> call, Response<List<CabangDAO>> response) {
                progressBar.setVisibility(View.GONE);
                cabList = response.body();
                Log.i(CabangTampilSemuaActivity.class.getSimpleName(), response.body().toString());
                adapterCabang = new AdapterCabang(cabList, CabangTampilSemuaActivity.this, listener);
                recyclerView.setAdapter(adapterCabang);
                adapterCabang.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<CabangDAO>> call, Throwable t) {
                Toast.makeText(CabangTampilSemuaActivity.this, "rp :"+ t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCabang();
    }
}
