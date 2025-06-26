package com.example.mapmate.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mapmate.R;

public class EmailVerificationActivity extends AppCompatActivity {

    private EditText otp1, otp2, otp3, otp4, otp5, otp6;
    private TextView resendTimer;
    private CountDownTimer timer;
    private static final long RESEND_INTERVAL = 60000; // 60 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);

        // ✅ Correct place to bind views
        otp1 = findViewById(R.id.otp1);
        otp2 = findViewById(R.id.otp2);
        otp3 = findViewById(R.id.otp3);
        otp4 = findViewById(R.id.otp4);
        otp5 = findViewById(R.id.otp5);
        otp6 = findViewById(R.id.otp6);
        resendTimer = findViewById(R.id.resendTimerText);
        Button verifyButton = findViewById(R.id.verifyButton);

        // Set up auto move for OTP fields
        setupOtpInput(otp1, otp2);
        setupOtpInput(otp2, otp3);
        setupOtpInput(otp3, otp4);
        setupOtpInput(otp4, otp5);
        setupOtpInput(otp5, otp6);
        setupOtpInput(otp6, null); // Last field

        // Handle Verify button click
        verifyButton.setOnClickListener(v -> {
            // You can add validation here if needed
            Toast.makeText(this, "Email verified!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });

        startResendCountdown();
    }

    private void setupOtpInput(final EditText current, final EditText next) {
        current.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1 && next != null) {
                    next.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void startResendCountdown() {
        timer = new CountDownTimer(RESEND_INTERVAL, 1000) {
            public void onTick(long millisUntilFinished) {
                resendTimer.setText("Resend (" + millisUntilFinished / 1000 + "s)");
            }

            public void onFinish() {
                resendTimer.setText("Resend");
                resendTimer.setEnabled(true);
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) timer.cancel();
    }
}
