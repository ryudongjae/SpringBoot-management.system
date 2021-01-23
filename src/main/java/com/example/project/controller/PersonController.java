package com.example.project.controller;

import com.example.project.controller.dto.PersonDto;
import com.example.project.domain.Person;
import com.example.project.exception.PersonNotFoundException;
import com.example.project.exception.RenameNotPermittedException;
import com.example.project.exception.dto.ErrorResponse;
import com.example.project.repository.PersonRepository;
import com.example.project.servisce.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/person")
@Slf4j //로그확인
public class PersonController {
    @Autowired
    private PersonService personService;



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

    @ExceptionHandler(value = RenameNotPermittedException.class)
    public ResponseEntity<ErrorResponse> handleRenameNoPermittedException(RenameNotPermittedException ex){
        return new ResponseEntity<>(ErrorResponse.of(HttpStatus.BAD_REQUEST,ex.getMessage()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = PersonNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePersonNotFoundException(PersonNotFoundException ex){
        return  new ResponseEntity<>(ErrorResponse.of(HttpStatus.BAD_REQUEST, ex.getMessage()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value =  RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex){
        log.error("SeverError : {}",ex.getMessage(),ex);
        return new ResponseEntity<>(ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR,"알 수 없는 서버 오류가 발생하였습니다."),HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
