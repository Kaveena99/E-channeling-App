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

public class AppoinmentsDoctorActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference dbRef;
    String id,dname,username;
    Appointment appoinments;
    String docname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoinments_doctor);

        //intent
        Intent intent = getIntent();
        docname = intent.getStringExtra("docname");

        appoinments = new Appointment();
        recyclerView = findViewById(R.id.recyclerviewAppoinment);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseReference = firebaseDatabase.getInstance().getReference().child("Appointments");

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), HomeDoctorActivity.class));
                        finish();
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.logout:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), com.example.talk_with_doctor.Profile.class));
                        finish();
                        overridePendingTransition(0, 0);
                        return true;
                }

                return false;
            }

        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Appointment> options =
                new FirebaseRecyclerOptions.Builder<Appointment>()
                        .setQuery(databaseReference.orderByChild("doctorName").equalTo(docname), Appointment.class)
                        .build();

        //retrieve Appoinments
        FirebaseRecyclerAdapter<Appointment, ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Appointment, ViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Appointment model) {
                        holder.setAppoinmnetsData(getApplicationContext(), model.getId(), model.getName(), model.getDateTime(), Integer.parseInt(model.getPhone()));

                        holder.setOnClickListener(new ViewHolder.Clicklistener() {
                            @Override
                            public void onItemlongClick(View view, int position) {
                                id = getItem(position).getId();
                                dname=getItem(position).getDoctorName();
                                username=getItem(position).getUsername();

                                showAcceptDialog(dname);
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

    private void showAcceptDialog(String dname){

        AlertDialog.Builder builder = new AlertDialog.Builder(AppoinmentsDoctorActivity.this);
        builder.setTitle("Accept");
        builder.setMessage("Do you want to accept the request?");

        //Yes button
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                ConfirmedAppointments cp = new ConfirmedAppointments();

                Query query = databaseReference.orderByChild("doctorName").equalTo(dname);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            if ((ds.child("username").getValue().toString()) == username) {

                                dbRef = FirebaseDatabase.getInstance().getReference().child("Bookings");

                                cp.setUsername(ds.child("username").getValue().toString());
                                cp.setDocName(ds.child("doctorName").getValue().toString());
                                cp.setDateTime(ds.child("dateTime").getValue().toString());
                                cp.setHospital(ds.child("hospital").getValue().toString());
                                cp.setId(ds.child("id").getValue().toString());

                                dbRef.push().setValue(cp);

                            }
                            Toast.makeText(AppoinmentsDoctorActivity.this, "Appointment accepted!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Query query1 = databaseReference.orderByChild("doctorName").equalTo(dname);
                query1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            if ((ds.child("username").getValue().toString()) == username) {

                                dbRef = FirebaseDatabase.getInstance().getReference().child("AllPatients");

                                cp.setUsername(ds.child("username").getValue().toString());
                                cp.setDocName(ds.child("doctorName").getValue().toString());
                                cp.setDateTime(ds.child("dateTime").getValue().toString());
                                cp.setHospital(ds.child("hospital").getValue().toString());
                                cp.setId(ds.child("id").getValue().toString());

                                dbRef.child(cp.getUsername()).setValue(cp);

                            }
                            Toast.makeText(AppoinmentsDoctorActivity.this, "Appointment accepted!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        //No button
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Query query = databaseReference.orderByChild("username").equalTo(username);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()){
                            ds.getRef().removeValue();
                        }
                        Toast.makeText(AppoinmentsDoctorActivity.this, "Appointment deleted!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}