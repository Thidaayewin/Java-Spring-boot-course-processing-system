package com.team3.caps.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.team3.caps.DataTransferObject.AdminCohortRequest;
import com.team3.caps.DataTransferObject.AdminCourseRequest;
import com.team3.caps.DataTransferObject.AdminEnrollmentRequest;
import com.team3.caps.DataTransferObject.AdminEnrollmentStatusRequest;
import com.team3.caps.DataTransferObject.AdminLecturerRequest;
import com.team3.caps.DataTransferObject.AdminStudentRequest;
import com.team3.caps.model.*;

import com.team3.caps.service.AdminServiceImpl;

import java.util.*;
import com.team3.caps.util.*;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_Admin')")
public class AdminController {

  static int pageSize = 5;

  private AdminServiceImpl adminServiceImpl;

  @Autowired
  public AdminController(AdminServiceImpl adminServiceImpl) {
    this.adminServiceImpl = adminServiceImpl;
  }

  @GetMapping("/lecturer/list")
  public String showLecturers(Model model) {

    List<Lecturer> lecturers = adminServiceImpl.findAllLecturers();
    ResponseEntity<List<Lecturer>> responseEntity = new ResponseEntity<>(lecturers, HttpStatus.OK);

    model.addAttribute("lecturers", responseEntity.getBody());
    return "admin/admin-lecturer-view";
  }

  @PostMapping("/lecturer/update")
  public ResponseEntity<String> updateLecturer(@Valid @RequestBody AdminLecturerRequest lecturerRequest) {
    try {
      int lecturerId = lecturerRequest.getId();
      String email = lecturerRequest.getEmail();

      // Retrieve the existing lecturer from the repository based on the ID
      Lecturer optionalLecturer = adminServiceImpl.findLecturerByLecturerId(lecturerId);

      if (optionalLecturer == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
      }

      if (!email.equals(optionalLecturer.getEmail())
          && adminServiceImpl.findLecturerByEmail(email) != null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists");
      }
      adminServiceImpl.updateLecturer(lecturerRequest);
      return ResponseEntity.status(HttpStatus.OK).body("Lecturer updated successfully");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
    }
  }

  @DeleteMapping("/lecturer/delete")
  public ResponseEntity<String> deleteLecturer(@RequestBody AdminLecturerRequest lecturerRequest) {
    try {
      Lecturer existingLecturer = adminServiceImpl.findLecturerByLecturerId(lecturerRequest.getId());
      if (existingLecturer == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
      }
      adminServiceImpl.deleteLecturer(lecturerRequest);
      return ResponseEntity.status(HttpStatus.OK).body("Lecturer deleted successfully");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
    }
  }

  @GetMapping("/course/list")
  public String showCourses(Model model) {
    List<Course> courses = adminServiceImpl.findAllCourses();
    model.addAttribute("courses", courses);
    return "admin/admin-course-view";
  }

  @PostMapping("/course/update")
  public ResponseEntity<String> updateCourse(@Valid @RequestBody AdminCourseRequest courseRequest) {
    try {
      int courseId = courseRequest.getId();
      Course existingCourse = adminServiceImpl.findCourseByCourseId(courseId);
      if (existingCourse == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
      }
      adminServiceImpl.updateExistingCourse(courseRequest);
      return ResponseEntity.status(HttpStatus.OK).body("Course updated successfully");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
    }
  }

  @PostMapping("/course/create")
  public ResponseEntity<String> createCourse(@Valid @RequestBody AdminCourseRequest courseRequest) {
    try {
      String courseName = courseRequest.getCourseName();
      int credits = courseRequest.getCredits();
      Course existingCourse = adminServiceImpl.findCourseByCourseName(courseName);
      if (existingCourse != null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
      }
      adminServiceImpl.saveNewCourse(courseName, credits);
      return ResponseEntity.status(HttpStatus.OK).body("Course created successfully");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
    }
  }

