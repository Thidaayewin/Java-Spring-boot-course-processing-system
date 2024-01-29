package com.team3.caps.DataTransferObject;
public class AdminCohortRequest {
            private int cohortId;
            private int courseId;
            private String courseName;
            private String name;
            private String description;
            private int capacity;
            private String classDay;
            private String classSlot; 
            private String cohort_start;
            private int lecturer;
            private String lecturerName;
            
            public AdminCohortRequest(int cohortId, int courseId, String courseName, String name, String description,
                    int capacity, String classDay, String classSlot, String cohort_start, int lecturer,
                    String lecturerName) {
                this.cohortId = cohortId;
                this.courseId = courseId;
                this.courseName = courseName;
                this.name = name;
                this.description = description;
                this.capacity = capacity;
                this.classDay = classDay;
                this.classSlot = classSlot;
                this.cohort_start = cohort_start;
                this.lecturer = lecturer;
                this.lecturerName = lecturerName;
            }
            public AdminCohortRequest(int cohortId, int courseId, String courseName, String name, String description,
                    int capacity, String classDay, String classSlot, String cohort_start, int lecturer) {
                this.cohortId = cohortId;
                this.courseId = courseId;
                this.courseName = courseName;
                this.name = name;
                this.description = description;
                this.capacity = capacity;
                this.classDay = classDay;
                this.classSlot = classSlot;
                this.cohort_start = cohort_start;
                this.lecturer = lecturer;
            }
            public int getCohortId() {
                return cohortId;
            }
            public void setCohortId(int cohortId) {
                this.cohortId = cohortId;
            }
            public int getCourseId() {
                return courseId;
            }
            public void setCourseId(int courseId) {
                this.courseId = courseId;
            }
            public String getCourseName() {
                return courseName;
            }
            public void setCourseName(String courseName) {
                this.courseName = courseName;
            }
            public String getName() {
                return name;
            }
            public void setName(String name) {
                this.name = name;
            }
            public String getDescription() {
                return description;
            }
            public void setDescription(String description) {
                this.description = description;
            }
            public int getCapacity() {
                return capacity;
            }
            public void setCapacity(int capacity) {
                this.capacity = capacity;
            }
            public String getClassDay() {
                return classDay;
            }
            public void setClassDay(String classDay) {
                this.classDay = classDay;
            }
            public String getClassSlot() {
                return classSlot;
            }
            public void setClassSlot(String classSlot) {
                this.classSlot = classSlot;
            }
            public String getCohort_start() {
                return cohort_start;
            }
            public void setCohort_start(String cohort_start) {
                this.cohort_start = cohort_start;
            }
            public AdminCohortRequest(int cohortId, int courseId, String courseName, String name, String description,
                    int capacity, String classDay, String classSlot, String cohort_start) {
                this.cohortId = cohortId;
                this.courseId = courseId;
                this.courseName = courseName;
                this.name = name;
                this.description = description;
                this.capacity = capacity;
                this.classDay = classDay;
                this.classSlot = classSlot;
                this.cohort_start = cohort_start;
            }
            public AdminCohortRequest() {
            }
            public int getLecturer() {
                return lecturer;
            }
            public void setLecturer(int lecturer) {
                this.lecturer = lecturer;
            }
            public String getLecturerName() {
                return lecturerName;
            }
            public void setLecturerName(String lecturerName) {
                this.lecturerName = lecturerName;
            }
            
            
            

            
}
