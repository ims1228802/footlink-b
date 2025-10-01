package com.footlink.footlink.user.login.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.footlink.footlink.user.login.JwtToken.JwtTokenProvider;
import com.footlink.footlink.user.login.domain.LoginRequest;
import com.footlink.footlink.user.login.domain.LoginResponse;
import com.footlink.footlink.user.login.service.LoginService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173/") // React 포트 허용
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final JwtTokenProvider jwtTokenProvider;
    private final LoginService loginService;

    /**
     * [로그인 처리 엔드포인트]
     * - 이메일, 비밀번호를 받아 DB 검증
     * - 성공 시 JWT 발급 후 응답
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginProcess(@RequestBody LoginRequest loginRequest) {

        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        log.info("로그인 시도: email={}, password={}", email, password);

        // DB 검증
        Map<String, Object> resultMap = loginService.matchedUser(email, password);
        if (resultMap == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new LoginResponse(null, null, "서버 에러: 로그인 서비스 실패"));
        }

        boolean isMatched = (boolean) resultMap.get("isMatched");
        LoginRequest userInfo = (LoginRequest) resultMap.get("userInfo");

        if (isMatched) {
            // ✅ 사용자 권한 가져오기 (기본값 USER)
            String role = "ROLE_USER";
            if (userInfo.getRole() != null) role = userInfo.getRole();

            // ✅ JWT 발급
            String token = jwtTokenProvider.createToken(userInfo.getEmail(), role);
            log.info("로그인 성공 - 토큰 발급 완료, role={}", role);

            // ✅ 로그인 성공 응답
            return ResponseEntity.ok(new LoginResponse(token, userInfo.getEmail(), "로그인 성공"));
        } else {
            // ❌ 비밀번호 불일치
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse(null, null, "이메일 또는 비밀번호가 일치하지 않습니다."));
        }
    }
}
