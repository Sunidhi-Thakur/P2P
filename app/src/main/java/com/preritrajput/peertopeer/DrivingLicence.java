package com.preritrajput.peertopeer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.preritrajput.peertopeer.databinding.ActivityDrivingLicenceBinding;

public class DrivingLicence extends AppCompatActivity {

    private ActivityDrivingLicenceBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.black_trans80));
        binding = ActivityDrivingLicenceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!binding.checkbox1.isChecked()) {
                    Snackbar snackbar = Snackbar
                            .make(binding.parent, "Please tick the check box to continue.", Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(getResources().getColor(R.color.sub_heading));
                    snackbar.show();
                }
                if(binding.checkbox1.isChecked()){
                    binding.nextButton.setText("Submit Application");
                }
                if(binding.nextButton.getText().toString().equals("Submit Application")){
                    changeAndProceed();
                }
                }
        });


    }

    private void changeAndProceed() {
        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DrivingLicence.this, ThankYou.class);
                startActivity(intent);
                finish();
            }
        });
    }
}