package com.footlink.footlink.user.myinfo.domain;

import lombok.Data;

@Data
public class MyCompletedMatch {

    private int matchNo;
    private String matchDate;
    private String matchTime;
    private String stadium;
    private String matchTypeNm;
    private Integer homeScore;
    private Integer awayScore;
    private String winner;
    
}
