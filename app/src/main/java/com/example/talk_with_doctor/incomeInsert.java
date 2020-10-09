package com.example.talk_with_doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class incomeInsert extends AppCompatActivity {

    Button button2;
    EditText txtMonth, txtSales, txtIncome, txtEx;
    DatabaseReference dbRef;
    Income in;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_insert);

        txtMonth = findViewById(R.id.txtM);
        txtSales = findViewById(R.id.txtS);
        txtIncome = findViewById(R.id.txtI);
        txtEx = findViewById(R.id.txtE);

        button2 = findViewById(R.id.button2);

        in = new Income();

        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openIncome();

                dbRef = FirebaseDatabase.getInstance().getReference().child("Income");

                try{
                    if(TextUtils.isEmpty(txtMonth.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Enter Month", Toast.LENGTH_SHORT).show();

                    else if(TextUtils.isEmpty(txtSales.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Enter Sales", Toast.LENGTH_SHORT).show();

                    else if(TextUtils.isEmpty(txtIncome.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Enter Income", Toast.LENGTH_SHORT).show();

                    else if(TextUtils.isEmpty(txtEx.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Enter Expences", Toast.LENGTH_SHORT).show();

                    else{
                        //take input from the user and assigning them to this instance (in) of the Income
                        in.setMonth(Integer.parseInt(txtMonth.getText().toString().trim()));
                        in.setSales(Integer.parseInt(txtSales.getText().toString().trim()));
                        in.setIncome(Integer.parseInt(txtIncome.getText().toString().trim()));
                        in.setExpences(Integer.parseInt(txtEx.getText().toString().trim()));

                        //insert in to the db
                        dbRef.push().setValue(in);

                        //feedback to the user via toast
                        Toast.makeText(getApplicationContext(), " Data Saved Successfully", Toast.LENGTH_SHORT).show();
                        clearControls();
                    }
                }catch(NumberFormatException e){
                    Toast.makeText(getApplicationContext(), "Invalid User Input", Toast.LENGTH_SHORT).show();
                }
            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        return true;

                    case R.id.logout:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.profile:
                        Intent intent = new Intent(getApplicationContext(),profilePharmacy.class);
                        intent.putExtra("username",username);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0, 0);
                        return true;
                }

                return false;
            }

        });
        Intent intent= getIntent();
        username = intent.getStringExtra("username");

    }

    private void openIncome() {
        Intent intent = new Intent (this, incomePharmacy.class);
        startActivity(intent);
    }

    private void clearControls(){ //method to clear all user inputs

        txtMonth.setText("");
        txtSales.setText("");
        txtIncome.setText("");
        txtEx.setText("");

    }
}


