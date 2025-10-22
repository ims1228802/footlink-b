package com.footlink.footlink.user.match.domain.ResultDTO;

import java.util.List;

import lombok.Data;


@Data
public class MatchParticipantState {
	private Long matchNo;
    private int gameNumber;
    private String userId; 
    private String teamCd;
    private int goal;
    private int assist;
    private int saves; 
}