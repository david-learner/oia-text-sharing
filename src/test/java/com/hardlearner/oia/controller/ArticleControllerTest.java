package com.hardlearner.oia.controller;

import com.hardlearner.oia.domain.DummyData;
import com.hardlearner.oia.domain.Member;
import com.hardlearner.oia.security.HttpSessionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ArticleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private MockHttpSession session;

    @Before
    public void setUp() {
        session = new MockHttpSession();
        session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, Member.GUEST_MEMBER);
    }

    @Test
    public void create() throws Exception {
        mockMvc.perform(post("/articles/new").session(session))
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION));
    }

    @Test
    public void getArticle() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/articles/new").session(session))
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andReturn();
        String location = mvcResult.getResponse().getHeader("Location");
        mockMvc.perform(get(location).session(session)).andExpect(status().isOk());
    }
}
