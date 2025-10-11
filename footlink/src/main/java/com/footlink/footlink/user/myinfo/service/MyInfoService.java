package com.footlink.footlink.user.myinfo.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.footlink.footlink.user.myinfo.domain.MyInfo;

public interface MyInfoService {
	
    MyInfo getMyInfoByEmail(String email);

	void modify(MyInfo myInfo, MultipartFile file);

	List<Map<String, Object>> getMyTeamsByEmail(String email);
    
}
