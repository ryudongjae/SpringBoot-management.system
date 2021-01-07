package com.example.java.project.project.repository;

import com.example.java.project.project.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
