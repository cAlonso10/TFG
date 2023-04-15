package com.example.tfg;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Cocina extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();
    }
}
