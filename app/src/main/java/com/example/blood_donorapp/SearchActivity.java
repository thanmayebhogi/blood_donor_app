package com.example.blood_donorapp;

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

public class SearchActivity extends AppCompatActivity {

    private DatabaseReference dbRef;
    private ArrayList<DonorModel> allDonorsList = new ArrayList<>();
    private ArrayList<String> displayList = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        dbRef = FirebaseDatabase.getInstance().getReference("Donors");

        EditText etSearchQuery = findViewById(R.id.etSearchQuery);
        ListView searchResultList = findViewById(R.id.searchResultList);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayList);
        searchResultList.setAdapter(adapter);

        // 1. Load data from Firebase
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                allDonorsList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    DonorModel donor = data.getValue(DonorModel.class);
                    if (donor != null) allDonorsList.add(donor);
                }
                performSearch(""); // Show all donors initially
            }
            @Override public void onCancelled(DatabaseError error) {}
        });

        // 2. Search logic
        etSearchQuery.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                performSearch(s.toString());
            }
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {}
            public void afterTextChanged(Editable s) {}
        });

        // 3. Click to Call/WhatsApp
        searchResultList.setOnItemClickListener((parent, view, position, id) -> {
            String selectedString = displayList.get(position);
            for (DonorModel donor : allDonorsList) {
                if (donor.name != null && selectedString.contains(donor.name)) {
                    showContactOptions(donor);
                    break;
                }
            }
        });
    }

    private void performSearch(String query) {
        displayList.clear();
        String q = query.toLowerCase().trim();

        for (DonorModel d : allDonorsList) {
            // MATCHING LOGIC
            String blood = (d.bloodGroup != null) ? d.bloodGroup.toLowerCase() : "";
            String city = (d.city != null) ? d.city.toLowerCase() : "";
            String name = (d.name != null) ? d.name : "Unknown";

            if (q.isEmpty() || blood.contains(q) || city.contains(q)) {
                // THIS LINE DISPLAYS THE DETAILS
                displayList.add("NAME: " + name + "\nBLOOD: " + d.bloodGroup + "\nCITY: " + d.city);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void showContactOptions(DonorModel donor) {
        String[] options = {"Call " + donor.name, "WhatsApp"};
        new AlertDialog.Builder(this).setTitle("Contact Donor")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + donor.phone)));
                    } else {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=" + donor.phone)));
                    }
                }).show();
    }
}