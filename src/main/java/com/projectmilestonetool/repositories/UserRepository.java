package com.projectmilestonetool.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projectmilestonetool.entites.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    User findByUsername(String username);
    User getById(Long id);
}
