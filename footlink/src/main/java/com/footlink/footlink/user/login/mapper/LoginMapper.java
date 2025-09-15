package com.footlink.footlink.user.login.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.footlink.footlink.user.login.DTO.LoginRequest;

@Mapper
public interface LoginMapper {
	
	//회원조회
	LoginRequest getUserInfoByEmail(String email);
}
