package com.footlink.footlink.user.match.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.footlink.footlink.user.match.domain.AddMatch;
import com.footlink.footlink.user.match.domain.Field;
import com.footlink.footlink.user.match.domain.Gender;
import com.footlink.footlink.user.match.domain.Match;
import com.footlink.footlink.user.match.domain.Province;
import com.footlink.footlink.user.match.domain.SelectSta;
import com.footlink.footlink.user.match.domain.Stadium;
import com.footlink.footlink.user.match.service.MatchService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
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
	
	 @GetMapping("/{staNo}/fields")
	 public List<Field> getStadiumFields(@PathVariable("staNo") String staNo) {
		 
		 List<Field> field = matchService.getfieldList(staNo);
		 
		 System.out.println("리액트로 넘어간 데이터: " + field );
		 
		 return field;
	 }
	 
	@GetMapping("/Match/booked-slots")
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
	
	@GetMapping("/Match")
	public ResponseEntity<Map<String, Object>> getMatchList(){
		
//		List<Match> matchList = matchService.findAll();
		List<Gender> testList = matchService.test();
		List<Stadium> staList = matchService.stadiumList();
		List<Province> proList = matchService.findProvince();
		
		Map<String, Object> response = new HashMap<>();
//		response.put("matchList", matchList);
		
		response.put("test", testList);
		response.put("Sta", staList);
		response.put("pro", proList);
		
		return ResponseEntity.ok(response);
	}
}
