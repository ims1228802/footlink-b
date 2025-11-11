package com.footlink.footlink.user.team.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.footlink.footlink.user.myinfo.domain.MyInfo;
import com.footlink.footlink.user.team.domain.Calendar;
import com.footlink.footlink.user.team.domain.Recruit;
import com.footlink.footlink.user.team.domain.StadiumArea;
import com.footlink.footlink.user.team.domain.State;
import com.footlink.footlink.user.team.domain.Team;
import com.footlink.footlink.user.team.domain.UserRecruit;

public interface TeamService {
	List<Team> getTeamList();									// 팀 리스트 조회
	Team getTeamDetail(String teamCode);
	List<StadiumArea> getArea();								// 팀 등록 주소 조회
	List<StadiumArea> getStadium();								// 팀 등록 구장 조회
	List<MyInfo> getTeamUserList(String teamCode);				// 팀에 포함된 사용자 조회
	State getTeamState(String teamCode);						// 팀 스텟 조회
	List<Calendar> getTeamCalendar(String teamCode);			// 팀 일정 조회
	int getRecruitCount(String teamCode);						// 팀 모집 카운트 조회
	Recruit getRecruitInfo(String teamCode);					// 팀 모집 내용 조회
	Calendar getCalendarDetail(String teamDateCode);			// 일정 상세 조회
	List<UserRecruit>getUserRecruitInfo(String recruitAplyCode);// 모집 신청 내역 조회
	void addEmblem(MultipartFile files, String teamCode);		// 팀 엠블렘 사진 추가
	void editEmblem(MultipartFile files, String teamCode);		// 팀 엠블렘 사진 수정
	void addTeamInfo(Team param);								// 팀 등록
	void editTeamInfo(Team param);								// 팀 수정
	void deleteTeam(String teamCode);							// 팀 삭제(소프트 삭제)
	void deleteTeamUser(String teamCode, String id);			// 팀 유저 탈퇴(하드 삭제)
	void addTeamState(State param);								// 팀 능력치 등록
	void editTeamState(State param);							// 팀 능력치 수정
	void addTeamUser(String teamCode, String userId);			// 팀 유저 등록
	void addTeamUser(Map<String, String> acceptUser);			// 팀 유저 등록(가입신청 수락)
	void addCalendar(Calendar calendar);						// 일정 등록
	void deleteCalendar(String teamDateCode);					// 일정 삭제
	void addTeamRecruit(Recruit recruit);						// 팀 모집글 등록
	void editTeamRecruit(Recruit recruit);						// 팀 모집글 수정
	void deleteRecruit(String teamCode);						// 팀 모집글 삭제
	void addUserRecruit(Map<String, String> recruit);			// 유저 모집 신청 내역 등록
	void modifyTeamDelegate(Map<String, String> teamUser);		// 팀장 위임
	void recruitRejectUser(String userId, String recruitAplyCode);	// 가입 신청 거절
}
