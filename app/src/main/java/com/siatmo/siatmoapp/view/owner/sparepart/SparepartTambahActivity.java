package com.siatmo.siatmoapp.view.owner.sparepart;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.siatmo.siatmoapp.R;
import com.siatmo.siatmoapp.api.ApiInterface;
import com.siatmo.siatmoapp.api.RetrofitClient;
import com.siatmo.siatmoapp.modul.JSONResponse;
import com.siatmo.siatmoapp.modul.JSONResponseSingle;
import com.siatmo.siatmoapp.modul.PosisiDAO;
import com.siatmo.siatmoapp.modul.SparepartDAO;
import com.siatmo.siatmoapp.modul.SparepartMotorDAO;
import com.siatmo.siatmoapp.modul.TipeMotorDAO;
import com.siatmo.siatmoapp.view.customerService.kendaraanPelanggan.KendaraanPelangganTambahActivity;
import com.siatmo.siatmoapp.view.owner.MainActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;

public class SparepartTambahActivity extends AppCompatActivity {
    private EditText spaID, spaNama, spaHargaBeli, spaHargaJual, spaStokMin, spaStokBar, spaTipe;
    private ImageView spaGambar;
    private Spinner spaPosisi;
    private String posisi, gambar;
    private Button uploadGambar;
    private ApiInterface apiInterface;
    private List<SparepartDAO> spaList;
    private FloatingActionButton simpan;

    public Bitmap ImageBitmap;
    int Image_Request_Code = 1;
    Uri FilePathUri;

    List<String> namaSpinnerPos= new ArrayList<>();
    List<PosisiDAO> spinnerArrayPos = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sparepart_tambah);

        spaID=findViewById(R.id.inputIDTambahSpa);
        spaNama=findViewById(R.id.inputNamaTambahSpa);
        spaHargaBeli=findViewById(R.id.inputHargaBeliTambahSpa);
        spaHargaJual=findViewById(R.id.inputHargaJualTambahSpa);
        spaStokBar=findViewById(R.id.inputStokAwalTambahSpa);
        spaStokMin=findViewById(R.id.inputStokMinimalTambahSpa);
        spaTipe=findViewById(R.id.inputTipeBarangTambahSpa);
        spaGambar=findViewById(R.id.gambarTambahSpa);
        apiInterface=RetrofitClient.getApiClient().create(ApiInterface.class);

        Call<List<PosisiDAO>> callpos=apiInterface.getPosisi();
        callpos.enqueue(new Callback<List<PosisiDAO>>() {
            @Override
            public void onResponse(Call<List<PosisiDAO>> call, Response<List<PosisiDAO>> response) {
                spinnerArrayPos=response.body();
                for (int i = 0; i < spinnerArrayPos.size(); i++) {
                    namaSpinnerPos.add(spinnerArrayPos.get(i).getKODE_PENEMPATAN());
                }

                ArrayAdapter<String> adapterPos = new ArrayAdapter<>(SparepartTambahActivity.this, R.layout.spinner_sparepart_layout, R.id.txtSparepart, namaSpinnerPos);
                spaPosisi.setAdapter(adapterPos);

            }

            @Override
            public void onFailure(Call<List<PosisiDAO>> call, Throwable t) {

            }
        });
        spaPosisi=findViewById(R.id.spinnerPosisiTambahSpa);
        spaPosisi.getSelectedItem();



        uploadGambar=findViewById(R.id.buttonUploadGambarTambahSpa);
        uploadGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFile();
            }
        });


        simpan=findViewById(R.id.fabSaveSpa);
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpanSparepartMulti();
            }
        });



    }
//=======================UNTUK GAMBAR==================================================================
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
                spaGambar.setImageBitmap(bitmap);
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