  @DeleteMapping("/course/delete")
  public ResponseEntity<String> deleteCourse(@RequestBody AdminCourseRequest courseRequest) {
    try {
      int courseId = courseRequest.getId();
      Course existingCourse = adminServiceImpl.findCourseByCourseId(courseId);

      if (existingCourse == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
      }
      if (adminServiceImpl.hasEnrollmentByCourseId(existingCourse.getId())) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("Deletion failed, there have enrollment students in this course.");
      }
      adminServiceImpl.deleteExistingCourse(existingCourse);
      return ResponseEntity.status(HttpStatus.OK).body("You have deleted Course");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
    }

  }

  @GetMapping("/course/cohort/list/{id}")
  public String showCohort(@PathVariable Integer id, Model model) {
    List<AdminCohortRequest> cohortList = adminServiceImpl.findCohortsAllByCourseId(id);
    List<Lecturer> lecturersList = adminServiceImpl.findAllLecturers();
    model.addAttribute("cohorts", cohortList);
    model.addAttribute("classDays", ClassDay.values());
    model.addAttribute("classSlots", ClassSlot.values());
    model.addAttribute("courseid", id);
    model.addAttribute("lecturers", lecturersList);
    return "admin/admin-cohort-view";
  }

  @PostMapping("/course/cohort/create")
  public ResponseEntity<String> createCohort(@RequestBody AdminCohortRequest cohortRequest) {
    try {
      adminServiceImpl.createNewCohort(cohortRequest);
      return ResponseEntity.status(HttpStatus.OK).body("Cohort created successfully");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
    }
  }

  @PostMapping("/course/cohort/update")
  public ResponseEntity<String> updateCohort(@RequestBody AdminCohortRequest cohortRequest) {
    try {
      Cohort existingCohort = adminServiceImpl.findCohortByCohortId(cohortRequest.getCohortId());

      if (existingCohort == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
      }
      adminServiceImpl.updateExistingCourseCohort(cohortRequest);
      return ResponseEntity.status(HttpStatus.OK).body("Cohort updated successfully");
    } catch (IncorrectResultSizeDataAccessException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("Updation not allowed. There are existing records of students in this cohort");
    } catch (Exception e) {
      System.out.println(e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
    }

  }

  @DeleteMapping("/course/cohort/delete")
  public ResponseEntity<String> deleteCohort(@RequestBody AdminCohortRequest cohortRequest) {
    try {
      Cohort existingCohort = adminServiceImpl.findCohortByCohortId(cohortRequest.getCohortId());
      if (existingCohort == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
      }
      adminServiceImpl.deleteExistingCourseCohort(cohortRequest);
      return ResponseEntity.status(HttpStatus.OK).body("You have deleted Cohort");
    } catch (IncorrectResultSizeDataAccessException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("Deletion not allowed. There are existing records of students in this cohort");
    } catch (Exception e) {
      System.out.println(e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
    }
  }

  @GetMapping("/enrollment/list")
  public String showEnrollment(Model model) {
    List<AdminEnrollmentRequest> enrollments = adminServiceImpl.findAllEnrollment();
    model.addAttribute("enrollments", enrollments);
    model.addAttribute("enrollmentStatus", EnrolmentStatus.values());
    return "admin/admin-enrollment-view";
  }

  @PostMapping("/enrollment/update")
  public ResponseEntity<String> updateEnrollment(
      @RequestBody AdminEnrollmentStatusRequest adminEnrollmentStatusRequest) {
    try {
      StudentCohort studentCohort = adminServiceImpl.findStudentCohortById(adminEnrollmentStatusRequest.getId());
      if (studentCohort == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
      }
      adminServiceImpl.updateExistingStudentCohort(adminEnrollmentStatusRequest);
      return ResponseEntity.status(HttpStatus.OK).body("Student Enrollment Status updated successfully");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
    }
  }

  @DeleteMapping("/enrollment/delete")
  public ResponseEntity<String> deleteEnrollment(
      @RequestBody AdminEnrollmentStatusRequest adminEnrollmentStatusRequest) {
    try {
      StudentCohort studentCohort = adminServiceImpl.findStudentCohortById(adminEnrollmentStatusRequest.getId());
      if (studentCohort == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
      }
      adminServiceImpl.deleteExistingStudentCohort(adminEnrollmentStatusRequest);
      return ResponseEntity.status(HttpStatus.OK).body("Student Enrollment Status deleted successfully");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
    }
  }

  @GetMapping("/student/list")
  public String showStudents(Model model) {
    List<Student> students = adminServiceImpl.findAllByisDelete();
    model.addAttribute("students", students);
    return "admin/admin-student-view";
  }

  @PostMapping("/student/update")
  public ResponseEntity<String> updateStudent(@Valid @RequestBody AdminStudentRequest studentRequest) {
    try {
      int studentId = studentRequest.getId();
      String email = studentRequest.getEmail();

      // Retrieve the existing student from the repository based on the ID
      Student optionalStudent = adminServiceImpl.findStudentByStudentId(studentId);

      if (optionalStudent == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
      }

      if (!email.equals(optionalStudent.getEmail()) && adminServiceImpl.findStudentByStudentEmail(email) != null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists");
      }

      adminServiceImpl.updateStudent(studentRequest);

      return ResponseEntity.status(HttpStatus.OK).body("Student updated successfully");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
    }
  }

  @DeleteMapping("/student/delete")
  public ResponseEntity<String> deleteStudent(@RequestBody AdminStudentRequest studentRequest) {
    try {
      adminServiceImpl.deleteStudent(studentRequest);
      return ResponseEntity.status(HttpStatus.OK).body("You have deleted the student");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
    }
  }

  @GetMapping("/admin/list")
  public String showAdmins(Model model) {

    List<Admin> admins = adminServiceImpl.findAllAdmins();
    ResponseEntity<List<Admin>> responseEntity = new ResponseEntity<>(admins, HttpStatus.OK);

    model.addAttribute("admins", responseEntity.getBody());
    return "admin/admin-view";
  }

  @PostMapping("/admin/update")
  public ResponseEntity<String> updateAdmin(@Valid @RequestBody AdminLecturerRequest adminRequest) {
    try {
      int adminId = adminRequest.getId();
      String email = adminRequest.getEmail();

      // Retrieve the existing lecturer from the repository based on the ID
      Admin optionalAdmin = adminServiceImpl.findAdminByAdminId(adminId);

      if (optionalAdmin == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
      }

      if (!email.equals(optionalAdmin.getEmail())
          && adminServiceImpl.findAdminByAdminEmail(adminRequest.getEmail()) != null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists");
      }
      adminServiceImpl.updateAdmin(adminRequest);
      return ResponseEntity.status(HttpStatus.OK).body("Admin updated successfully");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
    }
  }

  @DeleteMapping("/admin/delete")
  public ResponseEntity<String> deleteAdmin(@RequestBody AdminLecturerRequest adminRequest) {
    try {
      Admin existingAdmin = adminServiceImpl.findAdminByAdminId(adminRequest.getId());
      if (existingAdmin == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
      }
      adminServiceImpl.deleteAdmin(adminRequest);
      return ResponseEntity.status(HttpStatus.OK).body("Admin deleted successfully");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
    }
  }
}
