package com.example.project.service;


import com.example.project.domain.Person;
import com.example.project.servisce.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
class PersonServiceTest {
    @Autowired
    private PersonService personService;

    @Test
    void getPeopleExcludeBlocks() {
        List<Person> result = personService.getPeopleExcludeBlocks();

        assertThat(result.size()).isEqualTo(3);
        assertThat(result.get(0).getName()).isEqualTo("martin");
        assertThat(result.get(1).getName()).isEqualTo("benni");
        assertThat(result.get(2).getName()).isEqualTo("kai");

    }
    @Test
    void getPeopleByName(){

        List<Person> result = personService.getPeopleByName("martin");

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getName()).isEqualTo("martin");
    }

    @Test
    void getPerson(){
        Person person = personService.getPerson(3L);

        assertThat(person.getName()).isEqualTo("dennis");

    }


}