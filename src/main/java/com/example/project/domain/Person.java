package com.example.project.domain;


import lombok.*;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data

public class Person {

    @Id
    @GeneratedValue
    private Long Id;

    @NonNull
    private String name;

    @NonNull
    private int age;


    private String hobby;

    @NonNull
    private String bloodType;

    private String address;

    private String birthday;

    @ToString.Exclude
    private String job;
    @OneToOne
    private Block block;


}
