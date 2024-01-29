package com.team3.caps.service.impl;

import com.team3.caps.util.EnrolmentStatus;
import com.team3.caps.util.GradingScheme;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.team3.caps.service.StudentGradeService;
import com.team3.caps.repository.StudentCohortRepository;
import com.team3.caps.repository.StudentRepository;
import com.team3.caps.security.SecurityUtil;
import com.team3.caps.model.Student;
import com.team3.caps.model.StudentCohort;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class StudentGradeServiceImpl implements StudentGradeService {

    private StudentCohortRepository studentCohortRepo;
    private StudentRepository studentRepo;

    @Autowired
    public StudentGradeServiceImpl(StudentCohortRepository studentCohortRepo, StudentRepository studentRepo) {
        this.studentCohortRepo = studentCohortRepo;
        this.studentRepo = studentRepo;
    }

    @Override
    public Map<String, String> viewGrade() {
        String session = SecurityUtil.getSessionUser();
        Student s = studentRepo.findByEmailandIsNotDeleted(session);

        List<StudentCohort> studentCohorts = studentCohortRepo.findByStudent(s.getId());

        if (studentCohorts != null) {
            Map<String, String> grades = new HashMap<>();

            for (StudentCohort stuCohort : studentCohorts) {
                if (stuCohort.getEnrolmentStatus() != EnrolmentStatus.REMOVED){
                    double score = stuCohort.getScore();
                    String courseName = stuCohort.getCohort().getDescription();

                    if (score == -1.0) {
                        grades.put(courseName, "UNGRADED");
                    } else {
                        grades.put(courseName, String.valueOf(score));
                    }
                }
                

                // if (grades.containsKey(courseName)) {
                //     //only update if it was ungraded in a previous iteration
                //     if (grades.get(courseName).equals("UNGRADED")) {
                //         grades.put(courseName, String.valueOf(score));
                //     }
                // } else {
                //     //set does not contain the key
                //     if (score == -1.0) {
                //         grades.put(courseName, "UNGRADED");
                //     } else {
                //         grades.put(courseName, String.valueOf(score));
                //     }       
                // }

            }
            return grades;
        }
        return Collections.emptyMap();

    }

    @Override
    public Double viewGradePointAverage() {
        String session = SecurityUtil.getSessionUser();
        Student s = studentRepo.findByEmailandIsNotDeleted(session);

        List<StudentCohort> studentCohorts = studentCohortRepo.findByStudent(s.getId());

        return GradingScheme.calculateGPA(studentCohorts);

    }


}
