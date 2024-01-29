package com.team3.caps.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


//rename? TeacherAssignment? Or nah
@Entity
@Data
public class LecturerCohort extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Lecturer lecturer;

    @ManyToOne
    private Cohort cohort;

    public LecturerCohort() {}

    public LecturerCohort(
            String createdBy,
            LocalDateTime createdTime,
            String lastUpdatedBy,
            LocalDateTime lastUpdatedTime,
            Lecturer lecturer,
            Cohort cohort) {
        super(false, createdBy, createdTime, lastUpdatedBy, lastUpdatedTime);
        this.lecturer = lecturer;
        this.cohort = cohort;
    }

    @Override
    public String toString() {
        return "LecturerCohort{" +
                "id=" + id +
                ", lecturer=" + lecturer.getFirstName() +" "+ lecturer.getLastName()   +
                ", cohort=" + cohort.getName() +
                '}' +
                "\n\t"+
                super.toString();
    }
}
