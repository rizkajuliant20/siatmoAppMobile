package com.siatmo.siatmoapp.view.owner.tipeMotor;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.siatmo.siatmoapp.R;
import com.siatmo.siatmoapp.api.ApiInterface;
import com.siatmo.siatmoapp.api.RetrofitClient;
import com.siatmo.siatmoapp.modul.TipeMotorDAO;
import com.siatmo.siatmoapp.view.owner.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TipeMotorUbahActivity extends AppCompatActivity {

    private EditText mtipeId, mtipeTipe, mtipeMerk;

    private ApiInterface apiInterface;
    private String tipeTipe, tipeMerk;
    private int tipeId;

    ImageView img_save, img_del, img_edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipe_motor_ubah);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mtipeId=findViewById(R.id.inputIdUbahTipeMotor);
        mtipeTipe = findViewById(R.id.inputTipeUbahTipeMotor);
        mtipeMerk = findViewById(R.id.inputMerkUbahTipeMotor);

        img_del=findViewById(R.id.del_imgTipeUbah);
        img_edit=findViewById(R.id.edit_imgTipeUbah);
        img_save=findViewById(R.id.save_imgTipeUbah);

        Intent intent=getIntent();
        tipeId = intent.getIntExtra("ID_MOTOR",0);
        tipeTipe = intent.getStringExtra("MERK_MOTOR");
        tipeMerk = intent.getStringExtra("TIPE_MOTOR");
        setDataFromIntentExtra();

        img_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(TipeMotorUbahActivity.this);
                dialog.setMessage("Delete this Type?");
                dialog.setPositiveButton("Yes" ,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        deleteData(tipeId);
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editMode();

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mtipeTipe, InputMethodManager.SHOW_IMPLICIT);

                img_edit.setVisibility(View.GONE);
                img_save.setVisibility(View.VISIBLE);
                img_del.setVisibility(View.GONE);
            }
        });

        img_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tipeId == 0) {
                    if (TextUtils.isEmpty(mtipeTipe.getText().toString()) ||
                            TextUtils.isEmpty(mtipeMerk.getText().toString()) ){
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(TipeMotorUbahActivity.this);
                        alertDialog.setMessage("Please complete the field!");
                        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialog.show();
                    }
                    else {
//                      postData();
                        img_edit.setVisibility(View.VISIBLE);
                        img_save.setVisibility(View.GONE);
                        img_del.setVisibility(View.VISIBLE);
                        readMode();
                    }
                } else {
                    updateData(tipeId);
                    img_edit.setVisibility(View.VISIBLE);
                    img_save.setVisibility(View.GONE);
                    img_del.setVisibility(View.VISIBLE);

                    readMode();
                }
            }
        });

    }

    private void setDataFromIntentExtra() {
        readMode();
        getSupportActionBar().setTitle("Edit Tipe Motor");

        mtipeId.setText(Integer.toString(tipeId));
        mtipeTipe.setText(tipeTipe);
        mtipeMerk.setText(tipeMerk);
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

    void readMode(){
        mtipeId.setFocusableInTouchMode(false);
        mtipeTipe.setFocusableInTouchMode(false);
        mtipeMerk.setFocusableInTouchMode(false);

        mtipeId.setFocusable(false);
        mtipeTipe.setFocusable(false);
        mtipeMerk.setFocusable(false);
    }

    private void editMode(){
        mtipeTipe.setFocusableInTouchMode(true);
        mtipeMerk.setFocusableInTouchMode(true);
    }

    private void updateData(final int tipeId) {

        final ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Updating...");
        progress.show();

        readMode();

        String TIPE = mtipeTipe.getText().toString().trim();
        String MERK = mtipeMerk.getText().toString().trim();

        if(TIPE.isEmpty() ||MERK.isEmpty()){
            progress.dismiss();
            Toast.makeText(this,"Field Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
        }else {
            apiInterface = RetrofitClient.getApiClient().create(ApiInterface.class);
            Call<TipeMotorDAO> call = apiInterface.editDataMotor(tipeId, TIPE, MERK);

            call.enqueue(new Callback<TipeMotorDAO>() {
                @Override
                public void onResponse(Call<TipeMotorDAO> call, Response<TipeMotorDAO> response) {
                    progress.dismiss();
                    Toast.makeText(TipeMotorUbahActivity.this, "Bike Type Saved!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<TipeMotorDAO> call, Throwable t) {
                    t.printStackTrace();
                    progress.dismiss();
                    Toast.makeText(TipeMotorUbahActivity.this, "Bike Type Saved!", Toast.LENGTH_SHORT).show();
                }
            });
            startActivity(new Intent(TipeMotorUbahActivity.this, TipeMotorTampilSemuaActivity.class));
        }
    }

    private void deleteData(int tipeId) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Deleting...");
        progressDialog.show();
        readMode();
        apiInterface = RetrofitClient.getApiClient().create(ApiInterface.class);

        Call<TipeMotorDAO> call = apiInterface.deleteDataMotor(tipeId);
        call.enqueue(new Callback<TipeMotorDAO>() {
            @Override
            public void onResponse(Call<TipeMotorDAO> call, Response<TipeMotorDAO> response) {
                progressDialog.dismiss();
                Log.i(TipeMotorUbahActivity.class.getSimpleName(), response.toString());
                Toast.makeText(TipeMotorUbahActivity.this, "Success Deleting", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<TipeMotorDAO> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(TipeMotorUbahActivity.this, "Success Deleting", Toast.LENGTH_SHORT).show();
            }
        });
        startActivity(new Intent(TipeMotorUbahActivity.this, TipeMotorTampilSemuaActivity.class));
    }
}
