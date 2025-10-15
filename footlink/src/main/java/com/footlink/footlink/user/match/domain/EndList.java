package com.footlink.footlink.user.match.domain;

import java.util.Date;

import lombok.Data;


@Data
public class EndList {
	private int matchNo;
    private Date matchDate;
    private String stadiumNm;
    private String genderNm;
    private String minLevelName;
    private String maxLevelName;
    private String winnerTeamName;
    private String loserTeamName;
    private int winnerScore;
    private int loserScore;

}