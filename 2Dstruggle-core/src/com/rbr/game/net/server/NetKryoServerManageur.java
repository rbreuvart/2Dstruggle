package com.rbr.game.net.server;

import java.io.IOException;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.rbr.game.entity.physics.FabriqueAll;
import com.rbr.game.net.kryo.packet.PacketAddPlayer;
import com.rbr.game.net.kryo.packet.PacketRemovePlayer;
import com.rbr.game.net.kryo.packet.PacketUpdateGameObjectPlayer;
import com.rbr.game.player.PlayerMulti;
import com.rbr.game.screen.game.ScreenGame;
import com.rbr.game.utils.ConfigPref;


public class NetKryoServerManageur extends Listener{
	
	Server server;
	Kryo kryo;
	ScreenGame screenGame;
	
	private void registerPackets(){
		kryo = server.getKryo();		
		kryo.register(PacketAddPlayer.class);
		kryo.register(PacketRemovePlayer.class);
		kryo.register(PacketUpdateGameObjectPlayer.class);	
	}
	
	public NetKryoServerManageur(ScreenGame screenGame) throws IOException {
		this.screenGame = screenGame;
		server = new Server();
		registerPackets();
		
		server.addListener(this);
		
		server.bind(ConfigPref.Net_CommunicationPortTCP,ConfigPref.Net_CommunicationPortUDP);
	
		
		server.start();
	}
	
	public void update(ScreenGame screenGame) {
		
	}
	
	public void connected(Connection c){		
		createMultiPlayerServer(screenGame,c);
	}
	
	private void createMultiPlayerServer(ScreenGame screenGame,Connection c){
		Sprite spritePlayerMulti = new Sprite(  screenGame.getMainGame().getManager().get(ConfigPref.File_RedCircle,Texture.class));
	
		Vector2 position = screenGame.getMapManageur().getRandomSpawn();
	
		PlayerMulti playerMulti =new PlayerMulti(FabriqueAll.creationGameObjectCircle(screenGame.getWorldManageur(), 
				spritePlayerMulti,position,"player", 0.45f,ConfigPref.pixelMeter));
		
		//ajout a la MAp
		int idConnectionPlayer = (c.getID()+1);
		screenGame.getPlayerManageur().addPlayerInMap(idConnectionPlayer, playerMulti);
		
		//envoie a tout le monde qui est connecté l'id de la connection et la position du joueur 
		PacketAddPlayer packetAddPlayer = new PacketAddPlayer();
		packetAddPlayer.id = idConnectionPlayer;
		packetAddPlayer.positionSpawnx = position.x;
		packetAddPlayer.positionSpawny = position.y;
		server.sendToAllExceptTCP(idConnectionPlayer, packetAddPlayer);
		
	}
	


}
