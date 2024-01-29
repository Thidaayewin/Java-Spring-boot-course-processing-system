// package com.team3.caps.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.stereotype.Controller;
// import org.springframework.stereotype.Service;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;

// import com.team3.caps.DataTransferObject.AdminCohortRequest;
// import com.team3.caps.DataTransferObject.AdminCourseRequest;
// import com.team3.caps.DataTransferObject.AdminEnrollmentRequest;
// import com.team3.caps.DataTransferObject.AdminEnrollmentStatusRequest;
// import com.team3.caps.DataTransferObject.AdminLecturerRequest;
// import com.team3.caps.DataTransferObject.AdminStudentRequest;
// import com.team3.caps.model.*;
// import com.team3.caps.repository.AdminRepository;
// import com.team3.caps.repository.CohortRepository;
// import com.team3.caps.repository.CourseRepository;
// import com.team3.caps.repository.LecturerCohortRepository;
// import com.team3.caps.repository.LecturerRepository;
// import com.team3.caps.repository.StudentCohortRepository;
// import com.team3.caps.repository.StudentRepository;
// import com.team3.caps.security.SecurityUtil;
// import com.team3.caps.service.AdminService;

// import java.time.LocalDateTime;
// import java.time.format.DateTimeFormatter;
// import java.util.*;
// import com.team3.caps.util.*;

// @Service
// public class AdminMService {

// static int pageSize = 5;

// private AdminService adminService;

// private LecturerRepository lecturerRepository;

// private AdminRepository adminRepository;

// private CourseRepository courseRepository;

// private CohortRepository cohortRepository;

// private StudentCohortRepository studentCohortRepository;

// private StudentRepository studentRepository;

// private LecturerCohortRepository lecturerCohortRepository;

// @Autowired
// public AdminController(LecturerRepository lecturerRepository, AdminRepository
// adminRepository,
// CourseRepository courseRepository, AdminService adminService,
// CohortRepository cohortRepository, StudentCohortRepository
// studentCohortRepository,
// StudentRepository studentRepository, LecturerCohortRepository
// lecturerCohortRepository) {
// this.lecturerRepository = lecturerRepository;
// this.adminRepository = adminRepository;
// this.courseRepository = courseRepository;
// this.cohortRepository = cohortRepository;
// this.studentCohortRepository = studentCohortRepository;
// this.studentRepository = studentRepository;
// this.lecturerCohortRepository = lecturerCohortRepository;
// this.adminService = adminService;
// }

// // this is OK
// @GetMapping("/lecturer/list")
// public String showLecturers(Model model) {

// List<Lecturer> lecturers = adminService.findAllLecturers();
// ResponseEntity<List<Lecturer>> responseEntity = new
// ResponseEntity<>(lecturers, HttpStatus.OK);

// model.addAttribute("lecturers", responseEntity.getBody());
// return "admin/admin-lecturer-view";
// }

// @PostMapping("/lecturer/update")
// public ResponseEntity<String> updateLecturer(@RequestBody
// AdminLecturerRequest lecturerRequest) {
// try {
// int lecturerId = lecturerRequest.getId();
// String firstName = lecturerRequest.getFirstName();
// String lastName = lecturerRequest.getLastName();
// String email = lecturerRequest.getEmail();
// String phone = lecturerRequest.getPhone();

// // Retrieve the existing lecturer from the repository based on the ID
// Lecturer optionalLecturer =
// lecturerRepository.findByIdAndIsDeleted(lecturerId, false);

// if (optionalLecturer == null) {
// return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
// }

// if (!email.equals(optionalLecturer.getEmail())
// && lecturerRepository.findByEmailAndIsDeleted(email, false) != null) {
// return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already
// exists");
// }

