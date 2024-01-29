package com.team3.caps.DataTransferObject;


public class AdminEnrollmentStatusRequest {
    
    private int id;
    
    private String enrolmentStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnrolmentStatus() {
        return enrolmentStatus;
    }

    public void setEnrolmentStatus(String enrolmentStatus) {
        this.enrolmentStatus = enrolmentStatus;
    }

    public AdminEnrollmentStatusRequest(int id, String enrolmentStatus) {
        this.id = id;
        this.enrolmentStatus = enrolmentStatus;
    }

    public AdminEnrollmentStatusRequest() {
    }

}