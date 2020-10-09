package com.example.talk_with_doctor;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class homePatient extends AppCompatActivity {

    Button button;
    ImageView img;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_patient);

        //bottom navigation bar
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
                        Intent intent = new Intent(getApplicationContext(),profilePatient.class);
                        intent.putExtra("username",username);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0, 0);
                        return true;
                }

                return false;
            }

        });//end of the navigation bar

        //set onClickListeners to the buttons to navigate relevant activities
        button = (Button)findViewById(R.id.newAppointment_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opeNewAppointment();
            }
        });

        button = (Button)findViewById(R.id.orderMedicine);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openOrderMedicine();
            }
        });

        button = (Button)findViewById(R.id.viewBookings);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBookings();
            }
        });

        img = (ImageView)findViewById(R.id.notificationPatient);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNotifications();
            }
        });

        //receiving username from the intent
        Intent intent= getIntent();
        username = intent.getStringExtra("username");



    }


    //onClickListeners methods

    public void opeNewAppointment() {
        Intent intent = new Intent(this, newAppointment.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }

    public void openOrderMedicine() {
        Intent intent = new Intent(this, orderMedicines.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }

    public void openBookings() {
        Intent intent = new Intent(this, Bookings.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }

    public void openNotifications() {
        Intent intent = new Intent(this, notificationsPatient.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }

}