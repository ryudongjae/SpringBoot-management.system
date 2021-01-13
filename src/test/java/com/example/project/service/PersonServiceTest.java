package com.example.project.service;


import com.example.project.domain.Block;
import com.example.project.domain.Person;
import com.example.project.repository.BlockRepository;
import com.example.project.repository.PersonRepository;
import com.example.project.servisce.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        givenBlocks();

        List<Person> result = personService.getPeopleExcludeBlocks();

        //System.out.println(result);

        result.forEach(System.out::println);
    }



    private void givenPeople() {
        givenBlockPerson("martin",10,"A");
        givenPerson("david",22,"B");
        givenPerson("cris",33,"O");
        givenPerson("dennis",21,"AB");

    }
    private void givenBlocks() {
        givenBlocks("martin");
    }
    private void givenPerson(String name, int age, String  bloodType) {
        personRepository.save(new Person(name,age,bloodType));

    }
    private void givenBlockPerson(String name,int age, String bloodType){
        Person blockPerson = new Person(name,age,bloodType);
        blockPerson.setBlock(givenBlocks(name));



        personRepository.save(blockPerson);
    }
    private Block givenBlocks(String name) {
      return   blockRepository.save(new Block(name));
    }

}