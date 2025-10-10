package com.footlink.footlink.user.team.domain;

import lombok.Data;

@Data
public class StadiumArea {
	private int provinceId;			// 지역 아이디
	private String name;			// 지역 이름
	private String stadiumNo;		// 구장 번호 
	private String stadiumName;		// 구장 이름
	private String stadiumAddr;		// 구장 주소
}
