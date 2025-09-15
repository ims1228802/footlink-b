package com.footlink.footlink.user.login.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest  {
	
    private String id;
    private String email;
    private String password;
}
