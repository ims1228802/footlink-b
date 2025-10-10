package com.footlink.footlink.user.team.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.footlink.footlink.user.team.domain.StadiumArea;
import com.footlink.footlink.user.team.domain.State;
import com.footlink.footlink.user.team.domain.Team;
import com.footlink.footlink.user.team.mapper.TeamMapper;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
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

	public List<Team> getSearchTeamList(String param) {
		log.info("param: {}", param);
		List<Team> searchTeamList = teamMapper.getSearchTeamList(param);
		return searchTeamList;
	}

	@Override
	public List<StadiumArea> getArea() {
		return teamMapper.getArea();
	}

	@Override
	public List<StadiumArea> getStadium() {
		return teamMapper.getStadium();
	}

	@Override
	public void addTeamInfo(Team param) {
		teamMapper.addTeamInfo(param);
	}

	@Override
	public void addTeamState(State param) {
		teamMapper.addTeamState(param);
	}
	
}
