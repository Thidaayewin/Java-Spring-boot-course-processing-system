package com.team3.caps.model;

import com.team3.caps.util.EnrolmentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

//rename to EnrollmentGrades
//this class is the owner of the relationships
@Entity
@Data
public class StudentCohort extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Student student;

    @ManyToOne
    @JoinColumn(name = "cohort_id")
    private Cohort cohort;

    @Enumerated(EnumType.STRING)
    private EnrolmentStatus enrolmentStatus;

    @Min(value = -1, message = "Value of -1 for unsubmitted marks")
    private double score;

    @ManyToOne
    private Lecturer gradedBy;

    public StudentCohort() {}

    public StudentCohort(
            String createdBy,
            LocalDateTime createdTime,
            String lastUpdatedBy,
            LocalDateTime lastUpdatedTime,
            Student student,
            Cohort cohort,
            EnrolmentStatus enrolmentStatus) {
        super(false, createdBy, createdTime, lastUpdatedBy, lastUpdatedTime);
        this.student = student;
        this.cohort = cohort;
        this.enrolmentStatus = enrolmentStatus;
        //date enrolled is createdTime
        this.score = -1;    //-1 means ungraded since double is non-nullable
        this.gradedBy = null;   //not graded yet
    }

    @Override
    public String toString() {
        return "StudentCohort{" +
                "id=" + id +
                ", student=" + student.getFirstName() +" "+ student.getLastName()  +
                ", cohort=" + cohort.getName() +
                ", enrolmentStatus=" + enrolmentStatus +
                ", score=" + score +
                ", gradedBy=" + gradedBy.getFirstName() +" "+ gradedBy.getLastName() +
                '}' +
                "\n\t"+
                super.toString();
    }
    public double getScore() {
            return score;
}

public double setSc() {
            return score;
}

    public void addAttribute(String string, List<StudentCohort> stuScore) {
    }
}
