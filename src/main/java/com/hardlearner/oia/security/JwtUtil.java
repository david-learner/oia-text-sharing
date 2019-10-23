package com.hardlearner.oia.security;

import com.hardlearner.oia.exception.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

public class JwtUtil {
    private static final Logger log =  LoggerFactory.getLogger(JwtUtil.class);

    public static final String SECRET_KEY = "secretKey";

    public static String getEmailFromToken(String jwtToken) throws UnsupportedEncodingException {
        Jws<Claims> jws = Jwts.parser()
                .setSigningKey(SECRET_KEY.getBytes("UTF-8"))
                .parseClaimsJws(jwtToken);
        Object email = jws.getBody().get("email");
        if (email == null) {
            throw new IllegalArgumentException("인증정보가 잘못되었습니다");
        }
        log.debug("{}", email);
        return email.toString();
    }
}
