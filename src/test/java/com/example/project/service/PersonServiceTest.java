package com.example.project.service;


import com.example.project.domain.Block;
import com.example.project.domain.Person;
import com.example.project.repository.BlockRepository;
import com.example.project.repository.PersonRepository;
import com.example.project.servisce.PersonService;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;


@SpringBootTest
class PersonServiceTest {
    @Autowired
    private PersonService personService;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private BlockRepository blockRepository;

    @Test
    void getPeopleExcludeBlocks() {
        givenPeople();

        List<Person> result = personService.getPeopleExcludeBlocks();

        result.forEach(System.out::println);
    }
    @Test
    void getPeopleByName(){
        givenPeople();

        List<Person> result = personService.getPeopleByName("martin");

        result.forEach(System.out::println);
    }


    @Test
    void cascadeTest(){
        givenPeople();

        List<Person> result = personRepository.findAll();
       // result.forEach(System.out::println);

        Person person = result.get(3);
        person.getBlock().setStartDate(LocalDate.now());
        person.getBlock().setEndData(LocalDate.now());

        personRepository.save(person);
        personRepository.findAll().forEach(System.out::println);

        personRepository.delete(person);
        personRepository.findAll().forEach(System.out::println);
        blockRepository.findAll().forEach(System.out::println);

        person.setBlock(null);
        personRepository.save(person);
        personRepository.findAll().forEach(System.out::println);
        blockRepository.findAll().forEach(System.out::println);

    }
    @Test
    void getPerson(){
        givenPeople();

        Person person = personService.getPerson(3L);
    }



    private void givenPeople() {
        givenPerson("martin",10,"A");
        givenPerson("david",9,"B");
        givenBlockPerson("dennis",8,"O");
        givenBlockPerson("kai",11,"AB");
        givenBlockPerson("jack",12,"A");

    }


    private void givenPerson(String name, int age, String  bloodType) {
        personRepository.save(new Person(name,age,bloodType));

    }
    private void givenBlockPerson(String name,int age, String bloodType){
        Person blockPerson = new Person(name,age,bloodType);
        blockPerson.setBlock(new Block(name));



        personRepository.save(blockPerson);
    }

}