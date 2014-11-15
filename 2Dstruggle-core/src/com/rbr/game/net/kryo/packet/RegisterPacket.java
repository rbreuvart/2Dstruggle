package com.rbr.game.net.kryo.packet;

import com.esotericsoftware.kryo.Kryo;
import com.rbr.game.net.kryo.packet.lobby.PacketMessageLobby;
import com.rbr.game.net.kryo.packet.lobby.PacketUpdateLobby;
import com.rbr.game.net.kryo.packet.player.PacketAddMultiPlayer;
import com.rbr.game.net.kryo.packet.player.PacketRemovePlayer;
import com.rbr.game.net.kryo.packet.player.PacketSpawnPlayer;
import com.rbr.game.net.kryo.packet.player.PacketUpdateGameObjectPlayer;
import com.rbr.game.net.kryo.packet.projectile.PacketAddProjectile;
import com.rbr.game.net.kryo.packet.projectile.PacketRemoveProjectile;

public class RegisterPacket {

	public static void definitionPacket(Kryo kryo){
		kryo.register(PacketInfoServeur.class);	

		kryo.register(PacketMessageLobby.class);
		kryo.register(PacketUpdateLobby.class);
		
		//kryo.register(PacketAddPlayer.class);
		kryo.register(PacketAddMultiPlayer.class);
		kryo.register(PacketRemovePlayer.class);
		
		kryo.register(PacketSpawnPlayer.class);
		kryo.register(PacketUpdateGameObjectPlayer.class);	
		
		
		kryo.register(PacketAddProjectile.class);
		kryo.register(PacketRemoveProjectile.class);
	}
	
}
