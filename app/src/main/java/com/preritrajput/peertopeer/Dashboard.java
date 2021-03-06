package com.preritrajput.peertopeer;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.preritrajput.peertopeer.databinding.ActivityDashboardBinding;
import com.preritrajput.peertopeer.fragments.HomeFragment;
import com.preritrajput.peertopeer.fragments.NotificationFragment;
import com.preritrajput.peertopeer.fragments.ProfileFragment;
import com.preritrajput.peertopeer.fragments.SearchFragment;

public class Dashboard extends AppCompatActivity {

    final Fragment fragment1=new HomeFragment();
    final Fragment fragment2=new SearchFragment();
    final Fragment fragment3=new NotificationFragment();
    final Fragment fragment4=new ProfileFragment();
    final FragmentManager fm=getSupportFragmentManager();
    Fragment active=fragment1;

    Menu a;
    MenuItem b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.black_trans80));
        com.preritrajput.peertopeer.databinding.ActivityDashboardBinding binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fm.beginTransaction().add(R.id.main_container,fragment4,"4").hide(fragment4).commit();
        fm.beginTransaction().add(R.id.main_container,fragment3,"3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.main_container,fragment2,"2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.main_container,fragment1,"1").commit();

        SharedPreferences settings = getSharedPreferences(OptionsPage.LOGIN, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("hasRegistered2", true);
        editor.apply();
        editor.putBoolean("hasLoggedIn", true);
        editor.apply();
        editor.putBoolean("hasRegistered", true);
        editor.apply();



        binding.bottomNav.setOnNavigationItemSelectedListener(selectedListener);
        binding.bottomNav.setItemIconTintList(null);

        a= binding.bottomNav.getMenu();
        b=a.findItem(R.id.profile_nav);
        Glide.with(getApplicationContext()).asBitmap().load(R.drawable.preritpeer).apply(RequestOptions
                .circleCropTransform()
                .placeholder(R.drawable.dp)).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                b.setIcon(new BitmapDrawable(getResources(),resource));
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener= new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            {
                switch (item.getItemId())
                {
                    case R.id.home_nav:
                        fm.beginTransaction().hide(active).show(fragment1).commit();
                        active=fragment1;
                        return true;
                    case R.id.search_nav:
                        fm.beginTransaction().hide(active).show(fragment2).commit();
                        active=fragment2;
                        return true;
                    case R.id.like_nav:
                        fm.beginTransaction().hide(active).show(fragment3).commit();
                        active=fragment3;
                        return true;
                    case R.id.profile_nav:
                        fm.beginTransaction().hide(active).show(fragment4).commit();
                        active=fragment4;
                        return true;
                }
                return false;
            }
        }
    };
}