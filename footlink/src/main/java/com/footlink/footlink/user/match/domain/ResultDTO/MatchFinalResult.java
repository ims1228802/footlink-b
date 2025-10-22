package com.footlink.footlink.user.match.domain.ResultDTO;

import java.util.List;

import lombok.Data;


@Data
public class MatchFinalResult {
	private Long matchNo;
    private String teamCd;
    private int wins;
    private int draws;
    private int losses;
    private int goalsFor;
    private int goalsAgainst;
    private int goalDifference;
}