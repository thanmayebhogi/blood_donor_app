package com.example.blood_donorapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {    @Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_menu);

    // 1. To Search (With Details)
    findViewById(R.id.btnSearchOption).setOnClickListener(v ->
            startActivity(new Intent(this, SearchActivity.class)));

    // 2. To Emergency Requests
    findViewById(R.id.btnEmergencyOption).setOnClickListener(v ->
            startActivity(new Intent(this, EmergencyActivity.class)));

    // 3. To Register Form
    findViewById(R.id.btnRegisterOption).setOnClickListener(v ->
            startActivity(new Intent(this, RegisterActivity.class)));

    // 4. Back to Home
    findViewById(R.id.btnHomeFromMenu).setOnClickListener(v ->
            startActivity(new Intent(this, MainActivity.class)));
}
}