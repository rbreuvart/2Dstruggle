package com.rbr.game.net.client;
/*
import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.rbr.game.net.kryo.packet.RegisterPacket;
import com.rbr.game.net.kryo.packet.player.PacketAddMultiPlayer;
import com.rbr.game.net.kryo.packet.player.PacketAddPlayer;
import com.rbr.game.net.kryo.packet.player.PacketRemovePlayer;
import com.rbr.game.net.kryo.packet.player.PacketUpdateGameObjectPlayer;
import com.rbr.game.player.PlayerMulti;
import com.rbr.game.screen.game.ScreenGame;
import com.rbr.game.utils.ConfigPref;

public class NetKryoClientManageur extends Listener{
	
	private  Client client;
	private Kryo kryo;
	

	InetAddress address;
	
	ScreenGame screenGame;
	
	private void registerPackets(){
		kryo = client.getKryo();		
		RegisterPacket.definitionPacket(kryo);
	}
	public NetKryoClientManageur(ScreenGame screenGame,String ip) {
	
		this.screenGame = screenGame;
		client = new Client();
		registerPackets();
		client.addListener(this);
		
		new Thread (client).start();
	
		System.out.println("NetKryoClientManageur :"+ip);
    	try {
			client.connect(5000, ip, ConfigPref.Net_CommunicationPortTCP, ConfigPref.Net_CommunicationPortUDP);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	}
	public NetKryoClientManageur(ScreenGame screenGame) {
		this.screenGame = screenGame;
		client = new Client();
		registerPackets();
		address = client.discoverHost(ConfigPref.Net_CommunicationPortUDP,  ConfigPref.Net_CommunicationPortUDP);
		client.addListener(this);
		
		new Thread (client).start();
		
	    if(address == null) {
	        //System.exit(0);
	    }else{
	    	System.out.println("NetKryoClientManageur :"+address);
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
			final PacketAddPlayer packet = (PacketAddPlayer) o;
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {					
					screenGame.getPlayerManageur().createLocalPlayer(screenGame,packet.id,new Vector2(packet.positionSpawnx, packet.positionSpawny));
			
				}
			});
		
			
		}if(o instanceof PacketAddMultiPlayer){
	
			final PacketAddMultiPlayer packet = (PacketAddMultiPlayer) o;
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
					Vector2 position =new Vector2(packet.positionSpawnx, packet.positionSpawny);
					PlayerMulti playerControle = screenGame.getPlayerManageur().createMultiPlayer(screenGame, c, position);
					
					screenGame.getGameObjectManageur().getGameObjectArray().add(playerControle.getGameObject());
					screenGame.getPlayerManageur().addPlayerInMap(packet.id,playerControle);
					
					playerControle.getGameObject().getBody().setTransform(position, 0);
				}
			});
			
			
				
		}else if(o instanceof PacketRemovePlayer){
			
			final PacketRemovePlayer packet = (PacketRemovePlayer) o;
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
				}
			});
		}
	}
	public static List<InetAddress> getLanDiscovery(){
		Client staticClient = new Client();
		List<InetAddress> address = staticClient.discoverHosts(ConfigPref.Net_CommunicationPortUDP,1000);
		return address;
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



	
}
*/