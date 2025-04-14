package com.example.mapmate.auth;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.example.mapmate.R;
import com.example.mapmate.utils.WebUtils;
import com.example.mapmate.web.WebViewActivity;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Objects;
import java.util.regex.Pattern;
public class RegisterFragment extends Fragment {
    private TextInputEditText etEmail, etPassword, etConfirmPassword;
    private Button btnSignup;
    private LinearLayout googleSignup;
    TextView termsText;
    private TextView tvLogin;
    private CheckBox termsCheckbox;

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
    );

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        initializeViews(view);
        updateSignupButtonState(); // Ensure correct state on startup
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListeners();
    }

    private void initializeViews(View view) {
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        etConfirmPassword = view.findViewById(R.id.etConfirmPassword);
        btnSignup = view.findViewById(R.id.btnSignup);
        googleSignup = view.findViewById(R.id.googleSignup);
        termsText = view.findViewById(R.id.termsText);
        tvLogin = view.findViewById(R.id.tvLogin);
        termsCheckbox = view.findViewById(R.id.termsCheckBox);
    }

    private void setListeners() {
        tvLogin.setOnClickListener(v -> navigateToLogin());

        btnSignup.setOnClickListener(v -> {
            if (isValidInput()) {
                registerUser();
            } else {
                showRegisterError();
            }
        });

        googleSignup.setOnClickListener(v -> Toast.makeText(requireContext(), "Google Signup Clicked!", Toast.LENGTH_SHORT).show());

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                updateSignupButtonState();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        };

        etEmail.addTextChangedListener(textWatcher);
        etPassword.addTextChangedListener(textWatcher);
        etConfirmPassword.addTextChangedListener(textWatcher);
        termsCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> updateSignupButtonState());
        setupClickableTerms(termsText);
    }

    private void navigateToLogin() {
        ViewPager2 viewPager = requireActivity().findViewById(R.id.viewPager);
        if (viewPager != null) viewPager.setCurrentItem(0, true);
    }

    private void updateSignupButtonState() {
        if (isValidInput()) {
            btnSignup.setEnabled(true);
            btnSignup.setAlpha(1.0f); // Full opacity when valid
        } else {
            btnSignup.setEnabled(true); // Keep enabled for toast messages
            btnSignup.setAlpha(0.5f); // Make it look dimmed
        }
    }

    private boolean isValidInput() {
        String emailInput = Objects.requireNonNull(etEmail.getText()).toString().trim();
        String passwordInput = Objects.requireNonNull(etPassword.getText()).toString().trim();
        String confirmPasswordInput = Objects.requireNonNull(etConfirmPassword.getText()).toString().trim();
        boolean isCheckboxChecked = termsCheckbox.isChecked();

        return EMAIL_PATTERN.matcher(emailInput).matches() &&
                passwordInput.length() >= 8 &&
                passwordInput.equals(confirmPasswordInput) &&
                isCheckboxChecked;
    }

    private void showRegisterError() {
        String emailInput = Objects.requireNonNull(etEmail.getText()).toString().trim();
        String passwordInput = Objects.requireNonNull(etPassword.getText()).toString().trim();
        String confirmPasswordInput = Objects.requireNonNull(etConfirmPassword.getText()).toString().trim();
        boolean isCheckboxChecked = termsCheckbox.isChecked();

        if (!EMAIL_PATTERN.matcher(emailInput).matches()) {
            Toast.makeText(requireContext(), "Enter a valid email", Toast.LENGTH_SHORT).show();
        } else if (passwordInput.length() < 8) {
            Toast.makeText(requireContext(), "Password must be at least 8 characters", Toast.LENGTH_SHORT).show();
        } else if (!passwordInput.equals(confirmPasswordInput)) {
            Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
        } else if (!isCheckboxChecked) {
            Toast.makeText(requireContext(), "Please accept the terms and conditions", Toast.LENGTH_SHORT).show();
        }
    }

    private void registerUser() {
        Toast.makeText(requireContext(), "Registration successful!", Toast.LENGTH_SHORT).show();
    }

    private void setupClickableTerms(TextView termsTextView) {
        String fullText = getString(R.string.terms_and_conditions);

        int startUserAgreement = fullText.indexOf("User Agreement");
        int endUserAgreement = startUserAgreement + "User Agreement".length();

        int startPrivacyPolicy = fullText.indexOf("Privacy Policy");
        int endPrivacyPolicy = startPrivacyPolicy + "Privacy Policy".length();

        if (startUserAgreement == -1 || startPrivacyPolicy == -1) {
            Log.e("RegisterFragment", "Clickable text not found in fullText!");
            return;
        }

        SpannableString spannableString = new SpannableString(fullText);

        // Clickable span for "User Agreement"
        ClickableSpan userAgreementClick = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                openWebView("https://policies.google.com/terms");
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(ContextCompat.getColor(termsTextView.getContext(), R.color.primaryColor));
                ds.setUnderlineText(true);
            }
        };

        // Clickable span for "Privacy Policy"
        ClickableSpan privacyPolicyClick = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                openWebView("https://policies.google.com/privacy");
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(ContextCompat.getColor(termsTextView.getContext(), R.color.primaryColor));
                ds.setUnderlineText(true);
            }
        };

        spannableString.setSpan(userAgreementClick, startUserAgreement, endUserAgreement, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(privacyPolicyClick, startPrivacyPolicy, endPrivacyPolicy, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        termsTextView.setText(spannableString);
        termsTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    // Open WebViewActivity to load URL
    private void openWebView(String url) {
        Intent intent = new Intent(getContext(), WebViewActivity.class);
        intent.putExtra("URL", url);
        startActivity(intent);
    }

}
