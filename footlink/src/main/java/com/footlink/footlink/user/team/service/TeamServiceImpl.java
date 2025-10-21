package com.footlink.footlink.user.team.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.footlink.footlink.user.myinfo.domain.MyInfo;
import com.footlink.footlink.user.team.domain.Calendar;
import com.footlink.footlink.user.team.domain.Recruit;
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

	@Override
	public Team getTeamDetail(String teamCode) {
		return teamMapper.getTeamDetail(teamCode);
	}

	@Override
	public List<MyInfo> getTeamUserList(String teamCode) {
		return teamMapper.getTeamUserList(teamCode);
	}

	@Override
	public State getTeamState(String teamCode) {
		return teamMapper.getTeamState(teamCode);
	}

	@Override
	public void addTeamUser(String teamCode, String userId) {
		HashMap<String, String> map = new HashMap<>();
		
		map.put("teamCode", teamCode);
		map.put("userId", userId);
		
		teamMapper.addTeamUser(map);
	}

	@Override
	public void addCalendar(Calendar calendar) {
		teamMapper.addCalendar(calendar);
	}

	@Override
	public List<Calendar> getTeamCalendar(String teamCode) {
		return teamMapper.getTeamCalendar(teamCode);
	}

	@Override
	public void addTeamRecruit(Recruit recruit) {
		teamMapper.addTeamRecruit(recruit);
	}

	@Override
	public void deleteTeam(String teamCode) {
		teamMapper.deleteTeam(teamCode);
	}

	@Override
	public void deleteTeamUser(String teamCode, String id) {
		HashMap<String, String> userInfo = new HashMap<>();
		
		userInfo.put("teamCode", teamCode);
		userInfo.put("userId", id);
		
		teamMapper.deleteTeamUser(userInfo);
	}
	
}
