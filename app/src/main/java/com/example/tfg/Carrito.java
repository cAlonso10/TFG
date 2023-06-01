package com.example.tfg;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
    String SECRET_KEY="sk_test_51NDlLEKickMEKTkfPlbRqIJ74L9WluDjvPobpMwJyFWOahsDNQNqncfyzcgmzFjjodJO6da8y5xy56bWfWvwwd4h00chndM3pP";
    String PUBLISH_KEY="pk_test_51NDlLEKickMEKTkfmGCihEmv1Dpfc91vh7Il5dORzzxaPQnw9ir5WOMtR1tpJ3pxe5c2yDNKI6O86esdg8EYAWDW00Dv8xwo36";
    PaymentSheet paymentSheet;

    String customerID;
    String EphericalKey;
    String ClientSecret;
    String orderId;
    Pedido pedido;

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
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        emailUsuario = mAuth.getCurrentUser().getEmail();

        // Get the selected items from the previous activity
        Intent intent = getIntent();
        mSelectedItems = intent.getParcelableArrayListExtra("selectedItems");

        if (mCartItems == null) {
            mCartItems = new ArrayList<>();
        }
        // Add the selected items to the cart
        if (mSelectedItems != null) {
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
                if(mTotalPrice > 0){
                // Create an order
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                String timestamp = dateFormat.format(new Date());
                orderId = timestamp + String.format("%02d", new Random().nextInt(100));
                String status = "CREADO";
                List<CartItem> items = mCartItems;
                double totalPrice = mTotalPrice;
                //Create an Order
                pedido = new Pedido(orderId,emailUsuario, status, items, totalPrice);
                // Start the payment activity:
                PaymentFlow();
                }else{
                    Toast.makeText(Carrito.this, "Añade un producto al Carrito", Toast.LENGTH_LONG).show();
                    onBackPressed();
                }

            }
        });
        PaymentConfiguration.init(this, PUBLISH_KEY);

        paymentSheet = new PaymentSheet(this,paymentSheetResult -> {

            onPaymentResult(paymentSheetResult);
        });




        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://api.stripe.com/v1/customers",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object = new JSONObject(response);
                            customerID=object.getString("id");
                            Log.d(TAG, "Customer ID: " + customerID);
                            //Toast.makeText(Carrito.this, customerID, Toast.LENGTH_LONG).show();
                            getEphericalKey(customerID);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization","Bearer "+SECRET_KEY);
                return header;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Carrito.this);
        requestQueue.add(stringRequest);
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
    private void onPaymentResult(PaymentSheetResult paymentSheetResult) {

        if(paymentSheetResult instanceof PaymentSheetResult.Completed) {
            Toast.makeText(this, "payment success" , Toast.LENGTH_SHORT).show();
            //Send order to Firestore
            DocumentReference docRef = db.collection("pedidos").document(orderId);
            docRef.set(pedido);
            //Clear cart and go to Orders
            mCartItems.clear();
            Intent intent = new Intent(Carrito.this, PedidosUsuario.class);
            startActivity(intent);
        }

    }

    private void getEphericalKey(String customerID) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://api.stripe.com/v1/ephemeral_keys",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object = new JSONObject(response);
                            EphericalKey=object.getString("id");
                            Log.d(TAG, "EphericalKey" + EphericalKey);
                            //Toast.makeText(Carrito.this, EphericalKey, Toast.LENGTH_LONG).show();
                            getClientSecret(customerID,EphericalKey);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization","Bearer "+SECRET_KEY);
                header.put("Stripe-Version","2022-11-15");
                return header;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("customer",customerID);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Carrito.this);
        requestQueue.add(stringRequest);
    }

    private void getClientSecret(String customerID, String ephericalKey) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://api.stripe.com/v1/payment_intents",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object = new JSONObject(response);
                            ClientSecret=object.getString("client_secret");

                            //Toast.makeText(Carrito.this, "", Toast.LENGTH_LONG).show();


                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization","Bearer "+SECRET_KEY);
                return header;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("customer",customerID);
                params.put("amount", String.valueOf((int) (mTotalPrice * 100)));
                params.put("currency","eur");
                params.put("automatic_payment_methods[enabled]","true");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Carrito.this);
        requestQueue.add(stringRequest);

    }

    private void PaymentFlow() {

        paymentSheet.presentWithPaymentIntent(
                ClientSecret,new PaymentSheet.Configuration("RestoApp"
                        ,new PaymentSheet.CustomerConfiguration(
                        customerID,
                        EphericalKey
                ))
        );

    }
}