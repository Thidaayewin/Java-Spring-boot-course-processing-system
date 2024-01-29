package com.team3.caps.repository;

import com.team3.caps.model.Cohort;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CohortRepository extends JpaRepository<Cohort, Integer> {

    @Query("SELECT c FROM Cohort c WHERE c.courseType.id = :id and isDeleted = false ")
    public List<Cohort> findCohortsAllByCourseId(@Param("id") Integer id);

    public Cohort findByIdAndIsDeleted(int id, boolean isDeleted);

    public List<Cohort> findAll();

    @Query("SELECT ch " +
            "FROM Course c " +
            "JOIN c.cohorts ch " +
            "WHERE c.isDeleted = false AND ch.isDeleted = false AND c.id=:courseId OR :courseId = 0")
    public List<Cohort> findCohortByCourseId(@Param("courseId") int courseId);
}
