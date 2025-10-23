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
import com.footlink.footlink.user.match.domain.ResultDTO.MatchSummaryDto;
import com.footlink.footlink.user.match.domain.ResultDTO.PlayerRe;
import com.footlink.footlink.user.match.domain.ResultDTO.ResultDataDto;
import com.footlink.footlink.user.match.domain.ResultDTO.TeamSummaryDto;
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
    @Override
    public List<MatchParticipant> selectMatchParticipantList(Long matchNo) {
    	
    	return matchResultMapper.selectMatchParticipantList(matchNo);
    }
    @Override
    public List<GameRe> getMatchResultsForEdit(Long matchNo) {

        List<GameRe> games = matchResultMapper.selectMatchResultsByMatchNo(matchNo);
        if (games != null) {
            for (GameRe game : games) {
                fillEmptyStats(game.getTeamAStats());
                fillEmptyStats(game.getTeamBStats());
            }
        }

        return games;
    }
    private void fillEmptyStats(List<PlayerRe> stats) {
        int requiredSize = 5;
        if (stats == null) {
            stats = new ArrayList<>();
        }
        int currentSize = stats.size();
        if (currentSize < requiredSize) {
            for (int i = 0; i < requiredSize - currentSize; i++) {

                stats.add(new PlayerRe()); 
            }
        }
    }
    @Override
    @Transactional(readOnly = true) // 읽기 전용 트랜잭션
    public MatchSummaryDto getMatchSummary(Long matchNo) {
        // 1. 매치 기본 정보 조회
        // (selectMatchBasicInfo가 날짜, 시간, 경기장 이름 등을 포함한 객체나 Map을 반환한다고 가정)
        // 이를 위한 MatchBasicInfoDto 같은 특정 DTO가 필요할 수 있음
        Map<String, Object> basicInfo = matchResultMapper.selectMatchBasicInfo(matchNo);
        if (basicInfo == null) {
            return null; // 또는 예외 발생
        }

        // 2. 팀 요약 정보 조회
        List<TeamSummaryDto> teamSummaries = matchResultMapper.selectTeamSummaries(matchNo);

        // 3. 최종 DTO로 조합
        MatchSummaryDto summaryDto = new MatchSummaryDto();
        summaryDto.setMatchDate((String) basicInfo.get("match_date")); // 필요에 따라 타입 캐스팅 조정
        summaryDto.setMatchTime((String) basicInfo.get("match_time"));
        summaryDto.setStadiumName((String) basicInfo.get("stadium_nm"));
        // 필요하다면 다른 기본 정보 추가 (예: 구장 이름)

        // 승점 계산 (예: 승리 3점, 무승부 1점) 및 DTO에 설정
        if (teamSummaries != null) {
            for (TeamSummaryDto summary : teamSummaries) {
                summary.setPoints(summary.getWins() * 3 + summary.getDraws());
            }
        }
        summaryDto.setTeamSummaries(teamSummaries);

        return summaryDto;
    }
}
