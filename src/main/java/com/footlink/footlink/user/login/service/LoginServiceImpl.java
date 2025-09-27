package com.footlink.footlink.user.login.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.footlink.footlink.user.login.domain.LoginRequest;
import com.footlink.footlink.user.login.mapper.LoginMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final LoginMapper loginMapper;

    // 로그인
    @Override
    public Map<String, Object> matchedUser(String email, String password) {
        Map<String, Object> resultMap = new HashMap<>();
        boolean isMatched = false;

        LoginRequest userInfo = loginMapper.getUserInfoByEmail(email);

        if (userInfo == null) {
        } else {
            if (userInfo.getPassword().equals(password)) {
                isMatched = true;
                resultMap.put("userInfo", userInfo);
            } else {
                System.out.println("비밀번호 불일치");
            }
        }
        resultMap.put("isMatched", isMatched);
        return resultMap;
    }
    
    
}