// // Update the lecturer's information
// optionalLecturer.setFirstName(firstName);
// optionalLecturer.setLastName(lastName);
// optionalLecturer.setEmail(email);
// optionalLecturer.setPhone(phone);
// optionalLecturer.setLastUpdatedBy(SecurityUtil.getSessionUser());
// optionalLecturer.setLastUpdatedTime(LocalDateTime.now());

// // Save the updated lecturer in the repository
// lecturerRepository.save(optionalLecturer);
// return ResponseEntity.status(HttpStatus.OK).body("Lecturer updated
// successfully");
// } catch (Exception e) {
// return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal
// Server Error");
// }
// }

// @DeleteMapping("/lecturer/delete")
// public ResponseEntity<String> deleteLecturer(@RequestBody
// AdminLecturerRequest lecturerRequest) {
// try {
// int lecturerId = lecturerRequest.getId();
// Lecturer existingLecturer =
// lecturerRepository.findByIdAndIsDeleted(lecturerId, false);
// if (existingLecturer == null) {
// return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
// }
// existingLecturer.setDeleted(true);
// existingLecturer.setLastUpdatedBy(SecurityUtil.getSessionUser());
// existingLecturer.setLastUpdatedTime(LocalDateTime.now());
// lecturerRepository.save(existingLecturer);
// return ResponseEntity.status(HttpStatus.OK).body("Lecturer deleted
// successfully");
// } catch (Exception e) {
// return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal
// Server Error");
// }
// }

// @GetMapping("/course/list")
// public String showCourses(Model model) {
// List<Course> courses = courseRepository.findByIsDeleted(false);
// model.addAttribute("courses", courses);
// return "admin/admin-course-view";
// }

// @PostMapping("/course/update")
// public ResponseEntity<String> updateCourse(@RequestBody AdminCourseRequest
// courseRequest) {
// try {
// int courseId = courseRequest.getId();
// String courseName = courseRequest.getCourseName();
// int credits = courseRequest.getCredits();

// Course existingCourse = courseRepository.findById(courseId).get();

// if (existingCourse == null) {
// return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
// }

// existingCourse.setName(courseName);
// existingCourse.setCredits(credits);
// existingCourse.setLastUpdatedBy(SecurityUtil.getSessionUser());
// existingCourse.setLastUpdatedTime(LocalDateTime.now());
// courseRepository.save(existingCourse);
// return ResponseEntity.status(HttpStatus.OK).body("Course updated
// successfully");
// } catch (Exception e) {
// return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal
// Server Error");
// }
// }

// @PostMapping("/course/create")
// public ResponseEntity<String> createCourse(@RequestBody AdminCourseRequest
// courseRequest) {
// try {
// String courseName = courseRequest.getCourseName();
// int credits = courseRequest.getCredits();
// Course existingCourse = courseRepository.findByNameAndIsDeleted(courseName,
// false);
// if (existingCourse != null) {
// return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
// }
// Course course = new Course(SecurityUtil.getSessionUser(),
// LocalDateTime.now(), SecurityUtil.getSessionUser(),
// LocalDateTime.now(), courseName, credits);
// courseRepository.saveAndFlush(course);
// return ResponseEntity.status(HttpStatus.OK).body("Course created
// successfully");
// } catch (Exception e) {
// return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal
// Server Error");
// }
// }

// @DeleteMapping("/course/delete")
// public ResponseEntity<String> deleteCourse(@RequestBody AdminCourseRequest
// courseRequest) {
// try {
// int courseId = courseRequest.getId();
// Course existingCourse = courseRepository.findById(courseId).get();
// if (existingCourse == null) {
// return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
// }
// existingCourse.setDeleted(true);
// existingCourse.setLastUpdatedBy(SecurityUtil.getSessionUser());
// existingCourse.setLastUpdatedTime(LocalDateTime.now());
// courseRepository.save(existingCourse);

// return ResponseEntity.status(HttpStatus.OK).body("You have deleted Course");
// } catch (Exception e) {
// return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal
// Server Error");
// }

// }

