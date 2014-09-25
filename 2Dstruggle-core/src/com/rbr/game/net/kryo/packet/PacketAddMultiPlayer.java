package com.rbr.game.net.kryo.packet;

public class PacketAddMultiPlayer {
	public int id;
	public float positionSpawnx,positionSpawny;
	@Override
	public String toString() {
		return "PacketAddMultiPlayer [id=" + id + ", positionSpawnx="
				+ positionSpawnx + ", positionSpawny=" + positionSpawny + "]";
	}
	
}
