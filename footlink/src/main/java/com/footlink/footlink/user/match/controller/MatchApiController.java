package com.footlink.footlink.user.match.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.footlink.footlink.user.match.domain.AddMatch;
import com.footlink.footlink.user.match.domain.EndList;
import com.footlink.footlink.user.match.domain.Field;
import com.footlink.footlink.user.match.domain.Match;
import com.footlink.footlink.user.match.domain.MatchDetail;
import com.footlink.footlink.user.match.domain.Player;
import com.footlink.footlink.user.match.domain.Province;
import com.footlink.footlink.user.match.domain.SelectSta;
import com.footlink.footlink.user.match.domain.Stadium;
import com.footlink.footlink.user.match.domain.ResultDTO.GameRe;
import com.footlink.footlink.user.match.domain.ResultDTO.MatchParticipant;
import com.footlink.footlink.user.match.domain.ResultDTO.MatchSummaryDto;
import com.footlink.footlink.user.match.domain.ResultDTO.ResultDataDto;
import com.footlink.footlink.user.match.service.MatchResultService;
import com.footlink.footlink.user.match.service.MatchService;
import com.footlink.footlink.user.myinfo.domain.MyInfo;
import com.footlink.footlink.user.myinfo.service.MyInfoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/Match")
@CrossOrigin(origins = "http://localhost:5173")
public class MatchApiController {
	private final MatchService matchService;
	private final MyInfoService myInfoService;
	private final MatchResultService matchResultService;
	
