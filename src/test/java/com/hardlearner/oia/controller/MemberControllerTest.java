package com.hardlearner.oia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hardlearner.oia.domain.JoinDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void checkValidEmail() throws Exception {
        mockMvc.perform(get("/members/valid/email")
                .param("email", "guest@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void join() throws Exception {
        JoinDto joinDto = JoinDto.builder()
                .name("데이빗")
                .email("david@gmail.com")
                .password("david1234")
                .passwordConfirmed("david1234")
                .build();

        mockMvc.perform(post("/members/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(joinDto)))
                .andExpect(status().isOk());
    }
}
