package com.footlink.footlink.user.myinfo.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.footlink.footlink.user.myinfo.domain.MyAppliedMatch;
import com.footlink.footlink.user.myinfo.domain.MyCompletedMatch;
import com.footlink.footlink.user.myinfo.domain.MyInfo;
import com.footlink.footlink.user.myinfo.domain.MyLikeMatch;

public interface MyInfoService {
	
    MyInfo getMyInfoByEmail(String email);

	void modify(MyInfo myInfo, MultipartFile file);

	List<Map<String, Object>> getMyTeamsByEmail(String email);
	
    List<MyAppliedMatch> getAppliedMatches(String email);
    
    List<MyLikeMatch> getLikeMatches(String email);
    
    List<MyCompletedMatch> getCompletedMatches(String email);
	
    MyInfo getMySettings(String email);
    
    void updateMySettings(MyInfo myInfo);
    
    void updatePhone(MyInfo myInfo);
    
    void updatePassword(String email, String newPassword);
    
    void withdrawUser(String email);

    
}
