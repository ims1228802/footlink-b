package com.footlink.footlink.user.login.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class LoginRequest  {
	
    private String email;
    private String password;
    private String role; //관리자/일반유저 구분용
}
