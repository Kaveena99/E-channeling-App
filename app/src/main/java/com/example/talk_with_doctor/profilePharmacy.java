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

public class profilePharmacy extends AppCompatActivity {

    Button updatePha;
    EditText ID, name, mobile, address,email,password,city;
    DatabaseReference dbRef,updbRef;
    String username, encPass, decPass, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_pharmacy);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        Intent intent1 = new Intent(getApplicationContext(),homePharmacy.class);
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

        });


        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        ID = findViewById(R.id.ID);
        name=findViewById(R.id.name);
        mobile=findViewById(R.id.mobile);
        address=findViewById(R.id.address);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        city=findViewById(R.id.city);
        updatePha=findViewById(R.id.updatePha);



        //update method
        updatePha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pharmacy pha = new Pharmacy();

                updbRef = FirebaseDatabase.getInstance().getReference().child("Pharmacy");
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

                            pha.setID(ID.getText().toString().trim());
                            pha.setName(name.getText().toString().trim());
                            pha.setMobile(Integer.parseInt(mobile.getText().toString().trim()));
                            pha.setAddress(address.getText().toString().trim());
                            pha.setEmail(email.getText().toString().trim());
                            pha.setCity(city.getText().toString().trim());
                            pha.setPassword(encPass);

                            dbRef = FirebaseDatabase.getInstance().getReference().child("Pharmacy").child(username);
                            dbRef.setValue(pha);

                            Toast.makeText(profilePharmacy.this,"Your profile updated successfully",Toast.LENGTH_SHORT).show();
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

        dbRef = FirebaseDatabase.getInstance().getReference().child("Pharmacy").child(username);
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

                    ID.setText(snapshot.child("id").getValue().toString());
                    name.setText(snapshot.child("name").getValue().toString());
                    mobile.setText(snapshot.child("mobile").getValue().toString());
                    address.setText(snapshot.child("address").getValue().toString());
                    email.setText(snapshot.child("email").getValue().toString());
                    password.setText(decPass);
                    city.setText(snapshot.child("city").getValue().toString());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}