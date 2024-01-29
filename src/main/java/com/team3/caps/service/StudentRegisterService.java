package com.team3.caps.service;

import com.team3.caps.model.Cohort;
import com.team3.caps.model.Course;

import java.util.List;

public interface StudentRegisterService {
    void enrollCourse(int cohortId);

    List<Course> viewAllCourses();

     List<Course> viewCourseRecommendations(int studentId);

    List<Cohort> getCohorts(int courseId);
}
