package com.rbr.game.net.lobby;

import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.rbr.game.net.kryo.packet.lobby.PacketMessageLobby;
import com.rbr.game.net.kryo.packet.lobby.PacketUpdateLobby;
import com.rbr.game.net.server.NetKryoNewServerManageur;
import com.rbr.game.player.Player;
import com.rbr.game.screen.game.ScreenGame;
import com.rbr.game.utils.ConfigLobby;
import com.rbr.game.utils.GameCodeTools;

public class LobbyServer extends Lobby {
	

	NetKryoNewServerManageur netNewServerManageur;
	ScreenGame screenGame;
	
	public LobbyServer(ScreenGame screenGame,NetKryoNewServerManageur netNewServerManageur) {
		this.netNewServerManageur = netNewServerManageur;
		this.screenGame = screenGame;
	
	}

	public boolean receivedMessage(final Connection c, final Object o){
		/*
		 * le Serveur recoie la demande de connection du client et la valide
		 */
		if (o instanceof PacketMessageLobby) {
			final PacketMessageLobby messageLobby = (PacketMessageLobby)o;
			
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {	
					if (ConfigLobby.TypeMessageConnectionLobby.equals(messageLobby.type)) {

						System.out.println("SERVER : "+messageLobby);
						PacketMessageLobby packetMessageLobby = new PacketMessageLobby();
						packetMessageLobby.idConnection = c.getID();
						packetMessageLobby.name = messageLobby.name;
						packetMessageLobby.type = ConfigLobby.TypeMessageConnectionValideLobby;
						packetMessageLobby.message = "";
						packetMessageLobby.heure = 0;
						packetMessageLobby.minute = 0;
						packetMessageLobby.seconde = 0;
						packetMessageLobby.color = messageLobby.color;
						c.sendTCP(packetMessageLobby);
						
						if (screenGame.getPlayerManageur().getPlayerLocal()==null) {
							System.out.println("Serveur : creation du joueur local ");
							screenGame.getPlayerManageur().createLocalPlayer(screenGame, 0,"PLAYER SERVEUR",GameCodeTools.getRandomColor());// ici est Defini le Nom du serveur
						}
						
						System.out.println("Serveur : creation du joueur Multi ");
						screenGame.getPlayerManageur().createMultiPlayer(screenGame, c,c.getID(),messageLobby.name,messageLobby.color);//ici est defini le Nom du client dans le Serveur
						
						
						//actualise a tout les Clients les joueurs Connecté
						netNewServerManageur.actualiseListPlayersForAll();// envoie a tout le monde les information du nouveau Player
						
					}else if(ConfigLobby.TypeMessageConnectionValideLobby.equals(messageLobby.type)){
						System.err.println("Message de TypeMessageConnectionValideLobby non conforme");
					}else{
						addMessageInLobby(c,messageLobby);
					}
				}					
			});
			return true;
		}
		if (o instanceof PacketUpdateLobby) {
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {	
					PacketUpdateLobby updateLobby = (PacketUpdateLobby)o;
					screenGame.getPlayerManageur().getPlayerById(updateLobby.id).setReadyToPlay(updateLobby.localPlayerReady);
					netNewServerManageur.getServer().sendToAllExceptTCP(updateLobby.id,updateLobby);
				}					
			});
		
			return true;
		}
		return false;
	}
	
	/**
	 * ajoute le message a la list et si le message n'etait pas deja la on envoie aux autres
	 * 
	 * @param messageLobby
	 */
	public void addMessageInLobby(Connection c ,PacketMessageLobby messageLobby){
	/*	for (PacketMessageLobby packetMessageLobby : arrayMessage) {
			//FIXME faire les verifications
			break;
		}*/
		
		sendMessageToOther(c, messageLobby);
		
	}
	
	public void sendMessageToOther(Connection c , PacketMessageLobby messageLobby){
		for (Entry<Integer, Player> playerEntry : screenGame.getPlayerManageur().getHashMapPlayer().entrySet()) {
			//System.out.println("playerentry"+playerEntry.getValue());
			if (c.getID()!=playerEntry.getKey()) {
				playerEntry.getValue().getConnection().sendTCP(messageLobby);
			}
		}
	}

	@Override
	public void eventPlayerLocalReadyToPlay() {
		Gdx.app.postRunnable(new Runnable() {
			@Override
			public void run() {	
				PacketUpdateLobby packetUpdateLobby = new PacketUpdateLobby();
				packetUpdateLobby.id = screenGame.getPlayerManageur().getPlayerLocal().getId();
				packetUpdateLobby.localPlayerReady = screenGame.getPlayerManageur().getPlayerLocal().isReadyToPlay();
				netNewServerManageur.getServer().sendToAllTCP(packetUpdateLobby);
			}					
		});
	
	}

	
	float timeLobbyState=0f; 
	public void updateLobby(ScreenGame screenGame,float delta){
		timeLobbyState+=delta;
		if(timeLobbyState>=3f){			
			timeLobbyState=0f; 
			
			updateLobbyState(screenGame, delta);
		}
	}
	
	/**
	 * pour chaque player on envoie la situation du lobby de chaque joueur
	 * @param screenGame
	 * @param delta
	 */
	private void updateLobbyState(final ScreenGame screenGame,float delta){
		Gdx.app.postRunnable(new Runnable() {
			@Override
			public void run() {	
				for (Entry<Integer, Player> playerEntry : screenGame.getPlayerManageur().getHashMapPlayer().entrySet()) {
					if (isGameStart()&&isRoundStart()&&!playerEntry.getValue().isSpawn()) {
						netNewServerManageur.spawnPlayer(playerEntry.getValue());
					}
					PacketUpdateLobby packetUpdateLobby = new PacketUpdateLobby();
					packetUpdateLobby.id = playerEntry.getValue().getId();
					packetUpdateLobby.localPlayerReady = playerEntry.getValue().isReadyToPlay();
					packetUpdateLobby.gameStart = isGameStart();
					packetUpdateLobby.roundStart = isRoundStart();
					netNewServerManageur.getServer().sendToAllExceptTCP(packetUpdateLobby.id, packetUpdateLobby);
				}
			}					
		});
		
	}
	
	
}
