package com.footlink.footlink.team.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.footlink.footlink.team.domain.Team;
import com.footlink.footlink.team.service.TeamService;

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
	
	@GetMapping(value = "/team")
	public List<Team> getTeamList(String param) {
		List<Team> teamList =  teamService.getTeamList();
		return teamList;
	}
	
	@PostMapping("/searchTeam")
	public List<Team> getSearchTeamList(@RequestBody Object params) {
		log.info("input: {}", params);
		List<Team> teamSearchList = teamService.getSearchTeamList(params.toString());
		return teamSearchList;
	}
	
}
