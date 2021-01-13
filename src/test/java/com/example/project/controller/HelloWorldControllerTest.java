package com.example.project.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@SpringBootTest
class HelloWorldControllerTest {

    @Autowired
    private HelloWorldContorller helloWorldContorller;

    private MockMvc mockMvc;

    @Test
    void helloworld() {
//        System.out.println("test");
        System.out.println(helloWorldContorller.helloWorld());


    }

    @Test
    void mockmvcTest() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(helloWorldContorller).build();


        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/helloworld"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("HelloWorld!!"));


    }
}
