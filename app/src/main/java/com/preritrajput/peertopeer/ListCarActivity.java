package com.preritrajput.peertopeer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.preritrajput.peertopeer.databinding.ActivityListCarBinding;

public class ListCarActivity extends AppCompatActivity {

    private ActivityListCarBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_car);

        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListCarActivity.this, Success_List.class));
            }
        });
    }
}