package com.rbr.game.net.lobby;

import java.util.Map.Entry;

import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryonet.Connection;
import com.rbr.game.net.kryo.packet.lobby.PacketMessageLobby;
import com.rbr.game.net.server.NetKryoNewServerManageur;
import com.rbr.game.player.Player;
import com.rbr.game.screen.game.ScreenGame;
import com.rbr.game.utils.ConfigLobby;

public class LobbyServer {
	
	private Array<PacketMessageLobby> arrayMessage;
	private boolean roundStart;

	NetKryoNewServerManageur netNewServerManageur;
	ScreenGame screenGame;
	
	public LobbyServer(ScreenGame screenGame,NetKryoNewServerManageur netNewServerManageur) {
		setArrayMessage(new Array<PacketMessageLobby>());
		this.netNewServerManageur = netNewServerManageur;
		this.screenGame = screenGame;
		setRoundStart(false);
	}

	public boolean receivedMessage(Connection c, Object o){
		if (o instanceof PacketMessageLobby) {
			PacketMessageLobby messageLobby = (PacketMessageLobby)o;
			
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
				c.sendTCP(packetMessageLobby);
				
				if (screenGame.getPlayerManageur().getPlayerLocal()==null) {
					System.out.println("Serveur : creation du joueur local ");
					screenGame.getPlayerManageur().createLocalPlayer(screenGame, 0);
				}
				
				System.out.println("Serveur : creation du joueur Multi ");
				screenGame.getPlayerManageur().createMultiPlayer(screenGame, c,c.getID());
				
				
				//actualise a tout les Clients les joueurs Connecté
				netNewServerManageur.actualisePlayersForAll();
				
			}else if(ConfigLobby.TypeMessageConnectionValideLobby.equals(messageLobby.type)){
				System.err.println("Message de TypeMessageConnectionValideLobby non conforme");
			}else{
				addMessageInLobby(c,messageLobby);
			}
			
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
	
	public boolean isRoundStart() {
		return roundStart;
	}
	public void setRoundStart(boolean roundStart) {
		this.roundStart = roundStart;
	}
	public Array<PacketMessageLobby> getArrayMessage() {
		return arrayMessage;
	}
	public void setArrayMessage(Array<PacketMessageLobby> arrayMessage) {
		this.arrayMessage = arrayMessage;
	}
	
}
