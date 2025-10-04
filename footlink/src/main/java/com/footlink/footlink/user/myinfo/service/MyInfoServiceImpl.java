package com.footlink.footlink.user.myinfo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.footlink.footlink.user.myinfo.domain.MyInfo;
import com.footlink.footlink.user.myinfo.mapper.MyInfoMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MyInfoServiceImpl implements MyInfoService {

    @Autowired
    private MyInfoMapper myInfoMapper;

    @Override
    public MyInfo getMyInfoByEmail(String email) {
        log.info("📩 내 정보 조회 요청: {}", email);

        MyInfo myInfo = myInfoMapper.getMyInfoByEmail(email);

        if (myInfo == null) {
            log.warn("⚠️ 존재하지 않는 사용자: {}", email);
        } else {
            log.info("✅ 사용자 정보 조회 성공: {}", myInfo.getName());
        }

        return myInfo;
    }
}
