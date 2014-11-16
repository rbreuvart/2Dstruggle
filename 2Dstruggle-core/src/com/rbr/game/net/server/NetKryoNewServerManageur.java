package com.rbr.game.net.server;

import java.io.IOException;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.rbr.game.entity.physics.FabriqueAll;
import com.rbr.game.entity.physics.GameObject;
import com.rbr.game.entity.projectile.Projectile;
import com.rbr.game.net.kryo.packet.PacketInfoServeur;
import com.rbr.game.net.kryo.packet.RegisterPacket;
import com.rbr.game.net.kryo.packet.lobby.PacketUpdateLobby;
import com.rbr.game.net.kryo.packet.player.PacketAddMultiPlayer;
import com.rbr.game.net.kryo.packet.player.PacketRemovePlayer;
import com.rbr.game.net.kryo.packet.player.PacketSpawnPlayer;
import com.rbr.game.net.kryo.packet.player.PacketUpdateGameObjectPlayer;
import com.rbr.game.net.kryo.packet.projectile.PacketAddProjectile;
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
	private String mapType;//FFA,2TEAM,COOP
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
		mapServeur = screenGame.getMapManageur().getFileMapAsset();
		mapType = screenGame.getMapManageur().getTypeMap();
		serveurType = "LAN";
		maxPlayerSlot = 9999;
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
	
	
	public void update(ScreenGame screenGame,float delta) {
		if (getLobbyServer().isRoundStart()) {
			
			updatePlayer(screenGame, delta);
			updateProjectile(screenGame, delta);
		}
		
		lobbyServer.updateLobby(screenGame,delta);
	}
	
	public void updatePlayer(ScreenGame screenGame,float delta){
		//Maj des joueurs
		if (screenGame.getPlayerManageur().getPlayerLocal()!=null) {
			
			for (Entry<Integer,Player> playerEntryConnection : screenGame.getPlayerManageur().getHashMapPlayer().entrySet()) {
				
				if (playerEntryConnection.getValue().getLife()<=0) {
					System.out.println("a respawn "+playerEntryConnection.getValue());
					//playerEntryConnection.getValue().setNeedRespawn(false);
					playerEntryConnection.getValue().setLife(playerEntryConnection.getValue().getLifeMax());
					playerEntryConnection.getValue().getGameObject().getBody().setLinearVelocity(new Vector2());
					playerEntryConnection.getValue().getGameObject().getBody().setLinearDamping(0);
					playerEntryConnection.getValue().getGameObject().getBody().setAngularVelocity(0);
					playerEntryConnection.getValue().getGameObject().getBody().setTransform(screenGame.getMapManageur().getRandomSpawn(), 0);
					
					PacketSpawnPlayer spawnPlayer = new PacketSpawnPlayer();
					spawnPlayer.id = playerEntryConnection.getValue().getId();
					spawnPlayer.positionX = playerEntryConnection.getValue().getGameObject().getBody().getPosition().x;
					spawnPlayer.positionY = playerEntryConnection.getValue().getGameObject().getBody().getPosition().y;
					spawnPlayer.spawn = true;
					server.sendToTCP(spawnPlayer.id, spawnPlayer);
				}
				
				PacketUpdateGameObjectPlayer packetUpdateGameObjectPlayer = new PacketUpdateGameObjectPlayer();
				packetUpdateGameObjectPlayer.id =	playerEntryConnection.getValue().getId();
				packetUpdateGameObjectPlayer.positionX = playerEntryConnection.getValue().getGameObject().getBody().getPosition().x;
				packetUpdateGameObjectPlayer.positionY = playerEntryConnection.getValue().getGameObject().getBody().getPosition().y;
				packetUpdateGameObjectPlayer.angle = playerEntryConnection.getValue().getGameObject().getBody().getAngle();
				packetUpdateGameObjectPlayer.life = playerEntryConnection.getValue().getLife();
				packetUpdateGameObjectPlayer.spawn = playerEntryConnection.getValue().isSpawn();
				server.sendToAllTCP(packetUpdateGameObjectPlayer);
			}
				
			
		}
	}
	public void updateProjectile(ScreenGame screenGame,float delta){
		//Maj des Projectiles
		for (GameObject go : screenGame.getGameObjectManageur().getGameObjectArray()) {
			if (go instanceof Projectile) {
				Projectile proj =  (Projectile)go;
				if (!proj.isEnvoyerToClient()) {
					
					PacketAddProjectile packetAddProjectile = new PacketAddProjectile();
					packetAddProjectile.idConnection = proj.getPlayerEmeteur().getId();
					packetAddProjectile.idEmeteur = proj.getPlayerEmeteur().getId();
					packetAddProjectile.idGameObject = proj.getIdArray();
					packetAddProjectile.classProjectile = proj.getClass().getName();
					
					packetAddProjectile.degat = proj.getDegat();
					
					packetAddProjectile.positionX = proj.getBody().getPosition().x;
					packetAddProjectile.positionY = proj.getBody().getPosition().y;
					packetAddProjectile.velocityX = proj.getBody().getLinearVelocity().x;
					packetAddProjectile.velocotyY = proj.getBody().getLinearVelocity().y;
					
					for (Entry<Integer,Player> playerEntryConnection : screenGame.getPlayerManageur().getHashMapPlayer().entrySet()) {				
						if (playerEntryConnection.getValue().getId()!=packetAddProjectile.idConnection) {
							server.sendToTCP(playerEntryConnection.getValue().getId(),packetAddProjectile);							
						}
					}
					proj.setEnvoyerToClient(true);
					proj.setEnvoyerToServer(true);
				}
				/*if (proj.isEnvoyerToServeur()) {
					
					//TODO update
					break;
				}*//*
				if (proj.isRemove()) {
					PacketRemoveProjectile packetRemoveProjectile = new PacketRemoveProjectile();
					packetRemoveProjectile.idConnection = screenGame.getPlayerManageur().getPlayerLocal().getId();
					packetRemoveProjectile.idEmeteur = screenGame.getPlayerManageur().getPlayerLocal().getId();
					packetRemoveProjectile.idGameObject = proj.getIdArray();
					server.sendToAllExceptTCP(packetRemoveProjectile.idConnection,packetRemoveProjectile);
				}*/
			
			}		
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
					screenGame.getPlayerManageur().getPlayerById(packet.id)
													.getGameObject()
													.getBody()
													.setTransform(new Vector2(packet.positionX, packet.positionY), packet.angle);
					for (Entry<Integer,Player> entry : screenGame.getPlayerManageur()
																.getHashMapPlayer()
																.entrySet()) {
						if (entry.getKey()!=packet.id && entry.getKey() != screenGame.getPlayerManageur()
																						.getPlayerLocal()
																						.getId()) {
							server.sendToAllUDP( packet);
						}
					}					
				}
			});
			return true;
		}
		return false;
	}
	
	
	private boolean projectileHandler(Connection c, Object o){
		
		if (o instanceof PacketAddProjectile) {
			final PacketAddProjectile addProjectile = (PacketAddProjectile)o;
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {	
					Projectile projectile = FabriqueAll.creationProjectile(screenGame, new Vector2(addProjectile.positionX, addProjectile.positionY), new Vector2(addProjectile.velocityX,addProjectile.velocotyY), screenGame.getPlayerManageur().getPlayerById(addProjectile.idEmeteur), addProjectile.degat);
					projectile.setEnvoyerToServer(true);
					projectile.setEnvoyerToClient(false);
					screenGame.getGameObjectManageur().add(addProjectile.idConnection,projectile);
				}
			});
			return true;
		}/*
		if (o instanceof PacketRemoveProjectile) {
			//TODO
			
			return true;
		}*/
		
		return false;
	}
	
	
	public void disconnected(final Connection c){		
		Gdx.app.postRunnable(new Runnable() {
			@Override
			public void run() {
				try {
				int id = c.getID();
				screenGame.getWorldManageur().getWorld().destroyBody(screenGame.getPlayerManageur().getPlayerById(id).getGameObject().getBody());
				//FIXME ont ne suppique les Objets de la GameOject que si la partie a commencé
				screenGame.getGameObjectManageur().removeByGameObject(screenGame.getPlayerManageur().getPlayerById(id).getGameObject());
			
				screenGame.getPlayerManageur().removePlayerById(id);
				
				PacketRemovePlayer packet = new PacketRemovePlayer();		
				packet.id = c.getID();
				server.sendToAllExceptTCP(c.getID(), packet);
				System.out.println("Connection dropped.");
					
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
		});
		
	}

	/**
	 * parcours tout les joueurs pour definir tout les joueurs connecté sur le serveur
	 * sauf pour le serveur lui meme
	 */
	public void actualiseListPlayersForAll(){
		System.out.println(" ---- actualisePlayersForAll ---- ");
		for (Entry<Integer,Player> playerEntryConnection : screenGame.getPlayerManageur().getHashMapPlayer().entrySet()) {
			for (Entry<Integer,Player> playerEntryPlayer : screenGame.getPlayerManageur().getHashMapPlayer().entrySet()) {
				if (playerEntryConnection.getKey()!=0) {//ne pas envoie au serveur lui meme
					PacketAddMultiPlayer addMultiPlayer = new PacketAddMultiPlayer();
					addMultiPlayer.id = playerEntryPlayer.getKey();
					addMultiPlayer.name = playerEntryPlayer.getValue().getName();
					addMultiPlayer.color = playerEntryPlayer.getValue().getColor().toString().toUpperCase();
					
					System.out.println("Serveur : envoie a CLIENT["+playerEntryConnection.getKey()+"] ca "+addMultiPlayer);
					playerEntryConnection.getValue().getConnection().sendTCP(addMultiPlayer);
				}
			}
		}
		System.out.println(" ---- --------------------- ---- ");
	}
	/**
	 * appelé quand les joueurs sont pret et que le bouton est appuillé
	 */
	public void startGame() {
		//FIXME pour debug SOLO
		if (screenGame.getPlayerManageur().getPlayerLocal()==null) {
			screenGame.getPlayerManageur().createLocalPlayer(screenGame, 0,"SERVEUR TEST SOLO","FFFFFF");
		}
		
		//Definition des boolean Game Start
		screenGame.getPlayerManageur().getPlayerLocal().setReadyToPlay(true);
		lobbyServer.setRoundStart(true);
		lobbyServer.setGameStart(true);
		
		//envoie a tout le monde l'information que la partie Commence
		PacketUpdateLobby packetUpdateLobby = new PacketUpdateLobby();
		packetUpdateLobby.id = screenGame.getPlayerManageur().getPlayerLocal().getId();
		packetUpdateLobby.gameStart = true;
		packetUpdateLobby.roundStart = true;	
		packetUpdateLobby.localPlayerReady = true;
		server.sendToAllTCP(packetUpdateLobby);
		
		
		
		//cache l'interface du HUD
		screenGame.getHudManageur().getTableStartGame().setVisible(false);
		screenGame.getHudManageur().getWindowListPlayer().setVisible(false);

		
		spawnAllPlayer();
	}
	
	public void spawnAllPlayer(){
		if ("FFA".equals(mapType)) {
			System.out.println("SERVEUR:  Spawn for FFA");
			//Definition des Spawn pour tout les Joueurs
			for (Entry<Integer,Player> playerEntry : screenGame.getPlayerManageur().getHashMapPlayer().entrySet()) {
				Vector2 position = screenGame.getMapManageur().getRandomSpawn();
				screenGame.getPlayerManageur().spawnPlayer(screenGame, playerEntry.getValue().getId(), position);
				//playerEntry.getValue().getGameObject().getBody().setTransform(position, 0);
				
			}
			
			for (Entry<Integer,Player> playerEntryPlayer : screenGame.getPlayerManageur().getHashMapPlayer().entrySet()) {
				
				PacketSpawnPlayer packetSpawnPlayer = new PacketSpawnPlayer();
				packetSpawnPlayer.id = playerEntryPlayer.getKey();
				packetSpawnPlayer.positionX = playerEntryPlayer.getValue().getGameObject().getBody().getPosition().x;
				packetSpawnPlayer.positionY = playerEntryPlayer.getValue().getGameObject().getBody().getPosition().y;
				packetSpawnPlayer.spawn = true;
				server.sendToAllExceptTCP(0, packetSpawnPlayer);//ne pas envoie au serveur lui meme
			}				
		}else{
			System.out.println("Server: Mode de Heu non Pris en Charge");
		}
	}
	
	public void spawnPlayer(Player player) {
		Vector2 position = screenGame.getMapManageur().getRandomSpawn();
		screenGame.getPlayerManageur().spawnPlayer(screenGame, player.getId(), position);
		
		PacketSpawnPlayer packetSpawnPlayer = new PacketSpawnPlayer();
		packetSpawnPlayer.id = player.getId();
		packetSpawnPlayer.positionX = player.getGameObject().getBody().getPosition().x;
		packetSpawnPlayer.positionY = player.getGameObject().getBody().getPosition().y;
		packetSpawnPlayer.spawn = true;
		server.sendToAllExceptTCP(0, packetSpawnPlayer);
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
