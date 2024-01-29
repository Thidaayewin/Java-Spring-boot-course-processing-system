package com.team3.caps.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team3.caps.DataTransferObject.*;
import com.team3.caps.model.*;
import com.team3.caps.repository.*;
import com.team3.caps.service.AdminServiceImpl;
import com.team3.caps.service.EmailService;
import com.team3.caps.util.ClassDay;
import com.team3.caps.util.ClassSlot;
import com.team3.caps.util.EnrolmentStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {

    @Mock
    private AdminServiceImpl adminService;

    @Mock
    private LecturerCohortRepository lecturerCohortRepository;

    @Mock
    private LecturerRepository lecturerRepository;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private StudentCohortRepository studentCohortRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CohortRepository cohortRepository;

    @Mock
    private StudentRepository studentRepository;

    private List<Lecturer> lecturers;

    private List<Student> students;

    private Lecturer lecturer;

    private Student student;

    private Admin admin;

    private Cohort cohort;

    private Course course;

    private AdminStudentRequest adminStudentRequest;

    private AdminLecturerRequest adminLecturerRequest;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @Mock
    private EmailService emailService;

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    void setup() {
        lecturer = new Lecturer("zhangsan", LocalDateTime.now(), "zhangsan",
                LocalDateTime.now(), "zhangsan", "lisi",
                "zhangsan@gmail.com", "Zhangsan12345!", "12345678", "L999999");
        admin = new Admin("zhangsan", LocalDateTime.now(), "zhangsan",
                LocalDateTime.now(), "Zhangsan", "lisi",
                "zhangsan@gmail.com", "Zhangsan12345!", "12345678", "A999999");
        student = new Student("zhangsan", LocalDateTime.now(), "zhangsan",
                LocalDateTime.now(), "zhangsan", "lisi",
                "zhangsan@gmail.com", "Zhangsan12345!", "12345678", "S999999");
        adminStudentRequest = new AdminStudentRequest(1, "zhangsan", "zhangsan",
                "zhangsan@gmail.com", "Zhangsan12345!", "12345678");

        adminLecturerRequest = new AdminLecturerRequest(1, "zhangsan", "zhangsan", "zhangsan@gmail.com", "Zhangsan12345!", "12345678");

        course = new Course("YenKwan@gmail.com", LocalDateTime.now(), "YenKwan@gmail.com",
                LocalDateTime.now(), "Course Name", 3);

        // Setup for Cohort

        cohort = new Cohort("YenKwan@gmail.com", LocalDateTime.now(), "YenKwan@gmail.com",
                LocalDateTime.now(), "Cohort Name", "Cohort Description", course, LocalDateTime.now(),
                1, ClassDay.MONDAY, ClassSlot.AM);

        // Setup for lists
        lecturers = new ArrayList<>();
        students = new ArrayList<>();
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }

    @AfterEach
    void tearDown() {
        lecturer = null;
        lecturers = null;
        student = null;
        admin = null;
        students = null;
        course = null;
        cohort = null;
    }

    @Test
    void testShowLecturers() throws Exception {
        when(adminService.findAllLecturers()).thenReturn(lecturers);
        mockMvc.perform(get("/admin/lecturer/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONObject.toJSONString(lecturer)))
                .andExpect(status().isOk());
        verify(adminService, times(1)).findAllLecturers();
    }


    @Test
    void testUpdateLecturer() throws Exception {
        when(adminService.findLecturerByLecturerId(Mockito.anyInt())).thenReturn(lecturer);
        mockMvc.perform(post("/admin/lecturer/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONObject.toJSONString(adminLecturerRequest)))
                .andExpect(status().isOk());
        verify(adminService, times(1)).updateLecturer(any());
    }

    @Test
    void testDeleteLecturer() throws Exception {
        when(adminService.findLecturerByLecturerId(anyInt())).thenReturn(lecturer);
        mockMvc.perform(delete("/admin/lecturer/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONObject.toJSONString(lecturer)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
        verify(adminService, times(1)).deleteLecturer(any());
    }

    @Test
    void testShowStudents() throws Exception {
        when(adminService.findAllByisDelete()).thenReturn(students);
        mockMvc.perform(get("/admin/student/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONObject.toJSONString(student)))
                .andExpect(status().isOk());
        verify(adminService, times(1)).findAllByisDelete();
    }



    @Test
    void testUpdateStudent() throws Exception {
        when(adminService.findStudentByStudentId(anyInt())).thenReturn(student);
        mockMvc.perform(post("/admin/student/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONObject.toJSONString(adminStudentRequest)))
                .andExpect(status().isOk());
        verify(adminService, times(1)).updateStudent(any());
    }

    @Test
    void testDeleteStudent() throws Exception {
        mockMvc.perform(delete("/admin/student/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONObject.toJSONString(student)))
                .andExpect(status().isOk());
        verify(adminService, times(1)).deleteStudent(any());
    }

    @Test
    void testShowCourses() throws Exception {
        // Mock the course data
        List<Course> courses = new ArrayList<>();
        Course course1 = new Course("YenKwan@gmail.com", LocalDateTime.now(), "YenKwan@gmail.com",
                LocalDateTime.now(), "Course Name", 3);
        Course course2 = new Course("YenKwan@gmail.com", LocalDateTime.now(), "YenKwan@gmail.com",
                LocalDateTime.now(), "Course Name", 3);
        courses.add(course1);
        courses.add(course2);
        when(adminService.findAllCourses()).thenReturn(courses);
        // Perform the request
        mockMvc.perform(get("/admin/course/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/admin-course-view"))
                .andExpect(model().attribute("courses", courses))
                .andDo(MockMvcResultHandlers.print());

        // Verify the courseRepository interactions
        verify(adminService, times(1)).findAllCourses();
    }

    @Test
    void testUpdateCourse() throws Exception {
        // Mock the request payload
        AdminCourseRequest courseRequest = new AdminCourseRequest();
        courseRequest.setId(1);
        courseRequest.setCourseName("Math");
        courseRequest.setCredits(3);

        // Mock the existing course in the repository
        Course existingCourse = new Course();
        existingCourse.setId(1);

        // Mock the courseRepository behavior
        when(adminService.findCourseByCourseId(courseRequest.getId())).thenReturn(existingCourse);

        // Perform the request
        mockMvc.perform(post("/admin/course/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(courseRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Course updated successfully"))
                .andDo(MockMvcResultHandlers.print());

        // Verify the courseRepository interactions
        verify(adminService, times(1)).updateExistingCourse(any());
    }


    @Test
    void testDeleteCourse() throws Exception {
        // Mock the request payload
        AdminCourseRequest courseRequest = new AdminCourseRequest();
        courseRequest.setId(1);

        // Mock the existing course in the repository
        Course existingCourse = new Course();
        existingCourse.setId(1);

        // Mock the courseRepository behavior
        when(adminService.findCourseByCourseId(courseRequest.getId())).thenReturn(existingCourse);

        // Perform the request
        mockMvc.perform(delete("/admin/course/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(courseRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("You have deleted Course"))
                .andDo(MockMvcResultHandlers.print());

        // Verify the courseRepository interactions
        verify(adminService, times(1)).deleteExistingCourse(any());
    }



    @Test
    void testShowCohort() throws Exception {
        // Mock the adminService behavior
        // Create sample data for cohorts
        List<AdminCohortRequest> cohortList = new ArrayList<>();

        // Create an instance of AdminCohortRequest and add it to the list
        AdminCohortRequest cohort1 = new AdminCohortRequest(
                1, // cohortId
                1, // courseId
                "Java Spring", // courseName
                "Spring Cohort 2023", // name
                "2023 intake for Java Spring", // description
                50, // capacity
                "TUESDAY", // classDay
                "AM", // classSlot
                "2023-01-01", // cohort_start
                1, // lecturer
                "John Doe" // lecturerName
        );
        cohortList.add(cohort1);

        when(adminService.findCohortsAllByCourseId(1)).thenReturn(cohortList);

        // Create sample data for lecturers
        List<Lecturer> lecturersList = new ArrayList<>();

        // Create an instance of Lecturer and add it to the list
        Lecturer lecturer1 = new Lecturer(
                "admin", // createdBy
                LocalDateTime.now(), // createdTime
                "admin", // lastUpdatedBy
                LocalDateTime.now(), // lastUpdatedTime
                "John", // firstName
                "Doe", // lastName
                "john.doe@example.com", // email
                "password", // password
                "1234567890", // phone
                "L999999" // userNumber
        );
        lecturersList.add(lecturer1);

        // Add more sample lecturers as needed

        when(adminService.findAllLecturers()).thenReturn(lecturersList);

        // Perform the request
        mockMvc.perform(get("/admin/course/cohort/list/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/admin-cohort-view"))
                .andExpect(model().attribute("cohorts", cohortList))
                .andExpect(model().attribute("classDays", ClassDay.values()))
                .andExpect(model().attribute("classSlots", ClassSlot.values()))
                .andExpect(model().attribute("courseid", 1))
                .andExpect(model().attribute("lecturers", lecturersList))
                .andDo(MockMvcResultHandlers.print());

        // Verify the adminService interactions
        verify(adminService, times(1)).findCohortsAllByCourseId(1);

        // Verify the lecturerRepository interactions
        verify(adminService, times(1)).findAllLecturers();
    }



    @Test
    void testUpdateCohort() throws Exception {
        // Mock the request payload
        AdminCohortRequest cohortRequest = new AdminCohortRequest();
        cohortRequest.setName("Cohort 1");
        cohortRequest.setDescription("Description 1");
        cohortRequest.setCourseId(1);
        cohortRequest.setCohort_start("2023-06-21T10:00");
        cohortRequest.setCapacity(20);
        cohortRequest.setClassDay("MONDAY");
        cohortRequest.setClassSlot("AM");

        // Mock the existing cohort in the repository
        Cohort existingCohort = new Cohort();
        existingCohort.setId((long) 1);

        // Mock the cohortRepository behavior
        when(adminService.findCohortByCohortId(anyInt()))
                .thenReturn(existingCohort);

        // Perform the request
        mockMvc.perform(post("/admin/course/cohort/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(cohortRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Cohort updated successfully"))
                .andDo(MockMvcResultHandlers.print());

        // Verify the cohortRepository interactions
        verify(adminService, times(1)).updateExistingCourseCohort(any());
        verify(adminService, times(1)).findCohortByCohortId(anyInt());
    }

    @Test
    void testDeleteCohort() throws Exception {
        // Mock the request payload
        AdminCohortRequest cohortRequest = new AdminCohortRequest();
        cohortRequest.setCohortId(1);

        // Mock the existing cohort in the repository
        Cohort existingCohort = new Cohort();
        existingCohort.setId((long) 1);

        // Mock the cohortRepository behavior
        when(adminService.findCohortByCohortId(anyInt())).thenReturn(existingCohort);

        // Perform the request
        mockMvc.perform(delete("/admin/course/cohort/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(cohortRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("You have deleted Cohort"))
                .andDo(MockMvcResultHandlers.print());

        // Verify the cohortRepository interactions
        verify(adminService, times(1)).deleteExistingCourseCohort(any());
    }

    @Test
    void testShowEnrollment() throws Exception {
        // Mock the enrollment data
        List<AdminEnrollmentRequest> enrollments = new ArrayList<>();
        enrollments.add(new AdminEnrollmentRequest());

        // Mock the adminService behavior
        when(adminService.findAllEnrollment()).thenReturn(enrollments);

        // Perform the request
        mockMvc.perform(get("/admin/enrollment/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/admin-enrollment-view"))
                .andExpect(model().attribute("enrollments", enrollments))
                .andExpect(model().attribute("enrollmentStatus", EnrolmentStatus.values()))
                .andDo(MockMvcResultHandlers.print());

        // Verify the adminService interactions
        verify(adminService, times(1)).findAllEnrollment();
    }

    @Test
    void testUpdateEnrollment() throws Exception {
        // Mock the request payload
        AdminEnrollmentStatusRequest adminEnrollmentStatusRequest = new AdminEnrollmentStatusRequest();
        adminEnrollmentStatusRequest.setId(1);
        adminEnrollmentStatusRequest.setEnrolmentStatus(EnrolmentStatus.ENROLLED.name());

        // Mock the existing student cohort in the repository
        StudentCohort studentCohort = new StudentCohort();
        studentCohort.setId(1);

        // Mock the studentCohortRepository behavior
        when(adminService.findStudentCohortById(anyInt())).thenReturn(studentCohort);

        // Perform the request
        mockMvc.perform(post("/admin/enrollment/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(adminEnrollmentStatusRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Student Enrollment Status updated successfully"))
                .andDo(MockMvcResultHandlers.print());

        // Verify the studentCohortRepository interactions
        verify(adminService, times(1)).updateExistingStudentCohort(any());
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}