package com.preritrajput.peertopeer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.preritrajput.peertopeer.databinding.ActivityChooseFlowBinding;

public class ChooseFlow extends AppCompatActivity {

    private ActivityChooseFlowBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.black_trans80));
        binding = ActivityChooseFlowBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences settings = getSharedPreferences(OptionsPage.LOGIN, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("hasRegistered2", true);
        editor.putBoolean("hasRegistered",true);
        editor.putBoolean("seenOnBoarding", true);
        editor.putBoolean("hasLoggedIn",true);
        editor.apply();


        binding.getKeys.setOnClickListener(view -> {
            binding.getKeys.setBackgroundResource(R.drawable.ic_selected_bg);
            binding.giveKeys.setBackgroundResource(R.drawable.dotted_rect);
        });

        binding.giveKeys.setOnClickListener(view -> {
            binding.giveKeys.setBackgroundResource(R.drawable.ic_selected_bg);
            binding.getKeys.setBackgroundResource(R.drawable.dotted_rect);
        });
    }
}