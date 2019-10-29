package com.hardlearner.oia.controller;

import com.hardlearner.oia.domain.Article;
import com.hardlearner.oia.domain.DummyData;
import com.hardlearner.oia.domain.Member;
import com.hardlearner.oia.security.JwtUtil;
import com.hardlearner.oia.service.ArticleService;
import com.hardlearner.oia.service.MemberService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.time.Instant;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApiArticleControllerTest {
    private static final Logger log =  LoggerFactory.getLogger(ApiArticleControllerTest.class);

    private static String BEARER = "Bearer ";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private MemberService memberService;
    private static Member savedMember;
    private static Article savedArticle;
    private String authHeaderWithToken;

    @Before
    public void setUp() throws UnsupportedEncodingException {
        String token = JwtUtil.generateToken(DummyData.DUMMY_MEMBER.getEmail(), Date.from(Instant.now()));
        authHeaderWithToken = BEARER + token;

        if (savedMember == null) {
            savedMember = memberService.save(DummyData.DUMMY_MEMBER);
        }
        if (savedArticle == null) {
            savedArticle = articleService.save(DummyData.DUMMY_ARTICLE);
        }
    }

    @Test
    public void getArticles() throws Exception {
        mockMvc.perform(get("/api/articles/" + savedArticle.getId())
                .header(HttpHeaders.AUTHORIZATION, authHeaderWithToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(savedArticle.getId()));
    }

    @Test
    public void getOwnArticles() throws Exception {
        mockMvc.perform(get("/api/articles/members/" + savedMember.getId())
                .header(HttpHeaders.AUTHORIZATION, authHeaderWithToken)
                .sessionAttr("loginMember", savedMember))
                .andExpect(jsonPath("$[0].id").value(savedArticle.getId()));
    }

    @Test
    public void getOwnArticlesUsingToken() throws Exception {
        mockMvc.perform(get("/api/articles/members/" + savedMember.getId())
                .header(HttpHeaders.AUTHORIZATION, authHeaderWithToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(savedArticle.getId()));
    }

    @Test
    public void getArticleShareLink() throws Exception {
        mockMvc.perform(post("/api/articles/" + savedArticle.getId() + "/share")
                .header(HttpHeaders.AUTHORIZATION, authHeaderWithToken))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("share?key=")))
                .andDo(print());
    }

    @Test
    public void getShareAllowedArticle() throws Exception {
        String token = JwtUtil.generateToken(DummyData.DUMMY_MEMBER.getEmail(), Date.from(Instant.now()));
        // dummyArticle은 총 2개의 서브블록을 가지고 있다
        mockMvc.perform(get("/api/articles/" + savedArticle.getId())
                .header(HttpHeaders.AUTHORIZATION, authHeaderWithToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.mainBlocks[0].subBlocks", hasSize(2)));

        MvcResult result = mockMvc.perform(post("/api/articles/" + savedArticle.getId() + "/share")
                .header(HttpHeaders.AUTHORIZATION, authHeaderWithToken))
                .andExpect(status().isOk())
                .andReturn();

        String articleShareLink = result.getResponse().getContentAsString();

        // dummyArticle이 가지고 있는 2개의 서브블록 중 공유 가능한 블록은 1개 뿐이다
        mockMvc.perform(get("/api" + articleShareLink))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.mainBlocks[0].subBlocks", hasSize(1)));
    }
}
