package com.siatmo.siatmoapp.view.pelanggan;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.siatmo.siatmoapp.R;
import com.siatmo.siatmoapp.adapter.AdapterSparepart;
import com.siatmo.siatmoapp.adapter.RecycleAdapter;
import com.siatmo.siatmoapp.api.ApiInterface;
import com.siatmo.siatmoapp.api.RetrofitClient;
import com.siatmo.siatmoapp.modul.JSONResponse;
import com.siatmo.siatmoapp.modul.SparepartDAO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KatalogSparepartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecycleAdapter adapter;
    private List<SparepartDAO> sparepartDAOList=new ArrayList<>();
    private ApiInterface apiInterface;
    private ImageView sortUpJumlah, sortDownJumlah, sortUpHarga, sortDownHarga;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_katalog_sparepart);

        recyclerView = findViewById(R.id.myrecycle);


        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setTitle("Katalog Sparepart");
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(15), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        apiInterface= RetrofitClient.getApiClient().create(ApiInterface.class);
        getData();

        sortDownHarga=findViewById(R.id.sortDownHarga);
        sortDownJumlah= findViewById(R.id.sortDownJumlah);
        sortUpHarga= findViewById(R.id.sortUpHarga);
        sortUpJumlah= findViewById(R.id.sortUpJumlah);

        sortDownJumlah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<sparepartDAOList.size();i++){
                    sparepartDAOList.remove(i);
                }
                sortUpJumlah.setVisibility(View.VISIBLE);
                sortDownJumlah.setVisibility(View.GONE);
                getDataDownJumlah();
            }
        });
        sortUpJumlah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<sparepartDAOList.size();i++){
                    sparepartDAOList.remove(i);
                }
                sortUpJumlah.setVisibility(View.GONE);
                sortDownJumlah.setVisibility(View.VISIBLE);
                getDataUpJumlah();
            }
        });

        sortUpHarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<sparepartDAOList.size();i++){
                    sparepartDAOList.remove(i);
                }
                sortUpHarga.setVisibility(View.GONE);
                sortDownHarga.setVisibility(View.VISIBLE);
                getDataUpHarga();
            }
        });

        sortDownHarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<sparepartDAOList.size();i++){
                    sparepartDAOList.remove(i);
                }
                sortUpHarga.setVisibility(View.VISIBLE);
                sortDownHarga.setVisibility(View.GONE);
                getDataDownHarga();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    private void getData() {

        Call<JSONResponse> call=apiInterface.getSpareparts();
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                sparepartDAOList =response.body().getData();
                adapter = new RecycleAdapter(KatalogSparepartActivity.this, sparepartDAOList);
                recyclerView.setAdapter(adapter);
                Toast.makeText(getApplicationContext(),"Welcome",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDataUpHarga() {

        Call<JSONResponse> call=apiInterface.sortingHargaAsc();
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                sparepartDAOList =response.body().getData();
                adapter = new RecycleAdapter(KatalogSparepartActivity.this, sparepartDAOList);
                recyclerView.setAdapter(adapter);
                Toast.makeText(getApplicationContext(),"Welcome",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDataDownHarga() {

        Call<JSONResponse> call=apiInterface.sortingHargaDesc();
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                sparepartDAOList =response.body().getData();
                adapter = new RecycleAdapter(KatalogSparepartActivity.this, sparepartDAOList);
                recyclerView.setAdapter(adapter);
                Toast.makeText(getApplicationContext(),"Welcome",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDataUpJumlah() {

        Call<JSONResponse> call=apiInterface.sortingJumlahAsc();
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                sparepartDAOList =response.body().getData();
                adapter = new RecycleAdapter(KatalogSparepartActivity.this, sparepartDAOList);
                recyclerView.setAdapter(adapter);
                Toast.makeText(getApplicationContext(),"Welcome",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDataDownJumlah() {
        Call<JSONResponse> call=apiInterface.sortingJumlahDesc();
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                sparepartDAOList =response.body().getData();
                adapter = new RecycleAdapter(KatalogSparepartActivity.this, sparepartDAOList);
                recyclerView.setAdapter(adapter);
                Toast.makeText(getApplicationContext(),"Welcome",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

}