package com.preritrajput.peertopeer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;

import com.preritrajput.peertopeer.databinding.ActivityChooseFlowBinding;
import com.preritrajput.peertopeer.databinding.ActivityDashboardBinding;

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

        binding.getKeys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.getKeys.setBackgroundResource(R.drawable.ic_selected_bg);
                binding.giveKeys.setBackgroundResource(R.drawable.dotted_rect);
            }
        });

        binding.giveKeys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.giveKeys.setBackgroundResource(R.drawable.ic_selected_bg);
                binding.getKeys.setBackgroundResource(R.drawable.dotted_rect);
            }
        });
    }
}