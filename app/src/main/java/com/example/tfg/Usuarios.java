package com.example.tfg;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class Usuarios extends AppCompatActivity {
    private EditText editTextCorreo;
    private TextView textViewNombre, textViewTelefono, textViewDireccion;
    private Button btnBuscar;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);

        db = FirebaseFirestore.getInstance();

        editTextCorreo = findViewById(R.id.editTextCorreo);
        textViewNombre = findViewById(R.id.textViewNombre);
        textViewTelefono = findViewById(R.id.textViewTelefono);
        textViewDireccion = findViewById(R.id.textViewDireccion);
        btnBuscar = findViewById(R.id.buscar);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarUsuario();
            }
        });
    }

    private void buscarUsuario() {
        String correo = editTextCorreo.getText().toString().trim();

        db.collection("usuarios")
                .whereEqualTo("emailUsuario", correo)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            if (querySnapshot != null && !querySnapshot.isEmpty()) {
                                DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                                String nombre = document.getString("nombre");
                                String telefono = document.getString("telefono");
                                String direccion = document.getString("direccion");

                                textViewNombre.setText("Nombre: " + nombre);
                                textViewTelefono.setText("Teléfono: " + telefono);
                                textViewDireccion.setText("Dirección: " + direccion);
                            } else {
                                Toast.makeText(Usuarios.this, "No se encontró el usuario", Toast.LENGTH_SHORT).show();
                                textViewNombre.setText("Nombre:");
                                textViewTelefono.setText("Teléfono:");
                                textViewDireccion.setText("Dirección:");
                            }
                        } else {
                            Toast.makeText(Usuarios.this, "Error al buscar el usuario", Toast.LENGTH_SHORT).show();
                            textViewNombre.setText("Nombre:");
                            textViewTelefono.setText("Teléfono:");
                            textViewDireccion.setText("Dirección:");
                        }
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_usuarios,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cocina:
                Intent intent = new Intent(Usuarios.this, Cocina.class);
                startActivity(intent);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
