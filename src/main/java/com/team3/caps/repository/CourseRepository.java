package com.team3.caps.repository;

import com.team3.caps.model.Cohort;
import com.team3.caps.model.Course;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

        @Query("SELECT c.cohorts FROM Course c JOIN c.cohorts co WHERE c = :course AND co.isDeleted = false")
        List<Cohort> findCohortByCourse(@Param("course") Course course);

        public Optional<Course> findByIdAndIsDeleted(int id, boolean isDeleted);

        public Course findByNameAndIsDeleted(String name, boolean isDeleted);

        public List<Course> findByIsDeleted(boolean isDeleted);

        @Query("SELECT c " +
                        "FROM Lecturer l " +
                        "JOIN l.cohortsTaught lc  " +
                        "JOIN lc.cohort ch " +
                        "JOIN ch.courseType c " +
                        "WHERE l.email = :lecturerEmail " +
                        "AND l.isDeleted = false AND lc.isDeleted = false AND ch.isDeleted = false AND c.isDeleted =false")
        public List<Course> findByLecturerEmail(@Param("lecturerEmail") String email);

        @Query("Select c From Course c where c.id=:courseId ")
        public Course findCourseByCourseId(@Param("courseId") int courseId);

        // ---------Jason's methods-----------

        // DISTINCT s to eliminate students who re-took the course
        @Query("SELECT c, COUNT(DISTINCT s) AS studentCount " +
                        "FROM Student s " +
                        "JOIN s.cohortsEnrolled sc " +
                        "JOIN sc.cohort ch " +
                        "JOIN ch.courseType c " +
                        "WHERE (sc.enrolmentStatus<> 'REMOVED' AND s.isDeleted = false AND sc.isDeleted = false AND ch.isDeleted = false)"
                        +
                        "GROUP BY c " +
                        "ORDER BY studentCount DESC")
        public List<Object[]> findTopCoursesByStudentsEnrolled();

        public List<Course> findAllByIsDeleted(boolean isDeleted);
}