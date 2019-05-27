package com.siatmo.siatmoapp.view;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.siatmo.siatmoapp.R;

public class LandingPageMainActivity extends AppCompatActivity {

    RelativeLayout rellay1;
    Button Adminbt, Guestbt;

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rellay1.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page_main);

        rellay1 = findViewById(R.id.rellay1);

        handler.postDelayed(runnable, 1500);

        Adminbt=findViewById(R.id.Adminbtn);
        Guestbt=findViewById(R.id.Guestbtn);

        Guestbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandingPageMainActivity.this, LandingPageGuestActivity.class));
            }
        });

        Adminbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandingPageMainActivity.this, LandingPageAdminActivity.class));
            }
        });
    }

}
