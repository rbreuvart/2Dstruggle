package com.rbr.game.net.kryo.packet.player;

public class PacketAddMultiPlayer {
	public int id;
	public String name;
	public String color;
	@Override
	public String toString() {
		return "PacketAddMultiPlayer [id=" + id + ", name=" + name + ", color="
				+ color + "]";
	}
	
	
	
}
