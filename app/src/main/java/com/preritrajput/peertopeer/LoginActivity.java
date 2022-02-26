package com.preritrajput.peertopeer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.preritrajput.peertopeer.databinding.ActivityLogin2Binding;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    private ActivityLogin2Binding binding;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;
    private static boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black_trans80));
        binding = ActivityLogin2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        binding.backButton.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, OptionsPage.class);
            startActivity(intent);
            finish();
        });


        binding.continueButton.setOnClickListener(v -> {
            String email = binding.emailEditText.getText().toString();
            String mobile = binding.mobEditText.getText().toString();

            if (!checkFields(email, mobile))
                checkFields(email, mobile);
            else {
                binding.progressBar.setVisibility(View.VISIBLE);
                if (check(mobile)) {
                    PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                            .setPhoneNumber("+91" + mobile)
                            .setTimeout(60L, TimeUnit.SECONDS)
                            .setActivity(LoginActivity.this)
                            .setCallbacks(mCallBacks)
                            .build();

                    PhoneAuthProvider.verifyPhoneNumber(options);
                }
            }
        });


        mCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull @NotNull PhoneAuthCredential phoneAuthCredential) {
                signIn(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull @NotNull FirebaseException e) {
                binding.progressBar.setVisibility(View.INVISIBLE);
                binding.continueButton.setVisibility(View.VISIBLE);
                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull @NotNull String s, @NonNull @NotNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                binding.continueButton.setVisibility(View.VISIBLE);
                Toast.makeText(LoginActivity.this, "OTP Sent", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(LoginActivity.this, OTPVerification2.class);
                intent.putExtra("auth", s);
                intent.putExtra("resend", forceResendingToken);
                intent.putExtra("phone", binding.mobEditText.getText().toString());
                startActivity(intent);
            }
        };

    }

        private boolean checkFields(String email, String mobile) {
            boolean check = true;
            if(mobile.isEmpty() && email.isEmpty()){
                Snackbar snackbar = Snackbar
                        .make(binding.parent, "Enter Email Address or Mobile number to continue.", Snackbar.LENGTH_LONG);
                snackbar.setBackgroundTint(getResources().getColor(R.color.sub_heading));
                snackbar.show();
                check = false;
            }
            else if (email.isEmpty() && (mobile.length() < 10 || !mobile.matches("[0-9]+"))) {
                binding.mobEditText.setError("Invalid Phone Number");
                check = false;
            }else if(mobile.isEmpty() && (email.length() <= 5 || !email.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"))) {
                binding.emailEditText.setError("Invalid email address");
                check = false;
            }else if(!mobile.isEmpty() && !email.isEmpty()){
                Snackbar snackbar = Snackbar
                        .make(binding.parent, "Please enter only one field to continue.", Snackbar.LENGTH_LONG);
                snackbar.setBackgroundTint(getResources().getColor(R.color.sub_heading));
                snackbar.show();
                check = false;
            }
            return check;
        }


    private void signIn(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Intent intent = new Intent(LoginActivity.this, OTPVerification2.class);
                startActivity(intent);
            } else {
                Toast.makeText(LoginActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private boolean check(String phoneNumber) {
        DatabaseReference database = FirebaseDatabase.getInstance("https://peertopeer-5851a-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Registered Users");
        Query phoneQuery = database.orderByKey();


        phoneQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String key = data.getKey();
//                    Log.d("Sunidhi", key + "----" + phoneNumber);

                    if (key.equals(phoneNumber)) {
                        LoginActivity.flag = true;
                        break;
                    }
                }
                if (!LoginActivity.flag) {
                    Toast.makeText(LoginActivity.this, "User not Registered. Please sign up", Toast.LENGTH_SHORT).show();
                    binding.progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return flag;
    }
}