package com.example.project.repository;



import com.example.project.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface  PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByName(String name);

    List<Person> findByBlockIsNull();

    List<Person> findByBloodType(String bloodtype);

    List<Person> findByBirthdayBetween(LocalDate startDate, LocalDate endDate);

}
