package com.preritrajput.peertopeer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
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
import com.google.firebase.firestore.auth.User;
import com.preritrajput.peertopeer.databinding.ActivitySplashLoginBinding;
import com.preritrajput.peertopeer.db.UserHelper;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private ActivitySplashLoginBinding binding;
    public static final String LOGIN = "login";
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.black_trans80));
        binding = ActivitySplashLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        LottieAnimationView animationView;
        animationView =  findViewById(R.id.imgView_logo);
        SharedPreferences settings = getSharedPreferences(MainActivity.LOGIN, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("seenOnBoarding", true);
        editor.apply();

        animationView.playAnimation();

        binding.LoginButton.setOnClickListener(v -> {
            String phone = binding.phoneNumber.getText().toString();
            if(!valid(phone))
                valid(phone);
            else {
                binding.progressBar.setVisibility(View.VISIBLE);
                if(check(phone)) {
                    PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                            .setPhoneNumber("+91" + phone)
                            .setTimeout(60L, TimeUnit.SECONDS)
                            .setActivity(MainActivity.this)
                            .setCallbacks(mCallBacks)
                            .build();

                    PhoneAuthProvider.verifyPhoneNumber(options);
                }
            }
             });

        binding.tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignUp1.class);
            startActivity(intent);
        });

        mCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull @NotNull PhoneAuthCredential phoneAuthCredential) {
                signIn(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull @NotNull FirebaseException e) {
                binding.progressBar.setVisibility(View.INVISIBLE);
                binding.LoginButton.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, e.getMessage(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull @NotNull String s, @NonNull @NotNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                binding.progressBar.setVisibility(View.INVISIBLE);
                binding.LoginButton.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, "OTP Sent",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, OTPVerification2.class);
                intent.putExtra("auth",s);
                intent.putExtra("phone", binding.phoneNumber.getText().toString());
                startActivity(intent);
            }
        };


    }

    private void signIn(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Intent intent = new Intent(MainActivity.this, OTPVerification2.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private boolean check(String phoneNumber) {
        DatabaseReference database = FirebaseDatabase.getInstance("https://peertopeer-5851a-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Registered Users");
        Query phoneQuery = database.orderByKey();
        final boolean[] flag = {true};

        phoneQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String key=data.getKey();
                    if(key.equals(phoneNumber)) {
                        flag[0] = false;
                    }
                }
                if(flag[0]) {
                    Toast.makeText(MainActivity.this, "User not Registered. Please sign up", Toast.LENGTH_SHORT).show();
                    binding.progressBar.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return flag[0];
    }

    private boolean valid(String phone) {
        if (!phone.matches("[0-9]+")) {
            binding.phoneNumber.setError("Invalid Phone Number");
            return false;
        }
        if (phone.length() != 10) {
            binding.phoneNumber.setError("Invalid Phone Number");
            return false;
        }
        return true;
    }
}