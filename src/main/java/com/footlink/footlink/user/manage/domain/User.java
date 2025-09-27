package com.footlink.footlink.user.manage.domain;

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
