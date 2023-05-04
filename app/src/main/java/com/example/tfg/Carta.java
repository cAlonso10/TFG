package com.example.tfg;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Carta extends AppCompatActivity {

    ImageView imgEntrantes, imgBebidas, imgPizza, imgPasta, imgPrincipales, imgPostres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_carta);


    // Get the app bar and set the title
    getSupportActionBar().setTitle("Carta");

    imgEntrantes = (ImageView) findViewById(R.id.imgEntrantes);
    imgBebidas = (ImageView) findViewById(R.id.imgBebidas);
    imgPizza = (ImageView) findViewById(R.id.imgPizza);
    imgPasta = (ImageView) findViewById(R.id.imgPasta);
    imgPrincipales = (ImageView) findViewById(R.id.imgPrincipales);
    imgPostres = (ImageView) findViewById(R.id.imgPostres);
    imgEntrantes.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Carta.this,CartaListar.class);
            intent.putExtra("categoría","entrantes");
            startActivity(intent);
        }
    });
    imgBebidas.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Carta.this,CartaListar.class);
            intent.putExtra("categoría","bebidas");
            startActivity(intent);
        }
    });
    imgPizza.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Carta.this,CartaListar.class);
            intent.putExtra("categoría","pizza");
            startActivity(intent);
        }
    });
    imgPasta.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Carta.this,CartaListar.class);
            intent.putExtra("categoría","pasta");
            startActivity(intent);
        }
    });
    imgPrincipales.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Carta.this,CartaListar.class);
            intent.putExtra("categoría","principales");
            startActivity(intent);
        }
    });
    imgPostres.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Carta.this,CartaListar.class);
            intent.putExtra("categoría","postres");
            startActivity(intent);
        }
    });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_carta, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_cart) {
            //Intent intent = new Intent(this, Carrito.class);
            //startActivity(intent);
            return true;
        } else if (id == R.id.action_profile) {
            //TODO:Handle profile page
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}