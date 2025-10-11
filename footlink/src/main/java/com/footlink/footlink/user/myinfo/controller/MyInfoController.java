package com.footlink.footlink.user.myinfo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.footlink.footlink.user.myinfo.domain.MyInfo;
import com.footlink.footlink.user.myinfo.service.MyInfoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:5173")
public class MyInfoController {

	private final MyInfoService myInfoService;

	@GetMapping("/user/my-info")
	public ResponseEntity<?> getMyInfo(@AuthenticationPrincipal String email) {

        MyInfo myInfo = myInfoService.getMyInfoByEmail(email);
        if (myInfo == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자 정보를 찾을 수 없습니다.");
        }
        return ResponseEntity.ok(myInfo);
	}
	
    @PutMapping("/modify")
    public ResponseEntity<?> modifyMyInfo(
            @AuthenticationPrincipal String email,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart("data") MyInfo myInfo) {

        myInfo.setEmail(email);
        myInfoService.modify(myInfo, file);
        return ResponseEntity.ok("정보 수정 완료");
    }
    
    @GetMapping("/user/my-team/{email}")
    public ResponseEntity<?> getMyTeamsByEmail(@PathVariable String email) {
        try {
            List<Map<String, Object>> myTeam = myInfoService.getMyTeamsByEmail(email);
            return ResponseEntity.ok(myTeam);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("소속 팀 정보를 불러오는 중 오류가 발생했습니다.");
        }
    }
    
    
}
