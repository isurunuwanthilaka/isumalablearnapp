package com.isumalab.learn.adapters;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.isumalab.learn.models.Course;
import com.isumalab.learn.activities.CourseListActivity;
import com.isumalab.learn.R;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.MyViewHolder> {

    private List<Course> coursesList;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView playListName,persentage;

        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            playListName = (TextView) view.findViewById(R.id.playListName);
            persentage = (TextView) view.findViewById(R.id.percentage);
        }

        @Override
        public void onClick(View view) {
            Course course = coursesList.get(getLayoutPosition());
            Bundle bundle = new Bundle();
            Intent intent = new Intent(view.getContext(), CourseListActivity.class);
            bundle.putString("playListName",course.getCourseName());
            bundle.putString("code",course.getCode());
//            bundle.putIntArray();
            intent.putExtras(bundle);
            view.getContext().startActivity(intent);
        }
    }

    public CourseAdapter(List<Course> coursesList) {
        this.coursesList = coursesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Course course = coursesList.get(position);
        holder.playListName.setText(course.getCourseName());
        holder.persentage.setText("4");
    }

    @Override
    public int getItemCount() {
        return coursesList.size();
    }
}