//===================================================================================================================
    private void SimpanSparepartMulti() {
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Saving...");
        progress.show();

        final String IDSPAREPARTs = this.spaID.getText().toString().trim();
        final String NAMASPAREPARTs = this.spaNama.getText().toString().trim();
        final String TIPESPAREPARTs = this.spaTipe.getText().toString().trim();
        final String POSISISPAREPARTs = spaPosisi.getSelectedItem().toString();
        final Double HARGABELISPAREPARTs = Double.parseDouble(this.spaHargaBeli.getText().toString().trim());
        final Double HARGAJUALSPAREPARTs = Double.parseDouble(this.spaHargaJual.getText().toString().trim());
        final int STOKMINSPAREPARTs = Integer.parseInt(this.spaStokMin.getText().toString().trim());
        final int STOKTERSEDIASPAREPARTs = Integer.parseInt(this.spaStokBar.getText().toString().trim());

        if(IDSPAREPARTs.isEmpty() ||NAMASPAREPARTs.isEmpty()){
            Toast.makeText(this,"ID dan Nama Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
        }
        else if(STOKTERSEDIASPAREPARTs<STOKMINSPAREPARTs){
            Toast.makeText(SparepartTambahActivity.this,"Stok tidak boleh lebih besar dari Stok Optimal", Toast.LENGTH_LONG);
        }
        else if(STOKTERSEDIASPAREPARTs <0 || STOKMINSPAREPARTs < 0 || HARGABELISPAREPARTs < 0 || HARGAJUALSPAREPARTs < 0){
            Toast.makeText(SparepartTambahActivity.this,"Tidak Boleh ada Minus", Toast.LENGTH_LONG);
        }else{
            apiInterface = RetrofitClient.getApiClient().create(ApiInterface.class);
            if(ImageBitmap!=null)
            {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageBitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
                byte[] data =baos.toByteArray();

                RequestBody ID_SPAREPARTS=RequestBody.create(MediaType.parse("multipart/form-data"), IDSPAREPARTs);
                RequestBody KODE_PENEMPATAN=RequestBody.create(MediaType.parse("multipart/form-data"),POSISISPAREPARTs);
                RequestBody NAMA_SPAREPART=RequestBody.create(MediaType.parse("multipart/form-data"),NAMASPAREPARTs);
                RequestBody HARGA_BELI=RequestBody.create(MediaType.parse("multipart/form-data"),String.valueOf(HARGABELISPAREPARTs));
                RequestBody HARGA_JUAL=RequestBody.create(MediaType.parse("multipart/form-data"),String.valueOf(HARGAJUALSPAREPARTs));
                RequestBody STOK_MINIMAL=RequestBody.create(MediaType.parse("multipart/form-data"),String.valueOf(STOKMINSPAREPARTs));
                RequestBody STOK_BARANG=RequestBody.create(MediaType.parse("multipart/form-data"),String.valueOf(STOKTERSEDIASPAREPARTs));
                RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), data);
                RequestBody TIPE=RequestBody.create(MediaType.parse("multipart/form-data"),TIPESPAREPARTs);

                MultipartBody.Part body = MultipartBody.Part.createFormData("GAMBAR", "image.jpg", requestFile);

                Call<JSONResponseSingle> call=apiInterface.createDataSpaMulti(ID_SPAREPARTS, KODE_PENEMPATAN, NAMA_SPAREPART, HARGA_BELI, HARGA_JUAL,
                        STOK_MINIMAL, STOK_BARANG, body, TIPE);
                call.enqueue(new Callback<JSONResponseSingle>() {
                    @Override
                    public void onResponse(Call<JSONResponseSingle> call, Response<JSONResponseSingle> response) {
                        if (response.isSuccessful()) {
                            JSONResponseSingle responseBody = response.body();
                            Log.d("SUKSES",responseBody.toString());
                            Toast.makeText(SparepartTambahActivity.this, "Sparepart Saved!", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d( "onResponse: ",response.message());
                            Toast.makeText(SparepartTambahActivity.this, "Failed to Save!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<JSONResponseSingle> call, Throwable t) {
                        Log.d("onFailure: ",t.toString());
                    }
                });
            }
            startActivity(new Intent(SparepartTambahActivity.this, SparepartTampilSemuaActivity.class));
        }
    }

}
