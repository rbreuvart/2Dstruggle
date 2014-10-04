package com.rbr.game.net.client;

import java.io.IOException;
import java.net.InetAddress;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.rbr.game.net.kryo.packet.PacketAddMultiPlayer;
import com.rbr.game.net.kryo.packet.PacketAddPlayer;
import com.rbr.game.net.kryo.packet.PacketRemovePlayer;
import com.rbr.game.net.kryo.packet.PacketUpdateGameObjectPlayer;
import com.rbr.game.net.kryo.packet.RegisterPacket;
import com.rbr.game.player.PlayerMulti;
import com.rbr.game.screen.game.ScreenGame;
import com.rbr.game.utils.ConfigPref;

public class NetKryoClientManageur extends Listener{
	
	private  Client client;
	private Kryo kryo;
	

	private InetAddress address;
	
	ScreenGame screenGame;
	
	private void registerPackets(){
		kryo = client.getKryo();		
		RegisterPacket.definitionPacket(kryo);
	}
	
	public NetKryoClientManageur(ScreenGame screenGame) {
		this.screenGame = screenGame;
		client = new Client();
		registerPackets();
		address = client.discoverHost(ConfigPref.Net_CommunicationPortUDP, 5000);
		client.addListener(this);
		
		new Thread (client).start();
		
	    System.out.println(address);
	    if(address == null) {
	        //System.exit(0);
	    	System.out.println("Client sans Address");
	    }else{
	    	try {
				client.connect(5000, address, ConfigPref.Net_CommunicationPortTCP, ConfigPref.Net_CommunicationPortUDP);
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
		
	}
	
	
	
	int comp = 0;
	public void update(ScreenGame screenGame ){
		
		if (screenGame.getPlayerManageur().getPlayerLocal() != null) {
			PacketUpdateGameObjectPlayer packetUpdateGameObjectPlayer = new PacketUpdateGameObjectPlayer();
			packetUpdateGameObjectPlayer.id = screenGame.getPlayerManageur().getPlayerLocal().getId();
			packetUpdateGameObjectPlayer.angle = screenGame.getPlayerManageur().getPlayerLocal().getGameObject().getBody().getAngle();
			packetUpdateGameObjectPlayer.positionX = screenGame.getPlayerManageur().getPlayerLocal().getGameObject().getBody().getPosition().x;
			packetUpdateGameObjectPlayer.positionY = screenGame.getPlayerManageur().getPlayerLocal().getGameObject().getBody().getPosition().y;
			client.sendUDP(packetUpdateGameObjectPlayer);
	//		System.out.println("clien sendTCP"+packetUpdateGameObjectPlayer);
		}
			
		
	}
	
	public void received(final Connection c, Object o){
		if(o instanceof PacketAddPlayer){
			//System.out.println("PacketAddPlayer");
			final PacketAddPlayer packet = (PacketAddPlayer) o;
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
					
					screenGame.getPlayerManageur().createLocalPlayer(screenGame,packet.id,new Vector2(packet.positionSpawnx, packet.positionSpawny));
				/*	Sprite spritePlayer = new Sprite(  screenGame.getMainGame().getManager().get(ConfigPref.File_BodyPerso,Texture.class));
					PlayerLocal playerControle = new PlayerLocal(FabriqueAll.creationGameObjectCircle(screenGame.getWorldManageur(), 
							spritePlayer,	new Vector2(packet.positionSpawnx, packet.positionSpawny),"player", 0.45f,ConfigPref.pixelMeter));
					screenGame.getGameObjectManageur().getGameObjectArray().add(playerControle.getGameObject());
					screenGame.getPlayerManageur().addPlayerInMap(packet.id,playerControle);*/
				}
			});
		
			
		}if(o instanceof PacketAddMultiPlayer){
	
			final PacketAddMultiPlayer packet = (PacketAddMultiPlayer) o;
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
					Vector2 position =new Vector2(packet.positionSpawnx, packet.positionSpawny);
					//System.out.println(position);
					//Sprite spritePlayer = new Sprite(  screenGame.getMainGame().getManager().get(ConfigPref.File_RedCircle,Texture.class));
				
//					PlayerMulti playerControle = new PlayerMulti(FabriqueAll.creationGameObjectCircle(screenGame.getWorldManageur(), 
//							spritePlayer,	new Vector2(0, 0) ,"playerMulti", 0.45f,ConfigPref.pixelMeter));
					PlayerMulti playerControle = screenGame.getPlayerManageur().createMultiPlayer(screenGame, c, position);
					
					screenGame.getGameObjectManageur().getGameObjectArray().add(playerControle.getGameObject());
					screenGame.getPlayerManageur().addPlayerInMap(packet.id,playerControle);
					
					playerControle.getGameObject().getBody().setTransform(position, 0);
				}
			});
			
			
		//	createMultiPlayer(packet);
		//	MPPlayer newPlayer = new MPPlayer();
			
		//	ClientProgram.players.put(packet.id, newPlayer);
			
		}else if(o instanceof PacketRemovePlayer){
			
			final PacketRemovePlayer packet = (PacketRemovePlayer) o;
		//	ClientProgram.players.remove(packet.id);
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
					screenGame.getWorldManageur().getWorld().destroyBody(screenGame.getPlayerManageur().getPlayerById(packet.id).getGameObject().getBody());
					screenGame.getGameObjectManageur().getGameObjectArray().removeValue(screenGame.getPlayerManageur().getPlayerById(packet.id).getGameObject(), false);
					screenGame.getPlayerManageur().removePlayerById(packet.id);
				}
			});
		
			
		}else if(o instanceof PacketUpdateGameObjectPlayer){
			
			final PacketUpdateGameObjectPlayer packet = (PacketUpdateGameObjectPlayer) o;
			//System.out.println("Client"+packet);
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
					Vector2 newPosition = new Vector2(packet.positionX, packet.positionY);
					Body bodyPlayer = screenGame.getPlayerManageur().getPlayerById(packet.id).getGameObject().getBody();
					Vector2 correctPosition = new Vector2(bodyPlayer.getPosition());
					if (newPosition.x != bodyPlayer.getPosition().x ) {
						correctPosition.x = newPosition.x;
					}
					if (newPosition.y != bodyPlayer.getPosition().y) {
						correctPosition.y = newPosition.y;
					}
					bodyPlayer.setTransform(correctPosition, packet.angle);
				/*
					bodyPlayer.setLinearDamping(0);
					bodyPlayer.setAngularDamping(0);
					bodyPlayer.setAngularVelocity(0);
					bodyPlayer.setLinearVelocity(new Vector2(0,0));*/
				}
			});
			
		}
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Kryo getKryo() {
		return kryo;
	}

	public void setKryo(Kryo kryo) {
		this.kryo = kryo;
	}

	public InetAddress getAddress() {
		return address;
	}

	public void setAddress(InetAddress address) {
		this.address = address;
	}

	
}
