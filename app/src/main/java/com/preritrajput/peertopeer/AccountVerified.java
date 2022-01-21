package com.preritrajput.peertopeer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.View;

import com.preritrajput.peertopeer.databinding.ActivityAccountVerifiedBinding;
import com.preritrajput.peertopeer.databinding.ActivityThankyouBinding;

public class AccountVerified extends AppCompatActivity {

    private ActivityAccountVerifiedBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_verified);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.black_trans80));
        binding = ActivityAccountVerifiedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Shader shader = new LinearGradient(0, 0, 0, binding.appName.getLineHeight(),
                Color.parseColor("#b17be1"), Color.parseColor("#459b41ee"), Shader.TileMode.REPEAT);
        binding.appName.getPaint().setShader(shader);

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountVerified.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}