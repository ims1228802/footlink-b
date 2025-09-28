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
	
}
