package com.footlink.footlink.user.myinfo.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.footlink.footlink.user.myinfo.domain.MyInfo;

@Mapper
public interface MyInfoMapper {
	
    MyInfo getMyInfoByEmail(String email);
    
}
