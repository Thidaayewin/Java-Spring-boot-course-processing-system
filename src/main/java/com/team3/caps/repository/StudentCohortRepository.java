package com.team3.caps.repository;

import com.team3.caps.model.Cohort;
import com.team3.caps.model.Student;
import com.team3.caps.model.StudentCohort;
import com.team3.caps.util.EnrolmentStatus;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentCohortRepository extends JpaRepository<StudentCohort, Integer> {

   @Query("SELECT sc FROM StudentCohort sc JOIN sc.student s WHERE s.id = :studentId AND sc.isDeleted = false")
   List<StudentCohort> findByStudent(@Param("studentId") int studentId);

   List<StudentCohort> findByIsDeleted(boolean isDeleted);

   StudentCohort findByIdAndIsDeleted(int id, boolean isDeleted);

   // public StudentCohort findByIdAndIsDeleted(int id, boolean isDeleted);

   // public List<StudentCohort> findByCohorts(int id);

   public List<StudentCohort> findAll();

   public StudentCohort findByStudentIdAndCohortId(int studentid, int cohortid);

   @Query("SELECT sc " +
         "FROM Student s " +
         "JOIN s.cohortsEnrolled sc " +
         "WHERE s.id = :studentId " +
         "AND NOT sc.enrolmentStatus = 'REMOVED' " +
         "AND s.isDeleted = false AND sc.isDeleted = false")
   public List<StudentCohort> findByStudentIdAndEnrolmentStatusAndIsDeleted(@Param("studentId") int id);

   @Query("SELECT sc FROM StudentCohort sc " +
         "JOIN sc.student s " +
         "JOIN sc.cohort c " +
         "JOIN AccountHolder ah " +
         "WHERE ah.id = :studentId " +
         "AND ah.userNumber = s.userNumber " +
         "AND c.id = :cohortId " +
         "AND sc.enrolmentStatus <> 'REMOVED' " +
         "AND s.isDeleted = false " +
         "AND sc.isDeleted = false")
   public StudentCohort getStudentCohortByStudentAndCohort(@Param("studentId") int studentId,
         @Param("cohortId") int cohortId);

   @Query("SELECT sc FROM StudentCohort sc " +
         "JOIN sc.student s " +
         "JOIN AccountHolder ah " +
         "WHERE ah.id = :studentId " +
         "AND ah.userNumber = s.userNumber " +
         "AND sc.enrolmentStatus <> 'REMOVED' " +
         "AND s.isDeleted = false " +
         "AND sc.isDeleted = false")
   public List<StudentCohort> getStudentCohortByStudentId(@Param("studentId") int studentId);

   boolean existsByStudentAndCohort(Student s, Cohort c);

   boolean existsByStudentAndCohortAndEnrolmentStatus(Student s, Cohort c, EnrolmentStatus enrolmentStatus);

}
