package com.example.talk_with_doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeDoctorActivity extends AppCompatActivity {

    private Button myDetails, viewPatient, Appoinments, myAppoinments, notifications;
    String docname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_doctor);

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
                        Intent intent = new Intent(getApplicationContext(),Profile.class);
                        intent.putExtra("docname",docname);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0, 0);
                        return true;
                }

                return false;
            }

        });


        viewPatient = (Button) findViewById(R.id.ViewPatient);
        viewPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewPatient();
            }
        });
        Appoinments = (Button) findViewById(R.id.Appoinment);
        Appoinments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Appoinments();
            }
        });
        myAppoinments = (Button) findViewById(R.id.my_appoinment);
        myAppoinments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyAppoinments();
            }
        });
        notifications = (Button) findViewById(R.id.Notification);
        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifications();
            }

        });

        Intent intent= getIntent();
        docname = intent.getStringExtra("docname");


    }



    private void ViewPatient(){
        Intent intent = new Intent(this, ViewPatientDoctorActivity.class);
        startActivity(intent);
    }
    private void Appoinments(){
        Intent intent = new Intent(this, AppoinmentsDoctorActivity.class);
        intent.putExtra("docname",docname);
        startActivity(intent);
    }
    private void MyAppoinments(){
        Intent intent = new Intent(this, MyAppoinmentsDoctorActivity.class);
        startActivity(intent);
    }
    private void notifications(){
        Intent intent = new Intent(this, NotificationDoctorActivity.class);
        startActivity(intent);
    }





}