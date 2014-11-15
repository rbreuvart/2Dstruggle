package com.rbr.game.player;

public enum TeamPlayer {

	RED("RED"),BLUE("BLUE"),GREEN("GREEN"),FFA("FFA");

	private String team;
	private TeamPlayer(String team) {
		this.setTeam(team);
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	
	
	
}
