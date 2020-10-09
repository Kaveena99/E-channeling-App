package com.example.talk_with_doctor;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Bookings extends AppCompatActivity {

    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    String username,docName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //receiving username from intent
        Intent intent = getIntent();
        username = intent.getStringExtra("username");


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);

        //bottom navigation bar
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);

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
                        Intent intent = new Intent(getApplicationContext(),profilePatient.class);
                        intent.putExtra("username",username);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0, 0);
                        return true;
                }

                return false;
            }

        });//end of the navigation bar


        //to retrieve
        ConfirmedAppointments cp = new ConfirmedAppointments();
        recyclerView = findViewById(R.id.bookings);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseReference = firebaseDatabase.getInstance().getReference().child("Bookings");
    }



    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<ConfirmedAppointments> options =
                new FirebaseRecyclerOptions.Builder<ConfirmedAppointments>()
                        .setQuery(databaseReference.orderByChild("username").equalTo(username), ConfirmedAppointments.class)
                        .build();

        //retrieve details
        FirebaseRecyclerAdapter<ConfirmedAppointments, ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<ConfirmedAppointments, ViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull ConfirmedAppointments model) {
                        holder.setBookings(getApplicationContext(), model.getDocName(), model.getDateTime(), model.getHospital());

                        holder.setOnClickListener(new ViewHolder.Clicklistener() {
                            @Override
                            public void onItemlongClick(View view, int position) {
                                docName = getItem(position).getDocName();

                                showDeleteDataDialog(docName);
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

    //showing dialog box to the user after a long click asking to delete that detail
    private void showDeleteDataDialog(String docName){

        AlertDialog.Builder builder = new AlertDialog.Builder(Bookings.this);
        builder.setTitle("Delete");
        builder.setMessage("Delete appointment details?");

        //Yes button
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Query query = databaseReference.orderByChild("docName").equalTo(docName);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()){
                            ds.getRef().removeValue();
                        }
                        Toast.makeText(Bookings.this, "Deleted!", Toast.LENGTH_SHORT).show();
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
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}