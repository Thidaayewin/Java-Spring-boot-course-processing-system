package com.team3.caps.repository;

import com.team3.caps.model.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    private Student student1;
    private Student student2;

    @BeforeEach
    void setUp() {
        String ykEm = "YenKwan@gmail.com";
        LocalDateTime now = LocalDateTime.now();
        student1 = new Student(ykEm, now, ykEm, now, "Marcus", "Leigh", "MarcusLeigh@gmail.com", "PWML", "99860239",
                "S999998");
        student2 = new Student(ykEm, now, ykEm, now, "Eugene", "Koh", "EugeneKoh@gmail.com", "PWLK", "99868899",
                "S999999");

    }

    @AfterEach
    void tearDown() {
        studentRepository.delete(student1);
        studentRepository.delete(student2);

        student1 = null;
        student2 = null;
    }

    @Test
    void findByEmailandIsNotDeleted() {
        studentRepository.saveAndFlush(student1);
        Student fetchedStudent = studentRepository.findByEmailAndIsDeleted(student1.getEmail(), false);
        assertEquals(false, fetchedStudent.isDeleted());
    }

    @Test
    void findByIsDeleted() {
        student1.setDeleted(true);
        student1 = studentRepository.saveAndFlush(student1);
        List<Student> retrievedStudents = studentRepository.findByIsDeleted(true);
        retrievedStudents = retrievedStudents.stream().filter(x -> x.getEmail() == student1.getEmail())
                .collect(Collectors.toList());
        assertEquals(student1.getEmail(), retrievedStudents.get(0).getEmail());
        assertEquals(student1.getId(), retrievedStudents.get(0).getId());
    }

    @Test
    void findAll() {
        studentRepository.saveAndFlush(student1);
        studentRepository.saveAndFlush(student2);
        List<Student> studentList = studentRepository.findAll();
        int lastIndex = studentList.size() - 1;
        assertEquals("EugeneKoh@gmail.com", studentList.get(lastIndex).getEmail());
    }

    @Test
    void findByEmailAndIsDeleted() {
        studentRepository.saveAndFlush(student1);
        Student fetchedstudent = studentRepository.findByEmailAndIsDeleted(student1.getEmail(), false);
        assertEquals(false, fetchedstudent.isDeleted());
    }

    @Test
    void findAllByisDelete() {
        student1.setDeleted(false);
        student1 = studentRepository.saveAndFlush(student1);
        List<Student> fetchedStudents = studentRepository.findAllByisDelete();
        int lastIndex = fetchedStudents.size() - 1;
        assertEquals(student1.getId(), fetchedStudents.get(lastIndex).getId());

    }

    @Test
    void findById() {
        student1 = studentRepository.saveAndFlush(student1);
        Student fetchedStudent = studentRepository.findById(student1.getId());
        assertEquals(student1.getEmail(), fetchedStudent.getEmail());
    }

    @Test
    void findByFirstNameAndLastNameAndIsDeleted() {
        student1 = studentRepository.saveAndFlush(student1);
        Student fetchedStudent = studentRepository.findByFirstNameAndLastNameAndIsDeleted(student1.getFirstName(),
                student1.getLastName(), student1.isDeleted());
        assertEquals(student1.getEmail(), fetchedStudent.getEmail());
    }

    @Test
    void softDelete() {
        student1 = studentRepository.saveAndFlush(student1);
        assertEquals(false, student1.isDeleted());
        studentRepository.softDelete(student1.getId());
        Student fetchedStudent = studentRepository.findByIdAndIsDeleted(student1.getId(), true);
        assertEquals(fetchedStudent.getEmail(), student1.getEmail());
    }

    @Test
    void findByIdAndIsDeleted() {
        student1.setDeleted(true);
        student1 = studentRepository.saveAndFlush(student1);
        Student fetchedStudent = studentRepository.findByIdAndIsDeleted(student1.getId(), true);
        assertEquals(student1.getEmail(), fetchedStudent.getEmail());
    }

    @Test
    void findByFirstNameAndLastName() {
        student1 = studentRepository.saveAndFlush(student1);
        Student fetchedStudent = studentRepository.findByFirstNameAndLastName(student1.getFirstName(),
                student1.getLastName());
        assertEquals(student1.getEmail(), fetchedStudent.getEmail());
    }
}