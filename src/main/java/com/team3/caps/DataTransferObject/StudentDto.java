
package com.team3.caps.DataTransferObject;

import java.util.List;

import com.team3.caps.model.Student;
import com.team3.caps.model.StudentCohort;

import lombok.Data;

@Data
public class StudentDto {
	private String firstName;
	private String lastName;
	private String email;
	private String userNumber;
	private double score;

	public StudentDto(String firstName, String lastName, String email, String userNumber, double score) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.userNumber = userNumber;
		this.score = score;
	}

	public static StudentDto toStudentDto(Student student, int cohortId) {
		List<StudentCohort> cohort = student.getCohortsEnrolled();
		double score = 0;

		// Assumption: The cohortID is guaranteed to be in the list
		for (StudentCohort c : cohort) {
			if (c.getCohort().getId() == cohortId) {
				score = c.getScore();
			}
		}
		return new StudentDto(student.getFirstName(), student.getLastName(), student.getEmail(),
				student.getUserNumber(), score);
	}
}
