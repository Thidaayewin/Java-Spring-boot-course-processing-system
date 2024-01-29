package com.team3.caps.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
// import com.team3.caps.model.AccountHolder;
// import com.team3.caps.model.Cohort;
import com.team3.caps.model.Course;
import com.team3.caps.model.Lecturer;
import com.team3.caps.model.Student;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

// import java.util.List;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, Integer> {
    public Lecturer findByEmailAndIsDeleted(String email, boolean isDeleted);

    public List<Lecturer> findAll();

    @Query("SELECT l FROM Lecturer l WHERE l.isDeleted = false")
    List<Lecturer> findAllLecturers();

    public Lecturer findByIdAndIsDeleted(int id, boolean isDeleted);

    // public List<Lecturer> findAll();

    public List<Lecturer> findByIsDeleted(boolean isDeleted);

    public Lecturer findByEmail(String email);

    @Query("SELECT c " +
            "FROM Lecturer l " +
            "JOIN l.cohortsTaught lc  " +
            "JOIN lc.cohort ch " +
            "JOIN ch.courseType c " +
            "WHERE l.email = :lecturerEmail " +
            "AND l.isDeleted = false " +
            "AND lc.isDeleted = false " +
            "AND ch.isDeleted = false " +
            "AND c.isDeleted = false")
    public List<Course> findLecturerCoursesByEmail(@Param("lecturerEmail") String lecturerEmail);
    // List of courses taught by the specific lecturer

    @Query("SELECT COUNT(sc) FROM StudentCohort sc WHERE sc.cohort.id = :cohortId AND NOT sc.enrolmentStatus = 'REMOVED'")
    public int findCourseEnrollmentByCohortId(@Param("cohortId") long cohortId);
    // Count of students enrolled in the specific cohort

    @Query("SELECT s " +
            "FROM Student s " +
            "JOIN s.cohortsEnrolled sc " +
            "JOIN sc.cohort ch " +
            "WHERE ch.id = :cohortId " +
            "AND NOT sc.enrolmentStatus = 'REMOVED' " +
            "AND s.isDeleted = false" +
            " AND sc.isDeleted = false " +
            "AND ch.isDeleted = false")
    public List<Student> findStudentsByCohortId(@Param("cohortId") int cohortId);
    // List of students in a specific cohort

}
