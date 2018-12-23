package com.isumalab.learn;

//This is for course details in short to display in recycler view

import java.time.LocalDateTime;
import java.util.List;

public class Course {
    private String courseName;
    private List<Integer> finishedLessonList;
    private LocalDateTime enrolledDate;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public List<Integer> getFinishedLessonList() {
        return finishedLessonList;
    }

    public void setFinishedLessonList(List<Integer> finishedLessonList) {
        this.finishedLessonList = finishedLessonList;
    }

    public LocalDateTime getEnrolledDate() {
        return enrolledDate;
    }

    public void setEnrolledDate(LocalDateTime enrolledDate) {
        this.enrolledDate = enrolledDate;
    }
}
