package com.footlink.footlink.common.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.footlink.footlink.user.login.filter.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

/**
 * [Spring Security 설정 클래스] - JWT 기반 인증 방식 적용 - 세션 사용하지 않음 (STATELESS) - 권한별 접근
 * 경로 제어
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				// CORS 활성화 (아래 corsConfigurationSource()를 사용함)
				.cors().configurationSource(corsConfigurationSource()).and()

				// CSRF 비활성화 (JWT는 세션 사용 안함)
				.csrf().disable()

				// 세션 비활성화
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

				// URL별 접근 권한 설정
				.authorizeRequests()
//				.requestMatchers("/api/login/**", "/api/check-phone", "/api/phone/request", "/api/phone/verify",
//						"/api/signup")
//				.permitAll() // 로그인은 누구나 접근 가능, 비인증 허용
//				.requestMatchers("/api/admin/**").hasRole("ADMIN") // 관리자만 접근 가능
//				.anyRequest().authenticated() // 나머지는 로그인 필요
				.anyRequest().permitAll() // 모든 요청 접근 허용 (임시로 완전 오픈)
				.and()

				// JWT 필터 추가 (UsernamePasswordAuthenticationFilter 전에 실행)
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	
	 // CORS 설정 추가
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();

		// 1. 허용할 출처(Origin)
		config.setAllowedOrigins(List.of("http://localhost:5173", // 개발 환경 (React 로컬)
				"https://footlink.com", // 운영 환경 (실제 도메인)
				"https://www.footlink.com" // www 버전도 허용
		));

		// 2. 허용할 HTTP 메서드
		config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

		// 3. 요청 헤더 허용
		config.setAllowedHeaders(List.of("Authorization", "Content-Type"));

		// 4. 인증정보(JWT, 쿠키) 포함 허용
		config.setAllowCredentials(true);

		// 5. 캐시 지속시간(선택)
		config.setMaxAge(3600L); // 1시간

		// 6. 모든 경로 적용
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}

	// 인증 매니저 (비밀번호 검증 등에 필요)
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
}
