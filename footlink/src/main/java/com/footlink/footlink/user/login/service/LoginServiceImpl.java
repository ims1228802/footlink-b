package com.footlink.footlink.user.login.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.footlink.footlink.user.login.JwtToken.JwtTokenProvider;
import com.footlink.footlink.user.login.domain.LoginRequest;
import com.footlink.footlink.user.login.domain.LoginResponse;
import com.footlink.footlink.user.login.mapper.LoginMapper;
import com.footlink.footlink.user.manage.domain.User;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginMapper loginMapper;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public LoginResponse login(LoginRequest request) {
        // 1️⃣ 사용자 조회
        User user = loginMapper.getUserInfoByEmail(request.getEmail());
        if (user == null) {
            return LoginResponse.builder()
                    .token(null)
                    .name(null)
                    .message("존재하지 않는 이메일입니다.")
                    .build();
        }

        // 2️⃣ 비밀번호 검증
        boolean isMatched = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!isMatched) {
            return LoginResponse.builder()
                    .token(null)
                    .name(user.getEmail())
                    .message("비밀번호가 일치하지 않습니다.")
                    .build();
        }

        // 3️⃣ JWT 토큰 생성
        String token = jwtTokenProvider.createToken(user.getEmail(), user.getPhone());

        // 4️⃣ 로그인 성공 응답
        return LoginResponse.builder()
                .token(token)
                .name(user.getName())
                .message("로그인 성공")
                .build();
    }

	@Override
	public Map<String, Object> matchedUser(String email, String password) {
		// TODO Auto-generated method stub
		return null;
	}
}
