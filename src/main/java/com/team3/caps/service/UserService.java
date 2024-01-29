package com.team3.caps.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.team3.caps.model.AccountHolder;
import com.team3.caps.model.Admin;
import com.team3.caps.model.Lecturer;
import com.team3.caps.model.Registration;
import com.team3.caps.model.Student;
import com.team3.caps.repository.AccountHolderRepository;
import com.team3.caps.repository.AdminRepository;
import com.team3.caps.repository.LecturerRepository;
import com.team3.caps.repository.StudentRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    private AdminRepository adminRepository;
    private LecturerRepository lecturerRepository;
    private StudentRepository studentRepository;
    private AccountHolderRepository accountHolderRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(
            AdminRepository adminRepository,
            LecturerRepository lecturerRepository,
            StudentRepository studentRepository,
            AccountHolderRepository accountHolderRepository,
            PasswordEncoder passwordEncoder) {

        this.adminRepository = adminRepository;
        this.lecturerRepository = lecturerRepository;
        this.studentRepository = studentRepository;
        this.accountHolderRepository = accountHolderRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AccountHolder findByEmail(String email) {
        AccountHolder accountHolder = accountHolderRepository.findByEmail(email);
        if (accountHolder != null) {
            return accountHolder;
        }
        return null;
    }

    @Transactional
    public Boolean createAdmin(Registration newUser, String password) {
        Admin newAdmin = (Admin) createUser(new Admin(), newUser, password);
        adminRepository.save(newAdmin);
        createUserNumber(newAdmin);
        return true;
    }

    @Transactional
    public Boolean createLecturer(Registration newUser, String password) {
        Lecturer newLecturer = (Lecturer) createUser(new Lecturer(), newUser, password);
        lecturerRepository.save(newLecturer);
        createUserNumber(newLecturer);
        return true;
    }

    @Transactional
    public Boolean createStudent(Registration newUser, String password) {
        Student newStudent = (Student) createUser(new Student(), newUser, password);
        studentRepository.save(newStudent);
        createUserNumber(newStudent);
        return true;
    }

    private AccountHolder createUser(AccountHolder newAccount, Registration newUser, String password) {
        LocalDateTime now = LocalDateTime.now();
        newAccount.setCreatedBy(newUser.getCreatedBy());
        newAccount.setCreatedTime(newUser.getCreatedTime());
        newAccount.setLastUpdatedBy(newUser.getEmail());
        newAccount.setLastUpdatedTime(now);
        newAccount.setFirstName(newUser.getFirstName());
        newAccount.setLastName(newUser.getLastName());
        newAccount.setEmail(newUser.getEmail());
        newAccount.setPassword(passwordEncoder.encode(password));
        newAccount.setPhone(newUser.getPhone());
        return newAccount;
    }

    @Transactional
    private void createUserNumber(AccountHolder newAccountHolder) {
        AccountHolder savedNewAccountHolder = accountHolderRepository
                .findByEmailAndIsDeleted(newAccountHolder.getEmail(), false);
        int currentId = savedNewAccountHolder.getId();
        String accountType = newAccountHolder.getClass().getSimpleName();
        switch (accountType) {
            case ("Admin"):
                newAccountHolder.setUserNumber(String.format("A%06d", currentId));
            case ("Lecturer"):
                newAccountHolder.setUserNumber(String.format("L%06d", currentId));
            case ("Student"):
                newAccountHolder.setUserNumber(String.format("S%06d", currentId));
        }
    }

}
