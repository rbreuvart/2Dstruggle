package com.rbr.game.net.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.rbr.game.net.kryo.Packet.PacketMessage;
import com.rbr.game.net.kryo.Packet.PacketRequest;
import com.rbr.game.net.kryo.Packet.PacketRequestAnswer;

public class KryoServerNetWorkListener extends Listener{
	public KryoServerNetWorkListener() {
	}

	@Override
	public void connected(Connection conn) {
		super.connected(conn);
	}
	@Override
	public void disconnected(Connection conn) {
		super.disconnected(conn);
	}
	@Override
	public void received(Connection conn, Object object) {
		super.received(conn, object);
		if (object instanceof PacketRequest) {
			PacketRequestAnswer answer = new PacketRequestAnswer();
			answer.accepted = true;
			conn.sendTCP(answer);
		}
		
		if (object instanceof PacketMessage) {
			PacketMessage pMessage = (PacketMessage) object;
			System.out.println(" Serveur From ["+pMessage.clientName+"] >> "+PacketMessage.class.toString()+" message ["+pMessage.message+"]");
		}
		
	}
	@Override
	public void idle(Connection conn) {
		super.idle(conn);
	}
}
