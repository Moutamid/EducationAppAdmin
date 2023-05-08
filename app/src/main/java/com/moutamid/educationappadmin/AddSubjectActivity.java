package com.moutamid.educationappadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.moutamid.educationappadmin.databinding.ActivityAddSubjectBinding;
import com.moutamid.educationappadmin.model.ClassModel;
import com.moutamid.educationappadmin.model.SubjectModel;
import com.moutamid.educationappadmin.utlis.Constants;

import java.util.ArrayList;
import java.util.UUID;
import java.util.function.BiConsumer;

public class AddSubjectActivity extends AppCompatActivity {
    ActivityAddSubjectBinding binding;

    ArrayAdapter<String> spinMakeAdapter;
    ArrayList<String> classList;
    ArrayList<String> classIDs;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddSubjectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();


        binding.header.back.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });

        binding.header.tittle.setText("Add A Subject");

        classList = new ArrayList<>();
        classIDs = new ArrayList<>();

        classList.add("Select Class");

        Constants.databaseReference().child(Constants.Class).get().addOnSuccessListener(dataSnapshot -> {
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ClassModel model = snapshot.getValue(ClassModel.class);
                    classList.add(model.getName());
                    classIDs.add(model.getID());
                }
            }
            progressDialog.dismiss();
            spinMakeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, classList);
            spinMakeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.classSpinner.setAdapter(spinMakeAdapter);

        }).addOnFailureListener(e -> {
            progressDialog.dismiss();
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        });


        binding.save.setOnClickListener(v -> {
            if (valid()) {
                String ID = UUID.randomUUID().toString();
                SubjectModel model = new SubjectModel(ID,
                        binding.subjectName.getEditText().getText().toString().trim(),
                        classList.get(binding.classSpinner.getSelectedItemPosition()), 0
                );
                String classId = classIDs.get(binding.classSpinner.getSelectedItemPosition()-1);
                Constants.databaseReference().child(Constants.Subject).child(classId).child(ID).setValue(model)
                        .addOnSuccessListener(unused -> {
                            Toast.makeText(this, "Subject Added", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }).addOnFailureListener(e -> {
                            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        });

    }

    private boolean valid() {
        if (binding.subjectName.getEditText().getText().toString().isEmpty()) {
            binding.subjectName.getEditText().setError("Please Add A Valid Subject Name");
            binding.subjectName.getEditText().requestFocus();
            return false;
        }
        if (binding.classSpinner.getSelectedItemPosition() == 0){
            Toast.makeText(this, "Please Add a class", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}