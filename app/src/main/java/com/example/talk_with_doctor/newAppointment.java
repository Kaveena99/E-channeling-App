package com.example.talk_with_doctor;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class newAppointment extends AppCompatActivity {

    Button button;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_appointment);

        //receiving username
        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        //bottom navigation bar
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        Intent intent1 = new Intent(getApplicationContext(),homePatient.class);
                        intent1.putExtra("username",username);
                        startActivity(intent1);
                        finish();
                        overridePendingTransition(0, 0);
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

        //setting onClick activity to the search button
        button = findViewById(R.id.search_btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData();
            }
        });
    }


    //sendData() method to send data to the doctorDetails activity to search
    public void sendData(){

        Intent intent = new Intent(this, doctorDetails.class);

        EditText dName = (EditText)findViewById(R.id.docName);
        Spinner specialization = findViewById(R.id.specialization);
        Spinner hospital = findViewById(R.id.hospital);
        EditText date = (EditText)findViewById(R.id.date);

        String Name = dName.getText().toString();
        String special = specialization.getSelectedItem().toString();
        String hos = hospital.getSelectedItem().toString();
        String Date = date.getText().toString();

        intent.putExtra("DName",Name);
        intent.putExtra("specialization",special);
        intent.putExtra("hospital",hos);
        intent.putExtra("date",Date);
        intent.putExtra("username",username);

        startActivity(intent);
    }


}