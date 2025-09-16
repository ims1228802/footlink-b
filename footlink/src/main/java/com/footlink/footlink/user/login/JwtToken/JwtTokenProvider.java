package com.footlink.footlink.user.login.JwtToken;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

    // 비밀키 (실서비스에서는 application.yml 에서 불러오기)
    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long accessTokenValidity = 1000L * 60 * 30; // 30분

    // 토큰 생성
    public String createToken(String userId, String role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + accessTokenValidity);

        return Jwts.builder()
                .setSubject(userId)                 // 토큰 제목 (사용자 식별값)
                .claim("role", role)                // 커스텀 클레임 (권한 등)
                .setIssuedAt(now)                   // 발급 시간
                .setExpiration(expiryDate)          // 만료 시간
                .signWith(secretKey) // ✅ 안전한 키로 서명
                .compact();
    }
}
	

