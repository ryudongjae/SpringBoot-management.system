package com.example.project.controller;

import com.example.project.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@Slf4j
class PersonControllerTest {
    @Autowired
    private PersonController personController;

    @Autowired
    private PersonRepository personRepository;

    private MockMvc mockMvc;

    @BeforeEach //매 테스트마다 먼저 한번씩 실행됨
    void beforeEach(){
        mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
    }


    @Test
    void getPerson() throws Exception{

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/person/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("martin"));
    }
    @Test
    void postPerson() throws Exception{

         mockMvc.perform(
                 MockMvcRequestBuilders.post("/api/person")
                         .contentType(MediaType.APPLICATION_JSON_UTF8)
                         .content("{\n" +
                                 "     \"name\":\"martin2\",\n" +
                                 "     \"age\":\"20\",\n" +
                                 "     \"bloodType\":\"A\"\n" +
                                 "}"))
                 .andDo(print())
                 .andExpect(status().isCreated());

    }
    @Test
    void modifyPerson() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/person/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\n" +
                        "     \"name\":\"martin\",\n" +
                        "     \"age\":\"20\",\n" +
                        "     \"bloodType\":\"A\"\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().isOk());

    }


    @Test
    void modifyName() throws Exception{

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/person/1")
                    .param("name","martin22"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    void deletePerson() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/person/1"))
                .andDo(print())
                .andExpect(status().isOk());

        log.info("people deleted : {}",personRepository.findPeopleDeleted());
    }

}