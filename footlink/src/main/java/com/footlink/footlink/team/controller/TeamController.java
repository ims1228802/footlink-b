package com.footlink.footlink.team.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.footlink.footlink.team.domain.Team;
import com.footlink.footlink.team.service.TeamService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/")
@RequestMapping("/api")
public class TeamController {
	private final TeamService teamService;
	
	@GetMapping(value = "/team")
	public List<Team> getMethodName(String param) {
		List<Team> teamList =  teamService.getTeamList();
		return teamList;
	}
}
