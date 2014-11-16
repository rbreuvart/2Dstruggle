package com.rbr.game.net.lobby;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.rbr.game.net.client.NetKryoNewClientManageur;
import com.rbr.game.net.kryo.packet.lobby.PacketMessageLobby;
import com.rbr.game.net.kryo.packet.lobby.PacketUpdateLobby;
import com.rbr.game.player.Player;
import com.rbr.game.screen.game.ScreenGame;
import com.rbr.game.utils.ConfigLobby;

public class LobbyClient extends Lobby{

	private boolean connectedToLobby;//Permet de verifier si la connection du client avec le Serveur et OK 
		
	NetKryoNewClientManageur netKryoNewClientManageur;
	ScreenGame screenGame;
	
	public boolean isConnectedToLobby() {
		return connectedToLobby;
	}	
	public void setConnectedToLobby(boolean connectedToLobby) {
		this.connectedToLobby = connectedToLobby;
	}
	
	public LobbyClient(ScreenGame screenGame,NetKryoNewClientManageur netKryoNewClientManageur) {
		super();
		this.screenGame = screenGame;
		this.netKryoNewClientManageur = netKryoNewClientManageur;
	}
	

	public boolean receivedMessage(Connection c, Object o) {
		
		if (o instanceof PacketMessageLobby) {
			final PacketMessageLobby messageLobby = (PacketMessageLobby)o;
			/**
			 * une fois le Message de Connection au lobby envoyer le serveur envoie ce message de confirmation qui indique que le Client peux crée le Player Local
			 */
			
			if (ConfigLobby.TypeMessageConnectionValideLobby.equals(messageLobby.type)) {
				//System.out.println("Client (2): Connection Lobby OK  : "+messageLobby);
				setConnectedToLobby(true);
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {					
						screenGame.getPlayerManageur().createLocalPlayer(screenGame,messageLobby.idConnection,messageLobby.name,messageLobby.color);
				
					}
				});
			}			
			return true;
		}
		if (o instanceof PacketUpdateLobby) {
			final PacketUpdateLobby updateLobby = (PacketUpdateLobby)o;
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {							
					if (connectedToLobby) {
						Player player = screenGame.getPlayerManageur().getPlayerById(updateLobby.id);
						if (player!=null) {
							player.setReadyToPlay(updateLobby.localPlayerReady);
							
							if (updateLobby.gameStart) {
								//System.out.println("CLIENT : updateLobby.gameStart:"+updateLobby.gameStart);
								setGameStart(updateLobby.gameStart);
								netKryoNewClientManageur.startGame();
							}
							if (updateLobby.roundStart) {
								//System.out.println("CLIENT : updateLobby.roundStart:"+updateLobby.roundStart);
								setRoundStart(updateLobby.roundStart);
							}	
						}
					}					
				}
			});
		//	netNewServerManageur.getServer().sendToAllExceptTCP(updateLobby.id,updateLobby);
			
			
			return true;
		}
		return false;
	}

	@Override
	public void eventPlayerLocalReadyToPlay() {
		/*if (!screenGame.getPlayerManageur().getPlayerLocal().isReadyToPlay()) {*/			
	//		screenGame.getPlayerManageur().getPlayerLocal().setReadyToPlay(true);	
		Gdx.app.postRunnable(new Runnable() {
			@Override
			public void run() {						
				PacketUpdateLobby packetUpdateLobby = new PacketUpdateLobby();
				packetUpdateLobby.id = screenGame.getPlayerManageur().getPlayerLocal().getId();
				packetUpdateLobby.localPlayerReady = screenGame.getPlayerManageur().getPlayerLocal().isReadyToPlay();
				netKryoNewClientManageur.getClient().sendTCP(packetUpdateLobby);
			}
		});
		//}
		
	}
	
	
	
	

	
}
