package com.example.talk_with_doctor;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpPatient extends AppCompatActivity {

    EditText textName,textEmail,textUsername,textMobile,textPassword;
    Button btn;
    DatabaseReference dbRef;
    Patient pt;

    private void clearControls(){
        textName.setText("");
        textEmail.setText("");
        textUsername.setText("");
        textMobile.setText("");
        textPassword.setText("");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_patient);

        textName= findViewById(R.id.name);
        textEmail= findViewById(R.id.email);
        textUsername= findViewById(R.id.username);
        textMobile= findViewById(R.id.mobile);
        textPassword= findViewById(R.id.password);

        btn = findViewById(R.id.signup);

        pt = new Patient();

        //setOnClickListener to the sign up button
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String enPass = "";

                //encrypting entered password
                try {
                    enPass = Security.encrypt(textPassword.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                dbRef = FirebaseDatabase.getInstance().getReference().child("Patient");

                try {
                    if (TextUtils.isEmpty(textName.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter Name", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(textEmail.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter Email", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(textUsername.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter Address", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(textPassword.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter Password", Toast.LENGTH_SHORT).show();
                    else if (textPassword.length() < 6)
                        textPassword.setError("Password must be more than 6 characters");

                        pt.setName(textName.getText().toString().trim());
                        pt.setEmail(textEmail.getText().toString().trim());
                        pt.setUsername(textUsername.getText().toString().trim());
                        pt.setMobile(textMobile.getText().toString().trim());
                        pt.setPassword(enPass);

                        dbRef.child(pt.getUsername()).setValue(pt).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "You registered successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignUpPatient.this, homePatient.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(SignUpPatient.this, "Try again!!", Toast.LENGTH_SHORT).show();
                                }
                                clearControls();
                            }
                        });

                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Invalid contact number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}