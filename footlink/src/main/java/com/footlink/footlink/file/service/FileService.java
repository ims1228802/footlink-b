package com.footlink.footlink.file.service;

import org.springframework.web.multipart.MultipartFile;

import com.footlink.footlink.file.dto.FileDto;

public interface FileService {

	void addFile(MultipartFile file); 
	
	void addFiles(MultipartFile[] files);

	void deleteFile(FileDto fileDto); 
}
