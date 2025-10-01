package com.footlink.footlink.user.login.filter;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.footlink.footlink.user.login.JwtToken.JwtTokenProvider;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * [JWT 인증 필터]
 * - 요청마다 Authorization 헤더에 담긴 토큰을 검증
 * - 유효한 토큰이면 SecurityContext에 사용자 정보를 저장
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Authorization 헤더에서 JWT 추출
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7); // "Bearer " 제거

            // 토큰 유효성 검증
            if (jwtTokenProvider.validateToken(token)) {
                // 토큰에서 사용자 정보 추출
                String email = jwtTokenProvider.getUserEmail(token);
                String role = extractRoleFromToken(token);

                // 권한 부여 (ROLE_ prefix 필수)
                GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(email, null, Collections.singleton(authority));

                // SecurityContext에 인증 정보 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

    // 토큰에서 role 클레임 추출 (JwtTokenProvider에 getUserRole() 있어도 OK)
    private String extractRoleFromToken(String token) {
        try {
            Claims claims = jwtTokenProvider.getClaims(token);
            return claims.get("role", String.class);
        } catch (Exception e) {
            return "USER";
        }
    }
}
