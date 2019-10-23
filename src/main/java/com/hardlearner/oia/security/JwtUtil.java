package com.hardlearner.oia.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.Date;

public class JwtUtil {
    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);

    public static final String SECRET_KEY = "secretKey";
    public static final String ISSUER = "hardlearner.com";
    public static Date NOW_DATE = Date.from(Instant.now());

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

    public static String generateToken(String email, Date date) throws UnsupportedEncodingException {
        return Jwts.builder()
                .setIssuer(ISSUER)
                .claim("email", email)
                // 넘어온 시간
                .setIssuedAt(date)
                // 넘어온 시간 + 30분(1800초)
                .setExpiration(Date.from(date.toInstant().plusSeconds(1800)))
                .signWith(
                        SignatureAlgorithm.HS256,
                        JwtUtil.SECRET_KEY.getBytes("UTF-8")
                )
                .compact();
    }
}
