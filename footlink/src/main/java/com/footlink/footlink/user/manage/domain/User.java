package com.footlink.footlink.user.manage.domain;

//DB에 저장되고 비즈니스 로직에서 다루는 도메인엔티티

import lombok.Data;

@Data
public class User {
	
    private String id;
    private String email;
    private String password;
    private String name;
    private String phone;
	private String birth;
    private String gender;
	private String addr;

//    private String role;          // 권한 (ex: ROLE_USER / ROLE_ADMIN)
//    private Long profileFileId;   // 파일 테이블(Files) FK — 프로필 이미지
    
}
