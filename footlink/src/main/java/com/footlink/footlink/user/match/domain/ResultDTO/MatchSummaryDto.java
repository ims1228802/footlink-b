package com.footlink.footlink.user.match.domain.ResultDTO;

import java.util.List;

import lombok.Data;


@Data
public class MatchSummaryDto {
	private String matchDate;
    private String matchTime;
    private String stadiumName;
    private String fieldName;
    private List<TeamSummaryDto> teamSummaries;
}