package com.footlink.footlink.user.match.domain.ResultDTO;

import java.util.List;

import lombok.Data;


@Data
public class ResultDataDto {
	private Long matchNo; 
    private List<GameRe> games;

}