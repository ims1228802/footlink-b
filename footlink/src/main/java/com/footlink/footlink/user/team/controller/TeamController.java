package com.footlink.footlink.user.team.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveAction;
import java.util.stream.IntStream;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.footlink.footlink.user.myinfo.domain.MyInfo;
import com.footlink.footlink.user.team.domain.Calendar;
import com.footlink.footlink.user.team.domain.Recruit;
import com.footlink.footlink.user.team.domain.StadiumArea;
import com.footlink.footlink.user.team.domain.Team;
import com.footlink.footlink.user.team.domain.UserRecruit;
import com.footlink.footlink.user.team.domain.State;
import com.footlink.footlink.user.team.service.TeamService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/")
@RequestMapping("/api")
@Log4j2
public class TeamController {
	private final TeamService teamService;
	private String weekStr = "";
	
	@GetMapping(value = "team/teamList")
	public List<Team> getTeamList(String param) {
		List<Team> teamList =  teamService.getTeamList();
		return teamList;
	}
	
	@GetMapping("/area")
	public List<StadiumArea> getArea(){
		List<StadiumArea> area = teamService.getArea();
		return area;
	}
	
	@GetMapping("/stadium")
	public List<StadiumArea> getStadium(){
		List<StadiumArea> stadium = teamService.getStadium();
		return stadium;
	}
	
	// 팀 상세 조회
	@GetMapping("team/teamDetail")
	public Team getTeamDetail(@RequestParam String teamCode) {
		log.info("input: {}", teamCode);
		Team teamDetail = teamService.getTeamDetail(teamCode);
		return teamDetail;
	}
	
	// 팀 스탯 정보 조회
	@GetMapping("team/states")
	public State getTeamState(@RequestParam String teamCode) {
		State teamState = teamService.getTeamState(teamCode);
		return teamState;
	}
	
	// 팀에 포함된 유저 조회
	@GetMapping("team/userInfo")
	public List<MyInfo> getTeamUserList(@RequestParam String teamCode) {
		List<MyInfo> teamUserList = teamService.getTeamUserList(teamCode);
		return teamUserList;
	}
	
	// 팀 일정 리스트 조회
	@GetMapping("team/calendar")
	public List<Calendar> getTeamCalendar(@RequestParam String teamCode){
		List<Calendar> teamCalendar = teamService.getTeamCalendar(teamCode);
		return teamCalendar;
	}
	
	// 팀이 모집하고 있는지 카운트 조회
	@GetMapping("team/teamRecruitCnt")
	public int getRecruitCount(@RequestParam String teamCode) {
		return teamService.getRecruitCount(teamCode);
	}
	
	// 팀원 모집 내용 조회
	@GetMapping("team/recruitInfo")
	public Recruit getRecruitInfo(@RequestParam String teamCode) {
		Recruit teamRecruit = teamService.getRecruitInfo(teamCode);
		return teamRecruit;
	}
	
	// 팀 일정 상세 조회
	@GetMapping("team/calendar/detail")
	public Calendar getCalendarDetail (@RequestParam String teamDateCode) {
		Calendar calendarDetail = teamService.getCalendarDetail(teamDateCode);
		return calendarDetail;
	}
	
	// 팀 엠블렘 이미지 추가
	@PostMapping("team/addTeamEmblem")
	public ResponseEntity<String> addTeamEmblem(@RequestPart(value = "files", required = false) MultipartFile files,
												@RequestPart(value = "teamCode", required = false) String teamCode){
		log.info("가져온 파일: {}", files);
		log.info("팀 코드: {}", teamCode);
		teamService.addEmblem(files, teamCode);
		
		return ResponseEntity.ok("팀 엠블렘 추가 성공");
	}
	
	// 팀 엠블렘 이미지 수정
	@PutMapping("team/editTeamEmblem")
	public ResponseEntity<String> editTeamEmblem(@RequestPart(value = "files", required = false) MultipartFile files,
												@RequestPart(value = "teamCode", required = false) String teamCode){
		log.info("가져온 파일: {}", files);
		log.info("팀 코드: {}", teamCode);
		
		teamService.editEmblem(files, teamCode);
		
		return ResponseEntity.ok("엠블렘 수정 완료");
	}
	
