package com.example.talk_with_doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import javax.security.auth.login.LoginException;

public class requestEmail extends AppCompatActivity {
    /*EditText etSubject, etMessage;
    TextView etTo;*/
    Button btnRequest;
    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_email);

        btnBack = findViewById(R.id.imgBtnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(requestEmail.this, com.example.talk_with_doctor.MainActivity.class);
                startActivity(i);
            }
        });


        //Sending email code

        /*etTo = findViewById(R.id.txtEmailAddress);
        etSubject = findViewById(R.id.editTxtSubject);
        etMessage = findViewById(R.id.editTxtMessage);*/
        btnRequest = findViewById(R.id.btnRequest);


        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String receipient = "admin@gmail.com";
                String subject = "Subject";
                String message = "Enter details here...";
                /*String subject = etSubject.getText().toString().trim();
                String message = etMessage.getText().toString().trim();*/

                //sendEmail(receipient, subject, message);
                sendEmail(receipient, subject, message);
            }

        });

    }

    /*private void sendEmail(String receipient, String subject, String message) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        intent.setType("text/plain");

        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{receipient});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        try{
            startActivity(Intent.createChooser(intent, "Choose email app;"));
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(), Toast.LENGTH_SHORT).show();
        }*/

    private void sendEmail(String receipient, String subject, String message) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        intent.setType("message/rfc822");

        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{receipient});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        try{
            //startActivity(Intent.createChooser(intent, "Choose email app;"));
            startActivity(intent);
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

}