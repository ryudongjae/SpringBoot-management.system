package com.example.project.service;


import com.example.project.controller.dto.PersonDto;
import com.example.project.domain.Person;
import com.example.project.domain.dto.Birthday;
import com.example.project.repository.PersonRepository;
import com.example.project.servisce.PersonService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.sun.xml.bind.v2.schemagen.Util.equal;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {
    @InjectMocks//테스트 대상이 되는 클래스에 붙혀주면
    private PersonService personService;
    @Mock //mock을 선언한 클래스를 mock으로 만든뒤 @injectMocks에 주입시켜준다.
    private PersonRepository personRepository;

    @Test
    void getPeopleByName(){ //mock테스트는 오로지 가정으로 이루어진다.
        when(personRepository.findByName("martin"))
                .thenReturn(Lists.newArrayList(new Person("martin")));

        List<Person> result = personService.getPeopleByName("martin");

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getName()).isEqualTo("martin");
    }

    @Test
    void getPerson(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("martin")));
        Person person = personService.getPerson(1L);

        assertThat(person.getName()).isEqualTo("martin");
    }

    @Test
    void getPersonIfNotFound(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.empty());

        Person person = personService.getPerson(1L);

        assertThat(person).isNull();

    }

    @Test
    void put(){
        PersonDto dto = PersonDto.of("martin","programming","판교", LocalDate.now(),"programmer","010-1111-2222");

        personService.put(dto);

        verify(personRepository, times(1)).save(argThat(new IsPersonWillBeInserted()));
    }

    @Test
    void modifyIfPersonNotFound(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,()->personService.modify(1L,mockPersonDto()));
    }

    @Test
    void modifyIfNameDifferent(){
        when(personRepository.findById(1L))
            .thenReturn(Optional.of(new Person("tony")));

        assertThrows(RuntimeException.class, ()->personService.modify(1L,mockPersonDto()));
    }

    @Test
    void modify(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("martin")));

        personService.modify(1L,mockPersonDto());

//        verify(personRepository,times(1)).save(any(Person.class));
        verify(personRepository, times(1)).save(argThat(new IsPersonWillBeUpdated()));
    }
    @Test
    void modifyByNameIfPersonNotFound(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, ()-> personService.modify(1L,"daniel"));
    }

    @Test
    void modifyByName(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("martin")));

        personService.modify(1L,"daniel");

        verify(personRepository,times(1)).save(argThat(new IsNameWillBeUpdated()));
    }

    @Test
    void deleteIfPersonNotFound(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,()->personService.delete(1L));
    }

    @Test
    void delete(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person ("martin")));

        personService.delete(1L);

        verify(personRepository,times(1)).save(argThat(new IsPersonWillBeDeleted()));
    }

    private PersonDto mockPersonDto(){
        return PersonDto.of("martin","programming","판교",LocalDate.now(),"programmer","010-1111-2222");
    }

    private static class  IsPersonWillBeInserted implements ArgumentMatcher<Person>{

        @Override
        public boolean matches(Person person) {
            return equals(person.getName(),"martin")
                    && equals(person.getHobby(),"programming")
                    && equals(person.getAddress(),"판교")
                    && equals(person.getBirthday(),Birthday.of(LocalDate.now()))
                    && equals(person.getJob(),"programmer")
                    && equals(person.getPhoneNumber(),"010-1111-2222");
        }

        private boolean equals(Object actual,Object expected){
            return expected.equals(actual);
        }
    }

    private static class IsPersonWillBeUpdated implements ArgumentMatcher<Person>{
        @Override
        public boolean matches(Person person){
                return equals(person.getName(),"martin")
                    && equals(person.getHobby(),"programming")
                    && equals(person.getAddress(),"판교")
                    && equals(person.getBirthday(),Birthday.of(LocalDate.now()))
                    && equals(person.getJob(),"programmer")
                    && equals(person.getPhoneNumber(),"010-1111-2222");
        }



        private boolean equals(Object actual,Object expected){
            return expected.equals(actual);
        }
    }

    private static class IsNameWillBeUpdated implements ArgumentMatcher<Person>{

        @Override
        public boolean matches(Person person) {
            return person.getName().equals("daniel");
        }
    }


    private static class IsPersonWillBeDeleted implements ArgumentMatcher<Person>{

        @Override
        public boolean matches(Person person) {
            return person.isDeleted();
        }
    }
}