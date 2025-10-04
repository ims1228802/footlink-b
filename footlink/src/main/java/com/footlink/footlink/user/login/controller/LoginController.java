package com.footlink.footlink.user.login.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.footlink.footlink.user.login.domain.LoginRequest;
import com.footlink.footlink.user.login.domain.LoginResponse;
import com.footlink.footlink.user.login.service.LoginService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginProcess(@RequestBody LoginRequest loginRequest) {
        log.info("로그인 요청: {}", loginRequest.getEmail());

        try {
            LoginResponse response = loginService.login(loginRequest);

            switch (response.getMessage()) {
                case "로그인 성공":
                    return ResponseEntity.ok(response);
                case "비밀번호가 일치하지 않습니다.":
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
                case "존재하지 않는 이메일입니다.":
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                default:
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

        } catch (Exception e) {
            log.error("로그인 처리 중 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new LoginResponse(null, null, "서버 내부 오류"));
        }
    }
}
