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

public class profileAdmin extends AppCompatActivity {

    EditText editTxtId, editTxtName, editTxtEmail, editTxtPassword;
    Button btnUpdate;
    DatabaseReference dbRef;
    FirebaseDatabase firebaseDatabase;
    Admin adm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_admin);

        editTxtId = findViewById(R.id.editTxtId);
        editTxtName = findViewById(R.id.editTxtName);
        editTxtEmail = findViewById(R.id.editTxtEmail);
        editTxtPassword = findViewById(R.id.editTxtPasswordAdminProfile);

        btnUpdate = findViewById(R.id.btnUpdate);

        adm = new Admin();

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), com.example.talk_with_doctor.homeAdmin.class));
                        finish();
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.logout:
                        startActivity(new Intent(getApplicationContext(), com.example.talk_with_doctor.MainActivity.class));
                        finish();
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.profile:
                        return true;

                }

                return false;
            }

        });


        //Update
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference upRef = FirebaseDatabase.getInstance().getReference().child("Admin").child("adm1");
                upRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                adm.setID(editTxtId.getText().toString().trim());
                                adm.setName(editTxtName.getText().toString().trim());
                                adm.setEmail(editTxtEmail.getText().toString().trim());
                                adm.setPassword(editTxtPassword.getText().toString().trim());

                                dbRef = FirebaseDatabase.getInstance().getReference().child("Admin").child("adm1");
                                dbRef.setValue(adm);

                                Toast.makeText(getApplicationContext(), "Data updated successfully", Toast.LENGTH_SHORT).show();
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
        dbRef = FirebaseDatabase.getInstance().getReference().child("Admin").child("adm1");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()) {
                    editTxtId.setText(dataSnapshot.child("id").getValue().toString());
                    editTxtName.setText(dataSnapshot.child("name").getValue().toString());
                    editTxtEmail.setText(dataSnapshot.child("email").getValue().toString());
                    editTxtPassword.setText(dataSnapshot.child("password").getValue().toString());

                }else
                    Toast.makeText(getApplicationContext(), "No source to display", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
