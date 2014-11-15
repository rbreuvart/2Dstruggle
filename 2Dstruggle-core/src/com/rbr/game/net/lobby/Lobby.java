package com.rbr.game.net.lobby;

import com.badlogic.gdx.utils.Array;
import com.rbr.game.net.kryo.packet.lobby.PacketMessageLobby;

public abstract class Lobby {

	private Array<PacketMessageLobby> arrayMessage;
	private boolean roundStart;//demare/stop un round
	private boolean gameStart;//demare/stop la partie
	
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
	
	
	public Lobby() {
		arrayMessage = new Array<PacketMessageLobby>();
		setRoundStart(false);
	}
	
	public  abstract void eventPlayerLocalReadyToPlay() ;
	public boolean isGameStart() {
		return gameStart;
	}
	public void setGameStart(boolean gameStart) {
		this.gameStart = gameStart;
	}
	

	
}

