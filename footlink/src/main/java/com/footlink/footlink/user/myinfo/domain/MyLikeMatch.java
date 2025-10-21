package com.footlink.footlink.user.myinfo.domain;

import lombok.Data;

@Data
public class MyLikeMatch {
	
    private int matchNo;
    private String matchDate;
    private String matchTime;
    private String stadium;
    private String genderNm;
    private String matchTypeCd;
    private String minLevel;
    private String maxLevel;
    
}
