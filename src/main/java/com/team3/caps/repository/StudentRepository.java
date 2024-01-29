package com.team3.caps.repository;

import com.team3.caps.model.Student;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    // @Query("SELECT s FROM Student s WHERE s.isDeleted = false AND s.email = :email")
    // public Student findByEmailandIsNotDeleted(@Param("email") String email);

    @Query("SELECT s FROM Student s WHERE s.isDeleted = false AND s.email = :email")
    public Student findByEmailandIsNotDeleted(@Param("email") String email);

    public List<Student> findByIsDeleted(boolean isDeleted);
    public List<Student> findAll();

    public Student findByEmailAndIsDeleted(String email,boolean isDeleted);
    @Query("SELECT s FROM Student s WHERE s.isDeleted = false AND s.email = :email")
    public Student findByEmailandIsDeleted(@Param("email") String email);


    @Query("SELECT s FROM Student s WHERE s.isDeleted = false")
    List<Student> findAllByisDelete();

    

    public Student findById(int id);

    public Student findByFirstNameAndLastNameAndIsDeleted(String firstName, String lastName,boolean isDeleted);

    @Transactional
    @Modifying
    @Query("update Student set isDeleted=true where id =?1")
    public void softDelete (int id);


    public Student findByIdAndIsDeleted(int id,boolean isDeleted);

    @Query("SELECT s " +
            "FROM Student s " +
            "JOIN s.cohortsEnrolled sc " +
            "JOIN sc.cohort ch " +
            "WHERE ch.id = :cohortId " +
            "AND NOT sc.enrolmentStatus = 'REMOVED' " +
            "AND s.isDeleted = false"+
            " AND sc.isDeleted = false "+
            "AND ch.isDeleted = false")
    public  List<Student> findStudentsByCohortId(@Param("cohortId") int cohortId); 

    public Student findByFirstNameAndLastName(String firstName, String lastName);

     @Query("SELECT s " +
            "FROM Student s " +
            "JOIN s.cohortsEnrolled sc " +
            "JOIN sc.cohort ch " +
            "JOIN ch.courseType c " +
            "WHERE c.id = :courseId " +
            "AND NOT sc.enrolmentStatus =  'REMOVED' " +
            "AND s.isDeleted = false AND sc.isDeleted = false AND ch.isDeleted = false AND c.isDeleted = false")
    public List<Student> findByCourseIdNotRemoved(@Param("courseId") int id);
    //@Modifying
    // @Query("UPDATE Student s SET s.isDeleted = true WHERE s.id = :id")
    // public void softDelete(@Param("id") int id);



}
