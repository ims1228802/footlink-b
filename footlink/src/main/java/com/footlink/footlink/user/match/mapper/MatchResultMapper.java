package com.footlink.footlink.user.match.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.footlink.footlink.user.match.domain.ResultDTO.MatchFinalResult;
import com.footlink.footlink.user.match.domain.ResultDTO.MatchParticipant;
import com.footlink.footlink.user.match.domain.ResultDTO.MatchParticipantState;
import com.footlink.footlink.user.match.domain.ResultDTO.MatchResult;

@Mapper
public interface MatchResultMapper {
	void deleteMatchParticipantsState(Long matchNo);
    void deleteMatchResults(Long matchNo);
    void deleteMatchFinalResults(Long matchNo);
    void deleteMatchParticipants(Long matchNo);
    void insertMatchParticipantsStateList(List<MatchParticipantState> states);
    void insertMatchResultList(List<MatchResult> results);
    void insertMatchFinalResultList(List<MatchFinalResult> finalResults);
    void insertMatchParticipantList(List<MatchParticipant> participants);
    void updateMatchStatusToCompleted(@Param("matchNo") Long matchNo, 
    									@Param("fromStatus") int fromStatus, 
    									@Param("toStatus") int toStatus);
}