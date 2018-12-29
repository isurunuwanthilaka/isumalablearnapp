package com.isumalab.learn.DTO;

import java.util.Date;

public class enrollCourseToUserDTO {

    private String done;
    private Date enrolledDate;
    private String playListName;

    public enrollCourseToUserDTO() {
    }

    public enrollCourseToUserDTO(String done, Date enrolledDate, String playListName) {
        this.done = done;
        this.enrolledDate = enrolledDate;
        this.playListName = playListName;
    }

    public String getDone() {
        return done;
    }

    public void setDone(String done) {
        this.done = done;
    }

    public Date getEnrolledDate() {
        return enrolledDate;
    }

    public void setEnrolledDate(Date enrolledDate) {
        this.enrolledDate = enrolledDate;
    }

    public String getPlayListName() {
        return playListName;
    }

    public void setPlayListName(String playListName) {
        this.playListName = playListName;
    }
}
