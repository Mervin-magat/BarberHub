package com.example.barberhub;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

public class AddProduct extends AppCompatActivity {

    EditText editProductName, editProductPrice, editProductQuantity;
    Button btnSaveProduct, btnUploadImage;
    ImageView productImage;
    Bitmap bitmap;
    String imageString;
    String URL = "http://192.168.102.184/barberhubcrud/add_product.php"; // Change to your PHP script URL

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        editProductName = findViewById(R.id.editProductName);
        editProductPrice = findViewById(R.id.editProductPrice);
        editProductQuantity = findViewById(R.id.editProductQuantity);
        btnSaveProduct = findViewById(R.id.btnSaveProduct);
        btnUploadImage = findViewById(R.id.btnUploadImage);
        productImage = findViewById(R.id.productImage);

        btnUploadImage.setOnClickListener(v -> selectImage());
        btnSaveProduct.setOnClickListener(v -> saveProduct());
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                productImage.setImageBitmap(bitmap);
                encodeImage(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void encodeImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private void saveProduct() {
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                response -> {
                    Log.d("ProductResponse", response);
                    Toast.makeText(AddProduct.this, response, Toast.LENGTH_SHORT).show();
                    finish(); // Close AddProduct activity after saving
                },
                error -> Toast.makeText(AddProduct.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show()) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", editProductName.getText().toString().trim());
                params.put("price", editProductPrice.getText().toString().trim());
                params.put("quantity", editProductQuantity.getText().toString().trim());
                params.put("image", imageString);
                return params;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }
}
