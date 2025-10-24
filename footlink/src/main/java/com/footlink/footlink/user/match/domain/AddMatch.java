package com.footlink.footlink.user.match.domain;

import lombok.Data;
import lombok.ToString;
import java.util.List;

@Data
@ToString
public class AddMatch {
	private String matchNo;
	private String fieldNo;
	private String matchDate;
	private String matchTime;
	private String matchEndTime;
    private Integer minLevel;
    private Integer maxLevel;
    private String title;
    private String content;
    private String gender;
    private String matchType;    
    private String minLevelCode;
    private String maxLevelCode;

}