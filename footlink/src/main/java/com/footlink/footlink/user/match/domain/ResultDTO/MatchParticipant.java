package com.footlink.footlink.user.match.domain.ResultDTO;

import java.util.List;

import lombok.Data;


@Data
public class MatchParticipant {
	private Long matchNo;
    private String userId;
    private String teamCd;
    private String userName;
    private String teamName;
}