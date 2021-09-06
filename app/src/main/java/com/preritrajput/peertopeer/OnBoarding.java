package com.preritrajput.peertopeer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.preritrajput.peertopeer.adapters.OnBoardingAdapter;
import com.preritrajput.peertopeer.databinding.ActivityLoginBinding;
import com.preritrajput.peertopeer.databinding.ActivityOnBoardingBinding;

import me.relex.circleindicator.CircleIndicator3;

public class OnBoarding extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private ActivityOnBoardingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOnBoardingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewPager2 = findViewById(R.id.view_pager2);

        CircleIndicator3 circleIndicator3 = binding.indicator;
        circleIndicator3.setViewPager(binding.viewPager2);


        OnBoardingAdapter viewPager2Adapter = new OnBoardingAdapter(this);

        viewPager2.setAdapter(viewPager2Adapter);
        binding.nextButton.setOnClickListener(v -> {
            if(viewPager2.getCurrentItem() == 2){
                Intent intent = new Intent(OnBoarding.this, Dashboard.class);
                startActivity(intent);
            }
            else
                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1, true);
        });

        viewPager2.setAdapter(viewPager2Adapter);
        binding.backButton.setOnClickListener(v -> {
            if(viewPager2.getCurrentItem() == 0){
                Intent intent = new Intent(OnBoarding.this, MainActivity.class);
                startActivity(intent);
            }else
                viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1, true);
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }

        });

    }
}