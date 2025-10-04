package com.footlink.footlink.user.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.footlink.footlink.user.login.JwtToken.JwtTokenProvider;
import com.footlink.footlink.user.login.domain.LoginRequest;
import com.footlink.footlink.user.login.domain.LoginResponse;
import com.footlink.footlink.user.login.mapper.LoginMapper;
import com.footlink.footlink.user.manage.domain.User;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginMapper loginMapper;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = loginMapper.getUserInfoByEmail(request.getEmail());

        if (user == null) {
            return new LoginResponse(null, null, "존재하지 않는 이메일입니다.");
        }

        boolean isMatched = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!isMatched) {
            return new LoginResponse(null, null, "비밀번호가 일치하지 않습니다.");
        }

        String token = jwtTokenProvider.createToken(user.getEmail(), user.getRole());
        log.info("로그인 성공 - {}", user.getEmail());

        return new LoginResponse(token, user.getEmail(), "로그인 성공");
    }
}
