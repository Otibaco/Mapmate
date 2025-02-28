package com.example.mapmate.auth;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.mapmate.R;

public class LoginFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        TextView tvUserAgreement = view.findViewById(R.id.tvUserAgreement);
        CheckBox cbEmailSubscription = view.findViewById(R.id.cbEmailSubscription);

        // Use system default text color (white in dark mode)
        int normalTextColor = Color.WHITE;

        // Text content
        String fullText = "By continuing, you agree to our User Agreement and acknowledge that you understand the Privacy Policy.";
        SpannableString spannableString = new SpannableString(fullText);

        // Apply white color to all text
        spannableString.setSpan(new android.text.style.ForegroundColorSpan(normalTextColor), 0, fullText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Set ClickableSpan for 'User Agreement'
        int start1 = fullText.indexOf("User Agreement");
        int end1 = start1 + "User Agreement".length();
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                openWebPage("https://yourwebsite.com/user-agreement");
            }
        }, start1, end1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Set ClickableSpan for 'Privacy Policy'
        int start2 = fullText.indexOf("Privacy Policy");
        int end2 = start2 + "Privacy Policy".length();
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                openWebPage("https://yourwebsite.com/privacy-policy");
            }
        }, start2, end2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvUserAgreement.setText(spannableString);
        tvUserAgreement.setMovementMethod(LinkMovementMethod.getInstance());

        // Use default accent color for checkbox
        cbEmailSubscription.setButtonTintList(null); // Removes custom color and uses accent color

        return view;
    }

    private void openWebPage(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
