package com.team3.caps.service;

import com.team3.caps.model.StudentCohort;
import com.team3.caps.repository.StudentCohortRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentCohortService {

    private StudentCohortRepository studentCohortRepository;

    @Autowired
    public StudentCohortService(StudentCohortRepository studentCohortRepository) {
        this.studentCohortRepository = studentCohortRepository;
    }

    public List<StudentCohort> findAll(){
        return studentCohortRepository.findAll();
    }

    public List<StudentCohort> findStudentCohortByStudentId(int id){
        return studentCohortRepository.getStudentCohortByStudentId(id);
    }

    public StudentCohort findById(int id){
        return studentCohortRepository.findById(id).get();
    }

    public StudentCohort findByStudentIdAndCohortId(int studentId, int cohortId){
        return studentCohortRepository.findByStudentIdAndCohortId(studentId,cohortId);
    }

    public StudentCohort save(StudentCohort studentCohort){
        return studentCohortRepository.save(studentCohort);
    }

}
