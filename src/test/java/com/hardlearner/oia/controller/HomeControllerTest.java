package com.hardlearner.oia.controller;

import com.hardlearner.oia.domain.Member;
import com.hardlearner.oia.security.JwtUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void login() throws Exception {
        String email = "guest@gmail.com";
        MvcResult result = mockMvc.perform(post("/login")
                .param("email", email)
                .param("password", Member.GUEST_MEMBER.getPassword())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andReturn();

        String token = result.getResponse().getContentAsString();
        String emailFromToken = JwtUtil.getEmailFromToken(token);
        assertEquals(email, emailFromToken);
    }

    @Test
    public void login_wrong_input() throws Exception {
        String wrongEmailForm = "guest_guest.com";

        mockMvc.perform(post("/login")
                .param("email", wrongEmailForm)
                .param("password", "password")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field").exists())
                .andExpect(jsonPath("$[0].objectName").exists())
                .andExpect(jsonPath("$[0].code").exists())
                .andExpect(jsonPath("$[0].defaultMessage").exists());
    }
}
