package com.footlink.footlink.user.match.service;

import java.util.List;

import com.footlink.footlink.user.match.domain.AddMatch;
import com.footlink.footlink.user.match.domain.EndList;
import com.footlink.footlink.user.match.domain.Field;
import com.footlink.footlink.user.match.domain.Match;
import com.footlink.footlink.user.match.domain.MatchDetail;
import com.footlink.footlink.user.match.domain.Player;
import com.footlink.footlink.user.match.domain.Province;
import com.footlink.footlink.user.match.domain.Stadium;

public interface MatchService {
	List<Match> findAllMatch();
	List<Stadium> stadiumList();
	List<Stadium> selectKeyword(String keyword);
	List<Province> findProvince();
	List<Field> getfieldList(String staNo);
	List<Match> getBookList(String fieldNo, String date);
	AddMatch addmatch(AddMatch addMatch);
	MatchDetail getMatchInfo(String matchNo);
	void applyMatch(String matchNo, String userId);
	List<EndList> getEndList();
	List<Match> adminMatchList();
	void addLike(String matchNo, String userId);
	void removeLike(String matchNo, String userId);
	boolean isLikedByUser(String matchNo, String userId);
	List<Player> getPlayerList(String matchNo);
}