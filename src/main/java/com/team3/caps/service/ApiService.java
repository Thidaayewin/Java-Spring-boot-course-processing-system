package com.team3.caps.service;

import com.team3.caps.DataTransferObject.CourseDTO;
import com.team3.caps.DataTransferObject.Mapper;
import com.team3.caps.exception.BadTagException;
import com.team3.caps.model.Cohort;
import com.team3.caps.model.Course;
import com.team3.caps.model.Student;
import com.team3.caps.model.Tag;
import com.team3.caps.repository.CohortRepository;
import com.team3.caps.repository.CourseRepository;
import com.team3.caps.repository.StudentRepository;
import com.team3.caps.repository.TagRepository;
import org.hibernate.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ApiService {

    private CourseRepository courseRepository;
    private TagRepository tagRepository;
    private StudentRepository studentRepository;
    private CohortRepository cohortRepository;
   

     @Autowired
    public ApiService(CourseRepository courseRepository, TagRepository tagRepository, 
                        CohortRepository cohortRepository,
                        StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.tagRepository = tagRepository;
        this.cohortRepository = cohortRepository;
        this.studentRepository=studentRepository;
    }

    // to override Impl
    public List<CourseDTO> findTopPopularCourseDTOByN(int n){
        List<Object[]> retrievedCourses = courseRepository.findTopCoursesByStudentsEnrolled();

        if(!(retrievedCourses.get(0)[0] instanceof Course)){
            throw new TypeMismatchException("The service or repository layer has retrieved an incorrect type!");
        }
        else if(!(retrievedCourses.get(0)[1] instanceof Long)){
            throw new TypeMismatchException("The service or repository layer has retrieved an incorrect type!");
        }

        List<CourseDTO> topCoursesDTO = retrievedCourses.stream()
                                                        .map(pair-> Mapper.toCourseDTO(
                                                            ((Course)pair[0]),
                                                            ((Long) pair[1]).intValue())
                                                        )
                                                        .limit(n).collect(Collectors.toList());
        return topCoursesDTO;
    }

    public List<CourseDTO> findTopPopularCourseDTOAll(){

        List<Course> courseListFull = courseRepository.findAllByIsDeleted(false);

        // instantiate final list to return
        // enrolmentCount values are not filled in- set to 0 first
        // later substitute with the retrieved values if their id is found within the retrievedCourses hashmap
        List<CourseDTO> courseDTOListFull = courseListFull.stream()
                                                    .map(course -> Mapper.toCourseDTO(
                                                        course,
                                                        0)
                                                    )
                                                    .collect(Collectors.toList());

        // NOTE- this will NOT retrieve/show courses with 0 students enrolmentCount, due to the nature of the query!
        List<Object[]> retrievedCourses = courseRepository.findTopCoursesByStudentsEnrolled();

        if(!(retrievedCourses.get(0)[0] instanceof Course)){
            throw new TypeMismatchException("The service or repository layer has retrieved an incorrect type!");
        }
        else if(!(retrievedCourses.get(0)[1] instanceof Long)){
            throw new TypeMismatchException("The service or repository layer has retrieved an incorrect type!");
        }
        // change to a map for O(1) access
        HashMap<Integer,CourseDTO> retrievedCoursesDTOMap = retrievedCourses.stream().collect(Collectors.toMap(
                entry-> ((Course)entry[0]).getId(),  //key mapper
                entry-> Mapper.toCourseDTO( (Course)entry[0] , ((Long)entry[1]).intValue()),   //value mapper
                (existing,replacement)-> existing, HashMap::new));  //merge function

        List<CourseDTO> allCoursesWithEnrolmentCount = courseDTOListFull.stream()
                                                            .map(courseDTO-> retrievedCoursesDTOMap.containsKey(courseDTO.getId())?  //if condition- does this id exist in the enrolmentcount retrieval map?
                                                                retrievedCoursesDTOMap.get(courseDTO.getId()):  //if it does exist, use the DTO mapped value in retrievedCoursesDTOMap
                                                                courseDTO)  //if it doesn't, just keep the original value with 0
                                                            .sorted(Comparator.comparing(CourseDTO::getEnrolmentCount).reversed())
                                                            .collect(Collectors.toList());

        return allCoursesWithEnrolmentCount;
    }

    public List<CourseDTO> findCourseDTOAll(){

        // simple implementation
        List<Course> courseList = courseRepository.findAllByIsDeleted(false);

        return courseList.stream().map(elem -> Mapper.toCourseDTO(elem,-1)).collect(Collectors.toList());
        // do not retrieve the count here, just map to -1
    }

    @Transactional(readOnly = false)
    public Tag saveTag(Tag tag) throws BadTagException {

         if(tag.getTagInfo().trim().equals("")){
             throw new BadTagException("Tag should not be blank!");
         }
        List<Tag> allTags = tagRepository.findAll();
        for(Tag existingTag :allTags){
            if(existingTag.getTagInfo().toLowerCase().equals(tag.getTagInfo().toLowerCase()))
                throw new BadTagException("Tag is a duplicate subject!");
                //do not create the tag
        }

        LocalDateTime now = LocalDateTime.now();
        tag.setDeleted(false);
        tag.setCreatedBy("community");
        tag.setCreatedTime(now);
        tag.setLastUpdatedBy("community");
        tag.setLastUpdatedTime(now);
        tag.setTaggedBy(new ArrayList<>());

        return tagRepository.save(tag);
    }


  public List<Student> findAllStudentsInCohort(int cohortId) {
        List<Student> studentList = studentRepository.findStudentsByCohortId(cohortId);
        return studentList;
    }

   

    public List<Cohort> findCohortsByCourseId(int courseId){
        List<Cohort> cohorts = cohortRepository.findCohortByCourseId(courseId);
        return cohorts;

    }

    public List<Student> findAllStudent() {
        List<Student> allstudents = studentRepository.findAll();
        return allstudents;
    }

  


}