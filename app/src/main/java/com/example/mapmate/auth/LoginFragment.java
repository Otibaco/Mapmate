package com.example.mapmate.auth;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.example.mapmate.R;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Objects;
import java.util.regex.Pattern;

public class LoginFragment extends Fragment {
    private TextInputEditText etEmail, etPassword;
    private Button buttonLogin;
    private LinearLayout googleSignup;
    private TextView tvSignUp, tvForgotPassword;
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
    );

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initializeViews(view);
        updateLoginButtonState(); // Ensure correct state on startup
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListeners(view);
    }


    private void initializeViews(View view) {
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        buttonLogin = view.findViewById(R.id.btnLogin);
        googleSignup = view.findViewById(R.id.googleSignup);
        tvSignUp = view.findViewById(R.id.tvSignUp);
        tvForgotPassword = view.findViewById(R.id.tvForgotPassword);
    }

    private void setListeners(View view) {
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToRegister();
            }
        });
        tvForgotPassword.setOnClickListener(v -> Toast.makeText(requireContext(), "Forgot Password Clicked!", Toast.LENGTH_SHORT).show());

        buttonLogin.setOnClickListener(v -> {
            if (isValidInput()) {
                loginUser();
            } else {
                showLoginError();
            }
        });

        googleSignup.setOnClickListener(v -> Toast.makeText(requireContext(), "Google Signup Clicked!", Toast.LENGTH_SHORT).show());

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                updateLoginButtonState();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        };

        etEmail.addTextChangedListener(textWatcher);
        etPassword.addTextChangedListener(textWatcher);
    }

    private void navigateToRegister() {
        ViewPager2 viewPager = requireActivity().findViewById(R.id.viewPager);
        if (viewPager != null) viewPager.setCurrentItem(1, true);
    }

    private void updateLoginButtonState() {
        if (isValidInput()) {
            buttonLogin.setEnabled(true);
            buttonLogin.setAlpha(1.0f); // Full opacity when valid
        } else {
            buttonLogin.setEnabled(true); // Keep enabled so user can click
            buttonLogin.setAlpha(0.5f); // Make it look disabled
        }
    }

    private boolean isValidInput() {
        String emailInput = Objects.requireNonNull(etEmail.getText()).toString().trim();
        String passwordInput = Objects.requireNonNull(etPassword.getText()).toString().trim();
        return EMAIL_PATTERN.matcher(emailInput).matches() && !passwordInput.isEmpty();
    }

    private void showLoginError() {
        String emailInput = Objects.requireNonNull(etEmail.getText()).toString().trim();
        String passwordInput = Objects.requireNonNull(etPassword.getText()).toString().trim();

        if (!EMAIL_PATTERN.matcher(emailInput).matches()) {
            Toast.makeText(requireContext(), "Enter a valid email", Toast.LENGTH_SHORT).show();
        } else if (passwordInput.isEmpty()) {
            Toast.makeText(requireContext(), "Password cannot be empty", Toast.LENGTH_SHORT).show();
        }
    }

    private void loginUser() {
        Toast.makeText(requireContext(), "Login successful!", Toast.LENGTH_SHORT).show();
    }
}
