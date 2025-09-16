package com.footlink.footlink.user.login.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.footlink.footlink.user.login.DTO.LoginRequest;
import com.footlink.footlink.user.login.DTO.LoginResponse;
import com.footlink.footlink.user.login.JwtToken.JwtTokenProvider;
import com.footlink.footlink.user.login.service.LoginService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173/")
@RequiredArgsConstructor
@Slf4j
public class LoginController {

	private final JwtTokenProvider jwtTokenProvider;
    private final LoginService loginService;
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginProcess(@RequestBody LoginRequest loginRequest) {
    	
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        System.out.println("이메일 검증: " + email);
        log.info("Login attempt email={}, password={}", email, password);
        
        Map<String, Object> resultMap = loginService.matchedUser(email, password);
        log.info("resultMap={}", resultMap);
        
        if (resultMap == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(new LoginResponse(null, "LoginService returned null"));
        }
        
        boolean isMatched = (boolean) resultMap.get("isMatched");
        LoginRequest userInfo = (LoginRequest) resultMap.get("userInfo");
        log.info("isMatched={}, userInfo={}", isMatched, userInfo);
        
        if (isMatched) {
        String token = jwtTokenProvider.createToken(userInfo.getEmail(), "ROLE_USER");
        // ✅ 로그인 성공 → JWT 생성
        log.info("token={}", token);
        return ResponseEntity.ok(new LoginResponse(token, userInfo.getEmail()));
    } else {
    	// ✅ 로그인 실패 → 401 Unauthorized
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    }

    
}
