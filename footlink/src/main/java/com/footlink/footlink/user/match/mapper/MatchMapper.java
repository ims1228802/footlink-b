package com.footlink.footlink.user.match.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.footlink.footlink.user.match.domain.AddMatch;
import com.footlink.footlink.user.match.domain.ApplyMatch;
import com.footlink.footlink.user.match.domain.EndList;
import com.footlink.footlink.user.match.domain.Field;
import com.footlink.footlink.user.match.domain.Match;
import com.footlink.footlink.user.match.domain.MatchDetail;
import com.footlink.footlink.user.match.domain.Province;
import com.footlink.footlink.user.match.domain.Stadium;

@Mapper
public interface MatchMapper {
	List<Match> findAllMatch();
	
	List<Stadium> stadiumList();
	List<Stadium> findByKeyword(String keyword);
	List<Province> findProvince();
	List<Field> getfieldList(String staNo);
	List<Match> getbookList(String fieldNo, String date);
	int addMatch(AddMatch addMatch);
	List<MatchDetail> getMatchInfo(String matchNo);
	int countApplicationByUser(@Param("matchNo") String matchNo, @Param("userId") String userId);
	void insertApplication(ApplyMatch application);
	List<EndList> getEndList();
}