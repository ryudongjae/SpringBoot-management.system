package com.example.project.domain;


import lombok.*;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString

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
    private String bloodtype;

    private String address;

    private String birthday;

    @ToString.Exclude
    private String job;

    private boolean block;


}
