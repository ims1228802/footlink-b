package com.footlink.footlink.user.team.domain;

import java.sql.Time;

import lombok.Data;

@Data
public class Team {
	private String teamCode;					// 팀 코드
	private String teamName;					// 팀 이름
	private String regionName;					// 지역명
	private String meetingTimeCode;				// 활동 시간 코드
	private String meetingTime;					// 활동 시간		
	private String teamAgeCode;					// 활동 연령 코드
	private String teamAge;						// 활동 연령
	private String levelCode;					// 팀 레벨 코드
	private String level;						// 팀 레벨
	private String isTemp;						// 정석, 임시 여부
	private int userCount;						// 팀 인원
	private int viewCount;						// 팀 조회수
	private int favoriteCount;					// 관심 조회수
	private String stadium;						// 구장
	private String gender;						// 성별
	private String activeDoWeek;				// 활동 요일
}
