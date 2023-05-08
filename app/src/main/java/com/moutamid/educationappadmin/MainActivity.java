package com.moutamid.educationappadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.moutamid.educationappadmin.databinding.ActivityMainBinding;
import com.moutamid.educationappadmin.utlis.Constants;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Constants.checkApp(this);

        binding.addSubject.setOnClickListener(v -> {
            startActivity(new Intent(this, AddSubjectActivity.class));
            finish();
        });

        binding.allSubjects.setOnClickListener(v -> {
            startActivity(new Intent(this, AllSubjectActivity.class));
            finish();
        });

        binding.addClass.setOnClickListener(v -> {
            startActivity(new Intent(this, AddClassActivity.class));
            finish();
        });

    }
}