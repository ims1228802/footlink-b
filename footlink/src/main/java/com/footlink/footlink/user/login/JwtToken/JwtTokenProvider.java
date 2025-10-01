package com.footlink.footlink.user.login.JwtToken;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	// 비밀키 (실서비스에서는 application.yml 에서 불러오기)
	private final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	// 토큰 만료시간 (1시간)
	private final long accessTokenValidity = 1000L * 60 * 60;

	// 토큰 생성
	// @param username 사용자 아이디
	// @return JWT 문자열

	public String createToken(String userEmail, String role) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + accessTokenValidity);

		return Jwts.builder().setSubject(userEmail) // 토큰 제목 (사용자 식별값)
				.claim("role", role) // 커스텀 클레임 (권한 등)
				.setIssuedAt(now) // 발급 시간
				.setExpiration(expiryDate) // 만료 시간
				.signWith(SECRET_KEY) // ✅ 안전한 키로 서명
				.compact();
	}

    // 토큰 검증
    // @param token 클라이언트가 보낸 JWT
    // @return 유효하면 true, 아니면 false

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
    
    // 토큰에서 userEmail 추출
     
    public String getUserEmail(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    
    //  필터에서 claims를 꺼낼 수 있게 추가
    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
    
}



