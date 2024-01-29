package com.team3.caps.repository;

import com.team3.caps.model.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountHolderRepository extends JpaRepository<AccountHolder, Integer> {

    public AccountHolder findByEmailAndIsDeleted(String email, boolean isDeleted);

    public AccountHolder findByFirstNameAndIsDeleted(String firstName, boolean isDeleted);

    public AccountHolder findByFirstNameAndLastNameAndIsDeleted(String firstName, String lastName, boolean isDeleted);

    public AccountHolder findByEmail(String email);

    public AccountHolder findByFirstName(String firstName);

    public AccountHolder findByFirstNameAndLastName(String firstName, String lastName);

    public boolean existsByEmail(String email);
}
