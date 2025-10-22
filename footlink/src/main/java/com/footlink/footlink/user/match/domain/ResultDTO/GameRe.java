package com.footlink.footlink.user.match.domain.ResultDTO;

import java.util.List;

import lombok.Data;


@Data
public class GameRe {
	private int gameNumber;
    private String selectedTeamA;
    private String selectedTeamB;
    private List<PlayerRe> teamAStats;
    private List<PlayerRe> teamBStats;
}