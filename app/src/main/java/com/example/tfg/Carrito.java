package com.example.tfg;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class Carrito extends AppCompatActivity {
    ListView mCartList;

    private List<CartItem> mCartItems = new ArrayList<>();
    private CartItemAdapter mAdapter;
    private double mTotalPrice = 0.0;
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    String emailUsuario;
    private ArrayList<FoodItem> mSelectedItems;
    private ArrayList<FoodItem> mCurrentSelectedItems;
    Button paymentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);
        getSupportActionBar().setTitle("Carrito");

        mCartList = findViewById(R.id.list_view_carrito);
        TextView totalTextView = findViewById(R.id.text_view_total);
        mSelectedItems = new ArrayList<>();
        mCurrentSelectedItems = new ArrayList<>();
        paymentButton = findViewById(R.id.button_payment);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        emailUsuario = mAuth.getCurrentUser().getEmail();

        // Get the selected items from the previous activity
        Intent intent = getIntent();
        mSelectedItems = intent.getParcelableArrayListExtra("selectedItems");
        Log.d(TAG, "Cart selected items: " + mSelectedItems);

        if (mCartItems == null) {
            mCartItems = new ArrayList<>();
        }
        // Add the selected items to the cart
        if (mSelectedItems != null) {
            // Add the selected items to the cart
            for (FoodItem selectedItem : mSelectedItems) {
                if (selectedItem.getQuantity() > 0 && !isItemInCart(selectedItem)) {
                    CartItem cartItem = new CartItem(selectedItem.getName(), selectedItem.getPrice(), selectedItem.getQuantity());
                    mCartItems.add(cartItem);
                    mTotalPrice += (selectedItem.getPrice() * selectedItem.getQuantity());
                }
                totalTextView.setText("Total: €" + String.format("%.2f", mTotalPrice));
            }
        }

        mAdapter = new CartItemAdapter(this, mCartItems);
        mCartList.setAdapter(mAdapter);

        paymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an order
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                String timestamp = dateFormat.format(new Date());
                String orderId = timestamp + String.format("%02d", new Random().nextInt(100));
                String status = "CREADO";
                List<CartItem> items = mCartItems;
                double totalPrice = mTotalPrice;

                Pedido pedido = new Pedido(orderId, status, items, totalPrice);
                Log.d(TAG, "Pedido Creado" + pedido.toString());
                // Start the payment activity:
                //Finished payment activity:
                //if respuesta == OK
                if(1>0){
                    //Send Order to Firestore
                    DocumentReference docRef = db.collection("pedidos").document(orderId);
                    docRef.set(pedido);
                    //Clear cart and go back to Carta
                    mCartItems.clear();
                    Intent intent = new Intent(Carrito.this, Carta.class);
                    startActivity(intent);
                }else{
                    Log.d(TAG, "Error al pagar" + pedido.getOrderId());
                }
            }
        });

    }

    private boolean isItemInCart(FoodItem item) {
        for (CartItem cartItem : mCartItems) {
            if (cartItem.getName().equals(item.getName())) {
                return true;
            }
        }
        return false;
    }
    public class CartItemAdapter extends BaseAdapter {
        private Carrito mContext;
        private List<CartItem> mCartItems;
        private List<FoodItem> mSelectedCartItems = new ArrayList<>();
        TextView totalTextView = findViewById(R.id.text_view_total);

        public CartItemAdapter(Carrito context, List<CartItem> cartItems) {
            mContext = context;
            mCartItems = cartItems;
        }

        @Override
        public int getCount() {
            return mCartItems.size();
        }

        @Override
        public Object getItem(int position) {
            return mCartItems.get(position);
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
                view = inflater.inflate(R.layout.activity_carrito_item, null);
            }

            TextView itemNameTextView = view.findViewById(R.id.item_name);
            TextView itemPriceTextView = view.findViewById(R.id.item_price);
            TextView itemQuantityTextView = view.findViewById(R.id.item_quantity);
            Button plusButton = (Button) view.findViewById(R.id.plus_button);
            Button minusButton = (Button) view.findViewById(R.id.minus_button);

            CartItem cartItem = mCartItems.get(position);
            itemNameTextView.setText(cartItem.getName());
            itemPriceTextView.setText("€" + cartItem.getPrice());
            itemQuantityTextView.setText(String.valueOf(cartItem.getQuantity()));

            plusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cartItem.setQuantity(cartItem.getQuantity() + 1);
                    notifyDataSetChanged();
                    updateTotalPrice();
                }
            });

            minusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cartItem.getQuantity() > 0) {
                        cartItem.setQuantity(cartItem.getQuantity() - 1);
                        if (cartItem.getQuantity() == 0) {
                            mCartItems.remove(position);
                        }
                        notifyDataSetChanged();
                        updateTotalPrice();
                    }
                }
            });

            return view;

        }

        private void updateTotalPrice() {
            double totalPrice = 0;
            for (CartItem item : mCartItems) {
                totalPrice += item.getPrice() * item.getQuantity();
            }
            totalTextView.setText("Total: €" + String.format("%.2f", totalPrice));
        }
    }
}