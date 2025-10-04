package com.footlink.footlink.user.myinfo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import com.footlink.footlink.user.myinfo.domain.MyInfo;
import com.footlink.footlink.user.myinfo.service.MyInfoService;
import com.footlink.footlink.user.login.JwtToken.JwtTokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:5173")
public class MyInfoController {

	private final MyInfoService myInfoService;
	private final JwtTokenProvider jwtTokenProvider;

	@GetMapping("/user/my-info")
	public ResponseEntity<?> getMyInfo(HttpServletRequest request) {
		try {
			// Authorization 헤더에서 JWT 추출
			String authHeader = request.getHeader("Authorization");

			if (authHeader == null || !authHeader.startsWith("Bearer ")) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 토큰이 없습니다.");
			}

			String token = authHeader.substring(7); // "Bearer " 제거
			log.info("📩 받은 토큰: {}", token);

			// ✅ JWT에서 이메일 추출
			String email = jwtTokenProvider.getUserEmail(token);
			log.info("📧 토큰에서 추출된 이메일: {}", email);

			// ✅ 이메일로 사용자 정보 조회
			MyInfo myInfo = myInfoService.getMyInfoByEmail(email);
			if (myInfo == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자 정보를 찾을 수 없습니다.");
			}

			// ✅ 성공
			return ResponseEntity.ok(myInfo);

		} catch (Exception e) {
			log.error("내 정보 조회 중 오류 발생", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생");
		}
	}
}
