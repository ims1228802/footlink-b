package com.footlink.footlink.user.match.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Stadium {
	
	@Id
	private String staNo;
	private String staName;
	private String staAddr;
	private String staPro;
	private String showerYn;
	private String restYn;
	private String parkingYn;
	private String shoRtYn;
	private String vestRtYn;
	private String ballRtYn;
	private String sellDrink;
	private String staTelNo;
	private String Detail;
	private String OperYn;
}
