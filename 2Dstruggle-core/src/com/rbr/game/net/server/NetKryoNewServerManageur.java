package com.rbr.game.net.server;

import java.io.IOException;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.rbr.game.net.kryo.packet.PacketInfoServeur;
import com.rbr.game.net.kryo.packet.RegisterPacket;
import com.rbr.game.net.kryo.packet.player.PacketAddMultiPlayer;
import com.rbr.game.net.kryo.packet.player.PacketRemovePlayer;
import com.rbr.game.net.kryo.packet.player.PacketUpdateGameObjectPlayer;
import com.rbr.game.net.lobby.LobbyServer;
import com.rbr.game.player.Player;
import com.rbr.game.screen.game.ScreenGame;
import com.rbr.game.utils.ConfigPref;

public class NetKryoNewServerManageur extends Listener{
	
	
	private Server server;
	private Kryo kryo;
	ScreenGame screenGame;
	
	
	
	
	private String mapServeur;//Maya
	private String serveurType;//Lan ,....
	private String mapType;//deathMatch
	private int maxPlayerSlot;//64
	private int currentPlayerSlot;//2	
	private String serveurPassword;
	
	
	
	
	private LobbyServer lobbyServer;
	
	
	
	private void registerPackets(){
		kryo = server.getKryo();		
		RegisterPacket.definitionPacket(kryo);
	}
	
	public NetKryoNewServerManageur(ScreenGame screenGame) throws IOException {
		this.screenGame = screenGame;
		System.out.println("------SERVEUR------");
		mapServeur = "maya";
		mapType = "deathmatch";
		serveurType = "LAN";
		maxPlayerSlot = 32;
		currentPlayerSlot = 0;
		serveurPassword = "";
		
		server = new Server();
		registerPackets();
		
		server.addListener(this);
		
		server.bind(ConfigPref.Net_CommunicationPortTCP,ConfigPref.Net_CommunicationPortUDP);
		
		server.start(); 
		
		setLobbyServer(new LobbyServer(screenGame, this));
		
	
		
		//creation du player pour le serveur
	//	screenGame.getPlayerManageur().createLocalPlayer(screenGame,0,screenGame.getMapManageur().getRandomSpawn());
		
	}
	public void update(ScreenGame screenGame) {
		if (getLobbyServer().isRoundStart()) {
			//Maj des joueurs
			if (screenGame.getPlayerManageur().getPlayerLocal()!=null) {
				PacketUpdateGameObjectPlayer packetUpdateGameObjectPlayer = new PacketUpdateGameObjectPlayer();
				packetUpdateGameObjectPlayer.id = screenGame.getPlayerManageur().getPlayerLocal().getId();
				packetUpdateGameObjectPlayer.positionX = screenGame.getPlayerManageur().getPlayerLocal().getGameObject().getBody().getPosition().x;
				packetUpdateGameObjectPlayer.positionY = screenGame.getPlayerManageur().getPlayerLocal().getGameObject().getBody().getPosition().y;
				packetUpdateGameObjectPlayer.angle = screenGame.getPlayerManageur().getPlayerLocal().getGameObject().getBody().getAngle();
					
				screenGame.getKryoManageur().getKryoServerManageur().getServer().sendToAllTCP( packetUpdateGameObjectPlayer);
			}
			
			//Maj des Projectiles
			
			
		}
		
		
	}
	
	public void connected(Connection c){
		
		int idConnectionPlayer = (c.getID());
	
		PacketInfoServeur infoServeur = new PacketInfoServeur();
		infoServeur.id = idConnectionPlayer;
		infoServeur.mapServeur = getMapServeur();
		infoServeur.serveurType = getServeurType();
		infoServeur.mapType = getMapType();
		infoServeur.maxPlayerSlot = getMaxPlayerSlot();
		infoServeur.currentPlayerSlot = getCurrentPlayerSlot();
		infoServeur.requirePassword = false;
		infoServeur.autorisation = true;
		
		c.sendTCP(infoServeur);
		
		
	}
	
	public void received(Connection c, Object o){
		for (int i = 0; i < 1; i++) {
			
			if (projectileHandler(c,o)){break;}
			
			if (playerHandler(c,o)){break;}
					
			if (commandHandler(c,o)){break;}
			
			if (lobbyHandler(c,o)) {break;}
			
			//if (infoServeurHandler(c,o)){break;}
			
		}
	}
	
	
	
	
	private boolean commandHandler(Connection c, Object o){
		
		return false;
	}
	
