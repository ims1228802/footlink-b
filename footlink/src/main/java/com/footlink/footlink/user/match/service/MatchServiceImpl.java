package com.footlink.footlink.user.match.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.footlink.footlink.user.match.domain.AddMatch;
import com.footlink.footlink.user.match.domain.Field;
import com.footlink.footlink.user.match.domain.Gender;
import com.footlink.footlink.user.match.domain.Match;
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
	public List<Stadium> stadiumList() {
		
		return matchMapper.stadiumList();
	}
	
	@Override
	public List<Province> findProvince() {

		return matchMapper.findProvince();
	}
	
	@Override
	public List<Stadium> selectKeyword(String keyword) {
		
		return matchMapper.findByKeyword(keyword);
	}

	@Override	
	public List<Match> findAll() {
		
		return matchMapper.findAll();
	}

	@Override
	public List<Gender> test() {
		
		return matchMapper.test();
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
		int result = matchMapper.addMatch(addMatch);

	    // INSERT가 성공했는지 확인
	    if (result == 1) {
	        // 성공했다면, 컨트롤러에게 전달할 addMatch 객체를 그대로 반환
	        // (만약 INSERT 후 생성된 ID를 다시 조회해서 반환해야 한다면 
	        //  이곳에서 findById 같은 메서드를 추가로 호출합니다)
	        return addMatch;
	    } else {
	        // INSERT에 실패한 경우, 예외를 발생시키거나 null을 반환
	        // throw new RuntimeException("매치 등록에 실패했습니다.");
	        return null;
	    }
	}
}
