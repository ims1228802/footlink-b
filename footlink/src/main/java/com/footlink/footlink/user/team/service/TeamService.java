package com.footlink.footlink.user.team.service;

import java.util.List;

import com.footlink.footlink.user.team.domain.StadiumArea;
import com.footlink.footlink.user.team.domain.State;
import com.footlink.footlink.user.team.domain.Team;

public interface TeamService {
	List<Team> getTeamList();							// 팀 리스트 조회
	List<Team> getSearchTeamList(String param);
	List<StadiumArea> getArea();						// 팀 등록 주소 조회
	List<StadiumArea> getStadium();						// 팀 등록 구장 조회
	void addTeamInfo(Team param);						// 팀 등록
	void addTeamState(State param);						// 팀 능력치 등록
}
