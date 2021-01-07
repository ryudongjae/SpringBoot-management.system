package com.example.java.project.project.domain;

import lombok.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Setter
@Getter
@ToString

@AllArgsConstructor
@RequiredArgsConstructor

public class Person {
    @Id
    @GeneratedValue
    private Long Id;
    @NonNull
    private String name;
    @NonNull
    private int age;
    @NonNull
    private String hobby;

    private String bloodtype;

    private String address;

    private String birthday;

    @ToString.Exclude
    private String job;



}
