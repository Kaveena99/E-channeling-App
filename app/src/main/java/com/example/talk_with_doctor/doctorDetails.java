package com.example.talk_with_doctor;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class doctorDetails extends AppCompatActivity {

    Button button;
    TextView name;
    TextView specialization;
    TextView hospital;
    TextView date;
    TextView time;
    DatabaseReference readRef;
    String dName;
    String category;
    String dHospital;

    String  docName;
    String docId;
    String dateTime;
    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        //get username from intent
        Intent intent = getIntent();
        username=intent.getStringExtra("username");

        //navigation bar
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

        //receiving doctor name and doctor category from the intent
        dName= intent.getStringExtra("DName");
        category=intent.getStringExtra("specialization");
        //dHospital = intent.getStringExtra("hospital");
        username=intent.getStringExtra("username");


        //capturing to views
        name = findViewById(R.id.dName);
        specialization = findViewById(R.id.special);
        hospital = findViewById(R.id.hospital);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);


    }

    @Override
    protected void onStart() {
        super.onStart();

        //searching the doctor name and displaying
        Query query = FirebaseDatabase.getInstance().getReference("Doctor").child(dName);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    name.setText(snapshot.child("name").getValue().toString());
                    specialization.setText(snapshot.child("category").getValue().toString());
                    hospital.setText(snapshot.child("hospital").getValue().toString());
                    date.setText(snapshot.child("dateTime").getValue().toString());

                    //values to pass patient details activity
                    docName = snapshot.child("name").getValue().toString();
                    docId = snapshot.child("id").getValue().toString();
                    dateTime =snapshot.child("dateTime").getValue().toString();
                    dHospital=snapshot.child("hospital").getValue().toString();
                }
                else
                    Toast.makeText(getApplicationContext(), "Sorry, the doctor you are looking for is not in our system",
                            Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //sending doctor details to patient details activity
        button = findViewById(R.id.requestApp_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendDetails();
            }
        });


    }

    //sendDetails() method
    public void sendDetails(){
        Intent intent = new Intent(this,patientDetails.class);

        intent.putExtra("docName",docName);
        intent.putExtra("docId",docId);
        intent.putExtra("dateTime",dateTime);
        intent.putExtra("username",username);
        intent.putExtra("hospital",dHospital);

        startActivity(intent);
    }
}