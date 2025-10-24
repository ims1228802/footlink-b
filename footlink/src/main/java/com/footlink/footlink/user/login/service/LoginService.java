package com.footlink.footlink.user.login.service;

import com.footlink.footlink.user.login.domain.LoginRequest;
import com.footlink.footlink.user.login.domain.LoginResponse;


public interface LoginService {

	//
	LoginResponse login(LoginRequest request);
	
	

	
}
