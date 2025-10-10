package com.footlink.footlink.user.myinfo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.footlink.footlink.file.dto.FileDto;
import com.footlink.footlink.file.mapper.FileMapper;
import com.footlink.footlink.file.util.FilesUtils;
import com.footlink.footlink.user.myinfo.domain.MyInfo;
import com.footlink.footlink.user.myinfo.mapper.MyInfoMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@RequiredArgsConstructor
@Service
@Slf4j
public class MyInfoServiceImpl implements MyInfoService {

	@Autowired
	private MyInfoMapper myInfoMapper;
	private final FileMapper fileMapper;
	private final FilesUtils filesUtils;

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

	@Transactional
	@Override
	public void modify(MyInfo myInfo, MultipartFile file) {
		try {
			// ✅ 파일이 있을 경우에만 업로드 처리
			if (file != null && !file.isEmpty()) {
				// ✅ FilesUtils 사용
				FileDto fileDto = filesUtils.uploadFile(file);

				// ✅ files 테이블 insert
				fileMapper.addfile(fileDto);

				// ✅ user 테이블 user_img에 file_idx 저장
				myInfo.setProfile(fileDto.getFileIdx());
			}

			// ✅ 사용자 정보 수정
			myInfoMapper.modifyMyInfo(myInfo);
			log.info("✅ 사용자 정보 수정 성공: {}", myInfo.getEmail());

		} catch (Exception e) {
			log.error("❌ 사용자 정보 수정 실패", e);
			throw new RuntimeException("정보 수정 실패", e);
		}
	}
}