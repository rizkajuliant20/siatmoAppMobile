package com.siatmo.siatmoapp.view.owner.tipeMotor;

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
import com.siatmo.siatmoapp.adapter.AdapterMotor;
import com.siatmo.siatmoapp.api.ApiInterface;
import com.siatmo.siatmoapp.api.RetrofitClient;
import com.siatmo.siatmoapp.modul.TipeMotorDAO;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TipeMotorTampilSemuaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private AdapterMotor adapterMotor;
    private List<TipeMotorDAO> tipeList;
    ApiInterface apiInterface;
    AdapterMotor.RecyclerViewClickListener listener;
    ProgressBar progressBar;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipe_motor_tampil_semua);

        apiInterface = RetrofitClient.getApiClient().create(ApiInterface.class);

        progressBar = findViewById(R.id.progressTipe);
        recyclerView = findViewById(R.id.recyclerViewTipe);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        listener = new AdapterMotor.RecyclerViewClickListener() {
            @Override
            public void onRowClick(View view, final int position) {
                Intent intent = new Intent(getApplicationContext(), TipeMotorUbahActivity.class);
                intent.putExtra("ID_MOTOR", tipeList.get(position).getID_MOTOR());
                intent.putExtra("MERK_MOTOR", tipeList.get(position).getMERK_MOTOR());
                intent.putExtra("TIPE_MOTOR", tipeList.get(position).getTIPE_MOTOR());
                startActivity(intent);

            }
        };

        fab=findViewById(R.id.fabTipe);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TipeMotorTampilSemuaActivity.this, TipeMotorTambahActivity.class));
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
        searchView.setQueryHint("Search Tipe Motor...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                adapterMotor.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterMotor.getFilter().filter(newText);
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

    public void getTipeMotor(){
        Call<List<TipeMotorDAO>> call = apiInterface.getMotors();
        call.enqueue(new Callback<List<TipeMotorDAO>>() {
            @Override
            public void onResponse(Call<List<TipeMotorDAO>> call, Response<List<TipeMotorDAO>> response) {
                progressBar.setVisibility(View.GONE);
                tipeList = response.body();
                Log.i(TipeMotorTampilSemuaActivity.class.getSimpleName(), response.body().toString());
                adapterMotor = new AdapterMotor(tipeList, TipeMotorTampilSemuaActivity.this, listener);
                recyclerView.setAdapter(adapterMotor);
                adapterMotor.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<TipeMotorDAO>> call, Throwable t) {
                Toast.makeText(TipeMotorTampilSemuaActivity.this, "rp :"+ t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getTipeMotor();
    }
}
