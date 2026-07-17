package com.example.blood_donorapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.*;
import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    DatabaseReference dbRef;
    ArrayList<DonorModel> donorList = new ArrayList<>();
    ArrayList<String> displayList = new ArrayList<>();
    ArrayAdapter<String> adapter;
    TextView tvCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize Firebase
        dbRef = FirebaseDatabase.getInstance().getReference("Donors");

        // UI Links
        EditText searchBar = findViewById(R.id.searchBar);
        ListView listView = findViewById(R.id.donorListView);
        tvCounter = findViewById(R.id.tvCounter);
        Button btnHome = findViewById(R.id.btnHomeFromDash);

        // Setup Adapter
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayList);
        listView.setAdapter(adapter);

        // 1. Fetch Data from Firebase
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                donorList.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        DonorModel donor = data.getValue(DonorModel.class);
                        if (donor != null) {
                            donorList.add(donor);
                        }
                    }
                }
                tvCounter.setText("Total Donors: " + donorList.size());
                filterList(""); // Show everything initially
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(DashboardActivity.this, "Database Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // 2. Search Bar Listener
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterList(s.toString());
            }
            @Override public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {}
            @Override public void afterTextChanged(Editable editable) {}
        });

        // 3. Quick Filter Chips
        findViewById(R.id.chipAll).setOnClickListener(v -> { searchBar.setText(""); filterList(""); });
        findViewById(R.id.chipApos).setOnClickListener(v -> filterList("A+"));
        findViewById(R.id.chipBpos).setOnClickListener(v -> filterList("B+"));
        findViewById(R.id.chipOpos).setOnClickListener(v -> filterList("O+"));
        findViewById(R.id.chipOneg).setOnClickListener(v -> filterList("O-"));

        // 4. Contact Dialog (Call/WhatsApp)
        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (displayList.get(position).equals("No donors found matching your search")) return;

            String selectedInfo = displayList.get(position);
            for (DonorModel donor : donorList) {
                // Find matching donor by name within the string
                if (donor.name != null && selectedInfo.contains(donor.name)) {
                    showContactDialog(donor);
                    break;
                }
            }
        });

        btnHome.setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));
    }

    private void showContactDialog(DonorModel donor) {
        String[] options = {"Call " + donor.name, "Message on WhatsApp"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Contact " + donor.bloodGroup);
        builder.setItems(options, (dialog, which) -> {
            if (which == 0) {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + donor.phone)));
            } else {
                String url = "https://api.whatsapp.com/send?phone=" + donor.phone;
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });
        builder.show();
    }

    private void filterList(String query) {
        displayList.clear();
        String q = query.toLowerCase().trim();

        for (DonorModel d : donorList) {
            // Null-safe check: Use String.valueOf to avoid crashes if data is missing in Firebase
            String blood = d.bloodGroup != null ? d.bloodGroup.toLowerCase() : "";
            String city = d.city != null ? d.city.toLowerCase() : "";
            String name = d.name != null ? d.name : "Unknown";

            if (blood.contains(q) || city.contains(q)) {
                displayList.add("NAME: " + name + "\nBLOOD: " + d.bloodGroup + "\nCITY: " + d.city);
            }
        }

        if (displayList.isEmpty()) {
            displayList.add("No donors found matching your search");
        }

        adapter.notifyDataSetChanged();
    }
}