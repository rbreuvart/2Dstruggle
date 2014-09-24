package com.rbr.game.net.kryo.packet;

import com.esotericsoftware.kryo.Kryo;

public class RegisterPacket {

	public static void definitionPacket(Kryo kryo){
		kryo.register(PacketAddPlayer.class);
		kryo.register(PacketAddMultiPlayer.class);
		kryo.register(PacketRemovePlayer.class);
		kryo.register(PacketUpdateGameObjectPlayer.class);	
	}
	
}
