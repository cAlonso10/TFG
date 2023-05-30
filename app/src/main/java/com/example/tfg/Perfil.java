package com.example.tfg;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Perfil extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    String emailUsuario,nombre,telefono;
    Button buttonCambiarTelefono,buttonCambiarDireccion;

    TextView textViewNombre,textViewEmail,textViewTelefono,textViewDireccion;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        mAuth = FirebaseAuth.getInstance();
        emailUsuario = mAuth.getCurrentUser().getEmail();
        nombre = mAuth.getCurrentUser().getDisplayName();
        actualizarUI();

         textViewNombre = findViewById(R.id.nombre);
         textViewEmail = findViewById(R.id.correo);
         textViewTelefono = findViewById(R.id.telefono);
         textViewDireccion = findViewById(R.id.direccion);
        textViewNombre.setText("Nombre: " + nombre);
        textViewEmail.setText("Email de usuario: " + emailUsuario);

        buttonCambiarTelefono = findViewById(R.id.cambiarTelefono);
        buttonCambiarTelefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarTelefono();
            }
        });
        buttonCambiarDireccion = findViewById(R.id.cambiarDireccion);
        buttonCambiarDireccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarDireccion();
            }
        });






    }

    private void actualizarUI() {
        db = FirebaseFirestore.getInstance();
        db.collection("usuarios")
                .whereEqualTo("emailUsuario", emailUsuario)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            if (querySnapshot != null && !querySnapshot.isEmpty()) {
                                DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                                String telefono = documentSnapshot.getString("telefono");
                                String direccion = documentSnapshot.getString("direccion");


                                textViewTelefono.setText("Teléfono: " + telefono);
                                textViewDireccion.setText("Dirección: " + direccion);
                            } else {

                                textViewTelefono.setText("Teléfono: No hay datos");
                                textViewDireccion.setText("Dirección: No hay datos");
                            }
                        } else {

                            textViewTelefono.setText("Teléfono: Error");
                            textViewDireccion.setText("Dirección: Error");
                        }
                    }
                });
    }

    private void cambiarTelefono() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cambiar teléfono");
        final EditText editTextTelefono = new EditText(this);
        editTextTelefono.setHint("Ingrese el nuevo número de teléfono");
        builder.setView(editTextTelefono);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String nuevoTelefono = editTextTelefono.getText().toString();
                cambiarTelefonoUsuario(nuevoTelefono);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }



    private void cambiarTelefonoUsuario(final String nuevoTelefono) {
        db = FirebaseFirestore.getInstance();
        db.collection("usuarios")
                .whereEqualTo("emailUsuario", emailUsuario)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            if (querySnapshot != null && !querySnapshot.isEmpty()) {
                                DocumentReference documentReference = querySnapshot.getDocuments().get(0).getReference();
                                documentReference.update("telefono", nuevoTelefono)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(Perfil.this, "Teléfono actualizado correctamente", Toast.LENGTH_SHORT).show();
                                                // Actualizar la UI después de cambiar el teléfono
                                                actualizarUI();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(Perfil.this, "Error al actualizar el teléfono", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            } else {
                                Map<String, Object> usuario = new HashMap<>();
                                usuario.put("emailUsuario", emailUsuario);
                                usuario.put("nombre", nombre);
                                usuario.put("telefono", nuevoTelefono);
                                usuario.put("direccion", "No hay datos");
                                db.collection("usuarios")
                                        .add(usuario)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Toast.makeText(Perfil.this, "Documento creado correctamente", Toast.LENGTH_SHORT).show();
                                                actualizarUI();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(Perfil.this, "Error al crear el documento", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        } else {
                            Toast.makeText(Perfil.this, "Error al buscar el documento", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void cambiarDireccion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cambiar dirección");
        final EditText editTextDireccion = new EditText(this);
        editTextDireccion.setHint("Ingrese la nueva dirección");
        builder.setView(editTextDireccion);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String nuevaDireccion = editTextDireccion.getText().toString();
                cambiarDireccionUsuario(nuevaDireccion);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
    private void cambiarDireccionUsuario(final String nuevaDireccion) {
        db = FirebaseFirestore.getInstance();
        db.collection("usuarios")
                .whereEqualTo("emailUsuario", emailUsuario)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            if (querySnapshot != null && !querySnapshot.isEmpty()) {
                                DocumentReference documentReference = querySnapshot.getDocuments().get(0).getReference();
                                documentReference.update("direccion", nuevaDireccion)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(Perfil.this, "Dirección actualizada correctamente", Toast.LENGTH_SHORT).show();
                                                // Actualizar la UI después de cambiar la dirección
                                                actualizarUI();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(Perfil.this, "Error al actualizar la dirección", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            } else {
                                Map<String, Object> usuario = new HashMap<>();
                                usuario.put("emailUsuario", emailUsuario);
                                usuario.put("nombre", nombre);
                                usuario.put("telefono", "No hay datos");
                                usuario.put("direccion", nuevaDireccion);
                                db.collection("usuarios")
                                        .add(usuario)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Toast.makeText(Perfil.this, "Documento creado correctamente", Toast.LENGTH_SHORT).show();
                                                actualizarUI();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(Perfil.this, "Error al crear el documento", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        } else {
                            Toast.makeText(Perfil.this, "Error al buscar el documento", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}
