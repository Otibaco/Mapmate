package com.example.mapmate;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;
import me.relex.circleindicator.CircleIndicator3;
import java.util.Arrays;
import java.util.List;

public class SlideActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private SlideAdapter slideAdapter;
    private Button btnSkip, btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.getDecorView().setSystemUiVisibility(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//            window.setStatusBarColor(android.graphics.Color.TRANSPARENT);
//        }
        setContentView(R.layout.activity_slide);

        viewPager = findViewById(R.id.view_pager);
        btnSkip = findViewById(R.id.btn_skip);
        btnNext = findViewById(R.id.btn_next);
        CircleIndicator3 circleIndicator = findViewById(R.id.dots_indicator);

        // List of slides (handled by the adapter)
        List<SlideItem> slideItems = Arrays.asList(
                new SlideItem(R.drawable.image1, R.string.feature1_header, R.string.feature1_description),
                new SlideItem(R.drawable.image2, R.string.feature2_header, R.string.feature2_description),
                new SlideItem(R.drawable.image3, R.string.feature3_header, R.string.feature3_description),
                new SlideItem(R.drawable.image4, R.string.feature4_header, R.string.feature4_description),
                new SlideItem(R.drawable.image5, R.string.feature5_header, R.string.feature5_description)
        );


        slideAdapter = new SlideAdapter(slideItems);
        viewPager.setAdapter(slideAdapter);
        circleIndicator.setViewPager(viewPager);

        btnSkip.setOnClickListener(v -> {
            // Go to main app screen
            startActivity(new Intent(SlideActivity.this, MainActivity.class));
            finish();
        });

        btnNext.setOnClickListener(v -> {
            if (viewPager.getCurrentItem() < slideItems.size() - 1) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            } else {
                startActivity(new Intent(SlideActivity.this, MainActivity.class));
                finish();
            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (position == slideItems.size() - 1) {
                    btnSkip.setVisibility(View.INVISIBLE);
                    btnNext.setText("Done");
                } else {
                    btnNext.setText("Next");
                    btnSkip.setVisibility(View.VISIBLE);

                }
            }
        });
    }
}
