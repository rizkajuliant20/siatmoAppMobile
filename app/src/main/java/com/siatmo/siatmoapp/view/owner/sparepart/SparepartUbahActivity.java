package com.siatmo.siatmoapp.view.owner.sparepart;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.siatmo.siatmoapp.R;
import com.siatmo.siatmoapp.api.ApiInterface;
import com.siatmo.siatmoapp.api.RetrofitClient;
import com.siatmo.siatmoapp.modul.JSONResponseSingle;
import com.siatmo.siatmoapp.modul.PosisiDAO;
import com.siatmo.siatmoapp.modul.SparepartDAO;
import com.siatmo.siatmoapp.modul.SparepartMotorDAO;
import com.siatmo.siatmoapp.modul.TipeMotorDAO;
import com.siatmo.siatmoapp.view.owner.MainActivity;
import com.siatmo.siatmoapp.view.owner.supplier.SupplierUbahActivity;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SparepartUbahActivity extends AppCompatActivity {

    private EditText spaID, spaNama, spaHargaBeli, spaHargaJual, spaStokMin, spaStokBar, spaTipe;
    private ImageView spaGambar;
    private String mspaID, mspaNama,  mspaTipe,  mkodePosisi;
    private Spinner spaPosisi;
    private String posisi, gambar;
    private int mspaStokMin, mspaStokBar;
    private Double  mspaHargaBeli, mspaHargaJual;
    private Button trigger;
    private Button uploadGambar;
    public Bitmap ImageBitmap;
    List<PosisiDAO> spinnerArrayPos = new ArrayList<>();
    int Image_Request_Code = 1;
    Uri FilePathUri;
    List<String> namaSpinnerPos= new ArrayList<>();

    private ApiInterface apiInterface;
    private List<SparepartDAO> spaList;
    private ImageView addKecocokan;
    private Spinner kecocokan;
    LinearLayout container;
    int idTipeMotor;

    List<SparepartMotorDAO> spaMotorList= new ArrayList<>();
    List<TipeMotorDAO> spinnerArray =  new ArrayList<>();
    List<String> IdTipeMotor = new ArrayList<>();

    ImageView img_save, img_del, img_edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sparepart_ubah);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        spaID = findViewById(R.id.inputIDUbahSpa);
        spaNama = findViewById(R.id.inputNamaUbahSpa);
        spaHargaBeli = findViewById(R.id.inputHargaBeliUbahSpa);
        spaHargaJual = findViewById(R.id.inputHargaJualUbahSpa);
        spaStokBar = findViewById(R.id.inputStokAwalUbahSpa);
        spaStokMin = findViewById(R.id.inputStokMinimalUbahSpa);
        spaTipe = findViewById(R.id.inputTipeBarangUbahSpa);
        spaGambar = findViewById(R.id.gambarUbahSpa);
        kecocokan=findViewById(R.id.spinnerKecocokanTipeMotordanSpa);
        container = findViewById(R.id.container);

        Intent intent=getIntent();
        mspaID = intent.getStringExtra("ID_SPAREPARTS");
        mkodePosisi = intent.getStringExtra("KODE_PENEMPATAN");
        mspaNama = intent.getStringExtra("NAMA_SPAREPART");
        mspaHargaBeli = intent.getDoubleExtra("HARGA_BELI",0);
        mspaHargaJual = intent.getDoubleExtra("HARGA_JUAL",0);
        mspaStokMin = intent.getIntExtra("STOK_MINIMAL",0);
        mspaStokBar = intent.getIntExtra("STOK_BARANG",0);
        gambar = intent.getStringExtra("GAMBAR");
        mspaTipe=intent.getStringExtra("TIPE");
        setDataFromIntentExtra();

        spaPosisi=findViewById(R.id.spinnerPosisiUbahSpa);
        spaPosisi.getSelectedItem();

        img_save=findViewById(R.id.save_imgUbah);
        img_del=findViewById(R.id.del_imgUbah);
        img_edit=findViewById(R.id.edit_imgUbah);

        uploadGambar=findViewById(R.id.buttonUploadGambarUbahSpa);

        apiInterface=RetrofitClient.getApiClient().create(ApiInterface.class);
        Call<List<TipeMotorDAO>> callTipeMotor = apiInterface.getMotors();
        callTipeMotor.enqueue(new Callback<List<TipeMotorDAO>>() {
            @Override
            public void onResponse(Call<List<TipeMotorDAO>> callTipeMotor, Response<List<TipeMotorDAO>> response) {
                spinnerArray=response.body();
                for(int i=0; i<spinnerArray.size();i++){
                    IdTipeMotor.add(String.format("%03d",spinnerArray.get(i).getID_MOTOR())+"-"+spinnerArray.get(i).getTIPE_MOTOR());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(SparepartUbahActivity.this, R.layout.spinner_tipemotor_layout,R.id.txtTipe, IdTipeMotor);
                kecocokan.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<TipeMotorDAO>> call, Throwable t) {

            }
        });

        Call<List<PosisiDAO>> callpos=apiInterface.getPosisi();
        callpos.enqueue(new Callback<List<PosisiDAO>>() {
            @Override
            public void onResponse(Call<List<PosisiDAO>> call, Response<List<PosisiDAO>> response) {
                spinnerArrayPos=response.body();
                for (int i = 0; i < spinnerArrayPos.size(); i++) {
                    namaSpinnerPos.add(spinnerArrayPos.get(i).getKODE_PENEMPATAN());
                }

                ArrayAdapter<String> adapterPos = new ArrayAdapter<>(SparepartUbahActivity.this, R.layout.spinner_sparepart_layout, R.id.txtSparepart, namaSpinnerPos);
                spaPosisi.setAdapter(adapterPos);

                int spinnerPosition = adapterPos.getPosition(mkodePosisi);
                spaPosisi.setSelection(spinnerPosition);
            }

            @Override
            public void onFailure(Call<List<PosisiDAO>> call, Throwable t) {

            }
        });


        img_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mspaID.isEmpty()) {
                    if (TextUtils.isEmpty(spaNama.getText().toString()) ||
                            TextUtils.isEmpty(spaHargaBeli.getText().toString()) ||
                            TextUtils.isEmpty(spaHargaJual.getText().toString()) ||
                            TextUtils.isEmpty(spaStokBar.getText().toString())||
                            TextUtils.isEmpty(spaStokMin.getText().toString())||
                            TextUtils.isEmpty(spaTipe.getText().toString())){
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SparepartUbahActivity.this);
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
                        img_edit.setVisibility(View.VISIBLE);
                        img_save.setVisibility(View.GONE);
                        img_del.setVisibility(View.VISIBLE);
                        readMode();
                    }
                } else {
                    UpdateSparepart();
                    img_edit.setVisibility(View.VISIBLE);
                    img_save.setVisibility(View.GONE);
                    img_del.setVisibility(View.VISIBLE);

                    readMode();
                }
            }
        });

        img_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(SparepartUbahActivity.this);
                dialog.setMessage("Delete this Supplier?");
                dialog.setPositiveButton("Yes" ,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        deleteData(mspaID);
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
                imm.showSoftInput(spaNama, InputMethodManager.SHOW_IMPLICIT);

                uploadGambar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chooseFile();
                    }
                });
                img_edit.setVisibility(View.GONE);
                img_save.setVisibility(View.VISIBLE);
                img_del.setVisibility(View.GONE);
            }
        });


        apiInterface=RetrofitClient.getApiClient().create(ApiInterface.class);
        Call<List<SparepartMotorDAO>> call=apiInterface.getSpaMotor();
        call.enqueue(new Callback<List<SparepartMotorDAO>>() {
            @Override
            public void onResponse(Call<List<SparepartMotorDAO>> call, Response<List<SparepartMotorDAO>> response) {
                spaMotorList=response.body();
                for(int i=0;i<spaMotorList.size();i++){
                    if(spaMotorList.get(i).getID_SPAREPARTS().equals(mspaID)){
                        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        final View custom = layoutInflater.inflate(R.layout.rowtwo, null);
                        final TextView textOut = custom.findViewById(R.id.textout);
                        Toast.makeText(getApplicationContext(),String.valueOf(spaMotorList.get(i).getID_MOTOR()),Toast.LENGTH_SHORT).show();
                        textOut.setText(String.valueOf(spaMotorList.get(i).getID_MOTOR()));
                        container.addView(custom);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<SparepartMotorDAO>> call, Throwable t) {

            }
        });

        addKecocokan=findViewById(R.id.btnAddKecocokan);
        addKecocokan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.row, null);
                final TextView textOut = addView.findViewById(R.id.textout);
                String[] sprte=kecocokan.getSelectedItem().toString().split("-");
                idTipeMotor=Integer.parseInt(sprte[0]);
                apiInterface=RetrofitClient.getApiClient().create(ApiInterface.class);
                Call<SparepartMotorDAO> call=apiInterface.createDataSpaMotor(mspaID,idTipeMotor);
                call.enqueue(new Callback<SparepartMotorDAO>() {
                    @Override
                    public void onResponse(Call<SparepartMotorDAO> call, Response<SparepartMotorDAO> response) {
                        Toast.makeText(getApplicationContext(),"Success", Toast.LENGTH_LONG).show();
                        textOut.setText("MTR-"+kecocokan.getSelectedItem().toString());
                    }

                    @Override
                    public void onFailure(Call<SparepartMotorDAO> call, Throwable t) {
                        Toast.makeText(SparepartUbahActivity.this,"Success", Toast.LENGTH_LONG).show();
                        textOut.setText("MTR-"+kecocokan.getSelectedItem().toString());
                    }
                });
                Button buttonRemove = addView.findViewById(R.id.remove);
                buttonRemove.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        String[] part=textOut.getText().toString().split("-");
                        Toast.makeText(getApplicationContext(),part[1],Toast.LENGTH_SHORT).show();
                        Call<SparepartMotorDAO> callaja= apiInterface.hapusSpaMotor(mspaID,Integer.parseInt(part[1]));
                        callaja.enqueue(new Callback<SparepartMotorDAO>() {
                            @Override
                            public void onResponse(Call<SparepartMotorDAO> call, Response<SparepartMotorDAO> response) {
                                ((LinearLayout)addView.getParent()).removeView(addView);
                                Toast.makeText(getApplicationContext(),"Success Delete",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<SparepartMotorDAO> call, Throwable t) {
                                ((LinearLayout)addView.getParent()).removeView(addView);
                                Toast.makeText(getApplicationContext(),"Success Delete",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }});
                container.addView(addView);
            }
        });

    }

    private void chooseFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {
            FilePathUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), FilePathUri);
                ImageBitmap=bitmap;
                spaGambar.setImageBitmap(ImageBitmap);
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setDataFromIntentExtra() {
        readMode();
        getSupportActionBar().setTitle("Edit Sparepart");

        if(gambar==null)
        {
            spaGambar.setImageResource(R.drawable.mirror);
        }else{
            Picasso.get().load("http://p3l.pillowfaceid.com"+gambar).fit().into(spaGambar);
//            Picasso.get().load("http://192.168.19.140/8926/public"+gambar).fit().into(spaGambar);
        }
        spaID.setText(mspaID);
        spaNama.setText(mspaNama);
        spaHargaJual.setText(Double.toString(mspaHargaJual));
        spaHargaBeli.setText(Double.toString(mspaHargaBeli));
        spaStokMin.setText(Integer.toString(mspaStokMin));
        spaStokBar.setText(Integer.toString(mspaStokBar));
        spaTipe.setText(mspaTipe);
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
//=======================================================================================================================
    private void UpdateSparepart() {
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Loading ...");
        progress.show();

        final String IDSPAREPART = this.spaID.getText().toString().trim();
        final String NAMASPAREPART = this.spaNama.getText().toString().trim();
        final String TIPESPAREPART = this.spaTipe.getText().toString().trim();
        final String POSISISPAREPART = spaPosisi.getSelectedItem().toString();
        final Double HARGABELISPAREPART = Double.parseDouble(this.spaHargaBeli.getText().toString().trim());
        final Double HARGAJUALSPAREPART = Double.parseDouble(this.spaHargaJual.getText().toString().trim());
        final int STOKMINSPAREPART = Integer.parseInt(this.spaStokMin.getText().toString().trim());
        final int STOKTERSEDIASPAREPART = Integer.parseInt(this.spaStokBar.getText().toString().trim());

        if(IDSPAREPART.isEmpty() || NAMASPAREPART.isEmpty()){
            Toast.makeText(this,"ID dan Nama Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
        }
        else if(STOKTERSEDIASPAREPART<STOKMINSPAREPART){
            Toast.makeText(SparepartUbahActivity.this,"Stok tidak boleh lebih besar dari Stok Optimal", Toast.LENGTH_LONG);
        }
        else if(STOKTERSEDIASPAREPART <0 || STOKMINSPAREPART < 0 || HARGABELISPAREPART < 0 || HARGAJUALSPAREPART < 0){
            Toast.makeText(SparepartUbahActivity.this,"Tidak Boleh ada Minus", Toast.LENGTH_LONG);
        }else{
            apiInterface = RetrofitClient.getApiClient().create(ApiInterface.class);

            if(ImageBitmap!=null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                final byte[] data = baos.toByteArray();

                Call<JSONResponseSingle> call = apiInterface.editDataSpa(IDSPAREPART,
                        POSISISPAREPART, NAMASPAREPART, HARGABELISPAREPART, HARGAJUALSPAREPART,
                        STOKMINSPAREPART, STOKTERSEDIASPAREPART,"",TIPESPAREPART);
                Toast.makeText(SparepartUbahActivity.this, POSISISPAREPART, Toast.LENGTH_LONG);

                call.enqueue(new Callback<JSONResponseSingle>() {
                    @Override
                    public void onResponse(Call<JSONResponseSingle> call, Response<JSONResponseSingle> response) {
                        progress.dismiss();
                        if (response.code() == 200)
                            Toast.makeText(SparepartUbahActivity.this, "Sparepart Updated!", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(SparepartUbahActivity.this, "Sparepart Updated!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<JSONResponseSingle> call, Throwable t) {
                        t.printStackTrace();
                        progress.dismiss();
                        Toast.makeText(SparepartUbahActivity.this, "Sparepart Updated!", Toast.LENGTH_SHORT).show();
                    }
                });
                RequestBody ID_SPAREPARTS=RequestBody.create(MediaType.parse("multipart/form-data"), IDSPAREPART);
                RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), data);
                MultipartBody.Part body = MultipartBody.Part.createFormData("GAMBAR", "image.jpg", requestFile);
                Call<JSONResponseSingle> callImage=apiInterface.uploadGambar(ID_SPAREPARTS, body);
                callImage.enqueue(new Callback<JSONResponseSingle>() {
                    @Override
                    public void onResponse(Call<JSONResponseSingle> call, Response<JSONResponseSingle> response) {}
                    @Override
                    public void onFailure(Call<JSONResponseSingle> call, Throwable t) { }
                });
            }else{
                Call<JSONResponseSingle> call = apiInterface.editDataSpa(IDSPAREPART,
                        POSISISPAREPART, NAMASPAREPART, HARGABELISPAREPART, HARGAJUALSPAREPART,
                        STOKMINSPAREPART, STOKTERSEDIASPAREPART,"",TIPESPAREPART);
                Toast.makeText(SparepartUbahActivity.this, POSISISPAREPART, Toast.LENGTH_LONG);

                call.enqueue(new Callback<JSONResponseSingle>() {
                    @Override
                    public void onResponse(Call<JSONResponseSingle> call, Response<JSONResponseSingle> response) {
                        progress.dismiss();
                        if (response.code() == 200)
                            Toast.makeText(SparepartUbahActivity.this, "Sparepart Updated!", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(SparepartUbahActivity.this, "Sparepart Updated!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<JSONResponseSingle> call, Throwable t) {
                        t.printStackTrace();
                        progress.dismiss();
                        Toast.makeText(SparepartUbahActivity.this, "Sparepart Updated!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            startActivity(new Intent(SparepartUbahActivity.this, SparepartTampilSemuaActivity.class));
        }
    }

    private void deleteData(String mspaID) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Deleting...");
        progressDialog.show();
        readMode();
        apiInterface = RetrofitClient.getApiClient().create(ApiInterface.class);

        Call<JSONResponseSingle> call = apiInterface.deleteDataSpa(mspaID);
        call.enqueue(new Callback<JSONResponseSingle>() {
            @Override
            public void onResponse(Call<JSONResponseSingle> call, Response<JSONResponseSingle> response) {
                progressDialog.dismiss();

                Log.i(SupplierUbahActivity.class.getSimpleName(), response.toString());
                Toast.makeText(SparepartUbahActivity.this,"Success Deleting",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<JSONResponseSingle> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(SparepartUbahActivity.this,"Success Deleting",Toast.LENGTH_SHORT).show();
            }
        });
        startActivity(new Intent(SparepartUbahActivity.this,SparepartTampilSemuaActivity.class));
    }




    void readMode(){
        spaID.setFocusableInTouchMode(false);
        spaNama.setFocusableInTouchMode(false);
        spaHargaBeli.setFocusableInTouchMode(false);
        spaHargaJual.setFocusableInTouchMode(false);
        spaStokBar.setFocusableInTouchMode(false);
        spaStokMin.setFocusableInTouchMode(false);
        spaTipe.setFocusableInTouchMode(false);

        spaID.setFocusable(false);
        spaNama.setFocusable(false);
        spaHargaJual.setFocusable(false);
        spaHargaBeli.setFocusable(false);
        spaStokMin.setFocusable(false);
        spaStokBar.setFocusable(false);
        spaTipe.setFocusable(false);
    }

    private void editMode(){
        spaID.setFocusableInTouchMode(true);
        spaNama.setFocusableInTouchMode(true);
        spaHargaBeli.setFocusableInTouchMode(true);
        spaHargaJual.setFocusableInTouchMode(true);
        spaStokBar.setFocusableInTouchMode(true);
        spaStokMin.setFocusableInTouchMode(true);
        spaTipe.setFocusableInTouchMode(true);
    }


}