	// 팀 추가하기
	@PostMapping("team/addTeam")
	public ResponseEntity<String> addTeam(@RequestBody Map<String, Object> team) {
		// State 객체 새로 생성
		State state = new State();
		// Team 객체 새로 생성
		Team teamDTO = new Team();
		
		String[] weekStrArr = {"일","월","화","수","목","금","토"};
		
		// state 수치 가져오기
		String teamCode = team.get("teamCode").toString();
		String teamName = team.get("teamName").toString();
		String region = team.get("area").toString() + " " + team.get("city").toString();
		String gender = team.get("gender").toString();
		String meetingTime = team.get("activeTime").toString();
		String teamAge = team.get("age").toString();
		String level = team.get("level").toString();
		String stadium = team.get("stadium").toString();
		String userId = team.get("userId").toString();
		
		// 대괄호 제거 후 쉼표로 자르기
		String[] weekRepleace = team.get("week").toString().replaceAll("[\\[\\]]", "").split(", ");
		
		if(weekRepleace.length == 7) {
			weekStr = "매일";
		}else {
			IntStream.range(0, weekRepleace.length).forEach(idx -> {
				if(idx == weekRepleace.length - 1) {
					weekStr = weekStr.concat(weekStrArr[Integer.parseInt(weekRepleace[idx])]);
				}else {
					weekStr = weekStr.concat(weekStrArr[Integer.parseInt(weekRepleace[idx])]).concat(",");				
				}
			});
		}
		
		log.info("week: {}", weekStr);
		
		int attack = Integer.parseInt(team.get("attack").toString());
		int defense = Integer.parseInt(team.get("defense").toString());
		int dribble = Integer.parseInt(team.get("dribble").toString());
		int pass = Integer.parseInt(team.get("pass").toString());
		int physical = Integer.parseInt(team.get("physical").toString());
		int shot = Integer.parseInt(team.get("shot").toString());
		int speed = Integer.parseInt(team.get("speed").toString());
		int stamina = Integer.parseInt(team.get("stamina").toString());
		
		//state 객체에 set
		state.setTeamCode(teamCode);
		state.setAttack(attack);
		state.setDefense(defense);
		state.setDribble(dribble);
		state.setPass(pass);
		state.setPhysical(physical);
		state.setShot(shot);
		state.setSpeed(speed);
		state.setStamina(stamina);
		
		//team 객체에 set
		teamDTO.setTeamCode(teamCode);
		teamDTO.setTeamName(teamName);
		teamDTO.setRegionName(region);
		teamDTO.setActiveDoWeek(weekStr);
		teamDTO.setTeamAge(teamAge);
		teamDTO.setLevel(level);
		teamDTO.setStadium(stadium);
		
		// 성별
		switch(gender) {
		case "man":
			teamDTO.setGender("남자");
			break;
		case "woman":
			teamDTO.setGender("여자");
			break;
		default:
			teamDTO.setGender("남녀모두");
			break;
		}
		
		// 활동 시간
		switch (meetingTime) {
		case "morning":
			teamDTO.setMeetingTime("아침");
			break;
		case "lunch":
			teamDTO.setMeetingTime("점심");
			break;
		case "dinner":
			teamDTO.setMeetingTime("저녁");
			break;
		case "lateNight":
			teamDTO.setMeetingTime("심야");
			break;
		}
		
		// 연령대
		teamDTO.setIsTemp("정석");
		
		//log 출력
		log.info("team: {}", team);
		log.info("teamDTO: {}", teamDTO);
		log.info("state: {}", state);
		
		// 각각 state 테이블과 team 테이블에 데이터 추가
		teamService.addTeamInfo(teamDTO);
		teamService.addTeamState(state);
		teamService.addTeamUser(teamCode, userId);
		
		weekStr = "";
		
		return ResponseEntity.ok("팀 등록 성공");
	}
	// 팀 수정하기
	@PutMapping("/team/editTeam")
	public ResponseEntity<String> editTeam(@RequestBody Map<String, Object> team){
		
		// State 객체 새로 생성
		State state = new State();
		// Team 객체 새로 생성
		Team teamDTO = new Team();
		
		String[] weekStrArr = {"일","월","화","수","목","금","토"};
		
		// state 수치 가져오기
		String teamCode = team.get("teamCode").toString();
		String teamName = team.get("teamName").toString();
		String region = team.get("area").toString() + " " + team.get("city").toString();
		String gender = team.get("gender").toString();
		String meetingTime = team.get("activeTime").toString();
		String teamAge = team.get("age").toString();
		String level = team.get("level").toString();
		String stadium = team.get("stadium").toString();
		String userId = team.get("userId").toString();
		
		// 대괄호 제거 후 쉼표로 자르기
		String[] weekRepleace = team.get("week").toString().replaceAll("[\\[\\]]", "").split(", ");
		
		if(weekRepleace.length == 7) {
			weekStr = "매일";
		}else {
			IntStream.range(0, weekRepleace.length).forEach(idx -> {
				if(idx == weekRepleace.length - 1) {
					weekStr = weekStr.concat(weekStrArr[Integer.parseInt(weekRepleace[idx])]);
				}else {
					weekStr = weekStr.concat(weekStrArr[Integer.parseInt(weekRepleace[idx])]).concat(",");				
				}
			});
		}
		
		log.info("week: {}", weekStr);
		
		int attack = Integer.parseInt(team.get("attack").toString());
		int defense = Integer.parseInt(team.get("defense").toString());
		int dribble = Integer.parseInt(team.get("dribble").toString());
		int pass = Integer.parseInt(team.get("pass").toString());
		int physical = Integer.parseInt(team.get("physical").toString());
		int shot = Integer.parseInt(team.get("shot").toString());
		int speed = Integer.parseInt(team.get("speed").toString());
		int stamina = Integer.parseInt(team.get("stamina").toString());
		
		//state 객체에 set
		state.setTeamCode(teamCode);
		state.setAttack(attack);
		state.setDefense(defense);
		state.setDribble(dribble);
		state.setPass(pass);
		state.setPhysical(physical);
		state.setShot(shot);
		state.setSpeed(speed);
		state.setStamina(stamina);
		
		//team 객체에 set
		teamDTO.setTeamCode(teamCode);
		teamDTO.setTeamName(teamName);
		teamDTO.setRegionName(region);
		teamDTO.setActiveDoWeek(weekStr);
		teamDTO.setTeamAge(teamAge);
		teamDTO.setLevel(level);
		teamDTO.setStadium(stadium);
		
		// 성별
		switch(gender) {
		case "man":
			teamDTO.setGender("남자");
			break;
		case "woman":
			teamDTO.setGender("여자");
			break;
		default:
			teamDTO.setGender("남녀모두");
			break;
		}
		
		// 활동 시간
		switch (meetingTime) {
		case "morning":
			teamDTO.setMeetingTime("아침");
			break;
		case "lunch":
			teamDTO.setMeetingTime("점심");
			break;
		case "dinner":
			teamDTO.setMeetingTime("저녁");
			break;
		case "lateNight":
			teamDTO.setMeetingTime("심야");
			break;
		}
		
		// 연령대
		teamDTO.setIsTemp("정석");
		
		//log 출력
		log.info("team: {}", team);
		log.info("teamDTO: {}", teamDTO);
		log.info("state: {}", state);
		
		// 각각 state 테이블과 team 테이블 데이터 수정 
		teamService.editTeamInfo(teamDTO);
		teamService.editTeamState(state);
		
		weekStr = "";
		
		return ResponseEntity.ok("팀 수정 성공");
	}
	
