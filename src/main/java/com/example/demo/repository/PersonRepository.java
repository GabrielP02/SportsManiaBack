package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Person;



@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByUsernameAndPassword(String username, String password);
    Person findByUsername(String username);
    Person findByEmail(String email);
}
