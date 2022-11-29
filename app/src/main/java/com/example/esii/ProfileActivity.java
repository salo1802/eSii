package com.example.esii;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.login.widget.ProfilePictureView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    ProfilePictureView profile_iv;
    TextView name_tv;

    String profileId;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();

        profile_iv = findViewById(R.id.imageView16);
        name_tv = findViewById(R.id.textView13);

        SharedPreferences p = getSharedPreferences("preferencias",MODE_PRIVATE);
        profileId = p.getString("Id", "noId");
        username = p.getString("username", "noUsername");

        profile_iv.setPresetSize(ProfilePictureView.NORMAL);
        profile_iv.setProfileId(profileId);
        name_tv.setText(username);

        bottomNavigationView = findViewById(R.id.bottom_navbar);
        bottomNavigationView.setSelectedItemId(R.id.profile);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.chat:
                        startActivity(new Intent(getApplicationContext(), ChatActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.cont:
                        startActivity(new Intent(getApplicationContext(), AddPictureActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.courses:
                        startActivity(new Intent(getApplicationContext(), CoursesActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.profile:

                        return true;
                }
                return false;
            }
        });
    }
}