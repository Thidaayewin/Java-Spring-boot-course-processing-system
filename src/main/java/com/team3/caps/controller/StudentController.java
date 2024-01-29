package com.team3.caps.controller;

import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.team3.caps.service.StudentGradeService;
import com.team3.caps.service.StudentRegisterService;
import com.team3.caps.model.Cohort;
import com.team3.caps.model.Course;

@Controller
@RequestMapping(value = "/student")
@PreAuthorize("hasRole('ROLE_Student')")
public class StudentController {
    @Autowired
    StudentGradeService stuGradeSvc;

    @Autowired
    StudentRegisterService stuRegSvc;

    @RequestMapping(method = RequestMethod.GET)
    public String showGrades(Model model) {
        Map<String, String> grades = stuGradeSvc.viewGrade();
        Double gpa = stuGradeSvc.viewGradePointAverage();

        model.addAttribute("studentGrade", grades);
        model.addAttribute("studentGpa", gpa);
        return "view-grades";
    }

    // Status code 200, but does not show recommended courses
    @RequestMapping(value = "/view-recommendations", method = RequestMethod.GET)
    public String showRecommendations(@RequestParam("studentId") int studentId, Model model) {
        List<Course> recommendedCourses = stuRegSvc.viewCourseRecommendations(studentId);
        model.addAttribute("recommendedCourses", recommendedCourses);
        return "view-recommendations";
    }

    @RequestMapping(value = "/view-all-courses", method = RequestMethod.GET)
    public String showAllCourses(Model model) {
        List<Course> allCourses = stuRegSvc.viewAllCourses();
        model.addAttribute("allCourses", allCourses);
        return "view-all-courses";
    }

    @RequestMapping(value = "/view-cohorts", method = RequestMethod.GET)
    public String showCohorts(@RequestParam("courseId") int courseId, Model model) {
        List<Cohort> cohorts = stuRegSvc.getCohorts(courseId);
        model.addAttribute("cohorts", cohorts);
        return "view-all-cohorts";
    }
}