	private boolean lobbyHandler(Connection c, Object o){			
		return lobbyServer.receivedMessage(c, o);
	}
	

	
	private boolean playerHandler(final Connection c, Object o){
		if(o instanceof PacketUpdateGameObjectPlayer){
			final PacketUpdateGameObjectPlayer packet = (PacketUpdateGameObjectPlayer) o;
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
					screenGame.getPlayerManageur().getPlayerById(packet.id).getGameObject().getBody().setTransform(new Vector2(packet.positionX, packet.positionY), packet.angle);
				
					for (Entry<Integer,Player> entry : screenGame.getPlayerManageur().getHashMapPlayer().entrySet()) {
						if (entry.getKey()!=packet.id && entry.getKey() != screenGame.getPlayerManageur().getPlayerLocal().getId()) {
							server.sendToUDP(packet.id, packet);
						}
					}					
				}
			});
			return true;
		}
		return false;
	}
	
	
	private boolean projectileHandler(Connection c, Object o){
		
		return false;
	}
	
	
	public void disconnected(final Connection c){		
		Gdx.app.postRunnable(new Runnable() {
			@Override
			public void run() {
				int id = c.getID();
				screenGame.getWorldManageur().getWorld().destroyBody(screenGame.getPlayerManageur().getPlayerById(id).getGameObject().getBody());
				screenGame.getGameObjectManageur().getGameObjectArray().removeValue(screenGame.getPlayerManageur().getPlayerById(id).getGameObject(), false);
			
				screenGame.getPlayerManageur().removePlayerById(id);
				
				PacketRemovePlayer packet = new PacketRemovePlayer();		
				packet.id = c.getID();
				server.sendToAllExceptTCP(c.getID(), packet);
				System.out.println("Connection dropped.");
			}
		});
		
	}

	/**
	 * parcout tout les jouer pour definir tout les joueur sur le serveur
	 * sauf pour le serveur lui meme
	 */
	public void actualisePlayersForAll(){
		System.out.println(" ---- actualisePlayersForAll ---- ");
		for (Entry<Integer,Player> playerEntryConnection : screenGame.getPlayerManageur().getHashMapPlayer().entrySet()) {
			for (Entry<Integer,Player> playerEntryPlayer : screenGame.getPlayerManageur().getHashMapPlayer().entrySet()) {
				if (playerEntryConnection.getKey()!=0) {//ne pas envoie au serveur lui meme
					PacketAddMultiPlayer addMultiPlayer = new PacketAddMultiPlayer();
					addMultiPlayer.id = playerEntryPlayer.getKey();
					System.out.println("Serveur : envoie a CLIENT["+playerEntryConnection.getKey()+"] ca "+addMultiPlayer);
					playerEntryConnection.getValue().getConnection().sendTCP(addMultiPlayer);
				}
			}
		}
	}
	
	
	
	public Server getServer() {return server;	}
	public void setServer(Server server) {this.server = server;	}
	public Kryo getKryo() {return kryo;	}
	public void setKryo(Kryo kryo) {this.kryo = kryo;	}
	public String getServeurType() {return serveurType;	}
	public void setServeurType(String serveurType) {this.serveurType = serveurType;}
	public String getMapType() {return mapType;}
	public void setMapType(String mapType) {this.mapType = mapType;}
	public String getMapServeur() {return mapServeur;}
	public void setMapServeur(String mapServeur) {this.mapServeur = mapServeur;	}	
	public int getMaxPlayerSlot() {	return maxPlayerSlot;}
	public void setMaxPlayerSlot(int maxPlayerSlot) {this.maxPlayerSlot = maxPlayerSlot;}
	public int getCurrentPlayerSlot() {return currentPlayerSlot;}
	public void setCurrentPlayerSlot(int currentPlayerSlot) {this.currentPlayerSlot = currentPlayerSlot;}
	public String getServeurPassword() {return serveurPassword;}
	public void setServeurPassword(String serveurPassword) {this.serveurPassword = serveurPassword;}
	public LobbyServer getLobbyServer() {return lobbyServer;}
	public void setLobbyServer(LobbyServer lobbyServer) {this.lobbyServer = lobbyServer;}
	
}
