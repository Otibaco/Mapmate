package com.example.mapmate.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mapmate.R;
import com.example.mapmate.activities.SignUpActivity;  // Make sure this import is correct
import com.example.mapmate.activities.MainActivity;

public class LoginActivity extends AppCompatActivity {

    EditText emailEditText, passwordEditText;
    ImageView togglePassword;
    boolean passwordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        togglePassword = findViewById(R.id.togglePassword);

        // Toggle password visibility
        togglePassword.setOnClickListener(view -> {
            passwordVisible = !passwordVisible;
            if (passwordVisible) {
                passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                togglePassword.setImageResource(R.drawable.ic_visibility);
            } else {
                passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                togglePassword.setImageResource(R.drawable.ic_visibility_off);
            }
            passwordEditText.setSelection(passwordEditText.length());
        });

        // Login button
        Button loginBtn = findViewById(R.id.loginButton);
        loginBtn.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                // You can add login logic here (e.g., Firebase/Auth API)
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();

                // Navigate to MainActivity
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        });

        // Sign up redirect
        TextView signUpText = findViewById(R.id.signupText); // We'll assign this ID in XML
        signUpText.setOnClickListener(v -> {
            startActivity(new Intent(this, SignUpActivity.class));
        });
    }
}
