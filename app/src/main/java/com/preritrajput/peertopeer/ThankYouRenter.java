package com.preritrajput.peertopeer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.preritrajput.peertopeer.databinding.ActivityListCarBinding;
import com.preritrajput.peertopeer.databinding.ActivityThankYouRenterBinding;

public class ThankYouRenter extends AppCompatActivity {

    private ActivityThankYouRenterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black_trans80));
        binding = ActivityThankYouRenterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.discoverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThankYouRenter.this, Dashboard.class);
                startActivity(intent);
                finish();
            }
        });
    }
}