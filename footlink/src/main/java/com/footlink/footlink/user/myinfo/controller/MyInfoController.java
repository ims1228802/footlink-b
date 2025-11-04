package com.footlink.footlink.user.myinfo.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.footlink.footlink.user.myinfo.domain.MyAppliedMatch;
import com.footlink.footlink.user.myinfo.domain.MyCompletedMatch;
import com.footlink.footlink.user.myinfo.domain.MyInfo;
import com.footlink.footlink.user.myinfo.domain.MyLikeMatch;
import com.footlink.footlink.user.myinfo.domain.PublicUserProfile;
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
    
    //팀 조회
    
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
    
    //매치리스트 조회
    
    @GetMapping("/applied-matches")
    public ResponseEntity<List<MyAppliedMatch>> getAppliedMatches(Principal principal) {
        return ResponseEntity.ok(myInfoService.getAppliedMatches(principal.getName()));
    }
    
    @DeleteMapping("/applied-matches/{matchNo}")
    public ResponseEntity<String> cancelAppliedMatch(
            @PathVariable("matchNo") Long matchNo,
            Principal principal) {
        String email = principal.getName();
        myInfoService.cancelAppliedMatch(email, matchNo);
        return ResponseEntity.ok("신청이 취소되었습니다.");
    }


    @GetMapping("/like-matches")
    public ResponseEntity<List<MyLikeMatch>> getLikeMatches(Principal principal) {
        return ResponseEntity.ok(myInfoService.getLikeMatches(principal.getName()));
    }
    
    @DeleteMapping("/like-matches/{matchNo}")
    public ResponseEntity<Void> unlikeMatch(@PathVariable Long matchNo, Principal principal) {
    	String email = principal.getName();
        myInfoService.unlikeMatch(email, matchNo);
        return ResponseEntity.noContent().build();
    }
    

    @GetMapping("/completed-matches")
    public ResponseEntity<List<MyCompletedMatch>> getCompletedMatches(Principal principal) {
        return ResponseEntity.ok(myInfoService.getCompletedMatches(principal.getName()));
    }
    
    // 설정
     
    @GetMapping("/myinfo/settings")
    public ResponseEntity<MyInfo> getMySettings(Principal principal) {
        String email = principal.getName();
        MyInfo info = myInfoService.getMyInfoByEmail(email); 
        return ResponseEntity.ok(info);
    }

    @PutMapping("/myinfo/settings")
    public ResponseEntity<Void> updateMySettings(@RequestBody MyInfo myInfo, Principal principal) {
        myInfo.setEmail(principal.getName());
        myInfoService.updateMySettings(myInfo);
        return ResponseEntity.ok().build();
    }
    
    @PutMapping("/myinfo/phone")
    public ResponseEntity<Void> updatePhone(@RequestBody MyInfo myInfo, Principal principal) {
        String email = principal.getName();
        myInfo.setEmail(email); // 현재 로그인 사용자 이메일
        myInfoService.updatePhone(myInfo);
        return ResponseEntity.ok().build();
    }
    
    @PutMapping("/myinfo/password")
    public ResponseEntity<Void> updatePassword(@RequestBody Map<String, String> body, Principal principal) {
        String email = principal.getName();
        String newPassword = body.get("password");
        myInfoService.updatePassword(email, newPassword);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/myinfo/withdraw")
    public ResponseEntity<Void> withdraw(Principal principal) {
        String email = principal.getName();
        myInfoService.withdrawUser(email);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/myinfo/updateLevel")
    public ResponseEntity<?> updateUserLevel(@RequestBody MyInfo myInfo, Principal principal) {
        String email = principal.getName(); // ✅ 로그인한 사용자의 이메일
        myInfo.setEmail(email);

        log.info("🎯 [POST] /myinfo/updateLevel - 이메일: {}, 레벨: {}", email, myInfo.getLevel());
        myInfoService.updateUserLevel(myInfo);

        return ResponseEntity.ok("레벨 변경 완료");
    }
    
    @GetMapping("/public/{userId}")
    public ResponseEntity<PublicUserProfile> getPublicUser(@PathVariable String userId) {
    	PublicUserProfile PublicUser = myInfoService.getPublicUser(userId);
      if (PublicUser == null) return ResponseEntity.notFound().build(); // 404
      return ResponseEntity.ok(PublicUser); // 200
    }
    
}
