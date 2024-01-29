package com.team3.caps.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team3.caps.DataTransferObject.AdminCohortRequest;
import com.team3.caps.DataTransferObject.AdminCourseRequest;
import com.team3.caps.DataTransferObject.AdminEnrollmentRequest;
import com.team3.caps.DataTransferObject.AdminEnrollmentStatusRequest;
import com.team3.caps.DataTransferObject.AdminLecturerRequest;
import com.team3.caps.DataTransferObject.AdminStudentRequest;
import com.team3.caps.model.Admin;
import com.team3.caps.model.Cohort;
import com.team3.caps.model.Course;
import com.team3.caps.DataTransferObject.Enrollment;
import com.team3.caps.model.Lecturer;
import com.team3.caps.model.LecturerCohort;
import com.team3.caps.model.Student;
import com.team3.caps.model.StudentCohort;
import com.team3.caps.repository.AdminRepository;
import com.team3.caps.repository.CohortRepository;
import com.team3.caps.repository.CourseRepository;
import com.team3.caps.repository.LecturerCohortRepository;
import com.team3.caps.repository.LecturerRepository;
import com.team3.caps.repository.StudentCohortRepository;
import com.team3.caps.repository.StudentRepository;
import com.team3.caps.security.SecurityUtil;
import com.team3.caps.util.ClassDay;
import com.team3.caps.util.ClassSlot;
import com.team3.caps.util.EnrolmentStatus;

import jakarta.transaction.Transactional;

@Service
public class AdminServiceImpl {

  private LecturerRepository lecturerRepository;
  private AdminRepository adminRepository;
  private CohortRepository cohortRepository;
  private CourseRepository courseRepository;
  private StudentCohortRepository studentCohortRepository;
  private LecturerCohortRepository lecturerCohortRepository;
  private StudentRepository studentRepository;

  @Autowired
  public AdminServiceImpl(LecturerRepository lecturerRepository,
      AdminRepository adminRepository,
      CohortRepository cohortRepository,
      CourseRepository courseRepository,
      StudentCohortRepository studentCohortRepository,
      LecturerCohortRepository lecturerCohortRepository,
      StudentRepository studentRepository) {
    this.lecturerRepository = lecturerRepository;
    this.adminRepository = adminRepository;
    this.cohortRepository = cohortRepository;
    this.courseRepository = courseRepository;
    this.studentCohortRepository = studentCohortRepository;
    this.lecturerCohortRepository = lecturerCohortRepository;
    this.studentRepository = studentRepository;

  }

  public List<Admin> findAllAdmins() {
    return adminRepository.findByIsDeleted(false);
  }

  public Admin findAdminByAdminId(int adminId) {
    return adminRepository.findByIdAndIsDeleted(adminId, false);
  }

  public Admin findAdminByAdminEmail(String email) {
    return adminRepository.findByEmailAndIsDeleted(email, false);
  }

  @Transactional
  public void updateAdmin(AdminLecturerRequest adminRequest) {
    Admin admin = findAdminByAdminId(adminRequest.getId());
    // Update the lecturer's information
    admin.setFirstName(adminRequest.getFirstName());
    admin.setLastName(adminRequest.getLastName());
    admin.setEmail(adminRequest.getEmail());
    admin.setPhone(adminRequest.getPhone());
    admin.setLastUpdatedBy(SecurityUtil.getSessionUser());
    admin.setLastUpdatedTime(LocalDateTime.now());
    adminRepository.save(admin);
  }

  @Transactional
  public void deleteAdmin(AdminLecturerRequest adminRequest) {
    Admin existingAdmin = findAdminByAdminId(adminRequest.getId());
    existingAdmin.setDeleted(true);
    existingAdmin.setLastUpdatedBy(SecurityUtil.getSessionUser());
    existingAdmin.setLastUpdatedTime(LocalDateTime.now());
    adminRepository.save(existingAdmin);
  }

  public List<Lecturer> findAllLecturers() {
    return lecturerRepository.findByIsDeleted(false);
  }

  public Lecturer findLecturerByLecturerId(int lecturerId) {
    return lecturerRepository.findByIdAndIsDeleted(lecturerId, false);
  }

  public Lecturer findLecturerByEmail(String email) {
    return lecturerRepository.findByEmailAndIsDeleted(email, false);
  }

