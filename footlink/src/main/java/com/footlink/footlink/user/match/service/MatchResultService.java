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
import com.footlink.footlink.user.match.domain.ResultDTO.ResultDataDto;

public interface MatchResultService {
	void saveMatchResults(ResultDataDto resultData);
}