package com.footlink.footlink.user.match.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.footlink.footlink.user.match.domain.AddMatch;
import com.footlink.footlink.user.match.domain.ApplyMatch;
import com.footlink.footlink.user.match.domain.EndList;
import com.footlink.footlink.user.match.domain.Field;
import com.footlink.footlink.user.match.domain.Match;
import com.footlink.footlink.user.match.domain.MatchDetail;
import com.footlink.footlink.user.match.domain.Province;
import com.footlink.footlink.user.match.domain.SelectSta;
import com.footlink.footlink.user.match.domain.Stadium;
import com.footlink.footlink.user.match.service.MatchService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/Match")
@CrossOrigin(origins = "http://localhost:5173")
public class MatchApiController {
	private final MatchService matchService;
	
	public MatchApiController(MatchService matchService) {
		this.matchService = matchService;
	}
	
	@PostMapping("/select")
	public List<Stadium> selectStadium(@RequestBody SelectSta request){
		System.out.println("리액트로부터 받은 검색어: "+ request.getKeyword());
		
		List<Stadium> results = matchService.selectKeyword(request.getKeyword());
		
		
		
		return results;
	}
	@GetMapping("/{matchNo}")
	public ResponseEntity<MatchDetail> getMatchInfo(@PathVariable("matchNo") String matchNo) {
		List<MatchDetail> matchInfoList = matchService.getMatchInfo(matchNo);
		
		 MatchDetail matchInfo = matchInfoList.get(0);
		 
		 log.info("리액트로 반환한 데이터" + matchInfo);
		
		return ResponseEntity.ok(matchInfo);
	}
	@GetMapping("/{staNo}/fields")
	 public List<Field> getStadiumFields(@PathVariable("staNo") String staNo) {
		 
		 List<Field> field = matchService.getfieldList(staNo);
		 
		 System.out.println("리액트로 넘어간 데이터: " + field );
		 
		 return field;
	 }
	 
	@GetMapping("/booked-slots")
	public List<Match> getBookedTimeSlots(
            @RequestParam("fieldNo") String fieldNo,
            @RequestParam("date") String date) {

        System.out.println("예약 시간 조회 요청 - 구장: " + fieldNo + ", 날짜: " + date);

        // 서비스 계층을 호출하여 데이터베이스에서 예약된 시간을 조회합니다.
        // MatchService.getBookedTimes 메서드는 List<BookedSlotDto>를 반환해야 합니다.
        List<Match> bookedTimes = matchService.getBookList(fieldNo, date);
        
        System.out.println("등록되어있는 매치 시간" + bookedTimes.toString());

        return bookedTimes;
    }
	
	@PostMapping("/addMatch")
    public ResponseEntity<?> createMatch(@RequestBody AddMatch addMatch) {
        
        // ✅ 가장 중요한 확인 부분! 받은 데이터를 로그로 출력합니다.
        log.info("React로부터 매치 생성 요청을 받았습니다: {}", addMatch.toString());

        matchService.addmatch(addMatch);
        

        // 프론트엔드로 보낼 성공 응답
        return ResponseEntity.ok("매치 등록 요청을 성공적으로 받았습니다.");
    } 
	@GetMapping("/fieldList")
	public ResponseEntity<Map<String, Object>> getSelectfield(){
		List<Stadium> staList = matchService.stadiumList();
		List<Province> proList = matchService.findProvince();
		Map<String, Object> response = new HashMap<>();
		
		response.put("Sta", staList);
		response.put("pro", proList);
		
		return ResponseEntity.ok(response);
	}
	@GetMapping("/matchList")
	public ResponseEntity<Map<String, Object>> getMatchList(){
		
		List<Match> matchList = matchService.findAllMatch();
		List<Stadium> staList = matchService.stadiumList();
		List<Province> proList = matchService.findProvince();
		
		Map<String, Object> response = new HashMap<>();

		
		response.put("matchList", matchList);
		response.put("Sta", staList);
		response.put("pro", proList);
		
		return ResponseEntity.ok(response);
	}
	@PostMapping("/apply")
	public ResponseEntity<?> applyForMatch(@RequestBody ApplyMatch applyMatch, @AuthenticationPrincipal String userId) {
        try {
            // "주방장에게 요리를 요청" -> 그냥 서비스 메서드를 호출하기만 합니다.
            matchService.applyMatch(applyMatch.getMatchNo(), userId);
            
            // 성공 시, 200 OK 응답
            return ResponseEntity.ok("매치 신청이 성공적으로 완료되었습니다.");

        } catch (IllegalStateException e) {
            // 서비스에서 "이미 신청한 매치" 예외가 발생하면, 409 Conflict 응답
            // 409 Conflict는 요청이 서버의 현재 상태와 충돌될 때 사용하는 상태 코드입니다. (예: 중복 데이터 생성)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
	@GetMapping("/endList")
	public ResponseEntity<Map<String, Object>> getEndList(){
		
		
		List<Province> proList = matchService.findProvince();
		List<EndList> endList = matchService.getEndList();
		
		
		Map<String, Object> response = new HashMap<>();
		
		response.put("pro", proList);
		response.put("end", endList);
		
		log.info("리액트로 보낸 매치 결과" + endList);
		return ResponseEntity.ok(response);
	} 
	
}
