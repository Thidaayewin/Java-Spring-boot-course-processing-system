package com.team3.caps.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@DiscriminatorValue("Student")
@Data
public class Student extends AccountHolder {
    @OneToMany(targetEntity = StudentCohort.class, mappedBy = "student", fetch = FetchType.EAGER)
    private List<StudentCohort> cohortsEnrolled;

    public Student() {
    }

    public Student(
            String createdBy,
            LocalDateTime createdTime,
            String lastUpdatedBy,
            LocalDateTime lastUpdatedTime,
            String firstName,
            String lastName,
            String email,
            String password,
            String phone,
            String userNumber) {
        super(false, createdBy, createdTime, lastUpdatedBy, lastUpdatedTime, firstName, lastName, email, password,
                phone, userNumber);
        this.cohortsEnrolled = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Student" +
                "\n\t{cohortsEnrolled=" +
                cohortsEnrolled.stream()
                        .map(x -> String.format("\n\t-Course Name=%s, Cohort Name=%s, Score=%f Enrolment Status=%s",
                                x.getCohort().getCourseType().getName(),
                                x.getCohort().getName(),
                                x.getScore(),
                                x.getEnrolmentStatus()))
                        .reduce("", (s1, s2) -> s1 + s2)
                +
                '}' +
                "\n\t" +
                super.toString();
    }

    // adding getter setter on 15 june by Allie
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public LocalDateTime getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(LocalDateTime lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    // public double getScore() {
    // return getScore();
    // }

}
