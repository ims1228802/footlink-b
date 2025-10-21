package com.footlink.footlink.user.myinfo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.footlink.footlink.user.myinfo.domain.MyAppliedMatch;
import com.footlink.footlink.user.myinfo.domain.MyCompletedMatch;
import com.footlink.footlink.user.myinfo.domain.MyInfo;
import com.footlink.footlink.user.myinfo.domain.MyLikeMatch;

@Mapper
public interface MyInfoMapper {
	
    MyInfo getMyInfoByEmail(String email);

	int modifyMyInfo(MyInfo myInfo);

	List<Map<String, Object>> getMyTeamsByEmail(String email);
	
    // 공개설정 전용
    MyInfo getMySettingsByEmail(@Param("email") String email);
    
    List<MyAppliedMatch> getAppliedMatches(@Param("email") String email);
    
    List<MyLikeMatch> getLikeMatches(@Param("email") String email);
    
    List<MyCompletedMatch> getCompletedMatches(@Param("email") String email);

    int updateMySettingsByEmail(MyInfo myInfo);
    
    int updatePhone(MyInfo myInfo);
    
    int updatePassword(@Param("email") String email, @Param("password") String password);
    
    int withdrawUser(@Param("email") String email);

}
