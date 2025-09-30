package com.footlink.footlink.user.login.service;

import java.util.Map;

import com.footlink.footlink.user.login.domain.LoginRequest;
import com.footlink.footlink.user.login.domain.LoginResponse;


public interface LoginService {

	// 회원 아이디,비밀번호 조회
	Map<String, Object> matchedUser(String email, String password);
	
	//
	LoginResponse login(LoginRequest request);
	
	

	
}
