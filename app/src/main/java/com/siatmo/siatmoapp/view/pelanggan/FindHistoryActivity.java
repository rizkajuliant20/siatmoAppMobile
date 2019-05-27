package com.siatmo.siatmoapp.view.pelanggan;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.siatmo.siatmoapp.R;
import com.siatmo.siatmoapp.api.ApiInterface;
import com.siatmo.siatmoapp.api.RetrofitClient;
import com.siatmo.siatmoapp.modul.CustomerBikeDAO;
import com.siatmo.siatmoapp.modul.CustomerDAO;
import com.siatmo.siatmoapp.modul.DetailPemesananSpaDAO;
import com.siatmo.siatmoapp.modul.DetailPenjualanJasaDAO;
import com.siatmo.siatmoapp.modul.DetailPenjualanSparepart;
import com.siatmo.siatmoapp.modul.JSONResponse;
import com.siatmo.siatmoapp.modul.JasaServiceDAO;
import com.siatmo.siatmoapp.modul.PenjualanDAO;
import com.siatmo.siatmoapp.modul.SparepartDAO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindHistoryActivity extends AppCompatActivity {

    private Button Find;
    private EditText LicenseNumber, PhoneNumber;
    private TextView IdCustomer, NamaCustomer;
    private ApiInterface apiInterface;
    private RelativeLayout lay;
    private LinearLayout transLay, OnProcessLay, DoneProcessLay, SpaLay;
    int idCustomerPhones=0;
    int idCustomerPlats=0;
    String namaCustomer;
    private List<CustomerBikeDAO> customerBike=new ArrayList<>();
    private List<CustomerDAO> customerPhone= new ArrayList<>();
    private List<PenjualanDAO> transaksi= new ArrayList<>();
    private List<DetailPenjualanJasaDAO> detJasa= new ArrayList<>();
    private List<DetailPenjualanSparepart> detSparepart= new ArrayList<>();
    private List<JasaServiceDAO> listService = new ArrayList<>();
    private List<SparepartDAO> listSparepart = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_history);

        apiInterface = RetrofitClient.getApiClient().create(ApiInterface.class);

        Find = findViewById(R.id.ButtonFind);
        LicenseNumber = findViewById(R.id.nomorPlatFind);
        PhoneNumber = findViewById(R.id.nomorTelponFind);
        lay = findViewById(R.id.layz2);
        IdCustomer= findViewById(R.id.IdUserFind);
        NamaCustomer= findViewById(R.id.NamaCustomerFind);
        transLay= findViewById(R.id.layoutTransaksiPelanggan);
        SpaLay= findViewById(R.id.layoutTransaksiSpa);
        OnProcessLay= findViewById(R.id.layoutOnProcessJasa);
        DoneProcessLay= findViewById(R.id.layoutTransaksiDone);

        Find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<List<CustomerBikeDAO>> findBike = apiInterface.getCustomerBike();
                final String Plats = LicenseNumber.getText().toString();
                final String Telepons = PhoneNumber.getText().toString();
