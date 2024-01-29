package com.team3.caps.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team3.caps.model.Course;
import com.team3.caps.model.Student;
import com.team3.caps.exception.EnrollmentException;
import com.team3.caps.model.Cohort;
import com.team3.caps.model.StudentCohort;
import com.team3.caps.model.Tag;
import com.team3.caps.repository.CohortRepository;
import com.team3.caps.repository.CourseRepository;
import com.team3.caps.repository.StudentCohortRepository;
import com.team3.caps.repository.StudentRepository;
import com.team3.caps.security.SecurityUtil;
import com.team3.caps.service.StudentRegisterService;
import com.team3.caps.util.EnrolmentStatus;

@Service
public class StudentRegisterServiceImpl implements StudentRegisterService {

    private StudentRepository studentRepo;
    private CourseRepository courseRepo;
    private StudentCohortRepository studentCohortRepo;
    private CohortRepository cohortRepo;

    @Autowired
    public StudentRegisterServiceImpl(StudentRepository studentRepo, CourseRepository courseRepo,
            StudentCohortRepository studentCohortRepo, CohortRepository cohortRepo) {
        this.studentRepo = studentRepo;
        this.courseRepo = courseRepo;
        this.studentCohortRepo = studentCohortRepo;
        this.cohortRepo = cohortRepo;
    }

    @Override
    public void enrollCourse(int cohortId) {
        String session = SecurityUtil.getSessionUser();
        Student s = studentRepo.findByEmailandIsNotDeleted(session);
        Cohort cohort = cohortRepo.findById(cohortId).get();

        boolean isEnrolled = studentCohortRepo.existsByStudentAndCohort(s, cohort);
        if (isEnrolled) {
            throw new EnrollmentException("You are already enrolled");
        }

        boolean isRemoved = studentCohortRepo.existsByStudentAndCohortAndEnrolmentStatus(s, cohort,
                EnrolmentStatus.REMOVED);
        if (isRemoved) {
            throw new EnrollmentException("You are unable to enroll as you have been removed by the admin");
        }

        int capacity = cohort.getCapacity();
        // int studentId = s.getId();

        if (capacity > 0) {
            capacity--;
            cohort.setCapacity(capacity);

            StudentCohort newStudent = new StudentCohort();

            newStudent.setCohort(cohort);
            newStudent.setCreatedTime(LocalDateTime.now());
            newStudent.setEnrolmentStatus(EnrolmentStatus.ENROLLED);
            newStudent.setLastUpdatedTime(LocalDateTime.now());
            newStudent.setCreatedBy(session);
            newStudent.setLastUpdatedBy(session);
            newStudent.setStudent(s);
            newStudent.setScore(-1.0);
            studentCohortRepo.saveAndFlush(newStudent);
        } else
            throw new EnrollmentException("Course is already full");
    }

    @Override
    public List<Course> viewAllCourses() {
        String session = SecurityUtil.getSessionUser();
        Student s = studentRepo.findByEmailandIsNotDeleted(session);

        List<Course> allCourses = courseRepo.findAll();

        // Optional<Student> studentOptional = studentRepo.findById(studentId);

        // if(studentOptional.isPresent()){
        // Student student = studentOptional.get();
        // // use custom query in the repo
        // List<StudentCohort> enrolledStudentCohorts = studentRepo.findBy

        List<StudentCohort> enrolledStudentCohorts = s.getCohortsEnrolled();

        if (enrolledStudentCohorts != null) {
            List<Course> enrolledCourses = enrolledStudentCohorts.stream()
                    // .filter(studentCohort ->
                    // studentCohort.getEnrolmentStatus()!=EnrolmentStatus.REMOVED)
                    .map(studentCohort -> studentCohort.getCohort().getCourseType()).collect(Collectors.toList());
            List<Course> availableCourses = allCourses.stream().filter(course -> !enrolledCourses.contains(course))
                    .collect(Collectors.toList());
            return availableCourses;
        }

        return allCourses;
    }

    @Override
    public List<Cohort> getCohorts(int courseId) {

        Optional<Course> courseOptional = courseRepo.findById(courseId);

        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            List<Cohort> cohorts = course.getCohorts();

            cohorts = cohorts.stream().filter(c -> c.getCohortStart().isAfter(LocalDateTime.now()))
                    .collect(Collectors.toList());

            return cohorts;
        }
        return Collections.emptyList();
    }

    @Override
    public List<Course> viewCourseRecommendations(int studentId) {
        Optional<Student> studentOptional = Optional.ofNullable(studentRepo.findById(studentId));

        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            List<StudentCohort> enrolledStudentCohorts = student.getCohortsEnrolled();

            List<Tag> courseTags = new ArrayList<>();

            for (StudentCohort enrolledStudentCohort : enrolledStudentCohorts) {
                courseTags.addAll(enrolledStudentCohort.getCohort().getCourseType().getTags());
            }

            List<Course> allCourses = courseRepo.findAll();
            List<Course> recommendedCourses = new ArrayList<>();

            for (Course course : allCourses) {

                List<Tag> courseTagsToCheck = course.getTags();

                if (courseTagsToCheck.retainAll(courseTags)) {
                    recommendedCourses.add(course);
                }
            }
            return recommendedCourses;
        }

        return Collections.emptyList();
    }

}
