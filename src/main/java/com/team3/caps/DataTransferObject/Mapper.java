package com.team3.caps.DataTransferObject;

import com.team3.caps.model.Course;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Mapper {
    public static CourseDTO toCourseDTO(Course course, int enrolmentCount){
        CourseDTO courseDTO = new CourseDTO();

        courseDTO.setId(course.getId());
        courseDTO.setName(course.getName());
        courseDTO.setCredits(course.getCredits());
        courseDTO.setDeleted(course.isDeleted());
        courseDTO.setEnrolmentCount(enrolmentCount);

        return courseDTO;
    }

    public static Course toCourse(CourseDTO courseDTO, String creatorEmail){
        LocalDateTime now = LocalDateTime.now();

        Course course = new Course(creatorEmail,now,creatorEmail,now,courseDTO.getName(),courseDTO.getCredits());
        course.setDeleted(courseDTO.isDeleted());

        return course;
    }
}
