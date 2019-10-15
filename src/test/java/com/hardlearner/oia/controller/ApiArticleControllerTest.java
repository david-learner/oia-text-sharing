package com.hardlearner.oia.controller;

import com.hardlearner.oia.domain.Article;
import com.hardlearner.oia.domain.ArticleInfo;
import com.hardlearner.oia.domain.DummyData;
import com.hardlearner.oia.domain.Member;
import com.hardlearner.oia.repository.ArticleRepository;
import com.hardlearner.oia.service.ArticleService;
import com.hardlearner.oia.service.MemberService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApiArticleControllerTest {
    private static final Logger log =  LoggerFactory.getLogger(ApiArticleControllerTest.class);
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private MemberService memberService;
    private Member savedMember;

    @Before
    public void setUp() {
        savedMember = memberService.save(DummyData.dummyMember);
    }

    @Test
    public void getOwnArticles() throws Exception {
        Article article = new Article(new ArticleInfo(DummyData.dummyMember, "테스트 아티클 제목", LocalDateTime.now()), DummyData.dummyContent);
        Article savedArticle = articleService.save(article);

        mockMvc.perform(get("/api/articles/members/" + savedMember.getId()).sessionAttr("loginMember", savedMember))
                .andExpect(jsonPath("$[0].id").value(savedArticle.getId()));
    }

    @Test
    public void getShareAllowedArticle() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("loginMember", savedMember);
        Article savedDummyArticle = articleService.save(DummyData.dummyArticle);
        // dummyArticle은 총 2개의 서브블록을 가지고 있다
        mockMvc.perform(get("/api/articles/" + savedDummyArticle.getId()).session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.mainBlocks[0].subBlocks", hasSize(2)));

        // dummyArticle이 가지고 있는 2개의 서브블록 중 공유 가능한 블록은 1개 뿐이다
        mockMvc.perform(get("/api/articles/" + savedDummyArticle.getId() + "/share"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.mainBlocks[0].subBlocks", hasSize(1)));
    }
}
