package com.footlink.footlink.user.match.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.footlink.footlink.user.match.domain.ResultDTO.GameRe;
import com.footlink.footlink.user.match.domain.ResultDTO.MatchFinalResult;
import com.footlink.footlink.user.match.domain.ResultDTO.MatchParticipant;
import com.footlink.footlink.user.match.domain.ResultDTO.MatchParticipantState;
import com.footlink.footlink.user.match.domain.ResultDTO.MatchResult;
import com.footlink.footlink.user.match.domain.ResultDTO.PlayerRe;
import com.footlink.footlink.user.match.domain.ResultDTO.ResultDataDto;
import com.footlink.footlink.user.match.mapper.MatchResultMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MatchResultServiceImpl implements MatchResultService{
	private final MatchResultMapper matchResultMapper;
	
	@Override
    @Transactional
    public void saveMatchResults(ResultDataDto resultData) {
        
        Long matchNo = resultData.getMatchNo();

        matchResultMapper.deleteMatchParticipantsState(matchNo);
        matchResultMapper.deleteMatchResults(matchNo);
        matchResultMapper.deleteMatchFinalResults(matchNo);
        matchResultMapper.deleteMatchParticipants(matchNo);

        List<MatchParticipantState> statesToInsert = new ArrayList<>();
        List<MatchResult> resultsToInsert = new ArrayList<>();       

        Map<String, TeamSummary> summaryMap = new HashMap<>();
        

        Set<MatchParticipant> participantsSet = new HashSet<>();
 
        for (GameRe game : resultData.getGames()) {
            int gameNumber = game.getGameNumber();
            String teamAId = game.getSelectedTeamA();
            String teamBId = game.getSelectedTeamB();
            
            int teamAScore = 0;
            int teamBScore = 0;

            for (PlayerRe stat : game.getTeamAStats()) {

                MatchParticipantState state = new MatchParticipantState();
                state.setMatchNo(matchNo);
                state.setGameNumber(gameNumber);
                state.setUserId(stat.getPlayerId());
                state.setTeamCd(teamAId);
                state.setGoal(stat.getGoal());
                state.setAssist(stat.getAssist());
                state.setSaves(stat.getSave());
                statesToInsert.add(state);
                
                teamAScore += stat.getGoal(); 

                MatchParticipant participantA = new MatchParticipant(); 
                participantA.setMatchNo(matchNo);
                participantA.setUserId(stat.getPlayerId());
                participantA.setTeamCd(teamAId);
                
                participantsSet.add(participantA);
            }


            for (PlayerRe stat : game.getTeamBStats()) {

                MatchParticipantState state = new MatchParticipantState();
                state.setMatchNo(matchNo);
                state.setGameNumber(gameNumber);
                state.setUserId(stat.getPlayerId());
                state.setTeamCd(teamBId);
                state.setGoal(stat.getGoal());
                state.setAssist(stat.getAssist());
                state.setSaves(stat.getSave());
                statesToInsert.add(state);

                teamBScore += stat.getGoal(); 
                MatchParticipant participantB = new MatchParticipant(); 
                participantB.setMatchNo(matchNo);
                participantB.setUserId(stat.getPlayerId());
                participantB.setTeamCd(teamBId);
                
                participantsSet.add(participantB);
            }

            MatchResult result = new MatchResult();
            result.setMatchNo(matchNo);
            result.setGameNumber(gameNumber);
            result.setTeamACd(teamAId);
            result.setTeamAScore(teamAScore);
            result.setTeamBCd(teamBId);
            result.setTeamBScore(teamBScore);

            String winner = null;
            String loser = null;
            if (teamAScore > teamBScore) {
                winner = teamAId;
                loser = teamBId;
            } else if (teamBScore > teamAScore) {
                winner = teamBId;
                loser = teamAId;
            }
            result.setWinnerTeamCd(winner);
            result.setLoserTeamCd(loser);
            
            resultsToInsert.add(result);
            updateSummaryMap(summaryMap, teamAId, teamAScore, teamBScore);
            updateSummaryMap(summaryMap, teamBId, teamBScore, teamAScore);
        } 
        if (!statesToInsert.isEmpty()) {
            matchResultMapper.insertMatchParticipantsStateList(statesToInsert);
        }

        if (!resultsToInsert.isEmpty()) {
            matchResultMapper.insertMatchResultList(resultsToInsert);
        }
        
        if (!summaryMap.isEmpty()) {
            List<MatchFinalResult> finalResults = new ArrayList<>();
            for (Map.Entry<String, TeamSummary> entry : summaryMap.entrySet()) {
                MatchFinalResult finalResult = new MatchFinalResult();
                finalResult.setMatchNo(matchNo);
                finalResult.setTeamCd(entry.getKey());
                TeamSummary summary = entry.getValue();
                finalResult.setWins(summary.wins);
                finalResult.setDraws(summary.draws);
                finalResult.setLosses(summary.losses);
                finalResult.setGoalsFor(summary.goalsFor);
                finalResult.setGoalsAgainst(summary.goalsAgainst);
                finalResult.setGoalDifference(summary.goalsFor - summary.goalsAgainst);
                finalResults.add(finalResult);
            }
            matchResultMapper.insertMatchFinalResultList(finalResults);
        }

        if (!participantsSet.isEmpty()) {
            matchResultMapper.insertMatchParticipantList(new ArrayList<>(participantsSet));
        }
        final int FROM_STATUS = 2;
        final int TO_STATUS = 3;
        matchResultMapper.updateMatchStatusToCompleted(matchNo,FROM_STATUS,TO_STATUS);
        
    }
	private static class TeamSummary {
        int wins = 0;
        int draws = 0;
        int losses = 0;
        int goalsFor = 0;
        int goalsAgainst = 0;
    }

    private void updateSummaryMap(Map<String, TeamSummary> map, String teamId, int scoreFor, int scoreAgainst) {
        
        TeamSummary summary = map.getOrDefault(teamId, new TeamSummary());
        
        if (scoreFor > scoreAgainst) {
            summary.wins++;
        } else if (scoreFor < scoreAgainst) {
            summary.losses++;
        } else {
            summary.draws++;
        }
        
        summary.goalsFor += scoreFor;
        summary.goalsAgainst += scoreAgainst;
        
        map.put(teamId, summary);
    }
}
