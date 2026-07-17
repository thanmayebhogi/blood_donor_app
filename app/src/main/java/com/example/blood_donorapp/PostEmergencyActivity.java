package com.example.blood_donorapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.FirebaseDatabase;

public class PostEmergencyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_emergency);

        EditText blood = findViewById(R.id.etUrgentBlood);
        EditText hosp = findViewById(R.id.etHospital);
        EditText phone = findViewById(R.id.etUrgentPhone);
        Button submit = findViewById(R.id.btnSubmitEmergency);
        Button cancel = findViewById(R.id.btnBackFromPost);

        submit.setOnClickListener(v -> {
            String b = blood.getText().toString().trim();
            String h = hosp.getText().toString().trim();
            String p = phone.getText().toString().trim();

            if(!b.isEmpty() && !h.isEmpty() && !p.isEmpty()) {
                String msg = "URGENT: " + b + "\nHospital: " + h + "\nContact: " + p;

                // Saving to "Emergency" folder in Firebase
                FirebaseDatabase.getInstance().getReference("Emergency")
                        .push()
                        .setValue(msg)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(this, "Emergency Posted!", Toast.LENGTH_SHORT).show();
                            finish(); // Returns to Emergency List
                        });
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
        });

        cancel.setOnClickListener(v -> finish());
    }
}