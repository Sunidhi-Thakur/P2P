package com.preritrajput.peertopeer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.preritrajput.peertopeer.adapters.OnBoardingAdapter;
import com.preritrajput.peertopeer.databinding.ActivityOnBoardingBinding;

import me.relex.circleindicator.CircleIndicator3;

public class OnBoarding extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private ActivityOnBoardingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.black_trans80));
        binding = ActivityOnBoardingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        viewPager2 = findViewById(R.id.view_pager2);

        OnBoardingAdapter viewPager2Adapter = new OnBoardingAdapter(this);
        viewPager2.setAdapter(viewPager2Adapter);

        CircleIndicator3 circleIndicator3 = binding.indicator;
        circleIndicator3.setViewPager(binding.viewPager2);

        viewPager2.setUserInputEnabled(false);

        binding.skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences settings = getSharedPreferences(OptionsPage.LOGIN, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("seenOnBoarding", true);
                editor.apply();

                Intent intent = new Intent(OnBoarding.this, OptionsPage.class);
                startActivity(intent);
            }
        });



        binding.nextButton.setOnClickListener(v -> {
            if(viewPager2.getCurrentItem() == 1){
                binding.skip.setVisibility(View.GONE);
                binding.nextButton.setVisibility(View.GONE);
                binding.continueButton.setVisibility(View.VISIBLE);
                viewPager2.setCurrentItem(2, true);
            }
            else if (viewPager2.getCurrentItem() == 2) {
                SharedPreferences settings = getSharedPreferences(OptionsPage.LOGIN, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("seenOnBoarding", true);
                editor.apply();
            } else
                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1, true);
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

        binding.continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OnBoarding.this, OptionsPage.class);
                startActivity(intent);
                finish();
            }
        });

    }
}