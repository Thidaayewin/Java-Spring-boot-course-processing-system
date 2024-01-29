package com.team3.caps.DataTransferObject;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class AdminCourseRequest {
    private int id;
    @NotBlank(message = "Course name required")
    private String courseName;
    @Min(value = 1, message = "Credits need to be at least 1")
    private int credits;

    // Getters and setters

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
