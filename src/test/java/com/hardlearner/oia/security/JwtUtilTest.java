package com.hardlearner.oia.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.Date;

import static org.junit.Assert.assertEquals;


public class JwtUtilTest {
    private static final Logger log = LoggerFactory.getLogger(JwtUtilTest.class);

    @Test
    public void getEmailFromToken() throws UnsupportedEncodingException {
        String jws = Jwts.builder()
                .setIssuer("hardlearner.com")
                .claim("email", "guest@gmail.com")
                .claim("password", "guest")
                // Fri Jun 24 2016 15:33:42 GMT-0400 (EDT)
                .setIssuedAt(Date.from(Instant.ofEpochSecond(1466796822L)))
                // Sat Jun 24 2116 15:33:42 GMT-0400 (EDT)
                .setExpiration(Date.from(Instant.ofEpochSecond(4622470422L)))
                .signWith(
                        SignatureAlgorithm.HS256,
                        JwtUtil.SECRET_KEY.getBytes("UTF-8")
                )
                .compact();

        String email = JwtUtil.getEmailFromToken(jws);
        assertEquals("guest@gmail.com", email);
    }
}
