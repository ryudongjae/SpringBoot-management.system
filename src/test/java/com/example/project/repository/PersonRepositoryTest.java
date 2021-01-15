package com.example.project.repository;



import com.example.project.domain.Person;
import com.example.project.domain.dto.Birthday;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class PersonRepositoryTest {
    @Autowired
    private PersonRepository personRepository;

    @Test
    void crud() {
        Person person = new Person();
        person.setName("martin");
        person.setAge(10);
        person.setBloodType("A");

        personRepository.save(person);

        System.out.println(personRepository.findAll());

        List<Person> people = personRepository.findAll();

        assertThat(people.size()).isEqualTo(1);
        assertThat(people.get(0).getName()).isEqualTo("martin");
        assertThat(people.get(0).getAge()).isEqualTo(10);
        assertThat(people.get(0).getBloodType()).isEqualTo("A");
    }

    @Test
    //해시 코드가 동일하면 ,같은 객체라는 것을 의미 한다.
    //*Equal:해당 객체가 같은 값을 가지고 있다는 것을 의미함
    void hashCodeAndEquals() {
        Person person1 = new Person("martin", 10, "A");
        Person person2 = new Person("martin", 10, "B");


        System.out.println(person1.equals(person2));
        System.out.println(person1.hashCode());
        System.out.println(person2.hashCode());

        Map<Person, Integer> map = new HashMap<>();
        map.put(person1, person1.getAge());

        System.out.println(map);
        System.out.println(map.get(person2));

    }
    @Test
    void findByBloodType(){
        givenPerson("martin",10,"A");
        givenPerson("david",9,"B");
        givenPerson("dennis", 11, "AB");
        givenPerson("kai",12,"AB");
        givenPerson("jack",22,"AB");
        givenPerson("john",5,"B");


        List<Person> result = personRepository.findByBloodType("B");

        result.forEach(System.out::println);

    }

    @Test
    void findByBirthdayBetween(){
        givenPerson("martin",10,"A",LocalDate.of(1992,8,15));
        givenPerson("david",9,"B",LocalDate.of(1991,8,2));
        givenPerson("dennis", 11, "AB", LocalDate.of(1991,8,4));
        givenPerson("kai",12,"AB",LocalDate.of(1999,9,3));
        givenPerson("jack",22,"AB",LocalDate.of(1999,8,22));


        List<Person> result = personRepository.findByMonthOfBirthday(8);

       result.forEach(System.out::println);
    }

    private void givenPerson(String name,int age, String bloodType){
        givenPerson(name,age,bloodType,null);
    }

    private void givenPerson(String name,int age,String bloodType,LocalDate birthday){
        Person person = new Person(name,age,bloodType);
        person.setBirthday(new Birthday(birthday));


        personRepository.save(person);
    }

}