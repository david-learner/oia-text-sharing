package com.hardlearner.oia.configuration;

import com.hardlearner.oia.security.LoginMemberHandlerMethodArgumentResolver;
import com.hardlearner.oia.security.VerifiedEmailHandlerMethodArgumentResolver;
import com.hardlearner.oia.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Bean
    public LoginMemberHandlerMethodArgumentResolver loginMemberArgumentResolver() {
        return new LoginMemberHandlerMethodArgumentResolver();
    }

    @Bean
    public VerifiedEmailHandlerMethodArgumentResolver verifiedEmailHandlerMethodArgumentResolver() {
        return new VerifiedEmailHandlerMethodArgumentResolver();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginMemberArgumentResolver());
        resolvers.add(verifiedEmailHandlerMethodArgumentResolver());
    }
}
