package com.team3.caps.DataTransferObject;

import lombok.Data;

@Data
public class CourseDTO {
    
    private int id;
    
    private String name;
    
    private int credits;
    
    private boolean isDeleted;
    
    private int enrolmentCount;

    public CourseDTO() {}

    public CourseDTO(int id, String name, int credits, boolean isDeleted, int enrolmentCount) {
        this.id = id;
        this.name = name;
        this.credits = credits;
        this.isDeleted = isDeleted;
        this.enrolmentCount = enrolmentCount;
    }
}
