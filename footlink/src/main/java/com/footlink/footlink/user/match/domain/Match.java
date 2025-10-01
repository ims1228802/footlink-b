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
	private String fieldNo;
	private String fieldName;
	private String matchDate;
	private String matchTime;
	private String matchEndTime;
	private String rcrtmNope;
	private String atndNope;
	private String minLevel;
	private String maxLevel;
	private String matchStts;
	private String genderName;
	private String matchMethod;
	
}