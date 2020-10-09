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

public class LoginActivityDoctor extends AppCompatActivity {

    private Button btnLogin;
    private EditText editTxtName, editTxtPassword;
    DatabaseReference dbRef;

    boolean checkPass;
    private String encPass;
    String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_doctor);

        btnLogin = findViewById(R.id.btnLoginDoc);
        editTxtName  = findViewById(R.id.NameDocLog);
        editTxtPassword = findViewById(R.id.PasswordDocLog);


        //Login activity
        dbRef= FirebaseDatabase.getInstance().getReference().child("Doctor");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Name =  editTxtName.getText().toString().trim();
                String pw = editTxtPassword.getText().toString().trim();


                dbRef.child(Name).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Doctor doctor = snapshot.getValue(Doctor.class);

                        //encrypting user provided password
                        try {
                            encPass = Security.encrypt(pw);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        //fetching password from database
                        pass = doctor.getPassword();


                        if(pass.equals(encPass))
                            checkPass = true;
                        else
                            checkPass = false;

                        //check whether input password and name are equal to database values
                        if(checkPass == true && Name.equals(doctor.getName()))
                        {
                            Toast.makeText(LoginActivityDoctor.this,"Login Successfull",Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(LoginActivityDoctor.this, HomeDoctorActivity.class);
                            intent.putExtra("docname", Name);
                            startActivity(intent);

                        }else {
//                            Toast.makeText(LoginActivityDoctor.this,"Please check again!!",Toast.LENGTH_SHORT).show();
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

