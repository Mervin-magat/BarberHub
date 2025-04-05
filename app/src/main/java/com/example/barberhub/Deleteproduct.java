package com.example.barberhub;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Deleteproduct {

    private static final String DELETE_URL = "http://192.168.102.184/barberhubcrud/delete_product.php"; // Change to your server URL

    public static void deleteProduct(Context context, int productId, OnDeleteListener listener) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DELETE_URL,
                response -> {
                    Toast.makeText(context, "Product Deleted", Toast.LENGTH_SHORT).show();
                    listener.onSuccess();
                },
                error -> {
                    Toast.makeText(context, "Failed to Delete Product", Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                    listener.onFailure();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(productId)); // Send product ID
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }

    public interface OnDeleteListener {
        void onSuccess();
        void onFailure();
    }
}
