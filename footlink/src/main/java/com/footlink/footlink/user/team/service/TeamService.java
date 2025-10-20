package com.footlink.footlink.user.team.service;

import java.util.List;

import com.footlink.footlink.user.myinfo.domain.MyInfo;
import com.footlink.footlink.user.team.domain.Calendar;
import com.footlink.footlink.user.team.domain.Recruit;
import com.footlink.footlink.user.team.domain.StadiumArea;
import com.footlink.footlink.user.team.domain.State;
import com.footlink.footlink.user.team.domain.Team;

public interface TeamService {
	List<Team> getTeamList();							// 팀 리스트 조회
	Team getTeamDetail(String teamCode);
	List<StadiumArea> getArea();						// 팀 등록 주소 조회
	List<StadiumArea> getStadium();						// 팀 등록 구장 조회
	List<MyInfo> getTeamUserList(String teamCode);		// 팀에 포함된 사용자 조회
	State getTeamState(String teamCode);				// 팀 스텟 조회
	List<Calendar> getTeamCalendar(String teamCode);	// 팀 일정 조회
	void addTeamInfo(Team param);						// 팀 등록
	void addTeamState(State param);						// 팀 능력치 등록
	void addTeamUser(String teamCode, String userId);	// 팀 유저 등록
	void addCalendar(Calendar calendar);				// 일정 등록
	void addTeamRecruit(Recruit recruit);				// 팀 모집글 등록
}
