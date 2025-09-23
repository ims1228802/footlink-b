package com.footlink.footlink.team.domain;

import java.sql.Time;

import lombok.Data;

@Data
public class Team {
	private String teamCode;
	private String teamName;
	private String regionName;
	private String meetingTime;
	private String teamAge;
	private String levelCode;
	private String isTemp;
}