// @GetMapping("/course/cohort/list/{id}")
// public String showCohort(@PathVariable Integer id, Model model) {
// List<AdminCohortRequest> cohortList =
// adminService.findCohortsAllByCourseId(1);
// List<Lecturer> lecturersList = lecturerRepository.findByIsDeleted(false);
// model.addAttribute("cohorts", cohortList);
// model.addAttribute("classDays", ClassDay.values());
// model.addAttribute("classSlots", ClassSlot.values());
// model.addAttribute("courseid", id);
// model.addAttribute("lecturers", lecturersList);
// return "admin/admin-cohort-view";
// }

// @PostMapping("/course/cohort/create")
// public ResponseEntity<String> createCohort(@RequestBody AdminCohortRequest
// cohortRequest) {
// try {
// LocalDateTime now = LocalDateTime.now();

// ClassSlot classSlot = ClassSlot.valueOf(cohortRequest.getClassSlot());
// ClassDay classDay = ClassDay.valueOf(cohortRequest.getClassDay());
// int intValue = cohortRequest.getCourseId();
// String dateString = cohortRequest.getCohort_start();
// Lecturer lecturer =
// lecturerRepository.findByIdAndIsDeleted(cohortRequest.getLecturer(), false);
// DateTimeFormatter formatter =
// DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
// LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
// Course course = courseRepository.findByIdAndIsDeleted(intValue, false).get();
// Cohort c = new Cohort(SecurityUtil.getSessionUser(), now,
// SecurityUtil.getSessionUser(), now,
// cohortRequest.getName(), cohortRequest.getDescription(), course, dateTime,
// intValue, classDay, classSlot);
// LecturerCohort lecturerCohort = new
// LecturerCohort(SecurityUtil.getSessionUser(), now,
// SecurityUtil.getSessionUser(), now, lecturer, c);
// cohortRepository.saveAndFlush(c);
// lecturerCohortRepository.saveAndFlush(lecturerCohort);
// return ResponseEntity.status(HttpStatus.OK).body("Cohort created
// successfully");
// } catch (Exception e) {
// return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal
// Server Error");
// }
// }

// @PostMapping("/course/cohort/update")
// public ResponseEntity<String> updateCohort(@RequestBody AdminCohortRequest
// cohortRequest) {
// try {
// // Extract the course data from the request payload
// ClassSlot classSlot = ClassSlot.valueOf(cohortRequest.getClassSlot());
// ClassDay classDay = ClassDay.valueOf(cohortRequest.getClassDay());
// int intValue = cohortRequest.getCourseId();
// // Retrieve the existing course from the repository based on the course ID
// String dateString = cohortRequest.getCohort_start();
// DateTimeFormatter formatter =
// DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
// LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
// Cohort existingCohort = cohortRepository.findByIdAndIsDeleted(intValue,
// false);
// Lecturer lecturer =
// lecturerRepository.findByIdAndIsDeleted(cohortRequest.getLecturer(), false);
// LecturerCohort existinglecturerCohort = lecturerCohortRepository
// .findByCohortIdAndIsDeleted(cohortRequest.getCohortId(), false);

// if (existingCohort == null) {
// return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
// }

// // Update the course with the new data
// existingCohort.setName(cohortRequest.getName());
// existingCohort.setDescription(cohortRequest.getDescription());
// existingCohort.setCohortStart(dateTime);
// existingCohort.setCapacity(cohortRequest.getCapacity());
// existingCohort.setClassDay(classDay);
// existingCohort.setClassSlot(classSlot);
// existingCohort.setLastUpdatedBy(SecurityUtil.getSessionUser());
// existingCohort.setLastUpdatedTime(LocalDateTime.now());
// cohortRepository.save(existingCohort);
// existinglecturerCohort.setLecturer(lecturer);
// lecturerCohortRepository.save(existinglecturerCohort);
// return ResponseEntity.status(HttpStatus.OK).body("Cohort updated
// successfully");
// } catch (Exception e) {
// return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal
// Server Error");
// }

