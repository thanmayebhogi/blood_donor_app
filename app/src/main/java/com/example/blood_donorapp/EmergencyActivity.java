package com.example.blood_donorapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EmergencyActivity extends AppCompatActivity {

    // UI Elements
    private ListView listViewEmergency;
    private Button btnPostUrgent, btnHomeFromEmergency;

    // Firebase and List Data
    private DatabaseReference dbRef;
    private ArrayList<String> emergencyRequests;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        // 1. Initialize UI Elements (Matching IDs in your XML)
        listViewEmergency = findViewById(R.id.listViewEmergency);
        btnPostUrgent = findViewById(R.id.btnPostUrgent);
        btnHomeFromEmergency = findViewById(R.id.btnHomeFromEmergency);

        // 2. Setup Firebase Reference
        dbRef = FirebaseDatabase.getInstance().getReference("Emergency");

        // 3. Setup List and Adapter
        emergencyRequests = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, emergencyRequests);
        listViewEmergency.setAdapter(adapter);

        // 4. Load Data from Firebase in Real-Time
        loadEmergencyData();

        // 5. Button Logic: Go to Post Emergency Form
        btnPostUrgent.setOnClickListener(v -> {
            Intent intent = new Intent(EmergencyActivity.this, PostEmergencyActivity.class);
            startActivity(intent);
        });

        // 6. Button Logic: Go Back to Home (MainActivity)
        btnHomeFromEmergency.setOnClickListener(v -> {
            Intent intent = new Intent(EmergencyActivity.this, MainActivity.class);
            // Clear activity stack so user doesn't "go back" into the directory
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    private void loadEmergencyData() {
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                emergencyRequests.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // Assuming data is saved as a simple String
                    String request = dataSnapshot.getValue(String.class);
                    if (request != null) {
                        emergencyRequests.add(request);
                    }
                }
                // Refresh the ListView
                adapter.notifyDataSetChanged();

                if (emergencyRequests.isEmpty()) {
                    Toast.makeText(EmergencyActivity.this, "No urgent requests found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EmergencyActivity.this, "Failed to load data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}