package com.example.tfg;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Cocina extends AppCompatActivity {

    Button botonOutC;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocina);

        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        botonOutC = findViewById(R.id.outC);
        botonOutC.setOnClickListener(view -> {
           logout();
        });

    }
    private void logout() {
        mAuth.signOut();
        goLogin();
    }

    private void goLogin() {
        Intent intent = new Intent(Cocina.this, Login.class);
        startActivity(intent);
        finish();
    }


    }


