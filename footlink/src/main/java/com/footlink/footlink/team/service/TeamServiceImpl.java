package com.footlink.footlink.team.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.footlink.footlink.team.domain.Team;
import com.footlink.footlink.team.mapper.TeamMapper;

@Service
public class TeamServiceImpl implements TeamService{
	
	private final TeamMapper teamMapper;
	
	public TeamServiceImpl (TeamMapper teamMapper) {
		this.teamMapper = teamMapper;
	}
	
	@Override
	public List<Team> getTeamList() {
		List<Team> teamList = teamMapper.getTeamList();
		return teamList;
	}
	
}
