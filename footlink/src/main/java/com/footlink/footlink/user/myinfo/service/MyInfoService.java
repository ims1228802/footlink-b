package com.footlink.footlink.user.myinfo.service;

import com.footlink.footlink.user.myinfo.domain.MyInfo;

public interface MyInfoService {
	
    MyInfo getMyInfoByEmail(String email);
    
}