  @Transactional
  public void updateLecturer(AdminLecturerRequest lecturerRequest) {
    Lecturer lecturer = findLecturerByLecturerId(lecturerRequest.getId());
    // Update the lecturer's information
    lecturer.setFirstName(lecturerRequest.getFirstName());
    lecturer.setLastName(lecturerRequest.getLastName());
    lecturer.setEmail(lecturerRequest.getEmail());
    lecturer.setPhone(lecturerRequest.getPhone());
    lecturer.setLastUpdatedBy(SecurityUtil.getSessionUser());
    lecturer.setLastUpdatedTime(LocalDateTime.now());
    lecturerRepository.save(lecturer);
    return;
  }

  @Transactional
  public void deleteLecturer(AdminLecturerRequest lecturerRequest) {
    Lecturer lecturer = findLecturerByLecturerId(lecturerRequest.getId());
    lecturer.setDeleted(true);
    lecturer.setLastUpdatedBy(SecurityUtil.getSessionUser());
    lecturer.setLastUpdatedTime(LocalDateTime.now());
    lecturerRepository.save(lecturer);
    return;
  }

  public List<Student> findAllByisDelete() {
    return studentRepository.findAllByisDelete();
  }

  public Student findStudentByStudentId(int studentId) {
    return studentRepository.findByIdAndIsDeleted(studentId, false);
  }

  public Student findStudentByStudentEmail(String studentEmail) {
    return studentRepository.findByEmailandIsNotDeleted(studentEmail);
  }

  @Transactional
  public void updateStudent(AdminStudentRequest studentRequest) {
    Student student = findStudentByStudentId(studentRequest.getId());
    student.setFirstName(studentRequest.getFirstName());
    student.setLastName(studentRequest.getLastName());
    student.setEmail(studentRequest.getEmail());
    student.setPhone(studentRequest.getPhone());
    studentRepository.save(student);
    return;
  }

  public void deleteStudent(AdminStudentRequest studentRequest) {
    int studentId = studentRequest.getId();
    studentRepository.softDelete(studentId);
    return;
  }

  public List<Course> findAllCourses() {
    return courseRepository.findByIsDeleted(false);
  }

  public Course findCourseByCourseId(int courseId) {
    return courseRepository.findById(courseId).get();
  }

  @Transactional
  public Course findCourseByCourseName(String courseName) {
    return courseRepository.findByNameAndIsDeleted(courseName, false);
  }

  @Transactional
  public void saveNewCourse(String courseName, int credits) {
    Course newCourse = new Course(SecurityUtil.getSessionUser(),
        LocalDateTime.now(),
        SecurityUtil.getSessionUser(),
        LocalDateTime.now(),
        courseName,
        credits);
    courseRepository.saveAndFlush(newCourse);
    return;
  }

  @Transactional
  public void updateExistingCourse(AdminCourseRequest courseRequest) {
    Course existingCourse = findCourseByCourseId(courseRequest.getId());
    existingCourse.setName(courseRequest.getCourseName());
    existingCourse.setCredits(courseRequest.getCredits());
    existingCourse.setLastUpdatedBy(SecurityUtil.getSessionUser());
    existingCourse.setLastUpdatedTime(LocalDateTime.now());
    courseRepository.save(existingCourse);
    return;
  }

  @Transactional
  public void deleteExistingCourse(Course existingCourse) {
    existingCourse.setDeleted(true);
    existingCourse.setLastUpdatedBy(SecurityUtil.getSessionUser());
    existingCourse.setLastUpdatedTime(LocalDateTime.now());
    courseRepository.save(existingCourse);
    return;
  }

