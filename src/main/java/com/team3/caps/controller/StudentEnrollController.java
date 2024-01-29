package com.team3.caps.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.team3.caps.exception.EnrollmentException;
import com.team3.caps.model.Cohort;
import com.team3.caps.DataTransferObject.EnrollStudentRequest;
import com.team3.caps.model.Student;
import com.team3.caps.repository.CohortRepository;
import com.team3.caps.repository.StudentRepository;
import com.team3.caps.security.SecurityUtil;
import com.team3.caps.service.EmailService;
import com.team3.caps.service.StudentRegisterService;

import jakarta.mail.MessagingException;

@Controller
@RequestMapping(value = "/api/students")
public class StudentEnrollController {

    @Autowired
    private final StudentRegisterService stuRegSvc;

    @Autowired
    private CohortRepository cohortRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private final EmailService emailService;

    public StudentEnrollController(StudentRegisterService stuRegSvc, EmailService emailService) {
        this.stuRegSvc = stuRegSvc;
        this.emailService = emailService;
    }

    @RequestMapping(value = "/enroll", method = { RequestMethod.POST, RequestMethod.GET })
    public ResponseEntity<String> enrollStudent(@RequestParam("cohortId") int cohortId,
            @RequestBody EnrollStudentRequest enrollRequest) {
        // Cohort cohort = enrollRequest.getCohort();
        // Student student = enrollRequest.getStudent();
        // String enrollmentStatus = enrollRequest.getEnrollmentStatus();
        // long cohortId = cohort.getId();
        // Course course = enrollRequest.getCourse();
        String email = SecurityUtil.getSessionUser();
        Cohort c = cohortRepository.findByIdAndIsDeleted(cohortId, false);
        String courseName = c.getCourseType().getName();
        // String courseName = enrollRequest.getCohort().getCourseType().getName();
        Student s = studentRepository.findByEmailAndIsDeleted(email, false);
        String studentName = s.getFirstName();
        try {
            stuRegSvc.enrollCourse(cohortId);
            emailService.sendSuccessfulEnrollmentEmail(email.toLowerCase(), studentName, courseName);
            return ResponseEntity.ok("You have successfully enrolled");
        } catch (EnrollmentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (MessagingException em) {
            return ResponseEntity.badRequest().body(em.getMessage());
        } catch (IOException ei) {
            return ResponseEntity.badRequest().body(ei.getMessage());
        }

    }

}
