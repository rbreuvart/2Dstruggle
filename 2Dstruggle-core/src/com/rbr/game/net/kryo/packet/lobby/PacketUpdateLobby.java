package com.rbr.game.net.kryo.packet.lobby;

public class PacketUpdateLobby {
	
	public int id;
	public boolean roundStart;
	public boolean localPlayerReady;
	public boolean gameStart;
	public String team;
	
	public PacketUpdateLobby() {
	}

	@Override
	public String toString() {
		return "PacketUpdateLobby [id=" + id + ", roundStart=" + roundStart
				+ ", localPlayerReady=" + localPlayerReady + ", gameStart="
				+ gameStart + ", team=" + team + "]";
	}
	
}
