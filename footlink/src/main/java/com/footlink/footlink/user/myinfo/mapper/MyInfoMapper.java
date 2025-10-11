package com.footlink.footlink.user.myinfo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import com.footlink.footlink.user.myinfo.domain.MyInfo;

@Mapper
public interface MyInfoMapper {
	
    MyInfo getMyInfoByEmail(String email);

	int modifyMyInfo(MyInfo myInfo);

	List<Map<String, Object>> getMyTeamsByEmail(String email);
    
}
