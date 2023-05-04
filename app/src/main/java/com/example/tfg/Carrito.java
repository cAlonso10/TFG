package com.example.tfg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/*public class Carrito extends AppCompatActivity {
    ListView mCartList;

    private List<CartItem> mCartItems = new ArrayList<>();
    private CartItemAdapter mAdapter;
    private double mTotalPrice = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        mCartList = findViewById(R.id.list_view_carrito);
        mAdapter = new CartItemAdapter(this, mCartItems);
        mCartList.setAdapter(mAdapter);

        // Get the selected items from the previous activity
        Intent intent = getIntent();
        CartaListar.FoodItemAdapter adapter = new CartaListar.FoodItemAdapter(this, new ArrayList<FoodItem>());
        ArrayList<FoodItem> selectedItems = adapter.getSelectedItems();

        // Add the selected items to the cart
        if (selectedItems != null) {
            // Add the selected items to the cart
            for (FoodItem selectedItem : selectedItems) {
                if (selectedItem.getQuantity() > 0) {
                    CartItem cartItem = new CartItem(selectedItem.getName(), selectedItem.getPrice(), selectedItem.getQuantity());
                    mCartItems.add(cartItem);
                    mTotalPrice += (selectedItem.getPrice() * selectedItem.getQuantity());
                }
            }
        }

        mAdapter.notifyDataSetChanged();
    }

    public void onPlaceOrderClicked(View view) {
        // Create a new order with an order ID, status, items, and total price
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = dateFormat.format(new Date());
        String orderId = timestamp + String.format("%02d", new Random().nextInt(100));
        String status = "CREADO";
        List<CartItem> items = mCartItems;
        double totalPrice = mTotalPrice;

        Pedido pedido = new Pedido(orderId, status, items, totalPrice);

        // TODO: Send the order to the server

        // Clear the cart and go back to the main activity
        mCartItems.clear();
        mAdapter.notifyDataSetChanged();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public class CartItemAdapter extends BaseAdapter {
        private Carrito mContext;
        private List<CartItem> mCartItems;

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

            final CartItem cartItem = mCartItems.get(position);
            itemNameTextView.setText(cartItem.getName());
            itemPriceTextView.setText("$" + cartItem.getPrice());
            itemQuantityTextView.setText(String.valueOf(cartItem.getQuantity()));

            return view;
        }
    }
}*/