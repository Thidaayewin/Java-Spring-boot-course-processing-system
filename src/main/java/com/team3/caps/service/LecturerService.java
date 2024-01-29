package com.team3.caps.service;

import java.util.List;

import com.team3.caps.util.GradingScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team3.caps.model.Course;
import com.team3.caps.model.Student;
import com.team3.caps.model.StudentCohort;
import com.team3.caps.repository.CourseRepository;
import com.team3.caps.repository.LecturerRepository;
import com.team3.caps.repository.StudentCohortRepository;
import com.team3.caps.repository.StudentRepository;
import com.team3.caps.security.SecurityUtil;

@Service
public class LecturerService {

    private final LecturerRepository lecturerRepository;
    private final StudentRepository stuRepo;
    private final StudentCohortRepository stuCohortRepo;
    private final CourseRepository courseRepo;

    @Autowired
    public LecturerService(StudentRepository stuRepo, StudentCohortRepository stuCohortRepo,
            LecturerRepository lecturerRepository, CourseRepository courseRepo) {
        this.stuRepo = stuRepo;
        this.stuCohortRepo = stuCohortRepo;
        this.lecturerRepository = lecturerRepository;
        this.courseRepo = courseRepo;
    }

    public List<Student> findAllStudents() {
        return stuRepo.findAll();
    }

    public List<Course> viewLecturerCourses() {
        String user = SecurityUtil.getSessionUser();
        return lecturerRepository.findLecturerCoursesByEmail(user);
    }

    public int viewCourseEnrollment(long cohortId) {
        return lecturerRepository.findCourseEnrollmentByCohortId(cohortId);
    }

    public List<Student> viewStudentsEnrolled(int cohortId) {
        List<Student> returnedStudents = lecturerRepository.findStudentsByCohortId(cohortId);
        return returnedStudents;
    }

    public StudentCohort getStudentCohortByStudentAndCohort(int studentId, int cohortId) {

        return stuCohortRepo.getStudentCohortByStudentAndCohort(studentId, cohortId);
    }

    public Course getCourseByCourseId(int courseId) {
        return courseRepo.findCourseByCourseId(courseId);
    }

    public Double lecturerViewGradePointAverage(int studentId) {
        // String session = SecurityUtil.getSessionUser();
        // Student s = stuRepo.findByEmailandIsNotDeleted(session);

        List<StudentCohort> studentCohorts = stuCohortRepo.findByStudent(studentId);

        return GradingScheme.calculateGPA(studentCohorts);

    }
}
