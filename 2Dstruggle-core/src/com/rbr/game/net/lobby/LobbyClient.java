package com.rbr.game.net.lobby;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.rbr.game.net.client.NetKryoNewClientManageur;
import com.rbr.game.net.kryo.packet.lobby.PacketMessageLobby;
import com.rbr.game.net.kryo.packet.lobby.PacketUpdateLobby;
import com.rbr.game.screen.game.ScreenGame;
import com.rbr.game.utils.ConfigLobby;

public class LobbyClient extends Lobby{

	private boolean connectedToLobby;//Permet de verifier si la connection du client avec le Serveur et OK 
		
	NetKryoNewClientManageur netKryoNewClientManageur;
	ScreenGame screenGame;
	
	
	public LobbyClient(ScreenGame screenGame,NetKryoNewClientManageur netKryoNewClientManageur) {
		super();
		this.screenGame = screenGame;
		this.netKryoNewClientManageur = netKryoNewClientManageur;
		
	}
	

	public boolean receivedMessage(Connection c, Object o) {
		
		if (o instanceof PacketMessageLobby) {
			final PacketMessageLobby messageLobby = (PacketMessageLobby)o;
			
			if (ConfigLobby.TypeMessageConnectionValideLobby.equals(messageLobby.type)) {
				//System.out.println("Client (2): Connection Lobby OK  : "+messageLobby);
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
		if (o instanceof PacketUpdateLobby) {
			PacketUpdateLobby updateLobby = (PacketUpdateLobby)o;
			screenGame.getPlayerManageur().getPlayerById(updateLobby.id).setReadyToPlay(updateLobby.localPlayerReady);
		//	netNewServerManageur.getServer().sendToAllExceptTCP(updateLobby.id,updateLobby);
			if (updateLobby.gameStart) {
				System.out.println("CLIENT : updateLobby.gameStart:"+updateLobby.gameStart);
				setGameStart(updateLobby.gameStart);
				netKryoNewClientManageur.startGame();
			}
			if (updateLobby.roundStart) {
				System.out.println("CLIENT : updateLobby.roundStart:"+updateLobby.roundStart);
				setRoundStart(updateLobby.roundStart);
			}	
			
			return true;
		}
		return false;
	}

	@Override
	public void eventPlayerLocalReadyToPlay() {
		/*if (!screenGame.getPlayerManageur().getPlayerLocal().isReadyToPlay()) {*/			
	//		screenGame.getPlayerManageur().getPlayerLocal().setReadyToPlay(true);		
			PacketUpdateLobby packetUpdateLobby = new PacketUpdateLobby();
			packetUpdateLobby.id = screenGame.getPlayerManageur().getPlayerLocal().getId();
			packetUpdateLobby.localPlayerReady = screenGame.getPlayerManageur().getPlayerLocal().isReadyToPlay();
			netKryoNewClientManageur.getClient().sendTCP(packetUpdateLobby);
		//}
		
	}
	
	
	
	
	public boolean isConnectedToLobby() {
		return connectedToLobby;
	}

	public void setConnectedToLobby(boolean connectedToLobby) {
		this.connectedToLobby = connectedToLobby;
	}

	
}
