package com.footlink.footlink.user.myinfo.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MyInfo {
    private String id;          // 계정 ID
    private String role;        // 권한 코드
    private String email;       // 이메일
    private String phone;      // 전화번호
    private LocalDate birth;    // 생년월일
    private String gender;      // 성별
    private String addr;        // 주소
    private LocalDateTime joinYmd;  // 가입일자
    private String name;          // 이름
    private String profile;         // 이미지 경로
    private String pstAbly;     // 포지션 능력
    private String pst;         // 포지션
    private String level;       // 레벨
    private String intro;       // 자기소개
}
