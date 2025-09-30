package com.footlink.footlink.user.login.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class LoginResponse {
	
    private String token;
    private String name;
    private String message;
    
}