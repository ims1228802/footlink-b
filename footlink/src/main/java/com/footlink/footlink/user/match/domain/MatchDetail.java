package com.footlink.footlink.user.match.domain;




import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Data
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchDetail {
	
	private String matchNo;
	private String fieldName;
	private String spcffct;
	private String filePath;
	private String staName;
	private String staAddr;
	private String matchDate;
	private String matchTime;
	private String matchEndTime;
	private String minLevelName;
	private String maxLevelName;
	private String genderName;
	private String matchTypeName;
	private String region;
	private int totalPlayers;
	private String showerYn;		//샤워실 유무
	private String restYn;			//화장실 유무
	private String parkingYn;		//주차장 유무
	private String shoRtYn;			//풋살화 대여 유무
	private String vestRtYn;		//조끼 대여 유무
	private String ballRtYn;		//풋살공 대여 유무
	private String sellDrink;		//음료 판매 유무
	private int applyCount;
	private Boolean isLikedByUser;
}