  public List<AdminCohortRequest> findCohortsAllByCourseId(int id) {
    List<AdminCohortRequest> cohortList = new ArrayList<>();

    List<Cohort> cohorts = cohortRepository.findCohortsAllByCourseId(id);

    Course c = courseRepository.findByIdAndIsDeleted(id, false).get();
    for (Cohort co : cohorts) {
      AdminCohortRequest adminCohortRequest = new AdminCohortRequest();

      adminCohortRequest.setCourseId(id);
      adminCohortRequest.setCourseName(c.getName());
      adminCohortRequest.setName(co.getName());
      adminCohortRequest.setDescription(co.getDescription());
      adminCohortRequest.setCapacity(co.getCapacity());
      adminCohortRequest.setClassDay(co.getClassDay().toString());
      adminCohortRequest.setClassSlot(co.getClassSlot().toString());
      String dateTimeString = co.getCohortStart().toString();
      DateTimeFormatter inputFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
      DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

      LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, inputFormatter);
      String formattedDateTime = dateTime.format(outputFormatter);
      adminCohortRequest.setCohort_start(formattedDateTime);
      Long cohortId = co.getId();
      int intValue = cohortId.intValue();
      adminCohortRequest.setCohortId(intValue);
      LecturerCohort lecturerCohort = lecturerCohortRepository.findTop1ByCohortIdAndIsDeletedOrderByCohortIdDesc(intValue, false);
      if (lecturerCohort != null) {
        Lecturer lecturer = lecturerRepository.findByIdAndIsDeleted(lecturerCohort.getLecturer().getId(), false);
        adminCohortRequest.setLecturer(lecturer.getId());
        adminCohortRequest.setLecturerName(lecturer.getFirstName() + " " + lecturer.getLastName());
      } else {
        adminCohortRequest.setLecturer(0);
        adminCohortRequest.setLecturerName("");
      }
      cohortList.add(adminCohortRequest);
    }
    return cohortList;
  }

  public Cohort findCohortByCohortId(int cohortId) {
    return cohortRepository.findByIdAndIsDeleted(cohortId, false);
  }

  @Transactional
  public void createNewCohort(AdminCohortRequest cohortRequest) {
    LocalDateTime now = LocalDateTime.now();
    ClassSlot classSlot = ClassSlot.valueOf(cohortRequest.getClassSlot());
    ClassDay classDay = ClassDay.valueOf(cohortRequest.getClassDay());
    int intValue = cohortRequest.getCourseId();
    String dateString = cohortRequest.getCohort_start();
    Lecturer lecturer = findLecturerByLecturerId(cohortRequest.getLecturer());
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
    Course course = courseRepository.findByIdAndIsDeleted(intValue, false).get();
    Cohort c = new Cohort(SecurityUtil.getSessionUser(), now, SecurityUtil.getSessionUser(), now,
        cohortRequest.getName(), cohortRequest.getDescription(), course, dateTime, intValue, classDay, classSlot);
    LecturerCohort lecturerCohort = new LecturerCohort(SecurityUtil.getSessionUser(), now,
        SecurityUtil.getSessionUser(), now, lecturer, c);
    cohortRepository.saveAndFlush(c);
    lecturerCohortRepository.saveAndFlush(lecturerCohort);
    return;
  }

  @Transactional
  public void updateExistingCourseCohort(AdminCohortRequest cohortRequest) {
    // Extract the course data from the request payload
    ClassSlot classSlot = ClassSlot.valueOf(cohortRequest.getClassSlot());
    ClassDay classDay = ClassDay.valueOf(cohortRequest.getClassDay());
    // Retrieve the existing course from the repository based on the course ID
    String dateString = cohortRequest.getCohort_start();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);

    Lecturer lecturer = findLecturerByLecturerId(cohortRequest.getLecturer());

    LecturerCohort existinglecturerCohort = lecturerCohortRepository
        .findByCohortIdAndIsDeleted(cohortRequest.getCohortId(), false);
    Cohort existingCohort = findCohortByCohortId(cohortRequest.getCohortId());
    // Update the course with the new data
    existingCohort.setName(cohortRequest.getName());
    existingCohort.setDescription(cohortRequest.getDescription());
    existingCohort.setCohortStart(dateTime);
    existingCohort.setCapacity(cohortRequest.getCapacity());
    existingCohort.setClassDay(classDay);
    existingCohort.setClassSlot(classSlot);
    existingCohort.setLastUpdatedBy(SecurityUtil.getSessionUser());
    existingCohort.setLastUpdatedTime(LocalDateTime.now());
    cohortRepository.save(existingCohort);
    existinglecturerCohort.setLecturer(lecturer);
    lecturerCohortRepository.save(existinglecturerCohort);
  }

  @Transactional
  public void deleteExistingCourseCohort(AdminCohortRequest cohortRequest) {
    LecturerCohort existinglecturerCohort = lecturerCohortRepository
        .findByCohortIdAndIsDeleted(cohortRequest.getCohortId(), false);
    Cohort existingCohort = findCohortByCohortId(cohortRequest.getCohortId());
    existingCohort.setDeleted(true);
    existingCohort.setLastUpdatedBy(SecurityUtil.getSessionUser());
    existingCohort.setLastUpdatedTime(LocalDateTime.now());
    existinglecturerCohort.setDeleted(true);
    existinglecturerCohort.setLastUpdatedBy(SecurityUtil.getSessionUser());
    existinglecturerCohort.setLastUpdatedTime(LocalDateTime.now());
    cohortRepository.save(existingCohort);
    lecturerCohortRepository.save(existinglecturerCohort);
  }

  public StudentCohort findStudentCohortById(int id) {
    return studentCohortRepository.findByIdAndIsDeleted(id, false);
  }

  @Transactional
  public void updateExistingStudentCohort(AdminEnrollmentStatusRequest adminEnrollmentStatusRequest) {
    StudentCohort studentCohort = findStudentCohortById(adminEnrollmentStatusRequest.getId());
    // Update the course with the new data
    studentCohort.setEnrolmentStatus(EnrolmentStatus.valueOf(adminEnrollmentStatusRequest.getEnrolmentStatus()));
    studentCohort.setLastUpdatedBy(SecurityUtil.getSessionUser());
    studentCohort.setLastUpdatedTime(LocalDateTime.now());
    studentCohortRepository.save(studentCohort);
    return;
  }

  @Transactional
  public void deleteExistingStudentCohort(AdminEnrollmentStatusRequest adminEnrollmentStatusRequest) {
    StudentCohort studentCohort = findStudentCohortById(adminEnrollmentStatusRequest.getId());
    studentCohort.setDeleted(true);
    studentCohort.setLastUpdatedBy(SecurityUtil.getSessionUser());
    studentCohort.setLastUpdatedTime(LocalDateTime.now());
    studentCohortRepository.save(studentCohort);
    return;
  }

  public boolean hasEnrollmentByCourseId(int courseId) {
    List<StudentCohort> studentCohorts = studentCohortRepository.findByIsDeleted(false);
    for (StudentCohort studentcohort : studentCohorts) {
        Course course = courseRepository.findByIdAndIsDeleted(studentcohort.getCohort().getCourseType().getId(), false)
            .orElse(null);
        
        if (course != null && course.getId() == courseId) {
            return true;
        }
    }
    return false;
}


  public List<AdminEnrollmentRequest> findAllEnrollment() {
    List<AdminEnrollmentRequest> enrollments = new ArrayList<>();
    List<StudentCohort> studentCohorts = studentCohortRepository.findByIsDeleted(false);
    for (StudentCohort studentcohort : studentCohorts) {

      Course course = courseRepository.findByIdAndIsDeleted(studentcohort.getCohort().getCourseType().getId(), false)
          .orElse(null);
      ;
      Student student = studentRepository.findByIdAndIsDeleted(studentcohort.getStudent().getId(), false);
      int id = studentcohort.getId();
      String enrolmentStatus = "";
      if (studentcohort.getEnrolmentStatus().equals(EnrolmentStatus.ENROLLED)) {
        enrolmentStatus = "ENROLLED";
      } else if (studentcohort.getEnrolmentStatus().equals(EnrolmentStatus.REMOVED)) {
        enrolmentStatus = "REMOVED";
      }
      if (studentcohort.getEnrolmentStatus().equals(EnrolmentStatus.COMPLETED)) {
        enrolmentStatus = "COMPLETED";
      }
      Enrollment enroll = new Enrollment(id, student, course, studentcohort, 0,
          Double.toString(studentcohort.getScore()), enrolmentStatus);
      AdminEnrollmentRequest enrollment = new AdminEnrollmentRequest();
      enrollment.setStudentCohortId(studentcohort.getId());
      enrollment.setStudentName(student.getFirstName() + " " + student.getLastName());
      enrollment.setMatriculationNumber(student.getUserNumber().toString());
      enrollment.setCourseName(course.getName());
      enrollment.setDescription(studentcohort.getCohort().getDescription());
      enrollment.setCohortDate(studentcohort.getCohort().getCohortStart());
      enrollment.setClassDay(studentcohort.getCohort().getClassDay().toString());
      enrollment.setClassSlot(studentcohort.getCohort().getClassSlot().toString());
      enrollment.setCapacity(studentcohort.getCohort().getCapacity());
      enrollment.setScore(studentcohort.getScore());
      enrollment.setEnrollmentStatus(enrolmentStatus);
      enrollment.setGradeValue("0");
      enrollment.setMarks(0);
      enrollments.add(enrollment);
    }
    return enrollments;
  }

}
