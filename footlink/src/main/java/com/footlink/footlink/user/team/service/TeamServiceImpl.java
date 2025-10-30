package com.footlink.footlink.user.team.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.footlink.footlink.file.dto.FileDto;
import com.footlink.footlink.file.mapper.FileMapper;
import com.footlink.footlink.file.util.FilesUtils;
import com.footlink.footlink.user.myinfo.domain.MyInfo;
import com.footlink.footlink.user.team.domain.Calendar;
import com.footlink.footlink.user.team.domain.Recruit;
import com.footlink.footlink.user.team.domain.StadiumArea;
import com.footlink.footlink.user.team.domain.State;
import com.footlink.footlink.user.team.domain.Team;
import com.footlink.footlink.user.team.mapper.TeamMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Service
@Log4j2
public class TeamServiceImpl implements TeamService{
	
	private final TeamMapper teamMapper;
	private final FileMapper fileMapper;
	private final FilesUtils filesUtils;
	
	@Override
	public List<Team> getTeamList() {
		List<Team> teamList = teamMapper.getTeamList();
		return teamList;
	}

	@Override
	public List<StadiumArea> getArea() {
		return teamMapper.getArea();
	}

	@Override
	public List<StadiumArea> getStadium() {
		return teamMapper.getStadium();
	}

	@Override
	public void addTeamInfo(Team param) {
		teamMapper.addTeamInfo(param);
	}

	@Override
	public void addTeamState(State param) {
		teamMapper.addTeamState(param);
	}

	@Override
	public Team getTeamDetail(String teamCode) {
		return teamMapper.getTeamDetail(teamCode);
	}

	@Override
	public List<MyInfo> getTeamUserList(String teamCode) {
		return teamMapper.getTeamUserList(teamCode);
	}

	@Override
	public State getTeamState(String teamCode) {
		return teamMapper.getTeamState(teamCode);
	}

	@Override
	public void addTeamUser(String teamCode, String userId) {
		HashMap<String, String> map = new HashMap<>();
		
		map.put("teamCode", teamCode);
		map.put("userId", userId);
		
		teamMapper.addTeamUser(map);
	}

	@Override
	public void addCalendar(Calendar calendar) {
		teamMapper.addCalendar(calendar);
	}

	@Override
	public List<Calendar> getTeamCalendar(String teamCode) {
		return teamMapper.getTeamCalendar(teamCode);
	}

	@Override
	public void addTeamRecruit(Recruit recruit) {
		teamMapper.addTeamRecruit(recruit);
	}

	@Override
	public void deleteTeam(String teamCode) {
		teamMapper.deleteTeam(teamCode);
	}

	@Override
	public void deleteTeamUser(String teamCode, String id) {
		HashMap<String, String> userInfo = new HashMap<>();
		
		userInfo.put("teamCode", teamCode);
		userInfo.put("userId", id);
		
		teamMapper.deleteTeamUser(userInfo);
	}

	@Override
	public void editTeamInfo(Team param) {
		teamMapper.editTeamInfo(param);
	}

	@Override
	public void editTeamState(State param) {
		teamMapper.editTeamState(param);
	}

	@Override
	public int getRecruitCount(String teamCode) {
		return teamMapper.getRecruitCount(teamCode);
	}

	@Override
	public void deleteRecruit(String teamCode) {
		teamMapper.deleteRecruit(teamCode);
	}

	@Override
	public void deleteCalendar(String teamDateCode) {
		teamMapper.deleteCalendar(teamDateCode);
	}

	@Override
	public Recruit getRecruitInfo(String teamCode) {
		return teamMapper.getRecruitInfo(teamCode);
	}

	@Override
	public void editTeamRecruit(Recruit recruit) {
		teamMapper.editTeamRecruit(recruit);
	}

	@Override
	public Calendar getCalendarDetail(String teamDateCode) {
		return teamMapper.getCalendarDetail(teamDateCode);
	}

	@Override
	public void addEmblem(MultipartFile files, String teamCode) {
		try {
			// 받은 파일이 없거나 비어있는지 확인
			if(files != null || !files.isEmpty()) {
				// 1 파일 저장 
                FileDto fileDto = filesUtils.uploadFile(files);
                if (fileDto == null) {
                    throw new RuntimeException("파일 업로드 실패");
                }

                // 2️ file_idx 생성 
                String nextFileIdx = fileMapper.getNextFileIdx();
                if (nextFileIdx == null || nextFileIdx.isBlank()) {
                    nextFileIdx = "file_001";
                }
                fileDto.setFileIdx(nextFileIdx);

                // 3️ DB에 파일 등록
                fileMapper.addfile(fileDto);
                log.info("✅ 파일 등록 완료 - file_idx: {}", fileDto.getFileIdx());
                
                // 4 팀 프로필에 연결
                HashMap<String, String> teamEmblem = new HashMap<>();
                
                teamEmblem.put("teamCode", teamCode);
                teamEmblem.put("fileIdx", fileDto.getFileIdx());
                
                teamMapper.addEmblem(teamEmblem);
			}
		} catch (Exception e) {
			throw new RuntimeException("정보 수정 실패", e);
		}
	}

	@Override
	public void editEmblem(MultipartFile files, String teamCode) {
		try {
			// 받은 파일이 없거나 비어있는지 확인
			if(files != null || !files.isEmpty()) {
				// 1 파일 저장 
                FileDto fileDto = filesUtils.uploadFile(files);
                if (fileDto == null) {
                    throw new RuntimeException("파일 업로드 실패");
                }

                // 2️ file_idx 생성 
                String fileIdx = teamMapper.getFileIdx(teamCode);
                fileDto.setFileIdx(fileIdx);

                // 3️ DB에 파일 등록
                fileMapper.modifyfile(fileDto);
                log.info("✅ 파일 수정 완료 - file_idx: {}", fileDto.getFileIdx());
                
                // 4 팀 프로필에 연결
                HashMap<String, String> teamEmblem = new HashMap<>();
                
                teamEmblem.put("teamCode", teamCode);
                teamEmblem.put("fileIdx", fileDto.getFileIdx());
                
                teamMapper.addEmblem(teamEmblem);
			}
		} catch (Exception e) {
			throw new RuntimeException("정보 수정 실패", e);
		}
	}
	
}
