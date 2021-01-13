package com.example.project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldContorller {
    @GetMapping(value = "/api/helloworld")
    public String helloWorld() {
        return "HelloWorld!!";
    }
}
