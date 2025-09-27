package com.footlink.footlink.team.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.footlink.footlink.team.domain.Team;

@Mapper
public interface TeamMapper {
	List<Team> getTeamList();
}
