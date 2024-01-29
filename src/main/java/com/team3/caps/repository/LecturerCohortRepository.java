package com.team3.caps.repository;

import com.team3.caps.model.LecturerCohort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LecturerCohortRepository extends JpaRepository<LecturerCohort, Integer> {
	public LecturerCohort findByIdAndIsDeleted(int id, boolean isDeleted);

	LecturerCohort findByCohortIdAndIsDeleted(int cohortId, boolean isDeleted);

	LecturerCohort findTop1ByCohortIdAndIsDeletedOrderByCohortIdDesc(int cohortId, boolean isDeleted);
}
