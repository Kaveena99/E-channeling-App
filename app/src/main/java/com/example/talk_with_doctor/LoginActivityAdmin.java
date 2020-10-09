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

public class LoginActivityAdmin extends AppCompatActivity {

    private Button btnLogin;
    private EditText editTxtEmail, editTxtPassword;
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        btnLogin = findViewById(R.id.btnLoginDoc);
        editTxtEmail  = findViewById(R.id.NameDocLog);
        editTxtPassword = findViewById(R.id.PasswordDocLog);

        //Login activity

        dbRef= FirebaseDatabase.getInstance().getReference().child("Admin");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email =  editTxtEmail.getText().toString().trim();
                String pw = editTxtPassword.getText().toString().trim();



                dbRef.child("adm1").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Admin admin = snapshot.getValue(Admin.class);
                        if(pw.equals(admin.getPassword()) && email.equals(admin.getEmail()))
                        {
                            Toast.makeText(LoginActivityAdmin.this,"Login Successfull",Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(LoginActivityAdmin.this, homeAdmin.class);
                            intent.putExtra("username", email);
                            startActivity(intent);

                        }else {
                            Toast.makeText(LoginActivityAdmin.this,"Please check again!!",Toast.LENGTH_SHORT).show();
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