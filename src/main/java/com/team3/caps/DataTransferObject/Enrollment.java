package com.team3.caps.DataTransferObject;

import com.team3.caps.model.Cohort;
import com.team3.caps.model.Course;
import com.team3.caps.model.Student;
import com.team3.caps.model.StudentCohort;
import com.team3.caps.util.EnrolmentStatus;

public class Enrollment {
	private Integer id;
    private Student student;
	private Course course;
    private StudentCohort studentcohort;
	private Integer marks;
    private String gradeValue;
    private String enrollmentStatus;
    

    public Enrollment(Integer id, Student student, Course course, StudentCohort studentcohort, Integer marks,
            String gradeValue, String enrollmentStatus) {
        this.id = id;
        this.student = student;
        this.course = course;
        this.studentcohort = studentcohort;
        this.marks = marks;
        this.gradeValue = gradeValue;
        this.enrollmentStatus = enrollmentStatus;
    }
    
    public Student getStudent() {
        return student;
    }
    public void setStudent(Student student) {
        this.student = student;
    }
    public Course getCourse() {
        return course;
    }
    public void setCourse(Course course) {
        this.course = course;
    }
    public Integer getMarks() {
        return marks;
    }
    public void setMarks(Integer marks) {
        this.marks = marks;
    }
    public String getGradeValue() {
        return gradeValue;
    }
    public void setGradeValue(String gradeValue) {
        this.gradeValue = gradeValue;
    }
    public Enrollment(Integer id,Student student, Course course, Integer marks, String gradeValue) {
        this.student = student;
        this.course = course;
        this.marks = marks;
        this.gradeValue = gradeValue;
    }
    public Enrollment(Student student2, Cohort cohort, EnrolmentStatus enrolled) {
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getEnrollmentStatus() {
        return enrollmentStatus;
    }
    public void setEnrollmentStatus(String enrollmentStatus) {
        this.enrollmentStatus = enrollmentStatus;
    }
    public StudentCohort getStudentcohort() {
        return studentcohort;
    }
    public void setStudentcohort(StudentCohort studentcohort) {
        this.studentcohort = studentcohort;
    }

    
}



