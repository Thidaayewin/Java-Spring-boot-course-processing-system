package com.team3.caps.DataTransferObject;
import com.team3.caps.model.Cohort;
import com.team3.caps.model.Student;
import lombok.Data;

@Data
public class EnrollStudentRequest {
    // private int studentCohortId;
    private Student student;
    private Cohort cohort;
    private String enrollmentStatus;
    
}
