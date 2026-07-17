package com.example.blood_donorapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        // 1. FIND BLOOD: Leads to the Menu (Search/Emergency)
        Button btnFind = findViewById(R.id.btnFindBlood);
        btnFind.setOnClickListener(v -> {
            startActivity(new Intent(AuthActivity.this, MenuActivity.class));
        });

        // 2. DONATE BLOOD: Leads directly to Registration Form
        Button btnDonate = findViewById(R.id.btnDonateBlood);
        btnDonate.setOnClickListener(v -> {
            startActivity(new Intent(AuthActivity.this, RegisterActivity.class));
        });

        // 3. HOME BUTTON
        Button btnHome = findViewById(R.id.btnBackToHome);
        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(AuthActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
}