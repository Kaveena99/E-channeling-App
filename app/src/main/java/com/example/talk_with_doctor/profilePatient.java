package com.example.talk_with_doctor;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profilePatient extends AppCompatActivity {

    String username, encPass, decPass, pass;
    EditText userName,name,password,email,mobile;
    DatabaseReference dbRef,updbRef;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_patient);

        //bottom navigation bar
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);

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
                        return true;

                }

                return false;
            }

        });//end of the navigation bar


        //receiving intent
        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        userName = findViewById(R.id.username);
        name=findViewById(R.id.name);
        password=findViewById(R.id.password);
        mobile=findViewById(R.id.mobile);
        email=findViewById(R.id.email);
        update=findViewById(R.id.update);


        //update method
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Patient pt = new Patient();

                updbRef = FirebaseDatabase.getInstance().getReference().child("Patient");
                updbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(username))
                        {

                            String pw = password.getText().toString();

                            //encrypting user updated password
                            try {
                                encPass = Security.encrypt(pw);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            pt.setUsername(userName.getText().toString().trim());
                            pt.setName(name.getText().toString().trim());
                            pt.setPassword(encPass);
                            pt.setMobile(mobile.getText().toString().trim());
                            pt.setEmail(email.getText().toString().trim());

                            dbRef = FirebaseDatabase.getInstance().getReference().child("Patient").child(username);
                            dbRef.setValue(pt);

                            Toast.makeText(profilePatient.this,"Your profile updated successfully",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        //retrieve user details
        dbRef = FirebaseDatabase.getInstance().getReference().child("Patient").child(username);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren())
                {

                    //fetching password from database
                    pass = snapshot.child("password").getValue().toString();

                    //decrypting password
                    try {
                        decPass = Security.decrypt(pass);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    userName.setText(snapshot.child("username").getValue().toString());
                    name.setText(snapshot.child("name").getValue().toString());
                    password.setText(decPass);
                    mobile.setText(snapshot.child("mobile").getValue().toString());
                    email.setText(snapshot.child("email").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}