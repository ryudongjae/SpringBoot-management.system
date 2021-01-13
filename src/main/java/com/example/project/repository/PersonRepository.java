package com.example.project.repository;


import com.example.project.domain.Block;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Block, Long> {

}
