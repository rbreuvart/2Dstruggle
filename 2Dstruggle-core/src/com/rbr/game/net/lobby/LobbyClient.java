package com.rbr.game.net.lobby;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryonet.Connection;
import com.rbr.game.net.client.NetKryoNewClientManageur;
import com.rbr.game.net.kryo.packet.lobby.PacketMessageLobby;
import com.rbr.game.screen.game.ScreenGame;
import com.rbr.game.utils.ConfigLobby;

public class LobbyClient {

	private Array<PacketMessageLobby> arrayMessage;
	
	private boolean connectedToLobby;
	private boolean roundStart;
	
	NetKryoNewClientManageur netKryoNewClientManageur;
	ScreenGame screenGame;
	
	
	public LobbyClient(ScreenGame screenGame,NetKryoNewClientManageur netKryoNewClientManageur) {
		arrayMessage = new Array<PacketMessageLobby>();
		this.screenGame = screenGame;
		this.netKryoNewClientManageur = netKryoNewClientManageur;
		
	}
	
	public Array<PacketMessageLobby> getArrayMessage() {
		return arrayMessage;
	}
	public void setArrayMessage(Array<PacketMessageLobby> arrayMessage) {
		this.arrayMessage = arrayMessage;
	}
	public boolean isRoundStart() {
		return roundStart;
	}
	public void setRoundStart(boolean roundStart) {
		this.roundStart = roundStart;
	}

	public boolean receivedMessage(Connection c, Object o) {
		if (o instanceof PacketMessageLobby) {
			final PacketMessageLobby messageLobby = (PacketMessageLobby)o;
			
			if (ConfigLobby.TypeMessageConnectionValideLobby.equals(messageLobby.type)) {
				System.out.println("Client (2): Connection Lobby OK  : "+messageLobby);
				setConnectedToLobby(true);
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {					
						screenGame.getPlayerManageur().createLocalPlayer(screenGame,messageLobby.idConnection);
				
					}
				});
			}			
			return true;
		}
		return false;
	}

	public boolean isConnectedToLobby() {
		return connectedToLobby;
	}

	public void setConnectedToLobby(boolean connectedToLobby) {
		this.connectedToLobby = connectedToLobby;
	}
}
