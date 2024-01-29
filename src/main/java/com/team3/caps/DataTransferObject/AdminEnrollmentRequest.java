package com.team3.caps.DataTransferObject;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class AdminEnrollmentRequest {
   

    private Integer studentCohortId;

    @NotBlank(message="Name is required")
    private String studentName;


    private String matriculationNumber;

    @NotBlank(message="Course Name is required")
    private String courseName;

    @NotBlank(message="Description is required")
    private String description;

    private LocalDateTime cohortDate;

    private String classDay;
    
    private String classSlot;
    
    @Min(value = 1, message = "Capacity should be at least 1")
    private int capacity;
    
    @Min(value = -1, message = "Value of -1 for unsubmitted marks")
    private double score;
    
    @NotBlank(message="Enrollment Status is required")
    private String enrollmentStatus;
    
    private String gradeValue;
    
    @Min(value = -1, message = "Value of -1 for unsubmitted marks")
    private Integer marks;

    public Integer getStudentCohortId() {
        return studentCohortId;
    }

    public void setStudentCohortId(Integer studentCohortId) {
        this.studentCohortId = studentCohortId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getMatriculationNumber() {
        return matriculationNumber;
    }

    public void setMatriculationNumber(String matriculationNumber) {
        this.matriculationNumber = matriculationNumber;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCohortDate() {
        return cohortDate;
    }

    public void setCohortDate(LocalDateTime cohortDate) {
        this.cohortDate = cohortDate;
    }

    public String getClassDay() {
        return classDay;
    }

    public void setClassDay(String classDay) {
        this.classDay = classDay;
    }

    public String getClassSlot() {
        return classSlot;
    }

    public void setClassSlot(String classSlot) {
        this.classSlot = classSlot;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getEnrollmentStatus() {
        return enrollmentStatus;
    }

    public void setEnrollmentStatus(String enrollmentStatus) {
        this.enrollmentStatus = enrollmentStatus;
    }

    public String getGradeValue() {
        return gradeValue;
    }

    public void setGradeValue(String gradeValue) {
        this.gradeValue = gradeValue;
    }

    public Integer getMarks() {
        return marks;
    }

    public void setMarks(Integer marks) {
        this.marks = marks;
    }

    public AdminEnrollmentRequest(Integer studentCohortId, String studentName, String matriculationNumber,
                                  String courseName, String description, LocalDateTime cohortDate, String classDay, String classSlot,
                                  int capacity, double score, String enrollmentStatus, String gradeValue, Integer marks) {
        this.studentCohortId = studentCohortId;
        this.studentName = studentName;
        this.matriculationNumber = matriculationNumber;
        this.courseName = courseName;
        this.description = description;
        this.cohortDate = cohortDate;
        this.classDay = classDay;
        this.classSlot = classSlot;
        this.capacity = capacity;
        this.score = score;
        this.enrollmentStatus = enrollmentStatus;
        this.gradeValue = gradeValue;
        this.marks = marks;
    }

    public AdminEnrollmentRequest() {
    }

}



