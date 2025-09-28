package com.footlink.footlink.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 🔥 모든 보안 기능 비활성화
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            )
            .httpBasic(httpBasic -> httpBasic.disable()) // 🔥 Basic Auth 완전히 비활성화 (팝업 원인)
            .formLogin(form -> form.disable());         // 🔥 로그인 폼도 비활성화

        return http.build();
    }
}
