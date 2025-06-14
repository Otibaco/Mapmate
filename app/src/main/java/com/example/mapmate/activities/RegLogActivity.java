package com.example.mapmate.activities;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mapmate.R;
import com.example.mapmate.adapters.AuthPagerAdapter;
public class RegLogActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private Button loginButton, signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_log);

        viewPager = findViewById(R.id.viewPager);


        // Set up ViewPager2 with Adapter
        AuthPagerAdapter adapter = new AuthPagerAdapter(this);
        viewPager.setAdapter(adapter);

        // Swipe Listener
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == 0) {

                } else {

                }
            }
        });

    }
}
