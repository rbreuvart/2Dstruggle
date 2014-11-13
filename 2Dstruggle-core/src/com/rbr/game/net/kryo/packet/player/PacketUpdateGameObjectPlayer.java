package com.rbr.game.net.kryo.packet.player;


public class PacketUpdateGameObjectPlayer {
	public int id;
	public float positionX,positionY,angle;
	@Override
	public String toString() {
		return "PacketUpdateGameObjectPlayer [id=" + id + ", positionX="
				+ positionX + ", positionY=" + positionY + ", angle=" + angle
				+ "]";
	}
	
}
