package com.footlink.footlink.user.manage.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.footlink.footlink.user.manage.domain.User;
import com.footlink.footlink.user.manage.domain.VerificationRequest;

@Mapper
public interface ManageMapper {

	boolean existsByPhone(String phone);

	void save(VerificationRequest verification);

	Optional<VerificationRequest> findTopByPhoneOrderByCreatedAtDesc(String phone);

	void registerUser(User user);

	void signUp(User user);
	
	String findLastUserIdForUpdate();
	
}
