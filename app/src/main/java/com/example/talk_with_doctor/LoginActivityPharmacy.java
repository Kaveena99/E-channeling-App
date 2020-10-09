package com.example.talk_with_doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivityPharmacy extends AppCompatActivity {

    private Button btnLogin,btnSignup;
    private EditText editTxtName, editTxtPassword;
    DatabaseReference dbRef;

    boolean checkPass;
    private String encPass;
    String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pharmacy);

        btnSignup=findViewById(R.id.signupPha);
        btnLogin=findViewById(R.id.loginPha);
        editTxtName=findViewById(R.id.usernamePha);
        editTxtPassword=findViewById(R.id.passwordPha);

        //signUp button
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivityPharmacy.this, requestEmail.class);
                startActivity(intent);
            }
        });


        //Login activity
        dbRef=FirebaseDatabase.getInstance().getReference().child("Pharmacy");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Name= editTxtName.getText().toString().trim();
                String pw = editTxtPassword.getText().toString().trim();


                dbRef.child(Name).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Pharmacy pharmacy = snapshot.getValue(Pharmacy.class);

                        //encrypting user provided password
                        try {
                            encPass = Security.encrypt(pw);
                            System.out.println("\n" + "Login: Encrypted user provided password:  " + encPass + "\n");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        //fetching password from database

                        pass = pharmacy.getPassword();
                        System.out.println("\n" + "Login: DB retrieved password:  " + pass + "\n");


                        if (pass.equals(encPass))
                            checkPass = true;
                        else
                            checkPass = false;


                        //check whether input password and name are equal to database values
                        if (checkPass == true && Name.equals(pharmacy.getName())) {
                            Toast.makeText(LoginActivityPharmacy.this, "Login Successfull", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(LoginActivityPharmacy.this, homePharmacy.class);
                            intent.putExtra("username", Name);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivityPharmacy.this, "Please check again!!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }
}