// }

// @DeleteMapping("/course/cohort/delete")
// public ResponseEntity<String> deleteCohort(@RequestBody AdminCohortRequest
// cohortRequest) {
// try {
// int intValue = cohortRequest.getCohortId();
// Cohort existingCohort = cohortRepository.findByIdAndIsDeleted(intValue,
// false);
// LecturerCohort existinglecturerCohort =
// lecturerCohortRepository.findByCohortIdAndIsDeleted(intValue, false);
// if (existingCohort == null) {
// return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
// }
// existingCohort.setDeleted(true);
// existingCohort.setLastUpdatedBy(SecurityUtil.getSessionUser());
// existingCohort.setLastUpdatedTime(LocalDateTime.now());
// existinglecturerCohort.setDeleted(true);
// existinglecturerCohort.setLastUpdatedBy(SecurityUtil.getSessionUser());
// existinglecturerCohort.setLastUpdatedTime(LocalDateTime.now());
// cohortRepository.save(existingCohort);
// lecturerCohortRepository.save(existinglecturerCohort);
// return ResponseEntity.status(HttpStatus.OK).body("You have deleted Cohort");
// } catch (Exception e) {
// return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal
// Server Error");
// }
// }

// @GetMapping("/enrollment/list")
// public String showEnrollment(Model model) {
// List<AdminEnrollmentRequest> enrollments = adminService.findAllEnrollment();
// model.addAttribute("enrollments", enrollments);
// model.addAttribute("enrollmentStatus", EnrolmentStatus.values());
// return "admin/admin-enrollment-view";
// }

// @PostMapping("/enrollment/update")
// public ResponseEntity<String> updateEnrollment(
// @RequestBody AdminEnrollmentStatusRequest adminEnrollmentStatusRequest) {
// try {
// StudentCohort studentCohort =
// studentCohortRepository.findByIdAndIsDeleted(adminEnrollmentStatusRequest.getId(),
// false);
// if (studentCohort == null) {
// return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
// }

// // Update the course with the new data
// studentCohort.setEnrolmentStatus(EnrolmentStatus.valueOf(adminEnrollmentStatusRequest.getEnrolmentStatus()));
// studentCohort.setLastUpdatedBy(SecurityUtil.getSessionUser());
// studentCohort.setLastUpdatedTime(LocalDateTime.now());
// studentCohortRepository.save(studentCohort);
// return ResponseEntity.status(HttpStatus.OK).body("Student Enrollment Status
// updated successfully");
// } catch (Exception e) {
// return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal
// Server Error");
// }
// }

// @DeleteMapping("/enrollment/delete")
// public ResponseEntity<String> deleteEnrollment(
// @RequestBody AdminEnrollmentStatusRequest adminEnrollmentStatusRequest) {
// try {
// StudentCohort studentCohort =
// studentCohortRepository.findByIdAndIsDeleted(adminEnrollmentStatusRequest.getId(),
// false);
// if (studentCohort == null) {
// return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
// }
// studentCohort.setDeleted(true);
// studentCohort.setLastUpdatedBy(SecurityUtil.getSessionUser());
// studentCohort.setLastUpdatedTime(LocalDateTime.now());
// studentCohortRepository.save(studentCohort);
// return ResponseEntity.status(HttpStatus.OK).body("Student Enrollment Status
// deleted successfully");
// } catch (Exception e) {
// return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal
// Server Error");
// }
// }

// @GetMapping("/student/list")
// public String showStudents(Model model) {
// List<Student> students = adminService.findAllByisDelete();
// model.addAttribute("students", students);
// return "admin/admin-student-view";
// }

// @PostMapping("/student/update")
// public ResponseEntity<String> updateStudent(@RequestBody AdminStudentRequest
// studentRequest) {
// try {
// int studentId = studentRequest.getId();
// String firstName = studentRequest.getFirstName();
// String lastName = studentRequest.getLastName();
// String email = studentRequest.getEmail();
// String phone = studentRequest.getPhone();

