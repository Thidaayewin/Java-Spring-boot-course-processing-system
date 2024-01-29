package com.team3.caps;

import com.team3.caps.model.*;
import com.team3.caps.repository.*;
import com.team3.caps.security.SecurityConfig;
import com.team3.caps.service.ApiService;
import com.team3.caps.util.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.time.LocalDateTime;
import java.util.Arrays;

@SpringBootApplication
public class LaunchRegularApp {

	public static void main(String[] args) {
		SpringApplication.run(LaunchRegularApp.class, args);
	}

	@Bean
	CommandLineRunner initData(
			AdminRepository adminRepository,
			StudentRepository studentRepository,
			LecturerRepository lecturerRepository,
			AccountHolderRepository accountHolderRepository,
			CourseRepository courseRepository,
			CohortRepository cohortRepository,
			StudentCohortRepository studentCohortRepository,
			LecturerCohortRepository lecturerCohortRepository,
			TagRepository tagRepository,
			ApiService apiService) {
		return (args) -> {
			LocalDateTime now = LocalDateTime.now();
			String ykEm = "e1112632@u.nus.edu";

			// Persist ACCOUNT_HOLDER-------------------------------------------------

			// ADMINS
			Admin admin1 = adminRepository.saveAndFlush(new Admin(ykEm, now, ykEm, now, "Admin", "One",
					"e1112632@u.nus.edu", SecurityConfig.passwordEncoder().encode("PWA1"), "99869900",
					"A000001"));
			Admin admin2 = adminRepository.saveAndFlush(new Admin(ykEm, now, ykEm, now, "Admin", "Two",
					"admin2@gmail.com", SecurityConfig.passwordEncoder().encode("PWA2"), "99869900",
					"A000002"));
			Admin admin3 = adminRepository.saveAndFlush(new Admin(ykEm, now, ykEm, now, "Admin", "Three",
					"admin3@gmail.com", SecurityConfig.passwordEncoder().encode("PWA3"), "99869900",
					"A000003"));

			// LECTURERS
			Lecturer lecturer1 = lecturerRepository.saveAndFlush(new Lecturer(ykEm, now, ykEm, now, "Lecturer", "One",
					"lecturer1@gmail.com", SecurityConfig.passwordEncoder().encode("PWL1"), "99860099",
					"L000001"));
			Lecturer lecturer2 = lecturerRepository.saveAndFlush(new Lecturer(ykEm, now, ykEm, now, "Lecturer", "Two",
					"lecturer2@gmail.com", SecurityConfig.passwordEncoder().encode("PWL2"), "99869898",
					"L000002"));
			// lecturer2.setDeleted(false);
			// lecturer2 = lecturerRepository.saveAndFlush(lecturer2);
			Lecturer lecturer3 = lecturerRepository.saveAndFlush(new Lecturer(ykEm, now, ykEm, now, "Lecturer", "Three",
					"lecturer3@gmail.com", SecurityConfig.passwordEncoder().encode("PWL3"), "99867878",
					"L000003"));

			// STUDENTS
			Student mike = studentRepository.saveAndFlush(new Student(ykEm, now, ykEm, now, "Michael", "Tan",
					"MichaelTan@gmail.com", SecurityConfig.passwordEncoder().encode("PWMT"), "99860000",
					"S000005"));
			Student edel = studentRepository.saveAndFlush(new Student(ykEm, now, ykEm, now, "Heather", "Edelfelt",
					"HeatherEdelfelt@gmail.com", SecurityConfig.passwordEncoder().encode("PWHE"), "99867777",
					"S000006"));
			Student norm = studentRepository.saveAndFlush(new Student(ykEm, now, ykEm, now, "Norman", "Gough",
					"NormanGough@gmail.com", SecurityConfig.passwordEncoder().encode("PWNG"), "99878686",
					"S000007"));

			Student stud4 = studentRepository.saveAndFlush(new Student(ykEm, now, ykEm, now, "John", "Smith",
					"johnsmith@gmail.com", SecurityConfig.passwordEncoder().encode("PJS"), "99867777",
					"S000008"));
			Student stud5 = studentRepository.saveAndFlush(new Student(ykEm, now, ykEm, now, "Emily", "Johnson",
					"emilyjohnson@gmail.com", SecurityConfig.passwordEncoder().encode("PEJ"), "99867777",
					"S000009"));
			Student stud6 = studentRepository.saveAndFlush(new Student(ykEm, now, ykEm, now, "Michael", "Davis",
					"michaeldavis@gmail.com", SecurityConfig.passwordEncoder().encode("PMD"), "99867777",
					"S000010"));
			Student stud7 = studentRepository.saveAndFlush(new Student(ykEm, now, ykEm, now, "Sophia", "Brown",
					"sophiabrown@gmail.com", SecurityConfig.passwordEncoder().encode("PSB"), "99867777",
					"S000011"));
			Student stud8 = studentRepository.saveAndFlush(new Student(ykEm, now, ykEm, now, "Daniel", "Wilson",
					"danielwilson@gmail.com", SecurityConfig.passwordEncoder().encode("PDW"), "99867777",
					"S000012"));
			Student stud9 = studentRepository.saveAndFlush(new Student(ykEm, now, ykEm, now, "Olivia", "Miller",
					"oliviamiller@gmail.com", SecurityConfig.passwordEncoder().encode("POM"), "99867777",
					"S000013"));
			Student stud10 = studentRepository.saveAndFlush(new Student(ykEm, now, ykEm, now, "Ethan", "Anderson",
					"ethananderson@gmail.com", SecurityConfig.passwordEncoder().encode("PEA"), "99867777",
					"S000014"));
			Student stud11 = studentRepository.saveAndFlush(new Student(ykEm, now, ykEm, now, "Ava", "Taylor",
					"avataylor@gmail.com", SecurityConfig.passwordEncoder().encode("PAT"), "99867777",
					"S000015"));
			Student stud12 = studentRepository.saveAndFlush(new Student(ykEm, now, ykEm, now, "Benjamin", "Thomas",
					"benjaminthomas@gmail.com", SecurityConfig.passwordEncoder().encode("PBT"), "99867777",
					"S000016"));
			Student stud13 = studentRepository.saveAndFlush(new Student(ykEm, now, ykEm, now, "Mia", "Clark",
					"miaclark@gmail.com", SecurityConfig.passwordEncoder().encode("PMC"), "99867777",
					"S000017"));
			Student stud14 = studentRepository.saveAndFlush(new Student(ykEm, now, ykEm, now, "Engelbert", "Thompson",
					"engelbertthompson@gmail.com", SecurityConfig.passwordEncoder().encode("PET"), "99867777",
					"S000018"));
			Student stud15 = studentRepository.saveAndFlush(new Student(ykEm, now, ykEm, now, "Moshi", "Moshi",
					"moshi.moshi.9797@gmail.com", SecurityConfig.passwordEncoder().encode("PWMM"), "99867777",
					"S000019"));

			Student[] students = { stud4, stud5, stud6, stud7, stud8, stud9, stud10, stud11, stud12, stud13, stud14,
					stud15 };

			// Persist COURSES-------------------------------------------------

			Course jSpring = courseRepository.saveAndFlush(new Course(ykEm, now, ykEm, now, "Java Spring", 6));
			Course cSharp = courseRepository.saveAndFlush(new Course(ykEm, now, ykEm, now, "C#", 6));
			Course oop = courseRepository
					.saveAndFlush(new Course(ykEm, now, ykEm, now, "Object-Oriented Programming", 6));
			Course kotlinMobile = courseRepository
					.saveAndFlush(new Course(ykEm, now, ykEm, now, "Kotlin Mobile Development", 4));
			Course sql = courseRepository
					.saveAndFlush(new Course(ykEm, now, ykEm, now, "SQL Database Crash Course", 4));

			// Persist COHORTS-------------------------------------------------

			Cohort cShart24 = cohortRepository.saveAndFlush(new Cohort(ykEm, now, ykEm, now, "CSharp24",
					"2024 intake for C#", cSharp, LDT.getCourseStart(2024), 60, ClassDay.WEDNESDAY, ClassSlot.PM));
			Cohort cShart25 = cohortRepository.saveAndFlush(new Cohort(ykEm, now, ykEm, now, "CSharp25",
					"2025 intake for C#", cSharp, LDT.getCourseStart(2024), 50, ClassDay.WEDNESDAY, ClassSlot.PM));

			Cohort javaSpring24 = cohortRepository
					.saveAndFlush(new Cohort(ykEm, now, ykEm, now, "JavaSpring24", "2024 intake for Java Spring",
							jSpring, LDT.getCourseStart(2024), 60, ClassDay.TUESDAY, ClassSlot.AM));
			Cohort javaSpring25 = cohortRepository
					.saveAndFlush(new Cohort(ykEm, now, ykEm, now, "JavaSpring25", "2025 intake for Java Spring",
							jSpring, LDT.getCourseStart(2025), 50, ClassDay.TUESDAY, ClassSlot.AM));

			Cohort oop25 = cohortRepository.saveAndFlush(
					new Cohort(ykEm, now, ykEm, now, "OOP25", "2025 intake for Object-Oriented Programming", oop,
							LDT.getCourseStart(2025), 35, ClassDay.MONDAY, ClassSlot.PM));
			Cohort oop26 = cohortRepository.saveAndFlush(
					new Cohort(ykEm, now, ykEm, now, "OOP26", "2026 intake for Object-Oriented Programming", oop,
							LDT.getCourseStart(2026), 45, ClassDay.MONDAY, ClassSlot.PM));
			Cohort oop27 = cohortRepository.saveAndFlush(
					new Cohort(ykEm, now, ykEm, now, "OOP27", "2027 intake for Object-Oriented Programming", oop,
							LDT.getCourseStart(2027), 55, ClassDay.MONDAY, ClassSlot.AM));
			Cohort oop24 = cohortRepository.saveAndFlush(
					new Cohort(ykEm, now, ykEm, now, "OOP24", "2024 intake for Object-Oriented Programming", oop,
							LDT.getCourseStart(2024), 55, ClassDay.MONDAY, ClassSlot.AM));

			Cohort kotM25 = cohortRepository
					.saveAndFlush(new Cohort(ykEm, now, ykEm, now, "KM25", "2025 intake for Kotlin Mobile Development",
							kotlinMobile, LDT.getCourseStart(2025), 60, ClassDay.THURSDAY, ClassSlot.AM));
			Cohort kotM24 = cohortRepository
					.saveAndFlush(new Cohort(ykEm, now, ykEm, now, "KM24", "2024 intake for Kotlin Mobile Development",
							kotlinMobile, LDT.getCourseStart(2024), 60, ClassDay.THURSDAY, ClassSlot.AM));

			Cohort sql24 = cohortRepository
					.saveAndFlush(new Cohort(ykEm, now, ykEm, now, "SQL24", "2024 intake for SQL Database Crash Course",
							sql, LDT.getCourseStart(2024), 15, ClassDay.THURSDAY, ClassSlot.AM));

			Cohort[] allCohorts = { cShart24,
					cShart25,
					javaSpring24,
					javaSpring25,
					oop25,
					oop26,
					oop27,
					oop24,
					kotM25,
					kotM24,
					sql24 };

			// Persist TEACHING ASSIGNMENT
			// (LecturerCohort)-------------------------------------------------

			// assign lecturer1 to teach JavaSpring
			// 24,25
			// persist both sides
			LecturerCohort js25taughtByLecturer1 = new LecturerCohort(ykEm, now, ykEm, now, lecturer1, javaSpring25);
			LecturerCohort js24taughtByLecturer1 = new LecturerCohort(ykEm, now, ykEm, now, lecturer1, javaSpring24);
			lecturer1.getCohortsTaught().add(js25taughtByLecturer1);
			lecturer1.getCohortsTaught().add(js24taughtByLecturer1);
			// lecturerCohortRepository.saveAndFlush(js25taughtByLecturer1);
			// lecturerCohortRepository.saveAndFlush(js24taughtByLecturer1);
			// assign lecturer1 to teach OOP
			// 25,26,27
			LecturerCohort oop25taughtByLecturer1 = new LecturerCohort(ykEm, now, ykEm, now, lecturer1, oop25);
			LecturerCohort oop26taughtByLecturer1 = new LecturerCohort(ykEm, now, ykEm, now, lecturer1, oop26);
			LecturerCohort oop27taughtByLecturer1 = new LecturerCohort(ykEm, now, ykEm, now, lecturer1, oop27);
			lecturer1.getCohortsTaught().add(oop25taughtByLecturer1);
			lecturer1.getCohortsTaught().add(oop26taughtByLecturer1);
			lecturer1.getCohortsTaught().add(oop27taughtByLecturer1);
			// lecturerCohortRepository.saveAndFlush(oop25taughtByLecturer1);
			// lecturerCohortRepository.saveAndFlush(oop26taughtByLecturer1);
			// lecturerCohortRepository.saveAndFlush(oop27taughtByLecturer1);

			// assign Haneda to teach all cohorts
			for (Cohort cohort : allCohorts) {
				lecturerCohortRepository.saveAndFlush(new LecturerCohort(ykEm, now, ykEm, now, lecturer3, cohort));
			}

			// Persist ENROLMENTS
			// (StudentCohort)-------------------------------------------------

			// Mike is enrolled in
			// JS 24,25
			StudentCohort js25enrolMike = new StudentCohort(ykEm, now, ykEm, now, mike, javaSpring25,
					EnrolmentStatus.ENROLLED);
			StudentCohort js24enrolMike = new StudentCohort(ykEm, now, ykEm, now, mike, javaSpring24,
					EnrolmentStatus.ENROLLED);
			mike.getCohortsEnrolled().add(js25enrolMike);
			mike.getCohortsEnrolled().add(js24enrolMike);
			js25enrolMike = studentCohortRepository.saveAndFlush(js25enrolMike);
			js24enrolMike = studentCohortRepository.saveAndFlush(js24enrolMike);

			// Heather is enrolled in
			// OOP 25,27
			// Spring 24,25
			StudentCohort oop25enrolEdel = new StudentCohort(ykEm, now, ykEm, now, edel, oop25,
					EnrolmentStatus.ENROLLED);
			StudentCohort oop27enrolEdel = new StudentCohort(ykEm, now, ykEm, now, edel, oop27,
					EnrolmentStatus.ENROLLED);
			edel.getCohortsEnrolled().add(oop25enrolEdel);
			edel.getCohortsEnrolled().add(oop27enrolEdel);
			oop25enrolEdel = studentCohortRepository.saveAndFlush(oop25enrolEdel);
			oop27enrolEdel = studentCohortRepository.saveAndFlush(oop27enrolEdel);

			StudentCohort spring24enrolEdel = new StudentCohort(ykEm, now, ykEm, now, edel, javaSpring24,
					EnrolmentStatus.ENROLLED);
			StudentCohort spring25enrolEdel = new StudentCohort(ykEm, now, ykEm, now, edel, javaSpring25,
					EnrolmentStatus.ENROLLED);
			edel.getCohortsEnrolled().add(spring24enrolEdel);
			edel.getCohortsEnrolled().add(spring25enrolEdel);
			spring24enrolEdel = studentCohortRepository.saveAndFlush(spring24enrolEdel);
			spring25enrolEdel = studentCohortRepository.saveAndFlush(spring25enrolEdel);

			StudentCohort oop27enrollstud15 = new StudentCohort(ykEm, now, ykEm, now, stud15, oop27,
					EnrolmentStatus.ENROLLED);
			stud15.getCohortsEnrolled().add(oop27enrollstud15);
			oop27enrollstud15 = studentCohortRepository.saveAndFlush(oop27enrollstud15);

			// Norman is enrolled in OOP
			// but was removed!
			StudentCohort oop27enrolnorm = new StudentCohort(ykEm, now, ykEm, now, norm, oop27,
					EnrolmentStatus.REMOVED);
			norm.getCohortsEnrolled().add(oop27enrolnorm); // tbh don't need this line //check
			oop27enrolnorm = studentCohortRepository.saveAndFlush(oop27enrolnorm);

			// -----Enrol the 10 students for bulk, and to test REST api--------------
			Arrays.stream(students).forEach(stud -> {
				StudentCohort sc = new StudentCohort(ykEm, now, ykEm, now, stud, javaSpring25,
						EnrolmentStatus.ENROLLED);
				sc.setScore(65);
				sc.setGradedBy(lecturer3);
				studentCohortRepository.saveAndFlush(sc);
			});
			Arrays.stream(students).forEach(stud -> {
				StudentCohort sc = new StudentCohort(ykEm, now, ykEm, now, stud, oop27, EnrolmentStatus.ENROLLED);
				sc.setScore(75);
				sc.setGradedBy(lecturer3);
				studentCohortRepository.saveAndFlush(sc);
			});
			Arrays.stream(students).limit(8).forEach(stud -> {
				StudentCohort sc = new StudentCohort(ykEm, now, ykEm, now, stud, kotM25, EnrolmentStatus.ENROLLED);
				sc.setScore(80);
				sc.setGradedBy(lecturer3);
				studentCohortRepository.saveAndFlush(sc);
			});

			// Persist TAGS/SUBJECTS-------------------------------------------------
			// instantiate the tags
			Tag javaTag = new Tag(ykEm, now, ykEm, now, "Java");
			Tag springTag = new Tag(ykEm, now, ykEm, now, "Spring");
			Tag programmingTag = new Tag(ykEm, now, ykEm, now, "Program");

			// tag the courses
			javaTag.getTaggedBy().add(jSpring);
			springTag.getTaggedBy().add(jSpring);
			programmingTag.getTaggedBy().add(jSpring);
			programmingTag.getTaggedBy().add(cSharp);

			// persist tags
			tagRepository.saveAndFlush(javaTag);
			tagRepository.saveAndFlush(springTag);
			tagRepository.saveAndFlush(programmingTag);

			/* TEST RETRIEVAL */
			// ----------------------------------------------------------------------------------------------

			/* REMOVAL of Enrolment */
			System.out.println("\nDemonstrating REMOVAL of enrolment");
			StudentCohort invalidEnrolment = studentCohortRepository.findByIdAndIsDeleted(2, false);
			System.out.println(
					"Invalid Enrolment status before purged by kwan: " + invalidEnrolment.getEnrolmentStatus());
			// Admin Kwan removes invalid mike enrolment
			invalidEnrolment.setEnrolmentStatus(EnrolmentStatus.REMOVED);
			studentCohortRepository.save(invalidEnrolment);

			invalidEnrolment = studentCohortRepository.findByIdAndIsDeleted(2, false);
			System.out
					.println("Invalid Enrolment status after purge by kwan: " + invalidEnrolment.getEnrolmentStatus());

			/* GRADING of student */
			System.out.println("\nDemonstrating GRADING of student");
			StudentCohort validEnrolment = studentCohortRepository.findByIdAndIsDeleted(1, false);
			System.out.println("Mike's JS23 score before being graded by lecturer1: " + validEnrolment.getScore());
			// lecturer1 grades Mike here
			validEnrolment.setScore(99.5); // wow he is the advanced student
			validEnrolment.setGradedBy(lecturer1);
			studentCohortRepository.saveAndFlush(validEnrolment);
			System.out.println("Mike's JS23 score after being graded by lecturer1: " + validEnrolment.getScore());

			System.out.println("\nDemonstrating RETRIEVAL of course");
			String s = "JS23 is of the following course type: " + validEnrolment.getCohort().getCourseType().getName();
			System.out.println(s);

			Student found1 = studentRepository.findByFirstNameAndLastNameAndIsDeleted("Michael", "Tan", false);
			// System.out.println(found1.getEmail());
			String emailToUse = found1.getEmail();

			/* Spring Security demonstration- Retrieval By Object */
			System.out.println("\nDemonstrating RETRIEVAL of ACCOUNT_HOLDER for Spring Security");
			// demonstrate that Spring will retrieve correct object for login no matter
			// which repo is used
			AccountHolder found2 = accountHolderRepository.findByEmailAndIsDeleted(emailToUse, false); // can use
																										// adminRepository
																										// as well
																										// without
																										// casting
			System.out.println("AccountHolder was found: " + found2.getFirstName() + " " + found2.getLastName());
			System.out.println(found2 instanceof Student ? "Allow login using student login page."
					: "Do not allow login using student login page.");
			System.out.println(found2 instanceof Lecturer ? "Allow login using lecturer login page."
					: "Do not allow login using lecturer login page.");
			System.out.println(found2 instanceof Admin ? "Allow login using admin login page."
					: "Do not allow login using admin login page.");

			/* Tag demonstration */
			System.out.println("\nDemonstrating RETRIEVAL of TAGS for the course Java Spring");
			Course foundCourse = courseRepository.findById(1).get();
			foundCourse.getTags().stream().forEach(System.out::println);

			/* Additional Tests */
			// //TEST DELETING
			// COURSES-----------------------------------------------------------------------
			// //test deleting one of lecturer1's courses
			// oop.setDeleted(true);
			// oop = courseRepository.saveAndFlush(oop);
			//
			// test deleting one of lecturer1's studentCohorts (Michael)
			// validEnrolment.setDeleted(true);
			// validEnrolment = studentCohortRepository.saveAndFlush(validEnrolment);

			// //TESTING REST
			// API-------------------------------------------------------------------
			// //test deleting one of lecturer1's studentCohorts (Heather)
			// oop25enrolEdel.setDeleted(true);
			// oop25enrolEdel = studentCohortRepository.saveAndFlush(oop25enrolEdel);
			//
			// StudentCohort sql24enrolnorm = new
			// StudentCohort(ykEm,now,ykEm,now,norm,sql24, EnrolmentStatus.REMOVED);
			// norm.getCohortsEnrolled().add(sql24enrolnorm); //tbh don't need this line
			// //check
			// sql24enrolnorm = studentCohortRepository.saveAndFlush(sql24enrolnorm);
		};
	}
}