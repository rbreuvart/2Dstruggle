package com.rbr.game.net.server;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.rbr.game.entity.physics.FabriqueAll;
import com.rbr.game.net.kryo.packet.PacketAddMultiPlayer;
import com.rbr.game.net.kryo.packet.PacketAddPlayer;
import com.rbr.game.net.kryo.packet.PacketRemovePlayer;
import com.rbr.game.net.kryo.packet.PacketUpdateGameObjectPlayer;
import com.rbr.game.net.kryo.packet.RegisterPacket;
import com.rbr.game.player.Player;
import com.rbr.game.player.PlayerControle;
import com.rbr.game.player.PlayerMulti;
import com.rbr.game.screen.game.ScreenGame;
import com.rbr.game.utils.ConfigPref;


public class NetKryoServerManageur extends Listener{
	
	private Server server;
	private Kryo kryo;
	ScreenGame screenGame;
	
	private void registerPackets(){
		kryo = server.getKryo();		
		RegisterPacket.definitionPacket(kryo);
	}
	
	public NetKryoServerManageur(ScreenGame screenGame) throws IOException {
		this.screenGame = screenGame;
		server = new Server();
		registerPackets();
		
		server.addListener(this);
		
		server.bind(ConfigPref.Net_CommunicationPortTCP,ConfigPref.Net_CommunicationPortUDP);
	
		
		server.start();
		
		//creation du player pour le serveur
		Sprite spritePlayer = new Sprite(  screenGame.getMainGame().getManager().get(ConfigPref.File_Vaisseau1,Texture.class));
		PlayerControle playerControle = new PlayerControle(FabriqueAll.creationGameObjectCircle(screenGame.getWorldManageur(), 
				spritePlayer,screenGame.getMapManageur().getRandomSpawn(),"player", 0.45f,ConfigPref.pixelMeter));
		screenGame.getGameObjectManageur().getGameObjectArray().add(playerControle.getGameObject());
		screenGame.getPlayerManageur().addPlayerInMap(0,playerControle);
		
		
	}
	
	public void update(ScreenGame screenGame) {
		
		if (screenGame.getPlayerManageur().getPlayerLocal()!=null) {
			PacketUpdateGameObjectPlayer packetUpdateGameObjectPlayer = new PacketUpdateGameObjectPlayer();
			packetUpdateGameObjectPlayer.id = screenGame.getPlayerManageur().getPlayerLocal().getId();
			packetUpdateGameObjectPlayer.positionX = screenGame.getPlayerManageur().getPlayerLocal().getGameObject().getBody().getPosition().x;
			packetUpdateGameObjectPlayer.positionY = screenGame.getPlayerManageur().getPlayerLocal().getGameObject().getBody().getPosition().y;
			packetUpdateGameObjectPlayer.angle = screenGame.getPlayerManageur().getPlayerLocal().getGameObject().getBody().getAngle();
			
			screenGame.getKryoManageur().getKryoServerManageur().getServer().sendToAllTCP( packetUpdateGameObjectPlayer);
		}
		
	
		
	
	}
	
	public void connected(Connection c){
		
		int idConnectionPlayer = (c.getID());
		Sprite spritePlayerMulti = new Sprite( screenGame.getMainGame().getManager().get(ConfigPref.File_RedCircle,Texture.class));
	
		Vector2 position = screenGame.getMapManageur().getRandomSpawn();
	
		PlayerMulti playerMulti =new PlayerMulti(FabriqueAll.creationGameObjectCircle(screenGame.getWorldManageur(), 
				spritePlayerMulti,position,"playerMulti"+idConnectionPlayer, 0.45f,ConfigPref.pixelMeter));
		screenGame.getGameObjectManageur().getGameObjectArray().add(playerMulti.getGameObject());
		playerMulti.setConnection(c);
		
		
		
		//envoie a tout le monde qui est connecté l'id de la connection et la position du joueur 
		PacketAddPlayer packetAddPlayer = new PacketAddPlayer();
		packetAddPlayer.id = idConnectionPlayer;
		packetAddPlayer.positionSpawnx = position.x;
		packetAddPlayer.positionSpawny = position.y;
		server.sendToTCP(idConnectionPlayer, packetAddPlayer);
		//server.sendToAllExceptTCP(idConnectionPlayer, packetAddPlayer);
		
		//ajoute a tout le monde un player Multi et nouvellement Connecté
		for(Player p : screenGame.getPlayerManageur().getHashMapPlayer().values()){
			PacketAddMultiPlayer packet2 = new PacketAddMultiPlayer();
			packet2.id = p.getId();
			packet2.positionSpawnx = p.getGameObject().getBody().getPosition().x;
			packet2.positionSpawny = p.getGameObject().getBody().getPosition().y;
			if (packet2.id!=packetAddPlayer.id ) {
				c.sendTCP(packet2);
			}
			
		}
		
		//ajout a la MAp des joueurs
		screenGame.getPlayerManageur().addPlayerInMap(idConnectionPlayer, playerMulti);
		System.out.println("Connection received.");
	
	}
	
	public void received(Connection c, Object o){
		if(o instanceof PacketUpdateGameObjectPlayer){
		//	System.out.println("PacketUpdateGameObjectPlayer");
			final PacketUpdateGameObjectPlayer packet = (PacketUpdateGameObjectPlayer) o;
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
					screenGame.getPlayerManageur().getPlayerById(packet.id).getGameObject().getBody().setTransform(new Vector2(packet.positionX, packet.positionY), packet.angle);
					server.sendToAllExceptUDP(packet.id, packet);
				System.out.println("NetKryoServerManageur.sendToAllExceptUDP :"+packet);
				}
			});
			
		}
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

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public Kryo getKryo() {
		return kryo;
	}

	public void setKryo(Kryo kryo) {
		this.kryo = kryo;
	}

}
