package com.footlink.footlink.file.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.footlink.footlink.file.dto.FileDto;

@Mapper
public interface FileMapper {

	int deleteFileByIdx(String fileIdx);
	FileDto getFileInfoByIdx(String fileIdx);
	List<FileDto> getFileList();
	int addfile(FileDto fileDto);
	int addfiles(List<FileDto> fileDto);
	int modifyfile(FileDto fileDto);
	String getNextFileIdx();
}
