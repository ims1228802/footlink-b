package com.footlink.footlink.config;

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

import com.footlink.footlink.user.login.filter.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

/**
 * [Spring Security 설정 클래스]
 * - JWT 기반 인증 방식 적용
 * - 세션 사용하지 않음 (STATELESS)
 * - 권한별 접근 경로 제어
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
            // CSRF 비활성화 (JWT는 세션 사용 안함)
            .csrf().disable()

            // 세션 비활성화
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            

            // URL별 접근 권한 설정
            .authorizeRequests()
                .requestMatchers("/api/login/**", "/api/check-phone", "/api/phone/request","/api/phone/verify","/api/signup","/api/Match/**").permitAll()  // 로그인은 누구나 접근 가능, 비인증 허용
                .requestMatchers("/api/admin/**").hasRole("ADMIN") // 관리자만 접근 가능
                .anyRequest().authenticated()              // 나머지는 로그인 필요
            .and()

            // JWT 필터 추가 (UsernamePasswordAuthenticationFilter 전에 실행)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // 인증 매니저 (비밀번호 검증 등에 필요)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
