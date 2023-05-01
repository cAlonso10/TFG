package com.example.tfg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button botonOut, test;
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        botonOut = findViewById(R.id.out);
        botonOut.setOnClickListener(view -> {
            logout();
        });

        test = findViewById(R.id.test);
        test.setOnClickListener(view -> {
            test();
        });

    }

    protected void test() {
        // Crear un nuevo pedido con estado "En espera"
        Map<String, Object> pedido = new HashMap<>();
        pedido.put("estado", "En espera");

        // Agregar los productos al pedido
        List<Map<String, Object>> productos = new ArrayList<>();

        Map<String, Object> producto1 = new HashMap<>();
        producto1.put("nombre", "Pollo");
        producto1.put("cantidad", 1);
        productos.add(producto1);

        Map<String, Object> producto2 = new HashMap<>();
        producto2.put("nombre", "Arroz");
        producto2.put("cantidad", 2);
        productos.add(producto2);

        Map<String, Object> producto3 = new HashMap<>();
        producto3.put("nombre", "Cerveza");
        producto3.put("cantidad", 3);
        productos.add(producto3);

        pedido.put("productos", productos);

        // Agregar el pedido a la colección "pedidos" con un ID aleatorio generado por Firestore
        db.collection("pedidos")
                .add(pedido)
                .addOnSuccessListener(documentReference -> {

                })
                .addOnFailureListener(e -> {

                });
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user == null){
            goLogin();
        }
    }

    private void logout() {
        mAuth.signOut();
        goLogin();
    }

    private void goLogin() {
        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);
        finish();
    }
    protected void test2() {
        Map<String, Object> pedido = new HashMap<>();
        pedido.put("estado", "En espera");

        // Crear la colección "pedidos" y agregar el documento "pedido001" con el objeto Pedido
        db.collection("pedidos").document("pedido001").set(pedido);

        // Crear un objeto Producto con los campos nombre y cantidad
        Map<String, Object> producto1 = new HashMap<>();
        producto1.put("nombre", "Pollo");
        producto1.put("cantidad", 1);

        Map<String, Object> producto2 = new HashMap<>();
        producto2.put("nombre", "Arroz");
        producto2.put("cantidad", 2);

        Map<String, Object> producto3 = new HashMap<>();
        producto3.put("nombre", "Papas");
        producto3.put("cantidad", 3);

        // Agregar los documentos de productos a la subcolección "productos" del documento "pedido001"
        db.collection("pedidos").document("pedido001").collection("productos").document("producto001").set(producto1);
        db.collection("pedidos").document("pedido001").collection("productos").document("producto002").set(producto2);
        db.collection("pedidos").document("pedido001").collection("productos").document("producto003").set(producto3);
        logout();
    }
}