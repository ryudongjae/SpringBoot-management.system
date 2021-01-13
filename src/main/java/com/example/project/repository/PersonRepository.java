package com.example.project.repository;


import com.example.project.domain.Block;
import com.example.project.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
