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
public class Field {
	
	@Id
	private String fieldNo;
	private String staNo;
	private String fieldName;
	private String fio;
	private String ffm;
	private String fileNo;
	private String sft;
	private int amt;
	private String ost;
	private String oet;
}
