package com.example;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {

    @Test
    public void testGenerateJwt() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("id", 1);
        dataMap.put("username", "admin");
        String jwt =Jwts.builder().signWith(SignatureAlgorithm.HS256, "ZXhhbXBsZQ==")
                .addClaims(dataMap)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .compact();
        System.out.println(jwt);
    }

    @Test
    public void testParseJwt() {
        String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJhZG1pbiIsImV4cCI6MTc4MDY0ODA3Mn0.4UXEpbu-K8wMi3eQsIQKybPuwyv_RH-2rEo7vCPegVI";
        Claims claims = Jwts.parser()
                .setSigningKey("ZXhhbXBsZQ==")
                .parseClaimsJws(jwt)
                .getBody();
        System.out.println(claims);
    }
}
