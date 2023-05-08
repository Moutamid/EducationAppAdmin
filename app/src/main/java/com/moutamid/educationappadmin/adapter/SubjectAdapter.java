package com.moutamid.educationappadmin.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moutamid.educationappadmin.AddQuizActivity;
import com.moutamid.educationappadmin.R;
import com.moutamid.educationappadmin.model.SubjectModel;
import com.moutamid.educationappadmin.utlis.Constants;

import java.util.ArrayList;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectVH> {

    Context context;
    ArrayList<SubjectModel> list;

    public SubjectAdapter(Context context, ArrayList<SubjectModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SubjectVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SubjectVH(LayoutInflater.from(context).inflate(R.layout.subject_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectVH holder, int position) {
        SubjectModel model = list.get(holder.getAdapterPosition());
        holder.name.setText("Subject : " + model.getName());
        holder.quiz.setText("No# Quiz : " + model.getCount());

        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, AddQuizActivity.class);
            i.putExtra(Constants.ID, model.getID());
            i.putExtra(Constants.NAME, model.getName());
            context.startActivity(i);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SubjectVH extends RecyclerView.ViewHolder{
        TextView name, quiz;
        public SubjectVH(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.subjectName);
            quiz = itemView.findViewById(R.id.quiz);
        }
    }

}
