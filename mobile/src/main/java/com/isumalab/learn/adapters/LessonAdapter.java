package com.isumalab.learn.adapters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.isumalab.learn.models.Lesson;
import com.isumalab.learn.R;
import com.isumalab.learn.activities.YoutubeCourseActivity;

import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.MyViewHolder> {

    private List<Lesson> lessonsList;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name, category;

        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            name = (TextView) view.findViewById(R.id.lesson_name);
            category = (TextView) view.findViewById(R.id.lesson_category);
        }

        @Override
        public void onClick(View view) {
            Lesson lesson = lessonsList.get(getLayoutPosition());

            Bundle bundle = new Bundle();
            Intent intent = new Intent(view.getContext(), YoutubeCourseActivity.class);
            bundle.putString("url", lesson.getUrl());
            bundle.putString("title",lesson.getName());
            bundle.putString("courseID",lesson.getCourseID());
            bundle.putString("key",lesson.getKey());
            intent.putExtras(bundle);
            view.getContext().startActivity(intent);
        }
    }

    public LessonAdapter(List<Lesson> lessonsList) {
        this.lessonsList = lessonsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_lesson_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Lesson lesson = lessonsList.get(position);
        System.out.println("Lesson Name" + lesson.getName());
        holder.name.setText(lesson.getName());
        holder.category.setText(lesson.getCategory());
    }

    @Override
    public int getItemCount() {
        return lessonsList.size();
    }
}