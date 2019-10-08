package com.hardlearner.oia.controller;

import com.hardlearner.oia.domain.Article;
import com.hardlearner.oia.domain.ArticleInfo;
import com.hardlearner.oia.domain.DummyData;
import com.hardlearner.oia.domain.Member;
import com.hardlearner.oia.repository.ArticleRepository;
import com.hardlearner.oia.service.ArticleService;
import com.hardlearner.oia.service.MemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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

    @Test
    public void getOwnArticles() throws Exception {
        Member member = Member.builder().email("david@gmail.com").name("david").password("david1234").build();
        Member savedMember = memberService.save(member);
        log.debug("savedMember : " + savedMember.getId());
        Article article = new Article(new ArticleInfo(member, "테스트 아티클 제목", LocalDateTime.now()), DummyData.dummyContent);
        Article savedArticle = articleService.save(article);
        log.debug("savedArticle : " + savedArticle.getId());

        mockMvc.perform(get("/api/articles/members/" + savedMember.getId()).sessionAttr("loginMember", member))
                .andExpect(jsonPath("$[0].id").value(savedArticle.getId()));
    }
}
