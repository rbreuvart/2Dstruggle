package com.rbr.game.net.client;

import java.io.IOException;
import java.net.InetAddress;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.rbr.game.entity.physics.FabriqueAll;
import com.rbr.game.net.kryo.packet.PacketAddMultiPlayer;
import com.rbr.game.net.kryo.packet.PacketAddPlayer;
import com.rbr.game.net.kryo.packet.PacketRemovePlayer;
import com.rbr.game.net.kryo.packet.PacketUpdateGameObjectPlayer;
import com.rbr.game.net.kryo.packet.RegisterPacket;
import com.rbr.game.player.PlayerControle;
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
			client.sendTCP(packetUpdateGameObjectPlayer);
	//		System.out.println("clien sendTCP"+packetUpdateGameObjectPlayer);
		}
			
		
	}
	
	public void received(Connection c, Object o){
		if(o instanceof PacketAddPlayer){
			System.out.println("PacketAddPlayer");
			final PacketAddPlayer packet = (PacketAddPlayer) o;
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
					
					
					Sprite spritePlayer = new Sprite(  screenGame.getMainGame().getManager().get(ConfigPref.File_Vaisseau1,Texture.class));
					PlayerControle playerControle = new PlayerControle(FabriqueAll.creationGameObjectCircle(screenGame.getWorldManageur(), 
							spritePlayer,	new Vector2(packet.positionSpawnx, packet.positionSpawny),"player", 0.45f,ConfigPref.pixelMeter));
					screenGame.getGameObjectManageur().getGameObjectArray().add(playerControle.getGameObject());
					screenGame.getPlayerManageur().addPlayerInMap(packet.id,playerControle);
				}
			});
			
			
		//	createMultiPlayer(packet);
		//	MPPlayer newPlayer = new MPPlayer();
			
		//	ClientProgram.players.put(packet.id, newPlayer);
			
		}if(o instanceof PacketAddMultiPlayer){
			System.out.println("PacketAddMultiPlayer");
			final PacketAddMultiPlayer packet = (PacketAddMultiPlayer) o;
			System.out.println("Client recoie de serveur un AddMultyplayer id:"+packet.id+" a la position de spawn: "+packet.positionSpawnx+","+packet.positionSpawny);
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
					Vector2 position =new Vector2(packet.positionSpawnx, packet.positionSpawny);
					System.out.println(position);
					Sprite spritePlayer = new Sprite(  screenGame.getMainGame().getManager().get(ConfigPref.File_RedCircle,Texture.class));
					PlayerMulti playerControle = new PlayerMulti(FabriqueAll.creationGameObjectCircle(screenGame.getWorldManageur(), 
							spritePlayer,	new Vector2(0, 0) ,"playerMulti", 0.45f,ConfigPref.pixelMeter));
					screenGame.getGameObjectManageur().getGameObjectArray().add(playerControle.getGameObject());
					screenGame.getPlayerManageur().addPlayerInMap(packet.id,playerControle);
					
					playerControle.getGameObject().getBody().setTransform(position, 0);
				}
			});
			
			
		//	createMultiPlayer(packet);
		//	MPPlayer newPlayer = new MPPlayer();
			
		//	ClientProgram.players.put(packet.id, newPlayer);
			
		}else if(o instanceof PacketRemovePlayer){
			System.out.println("PacketRemovePlayer");
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
		//	System.out.println("PacketUpdateGameObjectPlayer");
			final PacketUpdateGameObjectPlayer packet = (PacketUpdateGameObjectPlayer) o;
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
					screenGame.getPlayerManageur().getPlayerById(packet.id).getGameObject().getBody().setTransform(new Vector2(packet.positionX, packet.positionY), packet.angle);
					
				}
			});
		//	ClientProgram.players.get(packet.id).x = packet.x;
			
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
	
	
	
	/*
	public static void main(String[] args){
		new NetKryoClientManageur();
	}
	*/
	
	/*
	
	clientName = "Remi";
	clientNetWorkListener = new KryoClientNetWorkListener(client,clientName);
	scanner = new Scanner(System.in);
	
	
	registerPackets();
	client.addListener(clientNetWorkListener);

    new Thread (client).start();
    System.out.println(address);
    if(address == null) {
        System.exit(0);
    }
    try {
		client.connect(5000, address, ConfigPref.Net_CommunicationPortTCP,ConfigPref.Net_CommunicationPortUDP);
	
	} catch (IOException e) {
		e.printStackTrace();
	}*/
	
}
