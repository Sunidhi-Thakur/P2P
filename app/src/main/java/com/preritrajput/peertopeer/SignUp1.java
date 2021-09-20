package com.preritrajput.peertopeer;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.preritrajput.peertopeer.databinding.ActivitySignUp1Binding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static java.security.AccessController.getContext;


public class SignUp1 extends AppCompatActivity {

    private ActivitySignUp1Binding binding;
    final Calendar myCalendar = Calendar.getInstance();
    static int age = 18;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUp1Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences settings = getSharedPreferences(MainActivity.LOGIN, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("seenOnBoarding", true);
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
                Intent intent = new Intent(SignUp1.this, OTPVerification.class);
                startActivity(intent);
            }else{
                checkFields(fName, lName, phone, age, gender[0]);
            }
        });
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
        if (gender.equals("X")) {
            Toast.makeText(this, "Select gender", Toast.LENGTH_SHORT).show();
        }
        if (age < 18) {
            binding.dob.setError("Age should be more than 18");
            check = false;
        }
        return check;
    }

}