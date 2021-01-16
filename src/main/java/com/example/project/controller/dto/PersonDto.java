package com.example.project.controller.dto;


import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class PersonDto {
    private String name;
    private int age;
    private String hobby;
    private String bloodType;
    private String address;
    private LocalDate birthday;
    private String job;
    private String phoneNumber;
}
