package com.footlink.footlink.user.match.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class ApplyMatch {

	private String matchNo;
	private String userId;
	private String ApplStatus;
	private String ApplAt;
	
}
