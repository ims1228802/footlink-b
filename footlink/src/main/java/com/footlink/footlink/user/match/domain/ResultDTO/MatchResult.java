package com.footlink.footlink.user.match.domain.ResultDTO;

import java.util.List;

import lombok.Data;


@Data
public class MatchResult {
	private Long matchNo;
    private int gameNumber;
    private String teamACd;
    private int teamAScore;
    private String teamBCd;
    private int teamBScore;
    private String winnerTeamCd; 
    private String loserTeamCd;
}