package com.hardlearner.oia.security;

import javassist.NotFoundException;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class VerifiedEmailHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(VerifiedEmail.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String authHeaderValue = webRequest.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeaderValue == null && !authHeaderValue.startsWith("Bearer ")) {
            throw new NotFoundException("Not found Authorization in Http Header");
        }
        String token = authHeaderValue.substring(7);
        return JwtUtil.getEmailFromToken(token);
    }
}