	// 팀 삭제하기
	@PutMapping("/team/deleteTeam")
	public ResponseEntity<String> deleteTeam(@RequestBody Map<String, Object> params){
		HashMap<String, String> teamCodeObj = (HashMap<String, String>) params.get("params");
		
		String teamCode = teamCodeObj.get("teamCode");
		
		log.info("teamCode: {}", teamCode);
		
		teamService.deleteTeam(teamCode);
		
		return ResponseEntity.ok("팀 삭제 완료");
	}
	
	// 팀 탈퇴하기
	@DeleteMapping("/team/outTeam")
	public ResponseEntity<String> userOutTeam(@RequestParam String teamCode, 
											  @RequestParam(value = "user") String id){
		
		log.info("user: {}", teamCode);
		log.info("user: {}", id);
		
		teamService.deleteTeamUser(teamCode, id);
		
		return ResponseEntity.ok("틸 탈퇴 완료");
	}
	
	// 일정 추가하기
	@PostMapping("team/postCalendar")
	public ResponseEntity<String> addCalendar(@RequestBody Map<String, Object> calendar){
		Calendar calendarDTO = new Calendar();
		HashMap<String, String> calendarDetail = (HashMap<String, String>) calendar.get("params");
		
		// 가져온 데이터 정의
		String title = calendarDetail.get("title");
		String location = calendarDetail.get("location");
		String date = calendarDetail.get("date");
		String startTime = calendarDetail.get("startTime");
		String endTime = calendarDetail.get("endTime");
		String contents = calendarDetail.get("contents");
		String teamCode = calendarDetail.get("teamCode");
		
		log.info("calendar: {}", calendar);
		
		System.out.println(calendarDetail);
		System.out.println(calendarDetail.get("title"));
		
		// 가져온 데이터 셋팅
		calendarDTO.setTitle(title);
		calendarDTO.setPlaceName(location);
		calendarDTO.setDate(date);
		calendarDTO.setStartTime(startTime);
		calendarDTO.setEndTime(endTime);
		calendarDTO.setContents(contents);
		calendarDTO.setTeamCode(teamCode);
		
		teamService.addCalendar(calendarDTO);
		
		return ResponseEntity.ok("일정 등록 성공");
	}
	