//                Find.setEnabled(false);
                findBike.enqueue(new Callback<List<CustomerBikeDAO>>() {
                    @Override
                    public void onResponse(Call<List<CustomerBikeDAO>> call, Response<List<CustomerBikeDAO>> response) {
                        customerBike = response.body();
                        for (int i = 0; i < customerBike.size(); i++) {
                            if (customerBike.get(i).getNO_PLAT().equals(Plats)) {
                                idCustomerPlats = customerBike.get(i).getID_PELANGGAN();
                            }
                        }

                        Call<List<CustomerDAO>> findPhone= apiInterface.getCustomer();
                        findPhone.enqueue(new Callback<List<CustomerDAO>>() {
                            @Override
                            public void onResponse(Call<List<CustomerDAO>> call, Response<List<CustomerDAO>> response) {
                                customerPhone=response.body();
                                for (int i=0;i<customerPhone.size();i++){
                                    if (customerPhone.get(i).getTELEPON_PELANGGAN().equals(Telepons)){
                                        idCustomerPhones = customerPhone.get(i).getID_PELANGGAN();
                                        namaCustomer = customerPhone.get(i).getNAMA_PELANGGAN();
                                    }
                                }
                                if (idCustomerPhones==idCustomerPlats){

                                    lay.setVisibility(View.VISIBLE);
                                    IdCustomer.setText(String.valueOf(idCustomerPhones));
                                    NamaCustomer.setText(namaCustomer);
                                    final int idd=Integer.parseInt(IdCustomer.getText().toString());
                                    Call<List<PenjualanDAO>> getIdTransaksi=apiInterface.getTransaksiPenjualan();
                                    getIdTransaksi.enqueue(new Callback<List<PenjualanDAO>>() {
                                        @Override
                                        public void onResponse(Call<List<PenjualanDAO>> call, Response<List<PenjualanDAO>> response) {
                                            transaksi=response.body();
                                            for (int i=0;i<transaksi.size();i++){
                                                if(transaksi.get(i).getID_PELANGGAN()==idd){
                                                    final String idTransaksi=transaksi.get(i).getID_TRANSAKSI();
                                                    Toast.makeText(getApplicationContext(), idTransaksi, Toast.LENGTH_SHORT).show();
                                                    final String tgl= transaksi.get(i).getTGL_TRANSAKSI();

                                                    //detailSparepart
                                                    Call<List<DetailPenjualanSparepart>> callDetSpa= apiInterface.getDetailSparepart();
                                                    callDetSpa.enqueue(new Callback<List<DetailPenjualanSparepart>>() {
                                                        @Override
                                                        public void onResponse(Call<List<DetailPenjualanSparepart>> call, Response<List<DetailPenjualanSparepart>> response) {
                                                            detSparepart = response.body();
                                                            for (int i = 0; i < detSparepart.size();i++){
                                                                Toast.makeText(getApplicationContext(), String.valueOf(idd), Toast.LENGTH_SHORT).show();
                                                                if (detSparepart.get(i).getID_TRANSAKSI().equals(idTransaksi)) {
                                                                    LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                                                    final View addView = layoutInflater.inflate(R.layout.layout_row_detsparepart, null);
                                                                    final TextView textOut = addView.findViewById(R.id.textoutKetSpa);
                                                                    final TextView textOut2 = addView.findViewById(R.id.textoutJumlah);
                                                                    final TextView textOut3 = addView.findViewById(R.id.textoutHargaSpa);
                                                                    final String idSPAREPART= detSparepart.get(i).getID_SPAREPARTS();
                                                                    Call<JSONResponse> calllSpa=apiInterface.getSpareparts();
                                                                    calllSpa.enqueue(new Callback<JSONResponse>() {
                                                                        @Override
                                                                        public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                                                                            listSparepart=response.body().getData();
                                                                            for (int i=0;i<listSparepart.size();i++){
                                                                                if (listSparepart.get(i).getID_SPAREPARTS().equals(idSPAREPART)){
                                                                                    textOut.setText(tgl+" "+listSparepart.get(i).getNAMA_SPAREPART());
                                                                                }
                                                                            }
                                                                        }

                                                                        @Override
                                                                        public void onFailure(Call<JSONResponse> call, Throwable t) {

                                                                        }
                                                                    });
                                                                    textOut2.setText(String.valueOf(detSparepart.get(i).getJUMLAH_SPAREPART()));
                                                                    textOut3.setText(String.valueOf(detSparepart.get(i).getSUBTOTAL_SPAREPART()));
                                                                    SpaLay.addView(addView);
                                                                }
                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(Call<List<DetailPenjualanSparepart>> call, Throwable t) {

                                                        }
                                                    });
                                                    //detailJasa
                                                    Call<List<DetailPenjualanJasaDAO>> callDetJasa= apiInterface.getDetailJasa();
                                                    callDetJasa.enqueue(new Callback<List<DetailPenjualanJasaDAO>>() {
                                                        @Override
                                                        public void onResponse(Call<List<DetailPenjualanJasaDAO>> call, Response<List<DetailPenjualanJasaDAO>> response) {
                                                            detJasa=response.body();
                                                            for (int i = 0; i < detJasa.size();i++){
                                                                if (detJasa.get(i).getID_TRANSAKSI().equals(idTransaksi)) {
                                                                    if (detJasa.get(i).getSTATUS_JASA().equals("On Process")) {
                                                                        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                                                        final View addView = layoutInflater.inflate(R.layout.layout_row_detsparepart, null);
                                                                        final TextView textOut = addView.findViewById(R.id.textoutKetSpa);
                                                                        final TextView textOut2 = addView.findViewById(R.id.textoutJumlah);
                                                                        final TextView textOut3 = addView.findViewById(R.id.textoutHargaSpa);
                                                                        final int IDJasa=detJasa.get(i).getID_JASA();
                                                                        Call<List<JasaServiceDAO>> callll=apiInterface.getService();
                                                                        callll.enqueue(new Callback<List<JasaServiceDAO>>() {
                                                                            @Override
                                                                            public void onResponse(Call<List<JasaServiceDAO>> call, Response<List<JasaServiceDAO>> response) {
                                                                                listService=response.body();
                                                                                for (int j=0;j<listService.size();j++){
                                                                                    if(listService.get(j).getID_JASA()==IDJasa){
                                                                                        textOut.setText(tgl+" "+listService.get(j).getNAMA_JASA());
                                                                                    }
                                                                                }
                                                                            }

                                                                            @Override
                                                                            public void onFailure(Call<List<JasaServiceDAO>> call, Throwable t) {

                                                                            }
                                                                        });
                                                                        textOut2.setText(String.valueOf(detJasa.get(i).getSUBTOTAL_JASA()));
                                                                        textOut3.setText(detJasa.get(i).getSTATUS_JASA());
                                                                        OnProcessLay.addView(addView);
                                                                    }else{
                                                                        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                                                        final View addView = layoutInflater.inflate(R.layout.layout_row_detsparepart, null);
                                                                        final TextView textOut = addView.findViewById(R.id.textoutKetSpa);
                                                                        final TextView textOut2 = addView.findViewById(R.id.textoutJumlah);
                                                                        final TextView textOut3 = addView.findViewById(R.id.textoutHargaSpa);
                                                                        final int IDJasa=detJasa.get(i).getID_JASA();
                                                                        Call<List<JasaServiceDAO>> callll=apiInterface.getService();
                                                                        callll.enqueue(new Callback<List<JasaServiceDAO>>() {
                                                                            @Override
                                                                            public void onResponse(Call<List<JasaServiceDAO>> call, Response<List<JasaServiceDAO>> response) {
                                                                                listService=response.body();
                                                                                for (int j=0;j<listService.size();j++){
                                                                                    if(listService.get(j).getID_JASA()==IDJasa){
                                                                                        textOut.setText(tgl+" "+listService.get(j).getNAMA_JASA());
                                                                                    }
                                                                                }
                                                                            }

                                                                            @Override
                                                                            public void onFailure(Call<List<JasaServiceDAO>> call, Throwable t) {

                                                                            }
                                                                        });
                                                                        textOut2.setText(String.valueOf(detJasa.get(i).getSUBTOTAL_JASA()));
                                                                        textOut3.setText(detJasa.get(i).getSTATUS_JASA());
                                                                        DoneProcessLay.addView(addView);
                                                                    }
                                                                }
                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(Call<List<DetailPenjualanJasaDAO>> call, Throwable t) {

                                                        }
                                                    });
                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<List<PenjualanDAO>> call, Throwable t) {

                                        }
                                    });
                                }else {
                                    Toast.makeText(getApplicationContext(),"Histori tidak ditemukan!",Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<List<CustomerDAO>> call, Throwable t) {

                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<List<CustomerBikeDAO>> call, Throwable t) {

                    }
                });
            }

        });
    }
}
