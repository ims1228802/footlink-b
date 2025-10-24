package com.footlink.footlink.user.match.domain.ResultDTO;

import java.util.List;

import lombok.Data;


@Data
public class TeamSummaryDto {
	private String teamCode;
    private String teamName;
    private int wins;
    private int draws;
    private int losses;
    private int goalDifference;
    private int points;
}