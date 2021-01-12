package com.example.java.project.project.repository;

import com.example.java.project.project.domain.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PersonRepositoryTest {
    @Autowired
    private PersonRepository personRepository;

    @Test
    void Crud(){
        Person person = new Person();
        person.setName("martin");
        person.setAge(23);
        person.setBloodtype("A");
        person.setHobby("movie");
        person.setBirthday("1999.10.14");
        person.setJob("developer");
        person.setAddress("seoul");


        personRepository.save(person);

        System.out.println(personRepository.findAll());
        List<Person> people = personRepository.findAll();

        assertThat(people.size()).isEqualTo(1);
        assertThat(people.get(0).getName()).isEqualTo("martin");
        assertThat(people.get(0).getAge()).isEqualTo(23);
        assertThat(people.get(0).getBloodtype()).isEqualTo("A");

    }
    @Test
    void allArgsConstructer(){
        Person person = new Perso
    }



}
