package com.example.tfg;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CartaListar extends AppCompatActivity {
        ListView mFoodList;

        private List<FoodItem> mFoodItems = new ArrayList<>();
        private FoodItemAdapter mAdapter;
        FirebaseFirestore db;
        private FirebaseAuth mAuth;
        String emailUsuario;
        private String CategoryName;

        TextView loadingText;

        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_carta, menu);
            return true;
        }
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.action_cart);
        menuItem.setVisible(false);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_cart) {
            Intent intent = new Intent(CartaListar.this, Carrito.class);
            intent.putParcelableArrayListExtra("selectedItems", mAdapter.getSelectedItems());
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        ArrayList<FoodItem> selectedItems = mAdapter.getSelectedItems();
        Intent backIntent = new Intent();
        backIntent.putParcelableArrayListExtra("selectedItems", selectedItems);
        setResult(RESULT_OK, backIntent);
        super.onBackPressed();
    }

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_carta_2);
            FoodItemAdapter foodItemAdapter = new FoodItemAdapter(this, new ArrayList<FoodItem>());
            ArrayList<FoodItem> selectedItems = foodItemAdapter.getSelectedItems();
            db = FirebaseFirestore.getInstance();
            loadingText = findViewById(R.id.loading_text);
            loadingText.setVisibility(View.VISIBLE);
            mFoodList = findViewById(R.id.listview);
            mAdapter = new FoodItemAdapter(this, mFoodItems);
            mFoodList.setAdapter(mAdapter);
            mAuth = FirebaseAuth.getInstance();
            emailUsuario = mAuth.getCurrentUser().getEmail();

            CategoryName = getIntent().getExtras().get("categoría").toString();
            String capCategoryName = CategoryName.substring(0, 1).toUpperCase() + CategoryName.substring(1);
            getSupportActionBar().setTitle(capCategoryName);

            CollectionReference foodItemsRef = db.collection("comida").document(CategoryName).collection("foodItems");
            ListenerRegistration listenerRegistration = foodItemsRef.addSnapshotListener((value, error) -> {
                if (error != null) {
                    // Handle errors
                    return;
                }
                mFoodItems.clear(); // Clear the existing food items
                if (value != null && !value.isEmpty()) {
                    for (DocumentSnapshot document : value.getDocuments()) {
                        FoodItem foodItem = document.toObject(FoodItem.class);
                        if (foodItem != null) {
                            mFoodItems.add(foodItem);

                        }
                    }
                }
            });

            mAdapter.notifyDataSetChanged();
            loadingText.setVisibility(View.GONE);

            // Handle other category names
            if (CategoryName.equals("entrantes")) {
                foodItemsRef = db.collection("comida").document("entrantes").collection("foodItems");
                listenerRegistration.remove(); // Remove the previous listener
                listenerRegistration = foodItemsRef.addSnapshotListener((value, error) -> {
                    if (error != null) {
                        // Handle errors
                        return;
                    }
                    mFoodItems.clear(); // Clear the existing food items
                    if (value != null && !value.isEmpty()) {
                        for (DocumentSnapshot document : value.getDocuments()) {
                            FoodItem foodItem = document.toObject(FoodItem.class);
                            if (foodItem != null) {
                                mFoodItems.add(foodItem);

                            }
                        }
                    }
                });
            }else if (CategoryName.equals("bebidas")) {
                foodItemsRef = db.collection("comida").document("bebidas").collection("foodItems");
                listenerRegistration.remove(); // Remove the previous listener
                listenerRegistration = foodItemsRef.addSnapshotListener((value, error) -> {
                    if (error != null) {
                        // Handle errors
                        return;
                    }
                    mFoodItems.clear(); // Clear the existing food items
                    if (value != null && !value.isEmpty()) {
                        for (DocumentSnapshot document : value.getDocuments()) {
                            FoodItem foodItem = document.toObject(FoodItem.class);
                            if (foodItem != null) {
                                mFoodItems.add(foodItem);

                            }
                        }
                    }
                });
            } else if (CategoryName.equals("pasta")) {

                foodItemsRef = db.collection("comida").document("pasta").collection("foodItems");
                listenerRegistration.remove(); // Remove the previous listener
                listenerRegistration = foodItemsRef.addSnapshotListener((value, error) -> {
                    if (error != null) {
                        // Handle errors
                        return;
                    }
                    mFoodItems.clear(); // Clear the existing food items
                    if (value != null && !value.isEmpty()) {
                        for (DocumentSnapshot document : value.getDocuments()) {
                            FoodItem foodItem = document.toObject(FoodItem.class);
                            if (foodItem != null) {
                                mFoodItems.add(foodItem);

                            }
                        }
                    }
                });
            } else if (CategoryName.equals("pizza")) {
                foodItemsRef = db.collection("comida").document("pizza").collection("foodItems");
                listenerRegistration.remove(); // Remove the previous listener
                listenerRegistration = foodItemsRef.addSnapshotListener((value, error) -> {
                    if (error != null) {
                        // Handle errors
                        return;
                    }
                    mFoodItems.clear(); // Clear the existing food items
                    if (value != null && !value.isEmpty()) {
                        for (DocumentSnapshot document : value.getDocuments()) {
                            FoodItem foodItem = document.toObject(FoodItem.class);
                            if (foodItem != null) {
                                mFoodItems.add(foodItem);

                            }
                        }
                    }
                });
            } else if (CategoryName.equals("postres")) {
                foodItemsRef = db.collection("comida").document("postres").collection("foodItems");
                listenerRegistration.remove(); // Remove the previous listener
                listenerRegistration = foodItemsRef.addSnapshotListener((value, error) -> {
                    if (error != null) {
                        // Handle errors
                        return;
                    }
                    mFoodItems.clear(); // Clear the existing food items
                    if (value != null && !value.isEmpty()) {
                        for (DocumentSnapshot document : value.getDocuments()) {
                            FoodItem foodItem = document.toObject(FoodItem.class);
                            if (foodItem != null) {
                                mFoodItems.add(foodItem);

                            }
                        }
                    }
                });
            } else if (CategoryName.equals("principales")) {
                foodItemsRef = db.collection("comida").document("principales").collection("foodItems");
                listenerRegistration.remove(); // Remove the previous listener
                listenerRegistration = foodItemsRef.addSnapshotListener((value, error) -> {
                    if (error != null) {
                        // Handle errors
                        return;
                    }
                    mFoodItems.clear(); // Clear the existing food items
                    if (value != null && !value.isEmpty()) {
                        for (DocumentSnapshot document : value.getDocuments()) {
                            FoodItem foodItem = document.toObject(FoodItem.class);
                            if (foodItem != null) {
                                mFoodItems.add(foodItem);

                            }
                        }
                    }
                });
            }


        Button checkoutButton = findViewById(R.id.checkout_button);
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("usuarios")
                        .whereEqualTo("emailUsuario", emailUsuario)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    QuerySnapshot querySnapshot = task.getResult();
                                    if (!querySnapshot.isEmpty()) {
                                        DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                                        if (document.contains("direccion") && document.contains("telefono")) {
                                            String direccion = document.getString("direccion");
                                            String telefono = document.getString("telefono");
                                            if (!TextUtils.isEmpty(direccion) && !TextUtils.isEmpty(telefono)) {
                                                onBackPressed();
                                            } else {
                                                Toast.makeText(CartaListar.this, "Complete su información", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(CartaListar.this, Perfil.class);
                                                startActivity(intent);
                                            }
                                        } else {
                                            Toast.makeText(CartaListar.this, "Complete su información", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(CartaListar.this, Perfil.class);
                                            startActivity(intent);
                                        }
                                    } else {
                                        Toast.makeText(CartaListar.this, "Complete su información", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(CartaListar.this, Perfil.class);
                                        startActivity(intent);
                                    }
                                }
                            }
                        });
            }
        });




    }
        public static class FoodItemAdapter extends BaseAdapter {
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
                        if (!mSelectedItems.contains(foodItem)) {
                            // Item not present in the list, add it with a quantity of 1
                            foodItem.setQuantity(1);
                            mSelectedItems.add(foodItem);
                        } else {
                            // Item already present, increase the quantity
                            foodItem.setQuantity(quantity);
                        }

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
                            if (quantity == 0) {
                                // Quantity reached 0, remove the item from the list
                                mSelectedItems.remove(foodItem);
                            }
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
