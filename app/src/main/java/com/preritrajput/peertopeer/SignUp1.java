package com.preritrajput.peertopeer;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.preritrajput.peertopeer.databinding.ActivitySignUp1Binding;
import com.preritrajput.peertopeer.db.UserHelper;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class SignUp1 extends AppCompatActivity {

    private ActivitySignUp1Binding binding;
    final Calendar myCalendar = Calendar.getInstance();
    static int age = 18;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.black_trans80));
        binding = ActivitySignUp1Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        SharedPreferences settings = getSharedPreferences(MainActivity.LOGIN, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("seenOnBoarding", true);
        editor.apply();
        editor.putBoolean("hasLoggedIn", true);
        editor.apply();


        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
            setAge(year, monthOfYear, dayOfMonth);
        };

        binding.dob.setInputType(InputType.TYPE_NULL);
        binding.dob.setOnClickListener(v -> new DatePickerDialog(SignUp1.this, date, myCalendar
                .get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show());

        final String[] gender = {"X"};
        binding.male.setOnClickListener(v -> {
            gender[0] = "M";
            binding.male.setCardElevation(5f);
            binding.male.setCardBackgroundColor(getResources().getColor(R.color.yellow));
            binding.female.setCardBackgroundColor(getResources().getColor(R.color.purple_700));
            binding.male.setPadding(5,5,5,5);
            binding.female.setPadding(0,0,0,0);

        });
        binding.female.setOnClickListener(v -> {
            gender[0] = "F";
            binding.female.setCardElevation(5f);
            binding.female.setCardBackgroundColor(getResources().getColor(R.color.yellow));
            binding.male.setCardBackgroundColor(getResources().getColor(R.color.purple_700));
            binding.female.setPadding(5,5,5,5);
            binding.male.setPadding(0,0,0,0);
        });

        binding.nextButton.setOnClickListener(v -> {
            String fName = binding.firstName.getText().toString();
            String lName = binding.lastName.getText().toString();
            String phone = binding.phoneNumber.getText().toString();

            boolean check = checkFields(fName, lName, phone, age, gender[0]);
            if (check) {
                UserHelper user = new UserHelper(fName,lName,phone,gender[0], age);
                DatabaseReference reference = FirebaseDatabase.getInstance("https://peertopeer-5851a-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Registered Users");
                reference.child(phone).setValue(user);

                binding.progressBar.setVisibility(View.VISIBLE);
                binding.nextButton.setVisibility(View.INVISIBLE);

                PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91"+phone)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(SignUp1.this)
                        .setCallbacks(mCallBacks)
                        .build();

                PhoneAuthProvider.verifyPhoneNumber(options);

            }else{
                checkFields(fName, lName, phone, age, gender[0]);
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
                binding.nextButton.setVisibility(View.VISIBLE);
                Toast.makeText(SignUp1.this, e.getMessage(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull @NotNull String s, @NonNull @NotNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                binding.progressBar.setVisibility(View.INVISIBLE);
                binding.nextButton.setVisibility(View.VISIBLE);
                Toast.makeText(SignUp1.this, "OTP Sent",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUp1.this, OTPVerification.class);
                        intent.putExtra("auth",s);
                        intent.putExtra("phone", binding.phoneNumber.getText().toString());
                        startActivity(intent);
            }
        };
    }

    private void setAge(int year, int monthOfYear, int dayOfMonth) {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        Calendar today = Calendar.getInstance();
        age = today.get(Calendar.YEAR) - myCalendar.get(Calendar.YEAR);
        if (today.get(Calendar.MONTH) == myCalendar.get(Calendar.MONTH)) {
            if (today.get(Calendar.DAY_OF_MONTH) < myCalendar.get(Calendar.DAY_OF_MONTH)) {
                age = age - 1;
            }
        } else if (today.get(Calendar.MONTH) < myCalendar.get(Calendar.MONTH)) {
            age = age - 1;
        }
        if(age > 18)
            binding.dob.setError(null);
    }

    private void updateLabel() {
        String myFormat = "dd-MMM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        binding.dob.setText(sdf.format(myCalendar.getTime()));
    }

    private boolean checkFields(String fName, String lName, String phone, int age, String gender) {
        boolean check = true;
        if(fName.length() == 0) {
            binding.firstName.setError("First Name cannot be Empty");
            check = false;
        }
        else if (!fName.matches("[a-zA-Z]+")) {
            binding.firstName.setError("First Name cannot have numbers or special characters");
            check = false;
        }

        if (lName.length() == 0) {
            binding.lastName.setError("Last Name cannot be Empty");
            check = false;
        }

        else if (!lName.matches("[a-zA-Z]+")) {
            binding.lastName.setError("Last Name cannot have numbers or special characters");
            check = false;
        }
        if (!phone.matches("[0-9]+")) {
            binding.phoneNumber.setError("Invalid Phone Number");
            check = false;
        }
        if (phone.length() != 10) {
            binding.phoneNumber.setError("Invalid Phone Number");
            check = false;
        }
        if (gender.equals("X")) {
            Toast.makeText(this, "Select gender", Toast.LENGTH_SHORT).show();
            check = false;
        }
        if (age < 18) {
            binding.dob.setError("Age should be more than 18");
            check = false;
        }
        return check;
    }

    private void signIn(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Intent intent = new Intent(SignUp1.this, OTPVerification.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(SignUp1.this, "Error occurred", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}