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
public class Match {
	
	private String matchNo;
	private String fieldName;
	private String filePath;
	private String staName;
	private String staAddr;
	private String matchDate;
	private String matchTime;
	private String matchEndTime;
	private String minLevelName;
	private String maxLevelName;
	private String genderName;
	private String matchStts;
	private String matchTypeName;
	private String region;
	private int totalPlayers;
	private int applyCount;
	
}