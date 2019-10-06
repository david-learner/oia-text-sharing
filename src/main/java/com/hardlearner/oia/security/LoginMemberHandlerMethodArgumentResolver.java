package com.hardlearner.oia.security;

import com.hardlearner.oia.domain.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class LoginMemberHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    private static final Logger log =  LoggerFactory.getLogger(LoginMemberHandlerMethodArgumentResolver.class);

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        // Controller의 method가 call되면 method의 parameter가 supportsParameter의 인자로 넘겨져서 특정 애노테이션을 가지는 지 판단한다
        log.debug("PARAMETER : {}", methodParameter.toString());
        return methodParameter.hasParameterAnnotation(LoginMember.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        Member loginMember = HttpSessionUtils.getMemberFromSession(nativeWebRequest);
        if(loginMember == null) {
            throw new IllegalArgumentException("Please Login");
        }
        if(!loginMember.isGuest()) {
            return loginMember;
        }

        LoginMember loginMemberAnnotation = methodParameter.getMethodAnnotation(LoginMember.class);
        if (loginMemberAnnotation.required()) {
            // unauthorized exception 만들기
            throw new IllegalArgumentException("Please Login");
        }
        return loginMember;
    }
}
