package com.example.talk_with_doctor;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class homeAdmin extends AppCompatActivity {

    Button btnDoctor, btnPharmacy, btnViewPatientHome, btnViewDoctorHome, btnViewPharmacyHome;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        btnDoctor = findViewById(R.id.btnAddDoctor);
        btnPharmacy = findViewById(R.id.btnAddPharmacy);
        btnViewPatientHome = findViewById(R.id.btnViewPatient);
        btnViewDoctorHome = findViewById(R.id.btnViewDoctor);
        btnViewPharmacyHome = findViewById(R.id.btnViewPharmacy);

        btnDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iDoctor = new Intent(getApplicationContext(), com.example.talk_with_doctor.addDoctor.class);
                startActivity(iDoctor);
            }
        });
        btnPharmacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iPharmacy = new Intent(getApplicationContext(), com.example.talk_with_doctor.addPharmacy.class);
                startActivity(iPharmacy);
            }
        });
        btnViewPatientHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iPatient = new Intent(getApplicationContext(), viewPatient.class);
                startActivity(iPatient);
            }
        });
        btnViewDoctorHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iViewDoctor = new Intent(getApplicationContext(), viewDoctor.class);
                startActivity(iViewDoctor);
            }
        });
        btnViewPharmacyHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iViewPharmacy = new Intent(getApplicationContext(), viewPharmacy.class);
                startActivity(iViewPharmacy);
            }
        });



        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        return true;

                    case R.id.logout:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), profileAdmin.class));
                        finish();
                        overridePendingTransition(0, 0);
                        return true;
                }

                return false;
            }

        });
    }



}
