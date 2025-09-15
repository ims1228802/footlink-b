package com.footlink.footlink.user.login.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.footlink.footlink.user.login.DTO.LoginRequest;
import com.footlink.footlink.user.login.mapper.LoginMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final LoginMapper loginMapper;

    @Override
    public Map<String, Object> matchedUser(String email, String password) {
        Map<String, Object> resultMap = new HashMap<>();
        boolean isMatched = false;

        System.out.println("프론트에서 받은 값 => email: " + email + ", password: " + password);

        LoginRequest userInfo = loginMapper.getUserInfoByEmail(email);

        if (userInfo == null) {
            System.out.println("DB 조회 결과: 없음 (email=" + email + ")");
        } else {
            System.out.println("DB 조회 결과 => email: " + userInfo.getEmail() + ", password: " + userInfo.getPassword());

            if (userInfo.getPassword().equals(password)) {
                isMatched = true;
                resultMap.put("userInfo", userInfo);
                System.out.println("비밀번호 일치 → 로그인 성공");
            } else {
                System.out.println("비밀번호 불일치");
            }
        }

        resultMap.put("isMatched", isMatched);
        return resultMap;
    }
}