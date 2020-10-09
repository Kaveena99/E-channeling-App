package com.example.talk_with_doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    EditText TxtId, TxtName, TxtHos, TxtMobile, TxtSpeci, TxtEmail, TxtPass, TxtDateTime;
    Button btnUpdate, retrivebtn;
    DatabaseReference dbRef;
    Doctor doctor;
    String Name, encPass, decPass, pass;;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);



        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        Intent intent1 = new Intent(getApplicationContext(),HomeDoctorActivity.class);
                        intent1.putExtra("docname", Name);
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
                        return true;

                }

                return false;
            }

        });
        Intent intent = getIntent();
        Name = intent.getStringExtra("docname");


        TxtId = findViewById(R.id.DoctorIDInput);
        TxtName = findViewById(R.id.DoctorNameInput);
        TxtHos = findViewById(R.id.DoctorHosInput);
        TxtMobile = findViewById(R.id.DoctorMobileInput);
        TxtSpeci = findViewById(R.id.DoctorCategoryInput);
        TxtEmail = findViewById(R.id.DocEmailInput);
        TxtPass = findViewById(R.id.DocPasswordInput);
        TxtDateTime = findViewById(R.id.DocDateTimeInput);
        btnUpdate = findViewById(R.id.Updatebutton);
//        retrivebtn = (Button) findViewById(R.id.profile);
        doctor = new Doctor();


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference updRef = FirebaseDatabase.getInstance().getReference().child("Doctor");
                updRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(Name)) {
                            String pw = TxtPass.getText().toString();
                            //encrypting user updated password
                            try {
                                encPass = Security.encrypt(pw);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                doctor.setID(TxtId.getText().toString().trim());
                                doctor.setName(TxtName.getText().toString().trim());
                                doctor.setHospital(TxtHos.getText().toString().trim());
                                doctor.setMobile(Integer.parseInt(TxtMobile.getText().toString().trim()));
                                doctor.setCategory(TxtSpeci.getText().toString().trim());
                                doctor.setEmail(TxtEmail.getText().toString().trim());
                                doctor.setPassword(encPass);
                                doctor.setDateTime(TxtDateTime.getText().toString().trim());

                                dbRef = FirebaseDatabase.getInstance().getReference().child("Doctor").child(Name);
                                dbRef.setValue(doctor);
//                                clearControls();

                                Toast.makeText(getApplicationContext(), "Data Update Successfully", Toast.LENGTH_SHORT).show();
                            } catch (NumberFormatException e) {
                                Toast.makeText(getApplicationContext(), "Invalid Contact Number", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "No Source Update", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Retrieve
        dbRef = FirebaseDatabase.getInstance().getReference().child("Doctor").child(Name);
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {

                    //fetching password from database
                    pass = dataSnapshot.child("password").getValue().toString();

                    //decrypting password
                    try {
                        decPass = Security.decrypt(pass);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    TxtId.setText(dataSnapshot.child("id").getValue().toString());
                    TxtName.setText(dataSnapshot.child("name").getValue().toString());
                    TxtHos.setText(dataSnapshot.child("hospital").getValue().toString());
                    TxtMobile.setText(dataSnapshot.child("mobile").getValue().toString());
                    TxtSpeci.setText(dataSnapshot.child("category").getValue().toString());
                    TxtEmail.setText(dataSnapshot.child("email").getValue().toString());
                    TxtPass.setText(decPass);
                    TxtDateTime.setText(dataSnapshot.child("dateTime").getValue().toString());

                } else
                    Toast.makeText(getApplicationContext(), "No source to display", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_profile);
//
//        TxtId = findViewById(R.id.DoctorIDInput);
//        TxtName = findViewById(R.id.DoctorNameInput);
//        TxtHos = findViewById(R.id.DoctorHosInput);
//        TxtMobile = findViewById(R.id.DoctorMobileInput);
//        TxtSpeci = findViewById(R.id.DoctorCategoryInput);
//        TxtEmail = findViewById(R.id.DocEmailInput);
//        TxtPass = findViewById(R.id.DocPasswordInput);
//        btnUpdate = findViewById(R.id.Updatebutton);
////        retrivebtn = (Button) findViewById(R.id.profile);
//        doctor = new Doctor();
//
//
//        btnUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DatabaseReference updRef = FirebaseDatabase.getInstance().getReference().child("Doctor");
//                updRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                        if (dataSnapshot.hasChild("doctor")) {
//                        try {
//                            doctor.setID(TxtId.getText().toString().trim());
//                            doctor.setName(TxtName.getText().toString().trim());
//                            doctor.setHospital(TxtHos.getText().toString().trim());
//                            doctor.setMobile(Integer.parseInt(TxtMobile.getText().toString().trim()));
//                            doctor.setCategory(TxtSpeci.getText().toString().trim());
//                            doctor.setEmail(TxtEmail.getText().toString().trim());
//                            doctor.setPassword(TxtPass.getText().toString().trim());
//
//                            dbRef = FirebaseDatabase.getInstance().getReference().child("Doctor");
//                            dbRef.setValue(doctor);
////                                clearControls();
//
//                            Toast.makeText(getApplicationContext(), "Data Update Successfully", Toast.LENGTH_SHORT).show();
//                        } catch (NumberFormatException e) {
//                            Toast.makeText(getApplicationContext(), "Invalid Contact Number", Toast.LENGTH_SHORT).show();
//                        }
//                    }
////                        Toast.makeText(getApplicationContext(), "No Source Update", Toast.LENGTH_SHORT).show();
////                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//            }
//        });
//    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        //Retrieve
//        dbRef = FirebaseDatabase.getInstance().getReference().child("Doctor");
//        dbRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.hasChildren()) {
//
//                    TxtId.setText(dataSnapshot.child("id").getValue().toString());
//                    TxtName.setText(dataSnapshot.child("name").getValue().toString());
//                    TxtHos.setText(dataSnapshot.child("hospital").getValue().toString());
//                    TxtMobile.setText(dataSnapshot.child("mobile").getValue().toString());
//                    TxtSpeci.setText(dataSnapshot.child("category").getValue().toString());
//                    TxtEmail.setText(dataSnapshot.child("email").getValue().toString());
//                    TxtPass.setText(dataSnapshot.child("password").getValue().toString());
//
//                } else
//                    Toast.makeText(getApplicationContext(), "No source to display", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

//            retrivebtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    //retreive
//                    DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("Doctor").child("-MHgtOB__EVNvNq7dk4x");
//                    readRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            if (dataSnapshot.hasChildren()){
//                        TxtId.setText(dataSnapshot.child("id").getValue().toString());
//                        TxtName.setText(dataSnapshot.child("name").getValue().toString());
//                        TxtHos.setText(dataSnapshot.child("hospital").getValue().toString());
//                        TxtMobile.setText(dataSnapshot.child("mobile").getValue().toString());
//                        TxtSpeci.setText(dataSnapshot.child("category").getValue().toString());
//                        TxtEmail.setText(dataSnapshot.child("email").getValue().toString());
//                        TxtPass.setText(dataSnapshot.child("password").getValue().toString());
//                            }
//                            else
//                                Toast.makeText(getApplicationContext(), "No source to display", Toast.LENGTH_SHORT).show();
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//                }
//            });


//        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
//        bottomNavigationView.setSelectedItemId(R.id.profile);
//
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                switch (menuItem.getItemId()) {
//                    case R.id.home:
//                        startActivity(new Intent(getApplicationContext(), HomeDoctorActivity.class));
//                        intent.putExtra("username",username);
//                        startActivity(intent);
//                        finish();
//                        overridePendingTransition(0, 0);
//                        return true;
//
//                    case R.id.logout:
//                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                        finish();
//                        overridePendingTransition(0, 0);
//                        return true;
//
//                    case R.id.profile:
//                        return true;
//
//                }
//
//                return false;
//            }
//
//        });
//        Intent intent = getIntent();
//        username = intent.getStringExtra("username");
//
//
//    }
//
//}