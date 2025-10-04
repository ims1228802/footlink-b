package com.footlink.footlink.config;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // 1. 허용할 출처(Origin)
        config.setAllowedOrigins(List.of(
            "http://localhost:5173",          // 개발 환경 (React 로컬)
            "https://footlink.com",           // 운영 환경 (실제 도메인)
            "https://www.footlink.com"        // www 버전도 허용
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
}
