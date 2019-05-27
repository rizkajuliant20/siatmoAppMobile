package com.siatmo.siatmoapp.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.siatmo.siatmoapp.R;
import com.siatmo.siatmoapp.view.pelanggan.FindHistoryActivity;
import com.siatmo.siatmoapp.view.pelanggan.KatalogSparepartActivity;
import com.siatmo.siatmoapp.view.pelanggan.ProfileCompanyActivity;

public class LandingPageGuestActivity extends AppCompatActivity {
    LinearLayout AboutUs, History, Catalog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page_guest);
        AboutUs=findViewById(R.id.layoutAboutUs);
        History=findViewById(R.id.layoutMyHistory);
        Catalog=findViewById(R.id.layoutCatalog);

        AboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandingPageGuestActivity.this, ProfileCompanyActivity.class));
            }
        });

        History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandingPageGuestActivity.this, FindHistoryActivity.class));
            }
        });

        Catalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandingPageGuestActivity.this, KatalogSparepartActivity.class));
            }
        });

    }


}
