package com.footlink.footlink.user.team.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.footlink.footlink.user.myinfo.domain.MyInfo;
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
	void addTeamInfo(Team param);
	void addTeamState(State param);
}
