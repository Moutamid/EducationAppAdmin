package com.moutamid.educationappadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.moutamid.educationappadmin.databinding.ActivityAddQuizBinding;
import com.moutamid.educationappadmin.model.QuizModel;
import com.moutamid.educationappadmin.utlis.Constants;

import java.util.UUID;

public class AddQuizActivity extends AppCompatActivity {
    ActivityAddQuizBinding binding;
    String ID, NAME;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");

        binding.header.back.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });

        ID = getIntent().getStringExtra(Constants.ID);
        NAME = getIntent().getStringExtra(Constants.NAME);

        binding.header.tittle.setText("Add A Quiz");

        binding.save.setOnClickListener(v -> {
            if (valid()) {
                progressDialog.show();
                String uid = UUID.randomUUID().toString();
                QuizModel model = new QuizModel(
                        uid,
                        binding.quizName.getEditText().getText().toString(),
                        binding.question.getEditText().getText().toString(),
                        binding.answer1.getEditText().getText().toString(),
                        binding.answer2.getEditText().getText().toString(),
                        binding.answer3.getEditText().getText().toString(),
                        binding.answer4.getEditText().getText().toString(),
                        binding.answerCorrect.getEditText().getText().toString(),
                        ID, NAME, binding.isMcqs.isChecked());

                Constants.databaseReference().child(Constants.Quiz)
                        .child(ID).child(uid).setValue(model)
                        .addOnSuccessListener(unused -> {
                            progressDialog.dismiss();
                            Toast.makeText(this, "Quiz Added", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }).addOnFailureListener(e -> {
                            progressDialog.dismiss();
                            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        });

    }

    private boolean valid() {

        if (binding.quizName.getEditText().getText().toString().isEmpty()){
            binding.quizName.getEditText().requestFocus();
            binding.quizName.getEditText().setError("Required");
            return false;
        }
        if (binding.question.getEditText().getText().toString().isEmpty()){
            binding.question.getEditText().requestFocus();
            binding.question.getEditText().setError("Required");
            return false;
        }
        if (binding.answer1.getEditText().getText().toString().isEmpty()){
            binding.answer1.getEditText().requestFocus();
            binding.answer1.getEditText().setError("Required");
            return false;
        }
        if (binding.answer2.getEditText().getText().toString().isEmpty()){
            binding.answer2.getEditText().requestFocus();
            binding.answer2.getEditText().setError("Required");
            return false;
        }
        if (binding.answerCorrect.getEditText().getText().toString().isEmpty()){
            binding.answerCorrect.getEditText().requestFocus();
            binding.answerCorrect.getEditText().setError("Required");
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