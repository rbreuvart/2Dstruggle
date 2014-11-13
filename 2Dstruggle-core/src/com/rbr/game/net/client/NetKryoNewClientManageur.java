package com.rbr.game.net.client;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.rbr.game.net.kryo.packet.PacketInfoServeur;
import com.rbr.game.net.kryo.packet.RegisterPacket;
import com.rbr.game.net.kryo.packet.lobby.PacketMessageLobby;
import com.rbr.game.net.kryo.packet.player.PacketAddMultiPlayer;
import com.rbr.game.net.kryo.packet.player.PacketRemovePlayer;
import com.rbr.game.net.kryo.packet.player.PacketUpdateGameObjectPlayer;
import com.rbr.game.net.lobby.LobbyClient;
import com.rbr.game.player.Player;
import com.rbr.game.screen.game.ScreenGame;
import com.rbr.game.utils.ConfigLobby;
import com.rbr.game.utils.ConfigPref;

public class NetKryoNewClientManageur extends Listener{
	private  Client client;
	private Kryo kryo;
	InetAddress address;
	ScreenGame screenGame;
	private PacketInfoServeur infoServeur;
	LobbyClient lobbyClient;
	
	
	private void registerPackets(){
		kryo = client.getKryo();		
		RegisterPacket.definitionPacket(kryo);
	}
	
	public NetKryoNewClientManageur(ScreenGame screenGame,String ip) {	
		this.screenGame = screenGame;
		System.out.println("------CLIENT------");
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
    	lobbyClient = new LobbyClient(screenGame,this);
	}
	
	public NetKryoNewClientManageur(ScreenGame screenGame) {		
		this.screenGame = screenGame;
		System.out.println("------CLIENT------");
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
		lobbyClient = new LobbyClient(screenGame,this);
	}
	
	
	
	public void update(ScreenGame screenGame ){
		
		if (!lobbyClient.isConnectedToLobby()) {
			if (infoServeur!=null) {
				PacketMessageLobby packetMessageLobby = new PacketMessageLobby();
				packetMessageLobby.idConnection = infoServeur.id;
				packetMessageLobby.name = "Client";
				packetMessageLobby.type = ConfigLobby.TypeMessageConnectionLobby;
				packetMessageLobby.message = "";
				packetMessageLobby.heure = 0;
				packetMessageLobby.minute = 0;
				packetMessageLobby.seconde = 0;
				client.sendTCP(packetMessageLobby);
			}else{
				System.out.println("infoServer non Recu");
			}			
		}	
		
		
		
		if (lobbyClient.isRoundStart()) {
			if (screenGame.getPlayerManageur().getPlayerLocal() != null) {
				PacketUpdateGameObjectPlayer packetUpdateGameObjectPlayer = new PacketUpdateGameObjectPlayer();
				packetUpdateGameObjectPlayer.id = screenGame.getPlayerManageur().getPlayerLocal().getId();
				packetUpdateGameObjectPlayer.angle = screenGame.getPlayerManageur().getPlayerLocal().getGameObject().getBody().getAngle();
				packetUpdateGameObjectPlayer.positionX = screenGame.getPlayerManageur().getPlayerLocal().getGameObject().getBody().getPosition().x;
				packetUpdateGameObjectPlayer.positionY = screenGame.getPlayerManageur().getPlayerLocal().getGameObject().getBody().getPosition().y;
				client.sendUDP(packetUpdateGameObjectPlayer);
			}
		}
		
	}
	
	public void received(final Connection c, Object o){
		for (int i = 0; i < 1; i++) {		
			if (projectileHandler(c,o)){break;}
			
			if (playerHandler(c,o)){break;}
					
			if (commandHandler(c,o)){break;}
			
			if (lobbyHandler(c,o)) {break;}
			
			if (infoServeurHandler(c,o)){break;}
		}
	}
	
	private boolean commandHandler(Connection c, Object o){
		return false;
	}
	
	private boolean lobbyHandler(Connection c, Object o){
		return lobbyClient.receivedMessage(c,o);	
	}
	
	private boolean infoServeurHandler(Connection c, Object o){
		if(o instanceof PacketInfoServeur){
			final PacketInfoServeur packet = (PacketInfoServeur) o;
			System.out.println("Client (1): "+packet);
			setInfoServeur(packet);
			return true;
		}
		return false;
	}
	
	private boolean playerHandler(final Connection c, Object o){
		
		if(o instanceof PacketAddMultiPlayer){	
			final PacketAddMultiPlayer packet = (PacketAddMultiPlayer) o;
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
						//	System.out.println("--- LISTPlayer ---");
						for (Entry<Integer, Player> entryPlayer : screenGame.getPlayerManageur().getHashMapPlayer().entrySet()) {		
							System.out.println(entryPlayer.getKey()+" "+entryPlayer.getValue().getGameObject().getName());
						}
						
						if (null != screenGame.getPlayerManageur().getPlayerById(packet.id)) {
							//	System.out.println("Client : Ce player et deja dans ma map de player "+packet);
						}else{
							//	System.out.println("Client : Ce player n'etait pas dans la map de player :"+packet+" Connection"+c.getID());
							screenGame.getPlayerManageur().createMultiPlayer(screenGame, c,packet.id);
						}
					}
			});
			return true;
		}
		
		
		if(o instanceof PacketRemovePlayer){
			
			final PacketRemovePlayer packet = (PacketRemovePlayer) o;
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
					screenGame.getWorldManageur().getWorld().destroyBody(screenGame.getPlayerManageur().getPlayerById(packet.id).getGameObject().getBody());
					screenGame.getGameObjectManageur().getGameObjectArray().removeValue(screenGame.getPlayerManageur().getPlayerById(packet.id).getGameObject(), false);
					screenGame.getPlayerManageur().removePlayerById(packet.id);
				}
			});		
			return true;
		}
		if(o instanceof PacketUpdateGameObjectPlayer){			
			final PacketUpdateGameObjectPlayer packet = (PacketUpdateGameObjectPlayer) o;
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
					Vector2 newPosition = new Vector2(packet.positionX, packet.positionY);
					Player player  = screenGame.getPlayerManageur().getPlayerById(packet.id);
					if (player!=null) {
						Body bodyPlayer = player.getGameObject().getBody();
						Vector2 correctPosition = new Vector2(bodyPlayer.getPosition());
						if (newPosition.x != bodyPlayer.getPosition().x ) {
							correctPosition.x = newPosition.x;
						}
						if (newPosition.y != bodyPlayer.getPosition().y) {
							correctPosition.y = newPosition.y;
						}
						bodyPlayer.setTransform(correctPosition, packet.angle);
					}else{
						System.out.println("Client : Player non present dans la map des joueurs");
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
	
	public static List<InetAddress> getLanDiscovery(){
		Client staticClient = new Client();
		List<InetAddress> address = staticClient.discoverHosts(ConfigPref.Net_CommunicationPortUDP,1000);
		return address;
	}
	
	public Client getClient() {	return client;	}
	public void setClient(Client client) {		this.client = client;	}
	public Kryo getKryo() {		return kryo;	}
	public void setKryo(Kryo kryo) {		this.kryo = kryo;	}
	public PacketInfoServeur getInfoServeur() {return infoServeur;}
	public void setInfoServeur(PacketInfoServeur infoServeur) {this.infoServeur = infoServeur;}

}
