package com.rbr.game.net.kryo.packet.lobby;

public class PacketMessageLobby {

	public int idConnection;
	public String name;
	public String type;
	public String message;
	public int heure;
	public int minute;
	public int seconde;
	public String color;
	
	public PacketMessageLobby() {
		
	}

	@Override
	public String toString() {
		return "PacketMessageLobby [idConnection=" + idConnection + ", name="
				+ name + ", type=" + type + ", message=" + message + ", heure="
				+ heure + ", minute=" + minute + ", seconde=" + seconde + "]";
	}
	
	
}
