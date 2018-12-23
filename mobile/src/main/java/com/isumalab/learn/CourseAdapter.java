package com.isumalab.learn;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.MyViewHolder> {

    private List<Course> coursesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView playListName,persentage;

        public MyViewHolder(View view) {
            super(view);
            playListName = (TextView) view.findViewById(R.id.playListName);
            persentage = (TextView) view.findViewById(R.id.percentage);
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
