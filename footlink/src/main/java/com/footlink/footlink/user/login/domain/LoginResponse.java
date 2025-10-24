package com.footlink.footlink.user.login.domain;

// 로그인 성공/실패 응답 DTO

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