	// 일정 삭제하기
	@DeleteMapping("team/deleteCalendar")
	public ResponseEntity<String> deleteCalendar(@RequestParam String teamDateCode){
		teamService.deleteCalendar(teamDateCode);
		return ResponseEntity.ok("일정 삭제 성공");
	}
	
	// 팀원 모집 추가
	@PostMapping("team/addTeamRecruit")
	public ResponseEntity<String> addRecruit(@RequestBody Map<String, String> recruit){
		Recruit recruitDTO = new Recruit();
		
		String teamCode = recruit.get("teamCode");
		String teamDistinction = recruit.get("teamDistinction");
		String stadium = recruit.get("stadium");
		String city = recruit.get("city");
		String area = recruit.get("area");
		String age = recruit.get("age");
		String gender = recruit.get("gender");
		String level = recruit.get("level");
		String teamImg = recruit.get("teamImg");
		String contents = recruit.get("contents");
		
		// 성별
		switch(gender) {
		case "man":
			recruitDTO.setGender("남자");
			break;
		case "woman":
			recruitDTO.setGender("여자");
			break;
		default:
			recruitDTO.setGender("남녀모두");
			break;
		}
		
		// 레벨
		switch(level) {
		case "noneLevel":
			recruitDTO.setLevel("실력무관");
			break;
		case "beginner":
			recruitDTO.setLevel("비기너");
			break;
		case "amateur":
			recruitDTO.setLevel("아마추어");
			break;
		case "semiPro":
			recruitDTO.setLevel("세미프로");
			break;
		case "pro":
			recruitDTO.setLevel("프로");
			break;
		}
		
		recruitDTO.setAge(age);
		recruitDTO.setArea(area);
		recruitDTO.setCity(city);
		recruitDTO.setContents(contents);
		recruitDTO.setStadium(stadium);
		recruitDTO.setTeamCode(teamCode);
		recruitDTO.setTeamDistinction(teamDistinction);
		
		teamService.addTeamRecruit(recruitDTO);
		
		log.info("recruit: {}", recruit);
		
		return ResponseEntity.ok("팀원 모집 등록 성공");
	}
	
