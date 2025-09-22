package com.footlink.footlink.user.manage.service;

import org.springframework.stereotype.Service;

import com.footlink.footlink.user.manage.mapper.ManageMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ManageServiceImpl implements ManageService {

	private final ManageMapper memberMapper;

	@Override
	public boolean checkPhoneDuplicate(String phone) {
		// TODO Auto-generated method stub
		return memberMapper.existsByPhone(phone);
	}
	

	
	
}
