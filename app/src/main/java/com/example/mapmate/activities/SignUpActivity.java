package com.example.mapmate.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mapmate.R;

public class SignUpActivity extends AppCompatActivity {

    EditText emailEditText, passwordEditText, referralEditText;
    Spinner useCaseSpinner;
    ImageView togglePassword;
    boolean passwordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        referralEditText = findViewById(R.id.referralCodeEditText);
        useCaseSpinner = findViewById(R.id.useCaseSpinner);
        togglePassword = findViewById(R.id.togglePassword);

        // Setup Spinner
        String[] useCases = {"Trading", "Investing", "Remittance", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, useCases);
        useCaseSpinner.setAdapter(adapter);

        // Toggle password visibility
        togglePassword.setOnClickListener(v -> {
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

        // Handle signup logic
        Button continueButton = findViewById(R.id.continueButton);
        continueButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String useCase = useCaseSpinner.getSelectedItem().toString();
            String referral = referralEditText.getText().toString();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            } else {
                // Mock signup success
                Toast.makeText(this, "Sign up successful!", Toast.LENGTH_SHORT).show();

                // Go to email verification
                Intent intent = new Intent(SignUpActivity.this, EmailVerificationActivity.class);
                intent.putExtra("email", email); // optional: pass email if needed
                startActivity(intent);
                finish();
            }
        });

        // Navigate back to login
        TextView loginLink = findViewById(R.id.loginLink); // fix in XML below
        loginLink.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}
