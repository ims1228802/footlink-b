package com.footlink.footlink.user.match.service;

import java.util.List;

import com.footlink.footlink.user.match.domain.AddMatch;
import com.footlink.footlink.user.match.domain.Field;
import com.footlink.footlink.user.match.domain.Gender;
import com.footlink.footlink.user.match.domain.Match;
import com.footlink.footlink.user.match.domain.MatchDetail;
import com.footlink.footlink.user.match.domain.Province;
import com.footlink.footlink.user.match.domain.Stadium;

public interface MatchService {
	List<Match> findAllMatch();
	List<Gender> test();
	List<Stadium> stadiumList();
	List<Stadium> selectKeyword(String keyword);
	List<Province> findProvince();
	List<Field> getfieldList(String staNo);
	List<Match> getBookList(String fieldNo, String date);
	AddMatch addmatch(AddMatch addMatch);
	List<MatchDetail> getMatchInfo(String matchNo);
}