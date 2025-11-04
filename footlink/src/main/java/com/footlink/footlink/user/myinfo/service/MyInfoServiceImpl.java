package com.footlink.footlink.user.myinfo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.footlink.footlink.file.dto.FileDto;
import com.footlink.footlink.file.mapper.FileMapper;
import com.footlink.footlink.file.util.FilesUtils;
import com.footlink.footlink.user.myinfo.domain.MyAppliedMatch;
import com.footlink.footlink.user.myinfo.domain.MyCompletedMatch;
import com.footlink.footlink.user.myinfo.domain.MyInfo;
import com.footlink.footlink.user.myinfo.domain.MyLikeMatch;
import com.footlink.footlink.user.myinfo.domain.PublicUserProfile;
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
	private final BCryptPasswordEncoder passwordEncoder;

	@Override
	public MyInfo getMyInfoByEmail(String email) {
		MyInfo myInfo = myInfoMapper.getMyInfoByEmail(email);
		return myInfo;
	}

	@Transactional
	@Override
	public void modify(MyInfo myInfo, MultipartFile file) {
        try {
            log.info("🔧 사용자 정보 수정 시작 - 이메일: {}", myInfo.getEmail());

            // 파일 업로드 처리
            if (file != null && !file.isEmpty()) {
                log.info("📂 업로드된 파일 처리 중... 파일명: {}", file.getOriginalFilename());

                // 1 파일 저장 
                FileDto fileDto = filesUtils.uploadFile(file);
                if (fileDto == null) {
                    throw new RuntimeException("파일 업로드 실패");
                }

                // 2️ file_idx 생성 
                String nextFileIdx = fileMapper.getNextFileIdx();
                if (nextFileIdx == null || nextFileIdx.isBlank()) {
                    nextFileIdx = "file_001";
                }
                fileDto.setFileIdx(nextFileIdx);

                // 3️ DB에 파일 등록
                fileMapper.addfile(fileDto);
                log.info("✅ 파일 등록 완료 - file_idx: {}", fileDto.getFileIdx());

                // 4️ 사용자 DTO에 연결
                myInfo.setProfile(fileDto.getFileIdx());
            }

            // 5️ 사용자 정보 수정
            myInfoMapper.modifyMyInfo(myInfo);
            log.info("✅ 사용자 정보 수정 완료 - 이메일: {}", myInfo.getEmail());

        } catch (Exception e) {
            log.error("❌ 사용자 정보 수정 실패", e);
            throw new RuntimeException("정보 수정 실패", e);
        }
    }

	@Override
	public List<Map<String, Object>> getMyTeamsByEmail(String email) {
		return myInfoMapper.getMyTeamsByEmail(email);
	}
	
    @Override
    @Transactional(readOnly = true)
    public List<MyAppliedMatch> getAppliedMatches(String email) {
        return myInfoMapper.getAppliedMatches(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MyLikeMatch> getLikeMatches(String email) {
        return myInfoMapper.getLikeMatches(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MyCompletedMatch> getCompletedMatches(String email) {
        return myInfoMapper.getCompletedMatches(email);
    }
	
    @Override
    @Transactional(readOnly = true)
    public MyInfo getMySettings(String email) {
        return myInfoMapper.getMySettingsByEmail(email);
    }

    @Override
    @Transactional
    public void updateMySettings(MyInfo myInfo) {
        myInfoMapper.updateMySettingsByEmail(myInfo);
    }
    
    @Override
    @Transactional
    public void updatePhone(MyInfo myInfo) {
        myInfoMapper.updatePhone(myInfo);
    }
    
    @Override
    @Transactional
    public void updatePassword(String email, String newPassword) {
        String encoded = passwordEncoder.encode(newPassword);
        myInfoMapper.updatePassword(email, encoded);
    }
    
    @Override
    @Transactional
    public void withdrawUser(String email) {
        myInfoMapper.withdrawUser(email);
    }
    
    @Override
    public void updateUserLevel(MyInfo myInfo) {
        log.info("사용자 레벨 수정: {}", myInfo);
        myInfoMapper.updateUserLevel(myInfo);
    }

	@Override
	public void cancelAppliedMatch(String email, Long matchNo) {
		myInfoMapper.deleteAppliedMatch(email, matchNo);
	}

	@Override
	public void unlikeMatch(String email, Long matchNo) {
		myInfoMapper.deleteLike(email, matchNo);
		
	}

	@Override
	public PublicUserProfile getPublicUser(String userId) {
		return myInfoMapper.selectPublicUser(userId);
	}
	
	

}