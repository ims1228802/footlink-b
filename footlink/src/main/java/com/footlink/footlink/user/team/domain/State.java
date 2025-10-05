package com.footlink.footlink.user.team.domain;

import lombok.Data;

@Data
public class State {
	private String teamCode;
	private int attack;
	private int speed;
	private int dribble;
	private int stamina;
	private int defense;
	private int physical;
	private int pass;
	private int shot;
}