	public MatchApiController(MatchService matchService, MyInfoService myInfoService,MatchResultService matchResultService) {
		this.matchService = matchService;
		this.myInfoService = myInfoService;
		this.matchResultService = matchResultService;
	}
	
	
	@PostMapping("/select")
	public List<Stadium> selectStadium(@RequestBody SelectSta request){
		System.out.println("리액트로부터 받은 검색어: "+ request.getKeyword());
		
		List<Stadium> results = matchService.selectKeyword(request.getKeyword());
		
		
		
		return results;
	}
	@GetMapping("/{matchNo}")
	public ResponseEntity<MatchDetail> getMatchInfo(@PathVariable("matchNo") String matchNo,
													@RequestParam(name = "email", required = false) String email) {
		MatchDetail matchInfo = matchService.getMatchInfo(matchNo);
		MyInfo userInfo = myInfoService.getMyInfoByEmail(email);
		boolean isLikedByUser = false;
		if (email != null) {
	        String userId = userInfo.getId();
	        
	        isLikedByUser = matchService.isLikedByUser(matchNo, userId); 
	        log.info("검색 결과" + isLikedByUser);
	    }
		matchInfo.setIsLikedByUser(isLikedByUser);
		
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
    public ResponseEntity<?> createMatch(@RequestBody AddMatch addMatch, String userInfo) {
        
 
        log.info("React로부터 매치 생성 요청을 받았습니다: {}", addMatch.toString());

        AddMatch matchdata = matchService.addmatch(addMatch);
        String matchNo = matchdata.getMatchNo();
        log.info(matchNo);
        matchService.applyMatch(matchNo, userInfo);

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
	public ResponseEntity<Map<String, Object>> getMatchList(String email){
		
		MyInfo myInfo = myInfoService.getMyInfoByEmail(email);
		List<Match> matchList = matchService.findAllMatch();
		List<Stadium> staList = matchService.stadiumList();
		List<Province> proList = matchService.findProvince();
		
		Map<String, Object> response = new HashMap<>();

		response.put("myInfo", myInfo);
		response.put("matchList", matchList);
		response.put("Sta", staList);
		response.put("pro", proList);
		
		return ResponseEntity.ok(response);
	}
	@PostMapping("/apply/{matchNo}/{email}")
	public ResponseEntity<?> applyForMatch(@PathVariable String matchNo, @PathVariable String email) {
	    try {
	        MyInfo myInfo = myInfoService.getMyInfoByEmail(email);
	        String userId = myInfo.getId(); 
	        
	        matchService.applyMatch(matchNo, userId);
	        
	        return ResponseEntity.ok("매치 신청이 성공적으로 완료되었습니다.");

	    } catch (IllegalStateException e) {
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
	@GetMapping("/result/{matchNo}")
	public ResponseEntity<Map<String, Object>> getMatchResult(){
		
		Map<String, Object> response = new HashMap<>();
		
		return ResponseEntity.ok(response);
	}
	@GetMapping("/admin")
	public ResponseEntity<Map<String, Object>> adminMatchList(String email){
		
		MyInfo myInfo = myInfoService.getMyInfoByEmail(email);
		List<Match> matchList = matchService.adminMatchList();
		List<Stadium> staList = matchService.stadiumList();
		List<Province> proList = matchService.findProvince();
		
		Map<String, Object> response = new HashMap<>();

		response.put("myInfo", myInfo);
		response.put("matchList", matchList);
		response.put("Sta", staList);
		response.put("pro", proList);
		
		return ResponseEntity.ok(response);
	}
	@PostMapping("/like/{matchNo}/{email}")
    public ResponseEntity<?> addLike(
            @PathVariable("matchNo") String matchNo,
            @PathVariable("email") String email) {
        
		 MyInfo myInfo = myInfoService.getMyInfoByEmail(email);
	     String userId = myInfo.getId(); 
	     matchService.addLike(matchNo, userId);

        
        return ResponseEntity.ok().build(); 
    }

    @DeleteMapping("/like/{matchNo}/{email}")
    public ResponseEntity<?> removeLike(
            @PathVariable("matchNo") String matchNo,
            @PathVariable("email") String email) {
    	 MyInfo myInfo = myInfoService.getMyInfoByEmail(email);
	     String userId = myInfo.getId(); 
	     matchService.removeLike(matchNo, userId);

        
        return ResponseEntity.ok().build(); 
    }
    @GetMapping("/playerList/{matchNo}")
    public ResponseEntity<Map<String, Object>> getPlayerList(@PathVariable("matchNo") String matchNo) {
    	
    	List<Player> playerList = matchService.getPlayerList(matchNo);
    	Map<String, Object> response = new HashMap<>();
    	
    	response.put("player", playerList);
    	
    	return ResponseEntity.ok(response);
    }
    @PostMapping("save-results")
	public ResponseEntity<?> saveMatchResults(@RequestBody ResultDataDto resultDataDto) {
	        
	        
	        try {
	            matchResultService.saveMatchResults(resultDataDto);
	            
	            return ResponseEntity.ok().body("매치 결과가 성공적으로 저장되었습니다.");
	
	        } catch (Exception e) {
	            e.printStackTrace(); 
	            return ResponseEntity.internalServerError().body("결과 저장 중 오류 발생: " + e.getMessage());
	        }
    }
    @GetMapping("/partiList/{matchNo}")
    public ResponseEntity<Map<String, Object>> getPartiList(@PathVariable("matchNo") Long matchNo) {
    	
    	List<MatchParticipant> partiList = matchResultService.selectMatchParticipantList(matchNo);
    	Map<String, Object> response = new HashMap<>();
    	
    	response.put("partiList", partiList);
    	
        return ResponseEntity.ok(response);
    }
    @GetMapping("/endMatch/{matchNo}")
    public ResponseEntity<Map<String, Object>> getEndMatchInfo(@PathVariable("matchNo") Long matchNo) {
    	
    	List<GameRe> EndMatchInfo = matchResultService.getMatchResultsForEdit(matchNo);
    	Map<String, Object> response = new HashMap<>();
    	
    	response.put("EndMatchInfo", EndMatchInfo);
    	
        return ResponseEntity.ok(response);
    }
    @PutMapping("/update-results")
    public ResponseEntity<?> updateMatchResults(@RequestBody ResultDataDto resultDataDto) {
        try {
            matchResultService.saveMatchResults(resultDataDto);
            return ResponseEntity.ok().body("매치 결과가 성공적으로 수정되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("결과 수정 중 오류 발생: " + e.getMessage());
        }
    }
    @GetMapping("/{matchNo}/summary")
    public ResponseEntity<?> getMatchSummary(@PathVariable("matchNo") Long matchNo) {
        try {
            MatchSummaryDto summary = matchResultService.getMatchSummary(matchNo);
            if (summary == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(summary);
        } catch (Exception e) {
            log.error("매치 요약 정보 조회 오류 (matchNo {}): {}", matchNo, e.getMessage());
            return ResponseEntity.internalServerError().body("매치 요약 정보 조회 중 오류 발생");
        }
    }
    @GetMapping("/{matchNo}/details")
    public ResponseEntity<?> getMatchDetails(@PathVariable("matchNo") Long matchNo) {
        try {

            List<GameRe> gameDetails = matchResultService.getMatchResultsForEdit(matchNo); 
            Map<String, Object> response = new HashMap<>();
            response.put("games", gameDetails);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("매치 상세 정보 조회 오류 (matchNo {}): {}", matchNo, e.getMessage());
            return ResponseEntity.internalServerError().body("매치 상세 정보 조회 중 오류 발생");
        }
    }
    
    
}
