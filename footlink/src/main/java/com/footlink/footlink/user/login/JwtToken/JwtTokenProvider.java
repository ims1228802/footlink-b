package com.footlink.footlink.user.login.JwtToken;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
// JWT 토큰 생성, 검증, 파싱 등을 담당하는 핵심 클래스
public class JwtTokenProvider {

	// application.properties에서 jwt.secret 값을 주입받는다.
	@Value("${jwt.secret}")
	private String secretKeyString; // properties에서 읽어올 시크릿 문자열

	// 실제 서명에 사용할 SecretKey 객체 (초기화 시 생성)
	private SecretKey secretKey;

	@PostConstruct
	protected void init() {
		// Base64 인코딩된 문자열이 아니어도 상관없지만, 안전하게 변환해줌
		this.secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes());
	}

	// 토큰 만료시간 (1시간)
	private final long validityInMilliseconds = 1000L * 60 * 60;

	// 토큰 생성
	public String createToken(String email, String role) {
		// 1. 토큰에 담을 클레임(Claims) 생성
		Claims claims = Jwts.claims().setSubject(email);
		claims.put("role", role);

		// 2️. 발급 시간(iat)과 만료 시간(exp) 설정
		Date now = new Date();
		Date validity = new Date(now.getTime() + validityInMilliseconds);

		// 3️. JWT 토큰 생성
		return Jwts.builder()
				.setClaims(claims) // 클레임(토큰 내용)
				.setIssuedAt(now) // 발급 시각
				.setExpiration(validity)  // 만료 시각
				.signWith(secretKey, SignatureAlgorithm.HS256) // HS256 알고리즘 + 시크릿키 서명
				.compact(); // 최종 JWT 문자열 생성
	}

	// 토큰에서 이메일 추출
	public String getUserEmail(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(secretKey) // 서명 검증용 시크릿키 지정
				.build()
				.parseClaimsJws(token) // 토큰 파싱 및 서명 검증
				.getBody()
				.getSubject(); // subject(email) 추출
		
	}

	// 토큰 유효성 검증 : 유효하면 true, 만료되었거나 서명 불일치 시 false
	public boolean validateToken(String token) {
		try {
			// 토큰 파싱 시 예외가 없으면 유효한 토큰
			Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			System.out.println("JWT 검증 실패: " + e.getMessage());
			return false;
		}
	}
	
	// 토큰에서 Claims(페이로드 전체) 추출
	public Claims getClaims(String token) {
	    try {
	        return Jwts.parserBuilder()
	                .setSigningKey(secretKey)
	                .build()
	                .parseClaimsJws(token)
	                .getBody(); // payload 부분 리턴
	    } catch (JwtException e) {
	        System.out.println("❌ JWT 파싱 실패: " + e.getMessage());
	        throw e; // 검증 실패 시 예외 전달
	    }
	}
	
	
}