	// 팀원 모집 수정
	@PutMapping("team/editTeamRecruit")
	public ResponseEntity<String> editRecruit(@RequestBody Map<String, String> recruit){
		Recruit recruitDTO = new Recruit();
		
		String teamCode = recruit.get("teamCode");
		String teamDistinction = recruit.get("teamDistinction");
		String stadium = recruit.get("stadium");
		String city = recruit.get("city");
		String area = recruit.get("area");
		String age = recruit.get("age");
		String gender = recruit.get("gender");
		String level = recruit.get("level");
		String teamImg = recruit.get("teamImg");
		String contents = recruit.get("contents");
		
		// 성별
		switch(gender) {
		case "man":
			recruitDTO.setGender("남자");
			break;
		case "woman":
			recruitDTO.setGender("여자");
			break;
		default:
			recruitDTO.setGender("남녀모두");
			break;
		}
		
		// 레벨
		switch(level) {
		case "noneLevel":
			recruitDTO.setLevel("실력무관");
			break;
		case "beginner":
			recruitDTO.setLevel("비기너");
			break;
		case "amateur":
			recruitDTO.setLevel("아마추어");
			break;
		case "semiPro":
			recruitDTO.setLevel("세미프로");
			break;
		case "pro":
			recruitDTO.setLevel("프로");
			break;
		}
		
		recruitDTO.setAge(age);
		recruitDTO.setArea(area);
		recruitDTO.setCity(city);
		recruitDTO.setContents(contents);
		recruitDTO.setStadium(stadium);
		recruitDTO.setTeamCode(teamCode);
		recruitDTO.setTeamDistinction(teamDistinction);
		
		teamService.editTeamRecruit(recruitDTO);
		
		log.info("recruit: {}", recruit);
		
		return ResponseEntity.ok("팀원 모집 수정 성공");
	}

	// 팀원 모집 삭제
	@DeleteMapping("team/deleteRecruit")
	public ResponseEntity<String> deleteRecruit(@RequestParam String teamCode){
		teamService.deleteRecruit(teamCode);
		return ResponseEntity.ok("팀원 모집 삭제 성공");
	}
	
	// 신청 내역 추가
	@PostMapping("team/appRecruit")
	public ResponseEntity<String> addRecruitApplication(@RequestBody Map<String, String> recruit){
		log.info("가져온 신청내역: {}",recruit);
		
		teamService.addUserRecruit(recruit);
		
		return ResponseEntity.ok("신청하기가 완료되었습니다");
	}
	
	// 모집 신청 내역 조회
	@GetMapping("team/userRecruit")
	public List<UserRecruit> getRecruitUserList(@RequestParam(required = false) String recruitAplyCode){
		log.info("모집 신청 코드: {}", recruitAplyCode);
		
		List<UserRecruit> userList = teamService.getUserRecruitInfo(recruitAplyCode);
		
		return userList;
	}
	
	// 팀장 위임
	@PutMapping("team/delegate")
	public ResponseEntity<String> modifyTeamDelegate(@RequestBody Map<String, String> teamUser){
		log.info("선택된 사용자: {}", teamUser);
		
		teamService.modifyTeamDelegate(teamUser);
		
		return ResponseEntity.ok("팀장 위임 완료");
	}
	
	// 팀원 가입 수락
	@PostMapping("team/acceptUser")
	public ResponseEntity<String> addTeamUser(@RequestBody Map<String, String> acceptUser){
		log.info("수락된 사용자: {}", acceptUser);
		teamService.addTeamUser(acceptUser);
		
		return ResponseEntity.ok("가입 수락 완료");
	}
	
	// 팀원 가입 거절
	@DeleteMapping("team/rejectUser")
	public ResponseEntity<String> deleteRecruitApp(@RequestParam String userId,
												   @RequestParam String recruitAplyCode){
		
		teamService.recruitRejectUser(userId, recruitAplyCode);
		
		return ResponseEntity.ok("가입 거절 완료");
	}
	
}
