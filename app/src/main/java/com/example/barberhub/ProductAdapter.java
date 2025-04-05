package com.example.barberhub;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ProductModel> productList;

    public ProductAdapter(Context context, ArrayList<ProductModel> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        }

        ProductModel product = productList.get(position);

        // Find views for each product item
        TextView productName = convertView.findViewById(R.id.productName);
        TextView productPrice = convertView.findViewById(R.id.productPrice);
        TextView productQuantity = convertView.findViewById(R.id.productQuantity);
        ImageView productImage = convertView.findViewById(R.id.productImage);

        // Set product details
        productName.setText(product.getName());
        productPrice.setText("₱" + product.getPrice());
        productQuantity.setText("Quantity: " + product.getQuantity());

        // Decode the base64 image string and set the image
        String base64Image = product.getImageUrl();
        if (base64Image != null && !base64Image.isEmpty()) {
            Bitmap decodedImage = decodeBase64(base64Image);
            productImage.setImageBitmap(decodedImage);
        }

        return convertView;
    }

    // Method to decode base64 string to Bitmap
    private Bitmap decodeBase64(String base64String) {
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }
}