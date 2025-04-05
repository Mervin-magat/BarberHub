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

public class SignupActivity extends AppCompatActivity {

    EditText editEmail, editPassword, editName, editAge;
    Button btnRegister;
    String URL = "http://192.168.1.7/barberhubcrud/register.php"; // Adjust if needed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        editName = findViewById(R.id.editName);
        editAge = findViewById(R.id.editAge);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        String name = editName.getText().toString().trim();
        String age = editAge.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty() || name.isEmpty() || age.isEmpty()) {
            Toast.makeText(SignupActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        StringRequest request = new StringRequest(Request.Method.POST, URL,
                response -> {
                    Log.d("SignupResponse", response);
                    if (response.trim().equals("Registration Successful")) {
                        Toast.makeText(SignupActivity.this, "Account Created! Please Login", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(SignupActivity.this, response, Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(SignupActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show()) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                params.put("name", name);
                params.put("age", age);
                return params;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }
}
