package com.example.tfg;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

    public class CartaListar extends AppCompatActivity {
        ListView mFoodList;

        private List<FoodItem> mFoodItems = new ArrayList<>();
        private FoodItemAdapter mAdapter;
        FirebaseFirestore db;

        private String CategoryName;

        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_carta, menu);
            return true;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_carta_2);
            FoodItemAdapter foodItemAdapter = new FoodItemAdapter(this, new ArrayList<FoodItem>());
            ArrayList<FoodItem> selectedItems = foodItemAdapter.getSelectedItems();
            db = FirebaseFirestore.getInstance();

            mFoodList = findViewById(R.id.listview);
            mAdapter = new FoodItemAdapter(this, mFoodItems);
            mFoodList.setAdapter(mAdapter);

            CategoryName = getIntent().getExtras().get("categoría").toString();
            if (CategoryName.equals("entrantes")) {
                mFoodItems.add(new FoodItem("Pizza de pepperoni", "Pizza de pepperoni con queso", 10.99));
                mFoodItems.add(new FoodItem("Pizza margarita", "Pizza con tomate, albahaca y queso", 11.99));
                mFoodItems.add(new FoodItem("Pizza hawaiana", "Pizza con jamón, piña y queso", 12.99));

                mFoodItems.add(new FoodItem("Spaghetti bolognese", "Spaghetti con salsa de carne", 9.99));
                mFoodItems.add(new FoodItem("Fettuccine Alfredo", "Fettuccine con salsa de queso parmesano", 10.99));
                mFoodItems.add(new FoodItem("Penne arrabiata", "Penne con salsa picante de tomate", 11.99));

                mFoodItems.add(new FoodItem("Patatas Bravas", "Patatas con salsa Brava", 8.99));
                mFoodItems.add(new FoodItem("Calamares", "Calamares fritos", 6.99));
                mFoodItems.add(new FoodItem("Nachos con queso", "Nachos con queso y pico de gallo", 9.99));

                mFoodItems.add(new FoodItem("CocaCola", "Lata 33cl", 1.99));
                mFoodItems.add(new FoodItem("Cerveza Lata", "Lata 33cl", 1.99));
                mFoodItems.add(new FoodItem("Copa Vino", "Vino Tinto Reserva", 2.99));

                mFoodItems.add(new FoodItem("Arroz con Bogavante", "Arroza con Bovagante y verduras", 22.99));
                mFoodItems.add(new FoodItem("Paella Valenciana", "Paella con Carne y Alubias", 16.99));
                mFoodItems.add(new FoodItem("Entrecot con patatas", "Entrecot de ternera Angus", 18.99));

                mFoodItems.add(new FoodItem("Tarta Chocolate", "Porcion tarta tres chocolates", 7.99));
                mFoodItems.add(new FoodItem("Arroz con Leche", "Vaso de Arroz con Leche y canela casero", 4.99));
                mFoodItems.add(new FoodItem("Cheescake", "Porción de tarta de queso casera", 7.99));
            } else {
                mFoodItems.add(new FoodItem("Not Found", "Not Found", 0));
            }
            mAdapter.notifyDataSetChanged();

            Button checkoutButton = findViewById(R.id.checkout_button);
            checkoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    ArrayList<FoodItem> selectedItems = mAdapter.getSelectedItems();
                    Log.d(TAG, "Selected items: " + selectedItems.toString());
                    List<Map<String, Object>> products = new ArrayList<>();
                    for (FoodItem selectedItem : selectedItems) {
                        if (selectedItem.getQuantity() > 0) {
                            Map<String, Object> product = new HashMap<>();
                            product.put("nombre", selectedItem.getName());
                            product.put("cantidad", selectedItem.getQuantity());
                            products.add(product);

                        }
                    }
                    Map<String, Object> data = new HashMap<>();
                    data.put("productos", products);
                    data.put("estado", "En espera");
                    db.collection("pedidos").add(data);
                    selectedItems.clear();
                }
            });
        }



        public class FoodItemAdapter extends BaseAdapter {
            private Context mContext;
            private List<FoodItem> mFoodItems;
            private List<FoodItem> mSelectedItems = new ArrayList<>();

            public FoodItemAdapter(Context context, List<FoodItem> foodItems) {
                mContext = context;
                mFoodItems = foodItems;
            }

            @Override
            public int getCount() {
                return mFoodItems.size();
            }

            @Override
            public Object getItem(int position) {
                return mFoodItems.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View view = convertView;
                if (view == null) {
                    LayoutInflater inflater = LayoutInflater.from(mContext);
                    view = inflater.inflate(R.layout.activity_carta_3, null);
                }

                TextView dishNameTextView = (TextView) view.findViewById(R.id.dish_name);
                TextView dishDescriptionTextView = (TextView) view.findViewById(R.id.dish_description);
                TextView dishPriceTextView = (TextView) view.findViewById(R.id.dish_price);
                TextView quantityTextView = (TextView) view.findViewById(R.id.quantity);
                Button plusButton = (Button) view.findViewById(R.id.plus_button);
                Button minusButton = (Button) view.findViewById(R.id.minus_button);

                final FoodItem foodItem = (FoodItem) getItem(position);
                dishNameTextView.setText(foodItem.getName());
                dishDescriptionTextView.setText(foodItem.getDescription());
                dishPriceTextView.setText("€" + foodItem.getPrice());
                quantityTextView.setText(String.valueOf(foodItem.getQuantity()));

                plusButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int quantity = foodItem.getQuantity();
                        quantity++;
                        foodItem.setQuantity(quantity);
                        mSelectedItems.add(foodItem);
                        Log.d(TAG, "items" + getSelectedItems().toString() );
                        notifyDataSetChanged();
                    }
                });

                minusButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int quantity = foodItem.getQuantity();
                        if (quantity > 0) {
                            quantity--;
                            foodItem.setQuantity(quantity);
                            notifyDataSetChanged();
                            Log.d(TAG, "items" + getSelectedItems().toString() );
                        }
                    }
                });

                return view;
            }

            public ArrayList<FoodItem> getSelectedItems() {
                ArrayList<FoodItem> selectedItems = new ArrayList<>();
                for (FoodItem foodItem : mSelectedItems) {
                    if (foodItem.getQuantity() > 0) {
                        selectedItems.add(foodItem);
                    }
                }
                return selectedItems;

            }
        }
    }

/*
                db.collection("comida")
                        .whereEqualTo(FieldPath.documentId(), CategoryName)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Map<String, Object> data = documentSnapshot.getData();
                            String foodName = documentSnapshot.getId();
                            String description = (String) data.get("Descripcion");
                            Object priceObj = data.get("Precio");
                            double price = priceObj != null ? (double) priceObj : 0.0;
                            mFoodItems.add(new FoodItem(foodName, description, price));
                            }
                            mAdapter.notifyDataSetChanged();
                            }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CartaListar.this, "Error al obtener los datos", Toast.LENGTH_SHORT).show();
                            }
                            });*/
