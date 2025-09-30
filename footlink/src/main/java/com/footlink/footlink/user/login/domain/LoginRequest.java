package com.footlink.footlink.user.login.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class LoginRequest  {
	
    private String id;
    private String email;
    private String password;
}