// // Retrieve the existing student from the repository based on the ID
// Student optionalStudent = studentRepository.findByIdAndIsDeleted(studentId,
// false);

// if (optionalStudent == null) {
// return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
// }

// if (!email.equals(optionalStudent.getEmail()) &&
// studentRepository.findByEmailandIsNotDeleted(email) != null) {
// return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already
// exists");
// }
// optionalStudent.setFirstName(firstName);
// optionalStudent.setLastName(lastName);
// optionalStudent.setEmail(email);
// optionalStudent.setPhone(phone);
// studentRepository.save(optionalStudent);
// return ResponseEntity.status(HttpStatus.OK).body("Student updated
// successfully");
// } catch (Exception e) {
// return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal
// Server Error");
// }
// }

// @DeleteMapping("/student/delete")
// public ResponseEntity<String> deleteStudent(@RequestBody AdminStudentRequest
// studentRequest) {
// try {
// int studentId = studentRequest.getId();
// studentRepository.softDelete(studentId);
// return ResponseEntity.status(HttpStatus.OK).body("You have deleted the
// student");
// } catch (Exception e) {
// return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal
// Server Error");
// }
// }

// @GetMapping("/admin/list")
// public String showAdmins(Model model) {

// List<Admin> admins = adminService.findAllAdmins();
// ResponseEntity<List<Admin>> responseEntity = new ResponseEntity<>(admins,
// HttpStatus.OK);

// model.addAttribute("admins", responseEntity.getBody());
// return "admin/admin-view";
// }

// @PostMapping("/admin/update")
// public ResponseEntity<String> updateAdmin(@RequestBody AdminLecturerRequest
// adminRequest) {
// try {
// int adminId = adminRequest.getId();
// String firstName = adminRequest.getFirstName();
// String lastName = adminRequest.getLastName();
// String email = adminRequest.getEmail();
// String phone = adminRequest.getPhone();

// // Retrieve the existing lecturer from the repository based on the ID
// Admin optionalAdmin = adminRepository.findByIdAndIsDeleted(adminId, false);

// if (optionalAdmin == null) {
// return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
// }

// if (!email.equals(optionalAdmin.getEmail()) &&
// adminRepository.findByEmailAndIsDeleted(email, false) != null) {
// return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already
// exists");
// }

// // Update the lecturer's information
// optionalAdmin.setFirstName(firstName);
// optionalAdmin.setLastName(lastName);
// optionalAdmin.setEmail(email);
// optionalAdmin.setPhone(phone);
// optionalAdmin.setLastUpdatedBy(SecurityUtil.getSessionUser());
// optionalAdmin.setLastUpdatedTime(LocalDateTime.now());

// // Save the updated lecturer in the repository
// adminRepository.save(optionalAdmin);
// return ResponseEntity.status(HttpStatus.OK).body("Admin updated
// successfully");
// } catch (Exception e) {
// return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal
// Server Error");
// }
// }

// @DeleteMapping("/admin/delete")
// public ResponseEntity<String> deleteAdmin(@RequestBody AdminLecturerRequest
// adminRequest) {
// try {
// int adminId = adminRequest.getId();
// Admin existingAdmin = adminRepository.findByIdAndIsDeleted(adminId, false);
// if (existingAdmin == null) {
// return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
// }
// existingAdmin.setDeleted(true);
// existingAdmin.setLastUpdatedBy(SecurityUtil.getSessionUser());
// existingAdmin.setLastUpdatedTime(LocalDateTime.now());
// adminRepository.save(existingAdmin);
// return ResponseEntity.status(HttpStatus.OK).body("Admin deleted
// successfully");
// } catch (Exception e) {
// return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal
// Server Error");
// }
// }
// }
