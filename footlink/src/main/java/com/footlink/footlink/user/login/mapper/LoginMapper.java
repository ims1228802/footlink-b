package com.footlink.footlink.user.login.mapper;

import com.footlink.footlink.user.login.DTO.LoginRequest;
import org.apache.ibatis.annotations.Mapper;


 @Mapper
 public interface LoginMapper {
	
	//회원조회
	LoginRequest getUserInfoByEmail(String email);
}
