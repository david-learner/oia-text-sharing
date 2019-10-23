package com.hardlearner.oia.security;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class JwtUtilTest {
    private static final Logger log = LoggerFactory.getLogger(JwtUtilTest.class);

    @Test
    public void createToken() throws UnsupportedEncodingException {
        String jws = JwtUtil.generateToken("guest@gmail.com", JwtUtil.NOW_DATE);
        assertNotNull(jws);
    }

    @Test
    public void getEmailFromToken() throws UnsupportedEncodingException {
        String email = "guest@gmail.com";
        String jws = JwtUtil.generateToken(email, JwtUtil.NOW_DATE);
        assertEquals(email, JwtUtil.getEmailFromToken(jws));
    }
}
