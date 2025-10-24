package com.footlink.footlink.user.manage.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.footlink.footlink.user.manage.domain.SignUpRequest;
import com.footlink.footlink.user.manage.domain.VerificationRequest;
import com.footlink.footlink.user.manage.service.ManageService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api")
public class ManageController {

	private final ManageService manageService;

	@GetMapping("/check-phone")
	public Map<String, Boolean> checkPhone(@RequestParam String phone) {

		boolean exists = manageService.checkPhoneDuplicate(phone);
		return Map.of("exists", exists);
	}
	
    @PostMapping("/phone/request")
    public ResponseEntity<String> requestPhoneVerification(@RequestBody VerificationRequest request) {
    	
        manageService.sendVerificationCode(request.getPhone());
        log.info("인증 요청 phone = {}", request.getPhone());
        return ResponseEntity.ok("인증번호 전송 완료");
    }
    
    @PostMapping("/phone/verify")
    public ResponseEntity<String> verifyPhoneCode(@RequestBody VerificationRequest request) {
        boolean result = manageService.verifyCode(request.getPhone(), request.getCode());
        return result ? ResponseEntity.ok("인증 성공")
                      : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 실패");
        
    }
    
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody SignUpRequest request) {
        manageService.signUp(request);
        log.info("회원가입 요청 gender = {}", request.getGender());
        return ResponseEntity.ok("회원가입 성공");
    }
    
}
