package com.rbr.game.net.kryo.packet.player;

public class PacketSpawnPlayer {
	 public int id;
	 public float positionX,positionY;
	 public boolean spawn;
	 public PacketSpawnPlayer() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "PacketSpawnPlayer [id=" + id + ", positionX=" + positionX
				+ ", positionY=" + positionY + ", spawn=" + spawn + "]";
	}
	 
	 
}
