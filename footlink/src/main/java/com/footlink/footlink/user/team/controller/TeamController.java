package com.footlink.footlink.user.team.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.footlink.footlink.user.team.domain.StadiumArea;
import com.footlink.footlink.user.team.domain.Team;
import com.footlink.footlink.user.team.domain.State;
import com.footlink.footlink.user.team.service.TeamService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/")
@RequestMapping("/api")
@Log4j2
public class TeamController {
	private final TeamService teamService;
	private String weekStr = "";
	
	@GetMapping(value = "/team")
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
	
	@PostMapping("/searchTeam")
	public List<Team> getSearchTeamList(@RequestBody Object params) {
		log.info("input: {}", params);
		List<Team> teamSearchList = teamService.getSearchTeamList(params.toString());
		return teamSearchList;
	}
	
	@PostMapping("/addTeam")
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
		
		// 대괄호 제거 후 쉼표로 자르기
		String[] weekRepleace = team.get("week").toString().replaceAll("[\\[\\]]", "").split(", ");
		
		Arrays.stream(weekRepleace).forEach((idx) -> {
			if(weekRepleace.length == 7) {
				weekStr = "매일";
			}
			else if(Integer.parseInt(idx) == weekRepleace.length) {
				weekStr = weekStr.concat(weekStrArr[Integer.parseInt(idx)]);
			}else {
				weekStr = weekStr.concat(weekStrArr[Integer.parseInt(idx)]).concat(",");				
			}
		});
		
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
		return ResponseEntity.ok("팀 등록 성공");
	}
	
}
