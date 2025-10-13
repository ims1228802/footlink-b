package com.footlink.footlink.user.team.domain;

import lombok.Data;

@Data
public class Calendar {
	private String teamCode;			// 팀 코드
	private String title;				// 제목
	private String placeName;			// 장소
	private String date;				// 날짜
	private String startTime;			// 시작 시간
	private String endTime;				// 종료 시간
	private String contents;			// 내용
}
