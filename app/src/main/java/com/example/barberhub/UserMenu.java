package com.example.barberhub;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class UserMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);

        ImageView userIcon = findViewById(R.id.userIcon);

        // Show popup when clicked
        userIcon.setOnClickListener(this::showUserOptions);
    }

    private void showUserOptions(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_option_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();

            if (id == R.id.option_settings) {
                Toast.makeText(this, "Opening Profile...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, Profile.class));
                return true;

            } else if (id == R.id.option_logout) {
                Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
                finish(); // Close activity
                return true;
            }

            return false;
        });

        popup.show();
    }
}
