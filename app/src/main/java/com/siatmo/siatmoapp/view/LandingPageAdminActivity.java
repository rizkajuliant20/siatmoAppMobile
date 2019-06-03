package com.siatmo.siatmoapp.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.siatmo.siatmoapp.R;
import com.siatmo.siatmoapp.modul.SupplierDAO;
import com.siatmo.siatmoapp.session.SessionManager;
import com.siatmo.siatmoapp.api.ApiInterface;
import com.siatmo.siatmoapp.api.RetrofitClient;
import com.siatmo.siatmoapp.session.SharedPrefManager;
import com.siatmo.siatmoapp.view.customerService.MainActivityCS;
import com.siatmo.siatmoapp.view.owner.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LandingPageAdminActivity extends AppCompatActivity {

    private EditText username, password;
    Button LoginAdmin;

    private ApiInterface apiInterface;

    SharedPrefManager sharedPrefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page_admin);

        username= findViewById(R.id.usernameAdm);
        password=findViewById(R.id.passwordAdm);

        sharedPrefManager = new SharedPrefManager(this);

        if (sharedPrefManager.getSPSudahLogin()){
            if(sharedPrefManager.getSPNama().equals("own")){
                startActivity(new Intent(LandingPageAdminActivity.this, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }else{
                startActivity(new Intent(LandingPageAdminActivity.this, MainActivityCS.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        }

        LoginAdmin=findViewById(R.id.loginBtn);
        LoginAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestLogin();
            }
        });
    }

    private void requestLogin(){
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Please Wait...");
        progress.show();
        apiInterface = RetrofitClient.getApiClient().create(ApiInterface.class);

        if(username.getText().toString().isEmpty()){
            username.setError("Please fill the Username");
            progress.dismiss();
        }else if (password.getText().toString().isEmpty()){
            password.setError("Please fill the Password");
            progress.dismiss();
        }else if(password.getText().toString().equals("own") && password.getText().toString().equals("own")){
            sharedPrefManager.saveSPString(SharedPrefManager.SP_Username, "own");
            sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);
            Toast.makeText(getApplicationContext(), "Welcome Boss Phillip", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LandingPageAdminActivity.this,MainActivity.class));
            progress.dismiss();
        }else {
            apiInterface.GetLogin(username.getText().toString(), password.getText().toString())
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                progress.dismiss();
                                try {
                                    JSONObject jsonRes = new JSONObject(response.body().string());
                                    String username = jsonRes.getJSONObject("data").getString("username");
                                    Toast.makeText(getApplicationContext(), "Login Successful "+username, Toast.LENGTH_SHORT).show();
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_Username, username);
                                    sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);
                                    Intent i =new Intent(getApplicationContext(), MainActivityCS.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(i);
                                    finish();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                progress.dismiss();
                                Toast.makeText(getApplicationContext(),"Wrong Username and Password!",Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.e("debug", "onFailure: ERROR > " + t.toString());
                            progress.dismiss();
                        }
                    });
        }
    }


}
