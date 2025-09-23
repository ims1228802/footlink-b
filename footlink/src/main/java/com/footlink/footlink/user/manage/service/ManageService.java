package com.footlink.footlink.user.manage.service;

import org.springframework.stereotype.Service;

@Service
public interface ManageService {

	boolean checkPhoneDuplicate(String phone);
	
}
