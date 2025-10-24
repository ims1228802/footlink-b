package com.footlink.footlink.file.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.footlink.footlink.file.dto.FileDto;
import com.footlink.footlink.file.mapper.FileMapper;
import com.footlink.footlink.file.util.FilesUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService{
	
	private final FilesUtils filesUtils;
	private final FileMapper fileMapper;
	
	@Override
	public void deleteFile(FileDto fileDto) {
		String path = fileDto.getFilePath();
		Boolean isDelete = filesUtils.deleteFileByPath(path);
		if(isDelete) fileMapper.deleteFileByIdx(fileDto.getFileIdx());
	}
	
	@Override
	public void addFile(MultipartFile file) {
		FileDto fileInfo = filesUtils.uploadFile(file);
		if(fileInfo != null) fileMapper.addfile(fileInfo); 
	}
	
	@Override
	public void addFiles(MultipartFile[] files) {
		List<FileDto> fileList = filesUtils.uploadFiles(files);
		if(!fileList.isEmpty()) fileMapper.addfiles(fileList);
	}
}

