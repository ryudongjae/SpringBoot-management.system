package com.example.project.controller;

import com.example.project.controller.dto.PersonDto;
import com.example.project.domain.Person;
import com.example.project.repository.PersonRepository;
import com.example.project.servisce.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/person")
@Slf4j
public class PersonController {
    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

//    @RequestMapping(method = RequestMethod.GET)
    @GetMapping("/{id}")
    public Person getPerson(@PathVariable Long id){

        return personService.getPerson(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postPerson(@RequestBody PersonDto personDto) {
        personService.put(personDto);
    }

    @PutMapping("/{id}")
    public void modify(@PathVariable  Long id,@RequestBody PersonDto personDto){
        personService.modify(id,personDto);
    }

    @PatchMapping("/{id}")
    public void modifyPerson(@PathVariable Long id , String name){
        personService.modify(id,name);
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable Long id){
        personService.delete(id);
    }
}
