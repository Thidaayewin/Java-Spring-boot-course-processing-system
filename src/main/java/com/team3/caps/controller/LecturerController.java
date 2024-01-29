package com.team3.caps.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.team3.caps.model.Cohort;
import com.team3.caps.model.Course;
import com.team3.caps.DataTransferObject.LecturerStudentScore;
import com.team3.caps.model.Student;
import com.team3.caps.model.StudentCohort;
import com.team3.caps.service.ApiService;
import com.team3.caps.service.LecturerService;
import com.team3.caps.service.CohortService;
import com.team3.caps.service.StudentCohortService;

@Controller
@PreAuthorize("hasRole('ROLE_Lecturer')")
public class LecturerController {
    private ApiService apiService;
    private CohortService cohortService;
    private StudentCohortService studentCohortService;
    private LecturerService lecturerSvc;

    @Autowired
    public LecturerController(ApiService apiService,
            CohortService cohortService,
            StudentCohortService studentCohortService,
            LecturerService lecturerSvc) {
        this.apiService = apiService;
        this.cohortService = cohortService;
        this.studentCohortService = studentCohortService;
        this.lecturerSvc = lecturerSvc;
    }

    // Home page for lecturer's view
    @GetMapping("/lecturer")
    public String home(Model model) {
        // Displays list of courses taught by lecturer
        // and also their respective course enrollments
        List<Course> courses = lecturerSvc.viewLecturerCourses();

        // using a map for cohort-student count mapping
        Map<Long, Integer> cohortStudentCountMap = new HashMap<>();

        for (Course course : courses) {
            for (Cohort cohort : course.getCohorts()) {
                long cohortId = cohort.getId().intValue();
                int studentCount = lecturerSvc.viewCourseEnrollment(cohortId);
                cohortStudentCountMap.put(cohortId, studentCount);
            }
        }

        model.addAttribute("courses", courses);
        model.addAttribute("cohortStudentCountMap", cohortStudentCountMap);

        return "lecturer-home";
    }

    // Listing students based on course cohort
    @GetMapping("/student/list/{courseId}/{cohortId}")
    public String listStudentInfo(@PathVariable("courseId") int courseId,
            @PathVariable("cohortId") int cohortId,
            Model model) {

        // List of students enrolled in the cohort
        List<Student> students = lecturerSvc.viewStudentsEnrolled(cohortId);
        model.addAttribute("students", students);

        // List of courses taught by lecturer
        List<Course> courses = lecturerSvc.viewLecturerCourses();
        model.addAttribute("courses", courses);

        // List of cohorts in the course
        List<Cohort> courseCohorts = cohortService.findAll();
        model.addAttribute("courseCohorts", courseCohorts);

        // Map of students and their grades for the specific cohort
        Map<String, Double> studentGrades = new HashMap<>();
        List<StudentCohort> allStudentCohort = studentCohortService.findAll();
        for (StudentCohort studentCohort : allStudentCohort) {
            Student student = studentCohort.getStudent();
            Cohort cohort = studentCohort.getCohort();
            String key = student.getId() + "-" + cohort.getId();
            Double score = studentCohort.getScore();
            studentGrades.put(key, score);
        }
        model.addAttribute("studentGrades", studentGrades);

        // map course id and name
        Map<Integer, String> courseIdNameMap = new HashMap<>();
        for (Course course : courses) {
            courseIdNameMap.put(course.getId(), course.getName());
        }
        model.addAttribute("courseIdNameMap", courseIdNameMap);

        // map cohort id and name
        Map<Integer, String> cohortIdNameMap = new HashMap<>();
        for (Cohort cohort : courseCohorts) {
            cohortIdNameMap.put(cohort.getId().intValue(), cohort.getName());
        }

        // list of student cohorts
        // Map<Integer,Integer> studentCohortIdMap=new HashMap<>();
        // for (Student student : students){
        // int studentId = student.getId();
        // int ;
        // studentCohortIdMap.put(studentId,studentCohortId);
        // }
        // model.addAttribute("studentCohortIdMap", studentCohortIdMap);

        // mapping cohort name for dropdown list
        model.addAttribute("cohortIdNameMap", cohortIdNameMap);

        return "studentsInfo";
    }

    @GetMapping("/cohorts/{courseId}")
    public ResponseEntity<List<Cohort>> getCohortsByCourse(@PathVariable("courseId") int courseId) {

        List<Cohort> cohorts = apiService.findCohortsByCourseId(courseId);
        if (cohorts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(cohorts, HttpStatus.OK);
        }
    }

    @GetMapping("/student/viewPerformance/{studentCohortId}/{cohortId}")
    public String viewStudentPerformance(@PathVariable("studentCohortId") Integer studentid,
            @PathVariable("cohortId") Integer cohortId, Model model) {
        // specific performance
        StudentCohort studentCohortId = studentCohortService.findByStudentIdAndCohortId(studentid, cohortId);
        StudentCohort stuPerformance = studentCohortService.findById(studentCohortId.getId());
        model.addAttribute("stuPerformance", stuPerformance);

        // get student id from student cohort id
        int studentId;
        studentId = stuPerformance.getStudent().getId();

        // list of student cohorts student has
        List<StudentCohort> stuCohorts = studentCohortService.findStudentCohortByStudentId(studentId);
        model.addAttribute("stuCohorts", stuCohorts);

        // student's GPA from student controller
        double studentGPA = lecturerSvc.lecturerViewGradePointAverage(studentId);
        model.addAttribute("studentGPA", studentGPA);

        return "view-student-Performance";
    }

    @GetMapping("/student/viewPerformance/{studentid}")
    public String viewStudentPerformanceDetails(@PathVariable("studentid") Integer studentid, Model model) {
        // specific performance
        StudentCohort stuPerformance = studentCohortService.findById(studentid);
        model.addAttribute("stuPerformance", stuPerformance);

        // get student id from student cohort id
        int studentId;
        studentId = stuPerformance.getStudent().getId();

        // list of student cohorts student has
        List<StudentCohort> stuCohorts = studentCohortService.findStudentCohortByStudentId(studentId);
        model.addAttribute("stuCohorts", stuCohorts);

        // student's GPA from student controller
        double studentGPA = lecturerSvc.lecturerViewGradePointAverage(studentId);
        model.addAttribute("studentGPA", studentGPA);

        return "view-student-Performance";
    }

    @GetMapping("/student/editScore/{id}")
    public String editStudentScore(@PathVariable("id") Integer id, Model model) {
        StudentCohort studentCohort = studentCohortService.findById(id);
        model.addAttribute("studentCohort", studentCohort);
        return "edit-student-score";

    }

    @PostMapping("/student/editScore/{id}")
    public String saveUpdatedScore(@PathVariable("id") Integer id, StudentCohort studentSCohort) {
        studentCohortService.save(studentSCohort);
        return "redirect:/studentsInfo";

    }

    @PostMapping("/student/editScore")
    public ResponseEntity<String> saveUpdatedScore(@RequestBody LecturerStudentScore lecturerStudentScore) {
        StudentCohort studentCohort = studentCohortService
                .findByStudentIdAndCohortId(lecturerStudentScore.getStudentid(), lecturerStudentScore.getCohortid());

        if (studentCohort == null) {
            return ResponseEntity.badRequest().body("Student cannot found");
        }

        // Update the course with the new data
        studentCohort.setScore(lecturerStudentScore.getScore());
        studentCohortService.save(studentCohort);

        return ResponseEntity.ok("Student Enrollment Status updated successfully");
    }

}
