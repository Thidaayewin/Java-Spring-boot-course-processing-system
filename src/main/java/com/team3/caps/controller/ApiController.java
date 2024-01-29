package com.team3.caps.controller;

import com.team3.caps.DataTransferObject.CourseDTO;
import com.team3.caps.DataTransferObject.StudentDto;
import com.team3.caps.exception.BadTagException;
import com.team3.caps.model.Cohort;
import com.team3.caps.model.Student;
import com.team3.caps.model.Tag;
import com.team3.caps.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {
    private ApiService apiService;

    @Autowired
    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    // For filtering of dropdown lists
    @CrossOrigin
    @GetMapping("/cohorts/{courseId}")
    public ResponseEntity<List<Cohort>> getCohortsByCourse(@PathVariable("courseId") int courseId) {
        List<Cohort> cohorts = apiService.findCohortsByCourseId(courseId);
        if (cohorts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(cohorts, HttpStatus.OK);
        }
    }

    // entire list of student
    @CrossOrigin
    @GetMapping("/students/{cohortId}")
    public ResponseEntity<List<StudentDto>> getAllStudents(@PathVariable("cohortId") int cohortId) {
        List<Student> students = apiService.findAllStudentsInCohort(cohortId);
        ArrayList<StudentDto> studentsDTO = new ArrayList<>();

        // System.out.println(String.format("Number of students found: %d",
        // students.size()));
        for (Student s : students) {
            StudentDto stdDTO = StudentDto.toStudentDto(s, cohortId);
            studentsDTO.add(stdDTO);
        }

        return new ResponseEntity<>(studentsDTO, HttpStatus.OK);

    }

    // full url is `http://localhost:4000/api/courses/top/99`
    @CrossOrigin()
    @GetMapping("/courses/top/{count}")
    public ResponseEntity<List<CourseDTO>> getCoursesPopular(@PathVariable("count") int count) {
        List<CourseDTO> courseDTOs = apiService.findTopPopularCourseDTOByN(count);

        if (courseDTOs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(courseDTOs, HttpStatus.OK);
        }
    }

    // full url is `http://localhost:4000/api/courses/all`
    @CrossOrigin()
    @GetMapping("/courses/all")
    public ResponseEntity<List<CourseDTO>> getCoursesAll() {
        // List<CourseDTO> courses = apiService.findCourseDTOAll();
        List<CourseDTO> courses = apiService.findTopPopularCourseDTOAll();

        if (courses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(courses, HttpStatus.OK);
        }
    }

    @CrossOrigin()
    @PostMapping("/tags")
    public ResponseEntity<Tag> saveTag(@RequestBody Tag tag) throws BadTagException {
//        try {
            Tag savedTag = apiService.saveTag(tag);
            return new ResponseEntity<>(savedTag, HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }

    }

}
