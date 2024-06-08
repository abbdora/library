package com.example.kpabarenova;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private static final int LOG_ACTIVITY_REQUEST_CODE = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                if (menuItem.getItemId() == R.id.like) {
                    fragment = new LikeFragment();
                } else if (menuItem.getItemId() == R.id.profile) {
                    fragment = new ProfileFragment();
                } else if (menuItem.getItemId() == R.id.home) {
                    fragment = new HomeFragment();
                } else {
                    return false;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container1, fragment).commit();
                return true;
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container1, new HomeFragment())
                    .commit();
        }
    }

    public void startLogActivity() {
        Intent intent = new Intent(this, LogActivity.class);
        startActivityForResult(intent, LOG_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOG_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                String email = data.getStringExtra("email");
                loadProfileFragmentWithEmail(email);
            }
        }
    }

    private void loadProfileFragmentWithEmail(String email) {
        Fragment fragment = ProfileFragment.newInstance(email);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container1, fragment)
                .addToBackStack(null)
                .commit();
    }
}