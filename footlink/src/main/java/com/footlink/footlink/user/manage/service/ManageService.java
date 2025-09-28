package com.footlink.footlink.user.manage.service;

import org.springframework.stereotype.Service;

import com.footlink.footlink.user.manage.domain.SignUpRequest;

@Service
public interface ManageService {

	boolean checkPhoneDuplicate(String phone);
	
    void sendVerificationCode(String phone);
    
    boolean verifyCode(String phone, String code);
    
    void signUp(SignUpRequest request);
	
}
