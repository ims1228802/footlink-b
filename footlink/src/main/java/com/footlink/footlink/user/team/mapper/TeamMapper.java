package com.footlink.footlink.user.team.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.footlink.footlink.user.myinfo.domain.MyInfo;
import com.footlink.footlink.user.team.domain.Calendar;
import com.footlink.footlink.user.team.domain.Recruit;
import com.footlink.footlink.user.team.domain.StadiumArea;
import com.footlink.footlink.user.team.domain.State;
import com.footlink.footlink.user.team.domain.Team;

@Mapper
public interface TeamMapper {
	List<Team> getTeamList();
	List<Team> getSearchTeamList(String inputParam);
	List<StadiumArea> getArea();
	List<StadiumArea> getStadium();
	Team getTeamDetail(String teamCode);
	List<MyInfo> getTeamUserList(String teamCode);
	State getTeamState(String teamCode);
	List<Calendar> getTeamCalendar(String teamCode);
	int getRecruitCount(String teamCode);
	Recruit getRecruitInfo(String teamCode);
	Calendar getCalendarDetail(String teamDateCode);
	String getFileIdx(String teamCode);
	void addEmblem(Map<String, String> teamEmblem);
	void addTeamInfo(Team param);
	void editTeamInfo(Team param);
	void deleteTeam(String teamCode);
	void deleteTeamUser(Map userInfo);
	void addTeamState(State param);
	void editTeamState(State param);
	void addTeamUser(Map<String, String> map);
	void addCalendar(Calendar params);
	void deleteCalendar(String teamDateCode);
	void addTeamRecruit(Recruit recruit);
	void editTeamRecruit(Recruit recruit);
	void deleteRecruit(String teamCode);
}
