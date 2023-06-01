package com.example.tfg;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Carta extends AppCompatActivity {

    ImageView imgEntrantes, imgBebidas, imgPizza, imgPasta, imgPrincipales, imgPostres;
    private FirebaseAuth mAuth;
    String emailUsuario;
    ArrayList<FoodItem> selectedItems;
    private ActivityResultLauncher<Intent> cartaListarLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_carta);

    cartaListarLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        ArrayList<FoodItem> updatedSelectedItems = data.getParcelableArrayListExtra("selectedItems");
                        if (updatedSelectedItems != null) {
                            // Add the updated selected items to the existing selected items
                            if (selectedItems == null) {
                                selectedItems = new ArrayList<>();
                            }
                            selectedItems.addAll(updatedSelectedItems);

                            // Handle the selected items as needed or store them for later use
                            // ...
                        }
                    }
                }
            }
    );

    // Get the app bar and set the title
    getSupportActionBar().setTitle("Carta");
    mAuth = FirebaseAuth.getInstance();
    emailUsuario = mAuth.getCurrentUser().getEmail();
    imgEntrantes = (ImageView) findViewById(R.id.imgEntrantes);
    imgBebidas = (ImageView) findViewById(R.id.imgBebidas);
    imgPizza = (ImageView) findViewById(R.id.imgPizza);
    imgPasta = (ImageView) findViewById(R.id.imgPasta);
    imgPrincipales = (ImageView) findViewById(R.id.imgPrincipales);
    imgPostres = (ImageView) findViewById(R.id.imgPostres);

    imgEntrantes.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Carta.this, CartaListar.class);
            intent.putExtra("categoría", "entrantes");
            cartaListarLauncher.launch(intent);
        }
    });
    imgBebidas.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Carta.this,CartaListar.class);
            intent.putExtra("categoría","bebidas");
            cartaListarLauncher.launch(intent);
        }
    });
    imgPizza.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Carta.this,CartaListar.class);
            intent.putExtra("categoría","pizza");
            cartaListarLauncher.launch(intent);
        }
    });
    imgPasta.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Carta.this,CartaListar.class);
            intent.putExtra("categoría","pasta");
            cartaListarLauncher.launch(intent);
        }
    });
    imgPrincipales.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Carta.this,CartaListar.class);
            intent.putExtra("categoría","principales");
            cartaListarLauncher.launch(intent);
        }
    });
    imgPostres.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Carta.this,CartaListar.class);
            intent.putExtra("categoría","postres");
            cartaListarLauncher.launch(intent);
        }
    });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_carta, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cart:
                goCart();
                return true;
            case R.id.action_profile:
                Intent intent = new Intent(this, Perfil.class);
                startActivity(intent);
                return true;
            case R.id.logout:
                logout();
                return true;
            case R.id.pedidos:
                goPedidos();
                return true;
            default:return super.onOptionsItemSelected(item);


        }

    }

    private void goCart() {
        Intent intent = new Intent(Carta.this, Carrito.class);
        intent.putParcelableArrayListExtra("selectedItems", selectedItems);

        cartaListarLauncher.launch(intent);
    }

    private void goPedidos() {
        Intent intent = new Intent(Carta.this, PedidosUsuario.class);
        startActivity(intent);
        finish();
    }

    private void logout() {
        List<String> emailsMesas = Arrays.asList("restaurante1@mesa.com", "restaurante2@mesa.com", "restaurante3@mesa.com");
        if (emailsMesas.contains(emailUsuario)){
            showPasswordDialog();
        } else {
            mAuth.signOut();
            goLogin();
        }
    }

    private void showPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ingrese la contraseña");

        final EditText passwordInput = new EditText(this);
        passwordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(passwordInput);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String password = passwordInput.getText().toString().trim();

                if (password.equals("54321")) {
                    mAuth.signOut();
                    goLogin();
                } else {
                    Toast.makeText(Carta.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }




    private void goLogin() {
        Intent intent = new Intent(Carta.this, Login.class);
        startActivity(intent);
        finish();
    }

}