package com.footlink.footlink.user.myinfo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublicUserProfile {
	
	private String userId; // u.user_id
	private String nickname; // u.user_nm
	private String position; // u.user_pst
	private String region; // u.user_addr
	private String level; // u.user_level (비공개면 null 처리)
	private String intro; // u.user_intro
	private String profileImageUrl; // f.file_path + '/' + f.file_new_name
}
