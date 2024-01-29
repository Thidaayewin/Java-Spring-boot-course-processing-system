package com.team3.caps.DataTransferObject;



public class LecturerStudentScore{
    public int studentid;
    public int cohortid;
    public double score;
    public LecturerStudentScore(){}
   

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public LecturerStudentScore(int studentid, int cohortid, double score) {
        this.studentid = studentid;
        this.cohortid = cohortid;
        this.score = score;
    }


    public int getStudentid() {
        return studentid;
    }


    public void setStudentid(int studentid) {
        this.studentid = studentid;
    }


    public int getCohortid() {
        return cohortid;
    }


    public void setCohortid(int cohortid) {
        this.cohortid = cohortid;
    }
        
    }
