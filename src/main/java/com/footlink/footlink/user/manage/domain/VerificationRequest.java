package com.footlink.footlink.user.manage.domain;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerificationRequest {
    
	private int id;
	private String phone;
    private String code;
    private LocalDateTime expireTime;
    private LocalDateTime createdAt = LocalDateTime.now();
	
}
