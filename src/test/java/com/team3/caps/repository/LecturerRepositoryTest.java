package com.team3.caps.repository;

import com.team3.caps.model.*;
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
class LecturerRepositoryTest {

    @Autowired
    private LecturerRepository lecturerRepository;

    private Lecturer lecturer1;
    private Lecturer lecturer2;

    @BeforeEach
    void setUp() {
        String ykEm = "YenKwan@gmail.com";
        LocalDateTime now = LocalDateTime.now();
        lecturer1 = new Lecturer(ykEm, now, ykEm, now, "Richard", "Nguyen", "RichardNguyen@gmail.com", "PWRN",
                "99860239", "L999998");
        lecturer2 = new Lecturer(ykEm, now, ykEm, now, "Leonard", "Koh", "LeonardKoh@gmail.com", "PWLK", "99868899",
                "L999999");

    }

    @AfterEach
    void tearDown() {

        lecturerRepository.delete(lecturer1);
        lecturerRepository.delete(lecturer2);

        lecturer1 = null;
        lecturer2 = null;

    }

    @Test
    void findByEmailAndIsDeleted() {
        lecturerRepository.saveAndFlush(lecturer1);
        Lecturer fetchedLecturer = lecturerRepository.findByEmailAndIsDeleted(lecturer1.getEmail(), false);
        assertEquals(false, fetchedLecturer.isDeleted());
    }

    @Test
    void findAll() {
        lecturerRepository.saveAndFlush(lecturer1);
        lecturerRepository.saveAndFlush(lecturer2);
        List<Lecturer> lecturerList = lecturerRepository.findAll();
        int lastIndex = lecturerList.size() - 1;
        assertEquals("LeonardKoh@gmail.com", lecturerList.get(lastIndex).getEmail());
    }

    @Test
    void findAllLecturers() {
        lecturerRepository.saveAndFlush(lecturer1);
        lecturerRepository.saveAndFlush(lecturer2);
        List<Lecturer> lecturerList = lecturerRepository.findAll();
        int indexLast2 = lecturerList.size() - 2;
        int indexLast1 = lecturerList.size() - 1;
        assertEquals(false, lecturerList.get(indexLast2).isDeleted());
        assertEquals(false, lecturerList.get(indexLast1).isDeleted());
    }

    @Test
    void findByIdAndIsDeleted() {
        Lecturer persistedLecturer = lecturerRepository.saveAndFlush(lecturer1);
        Lecturer retrievedLecturer = lecturerRepository.findByIdAndIsDeleted(persistedLecturer.getId(), false);
        assertEquals(persistedLecturer.getId(), retrievedLecturer.getId());
        assertEquals(false, retrievedLecturer.isDeleted());
    }

    @Test
    void findByIsDeleted() {
        lecturer1.setDeleted(true);
        lecturer1 = lecturerRepository.saveAndFlush(lecturer1);
        List<Lecturer> retrievedLecturers = lecturerRepository.findByIsDeleted(true);
        retrievedLecturers = retrievedLecturers.stream().filter(x -> x.getEmail() == lecturer1.getEmail())
                .collect(Collectors.toList());
        assertEquals(lecturer1.getEmail(), retrievedLecturers.get(0).getEmail());
        assertEquals(lecturer1.getId(), retrievedLecturers.get(0).getId());
    }

    @Test
    void findByEmail() {
        lecturer1 = lecturerRepository.saveAndFlush(lecturer1);
        Lecturer retrievedLecturer = lecturerRepository.findByEmail(lecturer1.getEmail());
        assertEquals(lecturer1.getEmail(), retrievedLecturer.getEmail());
        assertEquals(lecturer1.getId(), retrievedLecturer.getId());
    }

}