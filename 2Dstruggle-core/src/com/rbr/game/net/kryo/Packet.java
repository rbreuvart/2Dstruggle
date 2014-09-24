package com.rbr.game.net.kryo;

import com.rbr.game.entity.physics.GameObject;

public class Packet {

	public static class PacketRequest{}
	public static class PacketRequestAnswer{public Boolean accepted;};
	public static class PacketMessage{public String message, clientName;}
	public static class PacketGameObject {
		public GameObject gameObject;
	}
	

}
