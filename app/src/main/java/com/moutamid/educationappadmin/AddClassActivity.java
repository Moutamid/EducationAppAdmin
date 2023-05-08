package com.moutamid.educationappadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.moutamid.educationappadmin.databinding.ActivityAddClassBinding;
import com.moutamid.educationappadmin.model.ClassModel;
import com.moutamid.educationappadmin.utlis.Constants;

import java.util.UUID;

public class AddClassActivity extends AppCompatActivity {
    ActivityAddClassBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddClassBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.header.back.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });

        binding.header.tittle.setText("Add A Class");

        binding.save.setOnClickListener(v -> {
            if (binding.className.getEditText().getText().toString().isEmpty()){
                binding.className.getEditText().setError("Please Add A Valid Class Name");
                binding.className.getEditText().requestFocus();
            } else {
                String ID = UUID.randomUUID().toString();
                ClassModel model = new ClassModel(ID, binding.className.getEditText().getText().toString().trim());
                Constants.databaseReference().child(Constants.Class).child(ID)
                        .setValue(model).addOnSuccessListener(unused -> {
                            Toast.makeText(this, "Class Added", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }).addOnFailureListener(e -> {
                            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}