package com.footlink.footlink.user.match.service;

import java.util.List;

import com.footlink.footlink.user.match.domain.ResultDTO.GameRe;
import com.footlink.footlink.user.match.domain.ResultDTO.MatchParticipant;
import com.footlink.footlink.user.match.domain.ResultDTO.MatchSummaryDto;
import com.footlink.footlink.user.match.domain.ResultDTO.ResultDataDto;

public interface MatchResultService {
	void saveMatchResults(ResultDataDto resultData);
	List<MatchParticipant> selectMatchParticipantList(Long matchNo);
	List<GameRe> getMatchResultsForEdit(Long matchNo);
	MatchSummaryDto getMatchSummary(Long matchNo);

}