package com.example.talk_with_doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ViewPatientDoctorActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference dbRef;
    String id, dname, username;
    ConfirmedAppointments Myappoinments;
    String Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient_doctor);

        //intent
        Intent intent = getIntent();
        Name = intent.getStringExtra("docname");

        Myappoinments = new ConfirmedAppointments();
        recyclerView = findViewById(R.id.recyclerviewPatientDoc);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseReference = firebaseDatabase.getInstance().getReference().child("AllPatients");

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        Intent intent1 = new Intent(getApplicationContext(),HomeDoctorActivity.class);
                        intent1.putExtra("docname", Name);
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
    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<ConfirmedAppointments> options =
                new FirebaseRecyclerOptions.Builder<ConfirmedAppointments>()
                        .setQuery(databaseReference, ConfirmedAppointments.class)
                        .build();

        //retrieve All Patients who takes appoinments from the relavent doctor
        FirebaseRecyclerAdapter<ConfirmedAppointments, ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<ConfirmedAppointments, ViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull ConfirmedAppointments model) {
                        holder.setMyAppoinmnetsData(getApplicationContext(), model.getId(), model.getUsername(), model.getDateTime(),model.getHospital());

                        holder.setOnClickListener(new ViewHolder.Clicklistener() {
                            @Override
                            public void onItemlongClick(View view, int position) {
                                id = getItem(position).getId();

                            }
                        });
                    }

                    @NonNull
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


