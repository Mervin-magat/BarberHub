package com.example.barberhub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText editEmail, editPassword;
    Button btnLogin, btnSignup;
    String URL = "http://192.168.1.7/barberhubcrud/login.php"; // Adjust IP if needed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        editEmail = findViewById(R.id.editEmail); // Make sure this ID exists in your XML
        editPassword = findViewById(R.id.editPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);

        btnLogin.setOnClickListener(v -> loginUser());
        btnSignup.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });
    }

    private void loginUser() {
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        StringRequest request = new StringRequest(Request.Method.POST, URL,
                response -> {
                    Log.d("LoginResponse", response);
                    String role = response.trim().toLowerCase();

                    switch (role) {
                        case "admin":
                            startActivity(new Intent(LoginActivity.this, MenuPage.class));
                            finish();
                            break;
                        case "user":
                            startActivity(new Intent(LoginActivity.this, UserMenu.class));
                            finish();
                            break;
                        default:
                            Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                            break;
                    }
                },
                error -> Toast.makeText(LoginActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show()) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }
}
