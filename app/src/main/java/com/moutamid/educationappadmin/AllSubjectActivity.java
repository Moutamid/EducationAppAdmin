package com.moutamid.educationappadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.educationappadmin.adapter.SubjectAdapter;
import com.moutamid.educationappadmin.databinding.ActivityAllSubjectBinding;
import com.moutamid.educationappadmin.model.SubjectModel;
import com.moutamid.educationappadmin.utlis.Constants;

import java.util.ArrayList;

public class AllSubjectActivity extends AppCompatActivity {
    ActivityAllSubjectBinding binding;
    ArrayList<SubjectModel> list;
    ArrayList<SubjectModel> listAll;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllSubjectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        list = new ArrayList<>();
        listAll = new ArrayList<>();

        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        binding.recycler.setHasFixedSize(false);

        binding.header.back.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });

        Constants.databaseReference().child(Constants.Subject).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    list.clear();
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                        for (DataSnapshot dataSnapshot2: dataSnapshot.getChildren()) {
                            SubjectModel model = dataSnapshot2.getValue(SubjectModel.class);
                            list.add(model);
                        }
                    }
                    getCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
                Toast.makeText(AllSubjectActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getCount() {
        for (int i =0; i<list.size(); i++) {
            SubjectModel model = list.get(i);
            Constants.databaseReference().child(Constants.Quiz).child(model.getID())
                    .get().addOnSuccessListener(dataSnapshot1 -> {
                        if (dataSnapshot1.exists()){
                            SubjectModel subjectModel = new SubjectModel(model.getID(), model.getName(), model.getClassName (), (int) dataSnapshot1.getChildrenCount());
                            listAll.add(subjectModel);
                        } else {
                            SubjectModel subjectModel = new SubjectModel(model.getID(), model.getName(), model.getClassName (), 0);
                            listAll.add(subjectModel);
                        }
                        SubjectAdapter adapter = new SubjectAdapter(AllSubjectActivity.this, listAll);
                        binding.recycler.setAdapter(adapter);
                        progressDialog.dismiss();
                    }).addOnFailureListener(e -> {

                    });
        }

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}