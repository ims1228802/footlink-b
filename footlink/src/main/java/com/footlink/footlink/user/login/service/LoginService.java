package com.footlink.footlink.user.login.service;

import java.util.Map;

public interface LoginService {

	// 특정회원 email
	Map<String, Object> matchedUser(String email, String password);

	
}
