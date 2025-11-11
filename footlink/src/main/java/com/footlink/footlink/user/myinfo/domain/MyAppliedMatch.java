package com.footlink.footlink.user.myinfo.domain;

import lombok.Data;

@Data
public class MyAppliedMatch {
	
    private int matchNo;
    private String matchDate;
    private String matchTime;
    private String stadium;
    private String genderNm;
    private String matchTypeNm;
    private String minLevel;
    private String maxLevel;
    private Integer currentCount; 
    private Integer totalCount;   
}
