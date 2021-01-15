package com.example.project.servisce;




import com.example.project.domain.Person;
import com.example.project.repository.BlockRepository;
import com.example.project.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j//어노테이션을 적용하면 log.debug() 만으로 쉽게 로그를 확인할 수 있다.
public class PersonService {
    @Autowired
    private PersonRepository personRepository;


    public List<Person> getPeopleExcludeBlocks() {
        List<Person> people = personRepository.findAll();

        return personRepository.findByBlockIsNull();

    }

    public List<Person> getPeopleByName(String name){

        return personRepository.findByName(name);

    }


    @Transactional
    public Person getPerson(Long id){
        Person person = personRepository.findById(id).get();
//        System.out.println("person: "+ person);

        log.info("person: "+ person); //프로덕션에 배포가 되었을때 system.out의 형태는 모든로그가 출력되지만 로그형식은 출력을 제한 할 수 있다.
        return person;
    }
}
