package com.hardlearner.oia.security;

import com.hardlearner.oia.domain.Member;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "loginMember";

    public static boolean isLoginMember(NativeWebRequest webRequest) {
        Object loginUser = webRequest.getAttribute(USER_SESSION_KEY, WebRequest.SCOPE_SESSION);
        return loginUser != null;
    }

    public static Member getMemberFromSession(NativeWebRequest webRequest) {
        if (!isLoginMember(webRequest)) {
//            return Member.GUEST_MEMBER;
            return null;
        }
        return (Member) webRequest.getAttribute(USER_SESSION_KEY, WebRequest.SCOPE_SESSION);
    }

    public static boolean isLoginMember(HttpSession session) {
        Object sessionedUser = session.getAttribute(USER_SESSION_KEY);
        if (sessionedUser == null) {
            return false;
        }
        return true;
    }

    public static Member getMemberFromSession(HttpSession session) {
        if (!isLoginMember(session)) {
            return null;
        }
        return (Member) session.getAttribute(USER_SESSION_KEY);
    }
}