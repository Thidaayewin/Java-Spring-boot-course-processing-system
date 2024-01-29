
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
@DiscriminatorValue("Lecturer")
@Data
public class Lecturer extends AccountHolder {
        @OneToMany(targetEntity = LecturerCohort.class, mappedBy = "lecturer", fetch = FetchType.EAGER)
        private List<LecturerCohort> cohortsTaught;

        @OneToMany(targetEntity = StudentCohort.class, mappedBy = "gradedBy", fetch = FetchType.EAGER)
        private List<StudentCohort> cohortsGrades;

        public Lecturer() {
        }

        public Lecturer(
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
                super(false, createdBy, createdTime, lastUpdatedBy, lastUpdatedTime, firstName, lastName, email,
                                password, phone, userNumber);
                this.cohortsTaught = new ArrayList<>();
                this.cohortsGrades = new ArrayList<>();
        }

        @Override
        public String toString() {

                return "Lecturer" +
                                "\n\t{cohortsTaught" +
                                cohortsTaught.stream().map(x -> String.format("\n\t-Course Name=%s, Cohort Name=%s",
                                                x.getCohort().getCourseType().getName(),
                                                x.getCohort().getName()))
                                                .reduce("", (s1, s2) -> s1 + s2)
                                +
                                '}' +
                                "\n\t" +
                                super.toString();
        }
}
