package com.example.talk_with_doctor;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class notificationsPatient extends AppCompatActivity {

    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_patient);


    }
    @Override
    protected void onStart() {
        super.onStart();


        //receiving username
        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        ConfirmedAppointments cp = new ConfirmedAppointments();
        recyclerView = findViewById(R.id.bookings);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseReference = firebaseDatabase.getInstance().getReference().child("Bookings");


        FirebaseRecyclerOptions<ConfirmedAppointments> options =
                new FirebaseRecyclerOptions.Builder<ConfirmedAppointments>()
                        .setQuery(databaseReference.orderByChild("username").equalTo(username), ConfirmedAppointments.class)
                        .build();

        //retrieve notifications
        FirebaseRecyclerAdapter<ConfirmedAppointments, ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<ConfirmedAppointments, ViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull ConfirmedAppointments model) {
                        holder.setNotification(getApplicationContext(), model.getDocName());

                    }


                    @Override
                    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.row, parent, false);

                        return new ViewHolder(view);
                    }
                };
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);

    }

}