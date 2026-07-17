package com.example.blood_donorapp;

import android.os.Bundle;import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText etN = findViewById(R.id.etName), etB = findViewById(R.id.etBlood),
                etC = findViewById(R.id.etCity), etP = findViewById(R.id.etPhone),
                etD = findViewById(R.id.etLastDonation);
        Button btnSave = findViewById(R.id.btnSaveDonor);

        btnSave.setOnClickListener(v -> {
            String n = etN.getText().toString().trim();
            String b = etB.getText().toString().trim(); // e.g. "O+"
            String c = etC.getText().toString().trim(); // e.g. "Vizag"
            String p = etP.getText().toString().trim();
            String d = etD.getText().toString().trim();

            if (!n.isEmpty() && !b.isEmpty() && !p.isEmpty()) {
                DatabaseReference db = FirebaseDatabase.getInstance().getReference("Donors");
                String id = db.push().getKey();

                // Use the Model to save
                DonorModel donor = new DonorModel(id, n, b, c, p, d);

                db.child(id).setValue(donor).addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Donor Registered Successfully!", Toast.LENGTH_SHORT).show();
                    finish(); // Close page
                });
            } else {
                Toast.makeText(this, "Please fill Name, Blood, and Phone", Toast.LENGTH_SHORT).show();
            }
        });
    }
}