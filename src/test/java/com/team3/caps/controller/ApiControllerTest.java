package com.team3.caps.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team3.caps.DataTransferObject.CourseDTO;
import com.team3.caps.DataTransferObject.StudentDto;
import com.team3.caps.model.Cohort;
import com.team3.caps.model.Course;
import com.team3.caps.model.Student;
import com.team3.caps.model.Tag;
import com.team3.caps.service.ApiService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class ApiControllerTest {

    @Mock
    private ApiService apiService;
    @Mock
    private Cohort cohort;
    @Mock
    private Student student;
    @Mock
    private StudentDto studentDto;
    @Mock
    private Course course;
    @Mock
    private CourseDTO courseDto;
    @Mock
    private Tag tag;

    @InjectMocks
    private ApiController apiController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        cohort.setId(1L);
        student.setFirstName("John");
        studentDto.setFirstName("John");
        course.setCredits(6);
        courseDto.setCredits(6);
        tag.setTagInfo("Sample tag");
        mockMvc = MockMvcBuilders.standaloneSetup(apiController).build();
    }

    @AfterEach
    void tearDown() {
        cohort=null;
        student = null;
        studentDto=null;
        course = null;
        courseDto=null;
        tag = null;
    }

    @Test
    public void getCohortsByCourse() throws Exception {
        List<Cohort> cohorts = new ArrayList<>();
        cohorts.add(cohort);

        when(apiService.findCohortsByCourseId(anyInt())).thenReturn(cohorts);

        mockMvc.perform(get("/api/cohorts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(cohorts)))
                .andExpect(status().isOk());
        verify(apiService, times(1)).findCohortsByCourseId(anyInt());
    }

    @Test
    void getAllStudents() throws Exception {
        List<StudentDto> studentDtos = new ArrayList<>();
        studentDtos.add(studentDto);
        List<Student> students = new ArrayList<>();

        when(apiService.findAllStudentsInCohort(anyInt())).thenReturn(students);

        mockMvc.perform(get("/api/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(studentDtos)))
                .andExpect(status().isOk());
        verify(apiService, times(1)).findAllStudentsInCohort(anyInt());
    }

    @Test
    void getCoursesPopular() throws Exception {
        List<CourseDTO> courseDTOs = new ArrayList<>();
        courseDTOs.add(courseDto);

        when(apiService.findTopPopularCourseDTOByN(anyInt())).thenReturn(courseDTOs);

        mockMvc.perform(get("/api/courses/top/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(courseDto)))
                .andExpect(status().isOk());
        verify(apiService, times(1)).findTopPopularCourseDTOByN(anyInt());
    }

    @Test
    void saveTag() throws Exception {
        when(apiService.saveTag(any())).thenReturn(tag);
        mockMvc.perform(post("/api/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(tag)))
                .andExpect(status().isCreated());
        verify(apiService, times(1)).saveTag(any());
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}