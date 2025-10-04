package com.footlink.footlink.user.login.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.footlink.footlink.user.manage.domain.User;

@Mapper
public interface LoginMapper {

    User getUserInfoByEmail(String email);
    
}
