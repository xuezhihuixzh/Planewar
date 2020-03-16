package com.liuxuejian.planefight.entity;

/**
 * 
 * @content闯关模式实体类
 * @author bruce.liu
 * @version 1.0
 * @time 2015/5/6/22.26
 * 
 */

public class Advance_Score {
	private int score;
	private String player_name;
	private int advance_level;
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getPlayer_name() {
		return player_name;
	}
	public void setPlayer_name(String player_name) {
		this.player_name = player_name;
	}
	public int getAdvance_level() {
		return advance_level;
	}
	public void setAdvance_level(int advance_level) {
		this.advance_level = advance_level;
	}
	
}
