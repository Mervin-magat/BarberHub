package com.example.barberhub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Product extends AppCompatActivity {

    ListView listViewProducts;
    Button btnAddProduct;
    ArrayList<ProductModel> productList;
    ProductAdapter adapter;

    String URL = "http://192.168.1.7/barberhubcrud/get_product.php"; // Change to your PHP API URL

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        listViewProducts = findViewById(R.id.listViewProducts);
        btnAddProduct = findViewById(R.id.btnAddProduct);

        btnAddProduct.setOnClickListener(v -> {
            // Navigate to AddProduct activity
            Intent intent = new Intent(Product.this, AddProduct.class);
            startActivity(intent);
        });

        // Initialize product list
        productList = new ArrayList<>();

        // Load product list
        loadProductList();
    }

    private void loadProductList() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                response -> {
                    try {
                        Log.d("APIResponse", response); // Log response for debugging

                        if (response == null || response.isEmpty()) {
                            Toast.makeText(Product.this, "No data found", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Parse JSON response
                        JSONArray jsonArray = new JSONArray(response);
                        productList.clear();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject product = jsonArray.getJSONObject(i);
                            String name = product.optString("name", "Default Name");
                            double price = product.optDouble("price", 0.0);
                            int quantity = product.optInt("quantity", 0);
                            String imageUrl = product.optString("image", ""); // Base64 encoded image

                            // Add product to list
                            productList.add(new ProductModel(name, price, quantity, imageUrl));
                        }

                        // Set up adapter
                        adapter = new ProductAdapter(Product.this, productList);
                        listViewProducts.setAdapter(adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("JSONError", e.getMessage());
                        Toast.makeText(Product.this, "Error parsing data", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(Product.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                });

        // Add request to the queue
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }
}