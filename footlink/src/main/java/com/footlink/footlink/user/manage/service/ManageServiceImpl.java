package com.footlink.footlink.user.manage.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.footlink.footlink.config.SolapiProperties;
import com.footlink.footlink.user.manage.domain.SignUpRequest;
import com.footlink.footlink.user.manage.domain.User;
import com.footlink.footlink.user.manage.domain.VerificationRequest;
import com.footlink.footlink.user.manage.mapper.ManageMapper;
import com.footlink.footlink.util.SolapiAuth;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ManageServiceImpl implements ManageService {

	private final ManageMapper manageMapper;
	private final RestTemplate restTemplate;
	private final SolapiProperties solapiProperties;
	private final BCryptPasswordEncoder passwordEncoder;

	@Override
	public boolean checkPhoneDuplicate(String phone) {
		return manageMapper.existsByPhone(phone);
	}

	// code 생성, DB 저장, solapi 호출
	@Override
	public void sendVerificationCode(String phone) {
	    try {
	        // 1. 인증번호 생성
	        String code = String.valueOf((int)(Math.random() * 900000) + 100000);

	        // 2. DB 저장 (3분 유효)
	        VerificationRequest verification = new VerificationRequest();
	        verification.setPhone(phone);
	        verification.setCode(code);
	        verification.setExpireTime(LocalDateTime.now().plusMinutes(3));
	        manageMapper.save(verification);

	        // 3. Solapi 호출
	        String apiKey = solapiProperties.getApiKey();
	        String apiSecret = solapiProperties.getApiSecret();
	        String fromNumber = solapiProperties.getFrom(); // ✅ 등록된 발신번호

	        // Authorization 헤더 생성
	        String authHeader = SolapiAuth.createAuthHeader(apiKey, apiSecret);

	        // 메시지 JSON 구성 (배열 구조)
	        Map<String, Object> message = new HashMap<>();
	        message.put("to", phone);
	        message.put("from", fromNumber);
	        message.put("text", "[Footlink] 인증번호는 " + code + " 입니다.");

	        Map<String, Object> body = new HashMap<>();
	        body.put("messages", List.of(message));

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.set("Authorization", authHeader);

	        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

	        ResponseEntity<String> resp = restTemplate.postForEntity(
	            "https://api.solapi.com/messages/v4/send-many/detail",
	            entity,
	            String.class
	        );

	        System.out.println("Solapi Response: " + resp.getBody());

	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new RuntimeException("SMS 인증번호 발송 실패", e);
	    }
	}

	@Override
	public boolean verifyCode(String phone, String code) {
		Optional<VerificationRequest> latest = manageMapper.findTopByPhoneOrderByCreatedAtDesc(phone);
		if (latest.isPresent()) {
			VerificationRequest verification = latest.get();
			return verification.getCode().equals(code) && verification.getExpireTime().isAfter(LocalDateTime.now());
		}
		return false;
	}

	@Override
	public void signUp(SignUpRequest request) {
	    // 비밀번호 확인
	    if (!request.getPassword().equals(request.getConfirmPassword())) {
	        throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
	    }
		
        // 비밀번호 암호화
        String encodedPw = passwordEncoder.encode(request.getPassword());

        // User 객체 생성
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(encodedPw);
        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setBirth(request.getBirth());
        user.setGender(request.getGender());
        user.setAddr(request.getCity() + " " + request.getDistrict());

        // DB insert
        manageMapper.signUp(user);
    }

}
