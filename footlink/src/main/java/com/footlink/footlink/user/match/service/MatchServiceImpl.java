package com.footlink.footlink.user.match.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.footlink.footlink.user.match.domain.AddMatch;
import com.footlink.footlink.user.match.domain.ApplyMatch;
import com.footlink.footlink.user.match.domain.EndList;
import com.footlink.footlink.user.match.domain.Field;
import com.footlink.footlink.user.match.domain.Match;
import com.footlink.footlink.user.match.domain.MatchDetail;
import com.footlink.footlink.user.match.domain.Province;
import com.footlink.footlink.user.match.domain.Stadium;
import com.footlink.footlink.user.match.mapper.MatchMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MatchServiceImpl implements MatchService{
	private final MatchMapper matchMapper;
	
	public List<Match> getBookList(String fieldNo, String date) {
		
		return matchMapper.getbookList(fieldNo, date);
	}
	
	@Override
	public List<Field> getfieldList(String staNO) {
		
		return matchMapper.getfieldList(staNO);
	}
	@Override
	public void applyMatch(String matchNo, String userId) {
        // 1. 먼저 이미 신청 내역이 있는지 확인 (창고 관리인에게 물어봄)
        int count = matchMapper.countApplicationByUser(matchNo, userId);

        // 2. 신청 내역이 없다면 INSERT 실행 (요리 시작)
        if (count == 0) {
            ApplyMatch application = ApplyMatch.builder()
                                        .matchNo(matchNo)
                                        .userId(userId)
                                        .build();
            matchMapper.insertApplication(application);
        } else {
            // 3. 이미 신청 내역이 있다면 예외를 발생시켜 컨트롤러에게 알림
            throw new IllegalStateException("이미 신청한 매치입니다.");
        }
    }
	@Override
	public List<Stadium> stadiumList() {
		
		return matchMapper.stadiumList();
	}
	
	@Override
	public List<Province> findProvince() {

		return matchMapper.findProvince();
	}
	@Override
	public List<MatchDetail> getMatchInfo(String matchNo) {
		
		return matchMapper.getMatchInfo(matchNo);
	}
	@Override
	public List<Stadium> selectKeyword(String keyword) {
		
		return matchMapper.findByKeyword(keyword);
	}

	@Override
	public List<Match> findAllMatch() {
		
		return matchMapper.findAllMatch();
	}

	@Override
	public AddMatch addmatch(AddMatch addMatch) {
		
		String minLevelCode = "";
		switch (addMatch.getMinLevel()) {
		case 1 		-> minLevelCode = "level_01";
		case 2 		-> minLevelCode = "level_02";
		case 3 		-> minLevelCode = "level_03";
		case 4 		-> minLevelCode = "level_04";
		case 5 		-> minLevelCode = "level_05";
		case 6 		-> minLevelCode = "level_06";
		case 7 		-> minLevelCode = "level_07";
		case 8 		-> minLevelCode = "level_08";
		case 9 		-> minLevelCode = "level_09";
		case 10 	-> minLevelCode = "level_10";
		case 11		-> minLevelCode = "level_11";
		case 12		-> minLevelCode = "level_12";
	}
		
		addMatch.setMinLevelCode(minLevelCode);
		
		String maxLevelCode  ="";
		switch (addMatch.getMaxLevel()) {
		case 1 		-> maxLevelCode  = "level_01";
		case 2 		-> maxLevelCode  = "level_02";
		case 3 		-> maxLevelCode  = "level_03";
		case 4 		-> maxLevelCode  = "level_04";
		case 5 		-> maxLevelCode  = "level_05";
		case 6 		-> maxLevelCode  = "level_06";
		case 7 		-> maxLevelCode  = "level_07";
		case 8 		-> maxLevelCode  = "level_08";
		case 9 		-> maxLevelCode  = "level_09";
		case 10 	-> maxLevelCode  = "level_10";
		case 11		-> maxLevelCode  = "level_11";
		case 12		-> maxLevelCode  = "level_12";
	}
		addMatch.setMaxLevelCode(maxLevelCode);

		String matchTypeCode = "";
		switch (addMatch.getMatchType()) {
		case "6vs6" -> matchTypeCode = "match_type_01";
		case "5vs5" -> matchTypeCode = "match_type_02";
		case "4vs4" -> matchTypeCode = "match_type_03";
	}
		addMatch.setMatchType(matchTypeCode);
		
		int result = matchMapper.addMatch(addMatch);

	    if (result == 1) {
	        
	        return addMatch;
	    } else {
	       
	        return null;
	    }
	}
	@Override
	public List<EndList> getEndList() {
		
		return matchMapper.getEndList();
	}
	
}
