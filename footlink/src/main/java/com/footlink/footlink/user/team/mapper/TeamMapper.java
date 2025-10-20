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
	void addTeamInfo(Team param);
	void addTeamState(State param);
	void addTeamUser(Map<String, String> map);
	void addCalendar(Calendar params);
	void addTeamRecruit(Recruit recruit);
}
