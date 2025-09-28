package com.footlink.footlink.user.match.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.footlink.footlink.user.match.domain.AddMatch;
import com.footlink.footlink.user.match.domain.Field;
import com.footlink.footlink.user.match.domain.Gender;
import com.footlink.footlink.user.match.domain.Match;
import com.footlink.footlink.user.match.domain.Province;
import com.footlink.footlink.user.match.domain.Stadium;

@Mapper
public interface MatchMapper {
	List<Match> findAll();
	List<Gender> test();
	List<Stadium> stadiumList();
	List<Stadium> findByKeyword(String keyword);
	List<Province> findProvince();
	List<Field> getfieldList(String staNo);
	List<Match> getbookList(String fieldNo, String date);
	int addMatch(AddMatch addMatch);
}