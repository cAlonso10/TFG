package com.example.tfg;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
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

public class PedidosUsuario extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    String emailUsuario;
    ListView listViewPedidos;
    List<String> listaPedidos = new ArrayList<>();

    List<String> listaIdPedidos = new ArrayList<>();
    ArrayAdapter<Map<String, Object>> mAdapterPedidos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidosusuario);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(createCustomTitle());

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        emailUsuario = mAuth.getCurrentUser().getEmail();
        listViewPedidos = findViewById(R.id.ListView);

        actualizarUI();

        listViewPedidos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String pedidoId = listaIdPedidos.get(position);

                db.collection("pedidos")
                        .document(pedidoId)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    Map<String, Object> pedido = documentSnapshot.getData();

                                    // Obtener los datos del pedido
                                    List<Map<String, Object>> productos = (List<Map<String, Object>>) pedido.get("productos");
                                    String estado = (String) pedido.get("estado");

                                    // Crear el mensaje del AlertDialog c
                                    StringBuilder mensaje = new StringBuilder();
                                    mensaje.append("Estado: ").append(estado).append("\n\n");

                                    for (Map<String, Object> producto : productos) {
                                        String nombre = (String) producto.get("nombre");
                                        int cantidad = ((Long) producto.get("cantidad")).intValue();
                                        mensaje.append(nombre).append(": ").append(cantidad).append("\n");
                                    }

                                    // Mostrar el AlertDialog y su mensaje
                                    AlertDialog.Builder builder = new AlertDialog.Builder(PedidosUsuario.this);
                                    builder.setTitle("Detalles del Pedido" + (position + 1))
                                            .setMessage(mensaje.toString())
                                            .setPositiveButton("Aceptar", null)
                                            .show();
                                }
                            }
                        });
            }
        });
    }

    private void actualizarUI() {
        db.collection("pedidos")
                .whereEqualTo("emailUsuario",emailUsuario)
                .whereIn("estado", Arrays.asList("En espera", "Cocinando"))
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }

                        listaPedidos.clear();
                        listaIdPedidos.clear();
                        List<Map<String, Object>> listaPedidosMap = new ArrayList<>();



                        for (QueryDocumentSnapshot doc : value) {
                            listaIdPedidos.add(doc.getId());
                            listaPedidos.add(doc.getString("estado"));
                            Map<String, Object> pedido = new HashMap<>();
                            //  pedido.put("Producto", doc.getString("Producto"));
                            //pedido.put("Cantidad", doc.getLong("Cantidad"));
                            pedido.put("estado", doc.getString("estado"));
                            listaPedidosMap.add(pedido);
                        }
                        if (listaPedidosMap.size() == 0) {
                            listViewPedidos.setAdapter(null);
                        } else {
                            mAdapterPedidos = new ArrayAdapter<Map<String, Object>>(PedidosUsuario.this, R.layout.item_pedidousuario, listaPedidosMap) {
                                @NonNull
                                @Override
                                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                                    if (convertView == null) {
                                        convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_pedidousuario, parent, false);
                                    }
                                    TextView PedidoTextView = convertView.findViewById(R.id.PedidoTextView);

                                    TextView estadoTextView = convertView.findViewById(R.id.EstadoTextView);

                                    Map<String, Object> pedido = getItem(position);
                                    PedidoTextView.setText("Pedido");
                                    estadoTextView.setText(pedido.get("estado").toString());

                                    return convertView;
                                }
                            };
                            listViewPedidos.setAdapter(mAdapterPedidos);
                        }

                    }
                });

    }





    private TextView createCustomTitle() {
        TextView textView = new TextView(this);
        textView.setText("Pedidos");
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(20);
        textView.setTextColor(Color.BLACK);
        textView.setPadding(0, 0, 0, 0);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        return textView;
    }
}
