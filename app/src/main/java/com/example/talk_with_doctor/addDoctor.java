package com.example.talk_with_doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class addDoctor extends AppCompatActivity {

    EditText editTxtId, editTxtName, editTxtPassword, editTxtMobile, editTxtEmail, editTxtCategory, editTxtHospital, editTxtDateTime;
    Button btnAdd;
    DatabaseReference dbRef;
    Doctor doc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor);

        editTxtId = findViewById(R.id.editTxtId);
        editTxtName = findViewById(R.id.editTxtName);
        editTxtPassword = findViewById(R.id.editTxtPassword);
        editTxtMobile = findViewById(R.id.editTxtMobile);
        editTxtEmail = findViewById(R.id.editTxtEmail);
        editTxtCategory = findViewById(R.id.editTxtCategory);
        editTxtHospital = findViewById(R.id.editTxtHospital);
        editTxtDateTime = findViewById(R.id.editTxtDateAndTime);

        btnAdd = findViewById(R.id.btnAdd);

        doc = new Doctor();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String enPass = "";

                //encrypting entered password
                try {
                    enPass = Security.encrypt(editTxtPassword.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                dbRef = FirebaseDatabase.getInstance().getReference().child("Doctor");
                try{
                    if(TextUtils.isEmpty(editTxtId.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter ID", Toast.LENGTH_SHORT).show();
                    else if(TextUtils.isEmpty(editTxtName.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter Name", Toast.LENGTH_SHORT).show();
                    else if(TextUtils.isEmpty(editTxtPassword.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter Password", Toast.LENGTH_SHORT).show();
                    else if(TextUtils.isEmpty(editTxtMobile.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter Contact Number", Toast.LENGTH_SHORT).show();
                    else if(TextUtils.isEmpty(editTxtEmail.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter Email", Toast.LENGTH_SHORT).show();
                    else if(TextUtils.isEmpty(editTxtCategory.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter Category", Toast.LENGTH_SHORT).show();
                    else if(TextUtils.isEmpty(editTxtHospital.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter Hospital", Toast.LENGTH_SHORT).show();
                    else if(TextUtils.isEmpty(editTxtDateTime.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter Date and Time", Toast.LENGTH_SHORT).show();
                    else{
                        doc.setID(editTxtId.getText().toString().trim());
                        doc.setName(editTxtName.getText().toString().trim());
                        doc.setPassword(enPass);
                        doc.setMobile(Integer.parseInt(editTxtMobile.getText().toString().trim()));
                        doc.setEmail(editTxtEmail.getText().toString().trim());
                        doc.setCategory(editTxtCategory.getText().toString().trim());
                        doc.setHospital(editTxtHospital.getText().toString().trim());
                        doc.setDateTime(editTxtDateTime.getText().toString().trim());

                        dbRef.child(doc.getName()).setValue(doc);

                        Toast.makeText(getApplicationContext(), "Data saved successfully", Toast.LENGTH_SHORT).show();
                        clearControls();
                    }
                } catch(NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Invalid contact number", Toast.LENGTH_SHORT).show();
                }
            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), homeAdmin.class));
                        finish();
                        overridePendingTransition(0, 0);
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

    private void clearControls(){
        editTxtId.setText("");
        editTxtName.setText("");
        editTxtPassword.setText("");
        editTxtMobile.setText("");
        editTxtEmail.setText("");
        editTxtCategory.setText("");
        editTxtHospital.setText("");
        editTxtDateTime.setText("");

    }
}