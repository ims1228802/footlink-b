package com.footlink.footlink.user.match.domain.ResultDTO;

import java.util.Date;

import lombok.Data;


@Data
public class PlayerRe {
	private String playerId;
    private int goal;
    private int assist;
    private int save;

}