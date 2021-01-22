package com.example.project.controller;

import com.example.project.controller.dto.PersonDto;
import com.example.project.domain.Person;
import com.example.project.domain.dto.Birthday;
import com.example.project.repository.PersonRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@Slf4j
@Transactional
class PersonControllerTest {
    @Autowired
    private PersonController personController;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MappingJackson2HttpMessageConverter messageConverter;

    private MockMvc mockMvc;

    @BeforeEach //매 테스트마다 먼저 한번씩 실행됨
    void beforeEach(){
        mockMvc =
                MockMvcBuilders.standaloneSetup(personController)
                .setMessageConverters(messageConverter)
                .alwaysDo(print())
                .build();
    }


    @Test
    void getPerson() throws Exception{

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/person/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hobby").isEmpty())
                .andExpect(jsonPath("$." + "address").isEmpty())
                .andExpect(jsonPath("$.birthday").value("1991-08-15"))
                .andExpect(jsonPath("$.job").isEmpty())
                .andExpect(jsonPath("$.phoneNumber").isEmpty())
                .andExpect(jsonPath("$.deleted").value(false))
                .andExpect(jsonPath("$.age").isNumber())
                .andExpect(jsonPath("$.birthdayToday").isBoolean());
    }
    @Test
    void postPerson() throws Exception{
        PersonDto dto = PersonDto.of("martin","programming","판교",LocalDate.now(),"programmer","010-1111-2222");
         mockMvc.perform(
                 MockMvcRequestBuilders.post("/api/person")
                         .contentType(MediaType.APPLICATION_JSON_UTF8)
                         .content(toJsonString(dto)))
                 .andExpect(status().isCreated());
         Person result = personRepository.findAll(Sort.by(Sort.Direction.DESC,"id")).get(0);

         assertAll(
                 ()-> assertThat(result.getName()).isEqualTo("martin"),
                 ()-> assertThat(result.getHobby()).isEqualTo("programming"),
                 ()-> assertThat(result.getAddress()).isEqualTo("판교"),
                 ()-> assertThat(result.getBirthday()).isEqualTo(Birthday.of(LocalDate.now())),
                 ()-> assertThat(result.getJob()).isEqualTo("programmer"),
                 ()-> assertThat(result.getPhoneNumber()).isEqualTo("010-1111-2222")
         );

    }
    @Test
    void modifyPerson() throws Exception{
        PersonDto dto = PersonDto.of("martin","programming","판교",LocalDate.now(),"programmer","010-1111-2222");

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/person/1")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(toJsonString(dto)))
                .andExpect(status().isOk());

        Person result = personRepository.findById(1L).get();

            assertAll(
                    () -> assertThat(result.getName()).isEqualTo("martin"),
                    () -> assertThat(result.getHobby()).isEqualTo("programming"),
                    () -> assertThat(result.getAddress()).isEqualTo("판교"),
                    () -> assertThat(result.getBirthday()).isEqualTo(Birthday.of(LocalDate.now())),
                    () -> assertThat(result.getJob()).isEqualTo("programmer"),
                    ()->assertThat(result.getPhoneNumber()).isEqualTo("010-1111-2222")
            );

    }



    @Test
    void modifyPersonIfNameIsDifferent() throws Exception{
        PersonDto dto = PersonDto.of("james","programming","판교",LocalDate.now(),"programmer","010-2121-3434");

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/person/1")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJsonString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("이름 변경이 허용되지 않습니다."));
    }
    @Test
    void modifyPersonIfPersonNotFound() throws Exception{
        PersonDto dto = PersonDto.of("martin","programming","판교",LocalDate.now(),"programmer","010-1111-2222");

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/person/10")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJsonString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("Person Entity 존재하지 않습니다."));

    }
    @Test
    void modifyName() throws Exception{

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/person/1")
                    .param("name","martinModified"))
                .andExpect(status().isOk());

        assertThat(personRepository.findById(1L).get().getName()).isEqualTo("martinModified");
    }
    @Test
    void deletePerson() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/person/1"))
                .andExpect(status().isOk());


        assertTrue(personRepository.findPeopleDeleted().stream().anyMatch(person -> person.getId().equals(1L)));
    }

//    @Test
//    void checkJsonString() throws JsonProcessingException{
//        PersonDto dto = new PersonDto();
//        dto.setName("martin");
//        dto.setBirthday(LocalDate.now());
//        dto.setAddress("판교");
//
//        System.out.println(">>>" +toJsonString(dto));
//    }


    private String toJsonString(PersonDto personDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(personDto);
    }

}