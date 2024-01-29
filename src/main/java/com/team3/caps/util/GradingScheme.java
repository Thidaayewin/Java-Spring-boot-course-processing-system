package com.team3.caps.util;

import com.team3.caps.model.Cohort;
import com.team3.caps.model.Course;
import com.team3.caps.model.StudentCohort;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GradingScheme {

    public static double calculateGPA(List<StudentCohort> studentCohorts){
        if (studentCohorts != null) {
            HashMap<Cohort, Double> gradeCredit = new HashMap<>();


            //only if the score is 0 and above, means the lecturer has already graded the student
            //then include in calculations
            for (StudentCohort stuCohort : studentCohorts) {
            double score = stuCohort.getScore();

            if (score >= 0.0) {
                gradeCredit.put(stuCohort.getCohort(), score);
            }
        }

            

            double weightedGrade = 0.0;
            int totalCredit = 0;

            for (Map.Entry<Cohort, Double> entry : gradeCredit.entrySet()) {
                Cohort cohort = entry.getKey();
                double score = entry.getValue();

                if (score == -1.0) {
                    continue;
                }

                double gradePoints = 0;

                if (score > 80) {
                    gradePoints = 4.0;
                } else if (score >= 75 && score < 80) {
                    gradePoints = 3.5;
                } else if (score >= 70 && score < 75) {
                    gradePoints = 3.0;
                } else if (score >= 65 && score < 70) {
                    gradePoints = 2.5;
                } else if (score >= 60 && score < 65) {
                    gradePoints = 2.0;
                } else if (score >= 55 && score < 60) {
                    gradePoints = 1.5;
                } else if (score >= 50 && score < 55) {
                    gradePoints = 1.0;
                } else if (score >= 45 && score < 50) {
                    gradePoints = 0.5;
                } else gradePoints = 0.0;

                Course course = cohort.getCourseType();

                int credit = course.getCredits();

                weightedGrade += gradePoints * credit;
                totalCredit += credit;
            }

            if (totalCredit > 0) {
                return weightedGrade / totalCredit;
            } else {
                return 0.0;
            }
        }
        return 0.0;
    }
}
