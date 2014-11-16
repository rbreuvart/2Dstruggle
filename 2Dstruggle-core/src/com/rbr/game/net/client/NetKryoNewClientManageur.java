package com.rbr.game.net.client;

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
import com.rbr.game.entity.physics.FabriqueAll;
import com.rbr.game.entity.physics.GameObject;
import com.rbr.game.entity.projectile.Projectile;
import com.rbr.game.net.kryo.packet.PacketInfoServeur;
import com.rbr.game.net.kryo.packet.RegisterPacket;
import com.rbr.game.net.kryo.packet.lobby.PacketMessageLobby;
import com.rbr.game.net.kryo.packet.player.PacketAddMultiPlayer;
import com.rbr.game.net.kryo.packet.player.PacketRemovePlayer;
import com.rbr.game.net.kryo.packet.player.PacketSpawnPlayer;
import com.rbr.game.net.kryo.packet.player.PacketUpdateGameObjectPlayer;
import com.rbr.game.net.kryo.packet.projectile.PacketAddProjectile;
import com.rbr.game.net.lobby.LobbyClient;
import com.rbr.game.player.Player;
import com.rbr.game.screen.game.ScreenGame;
import com.rbr.game.utils.ConfigLobby;
import com.rbr.game.utils.ConfigPref;
import com.rbr.game.utils.GameCodeTools;

public class NetKryoNewClientManageur extends Listener{
	private  Client client;
	private Kryo kryo;
	InetAddress address;
	ScreenGame screenGame;	
	
	private LobbyClient lobbyClient;
	private PacketInfoServeur infoServeur;
	
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
		lobbyClient = new LobbyClient(screenGame,this);
		new Thread (client).start();
		System.out.println("NetKryoClientManageur :"+ip);
    	try {
			client.connect(5000, ip, ConfigPref.Net_CommunicationPortTCP, ConfigPref.Net_CommunicationPortUDP);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update(ScreenGame screenGame ,float delta){
		
		if (!lobbyClient.isConnectedToLobby()) {
			
			if (infoServeur!=null) {
				/*
				 * PreMiére Etape du Processus de Connection
				 */
				
				
				PacketMessageLobby packetMessageLobby = new PacketMessageLobby();
				packetMessageLobby.idConnection = infoServeur.id;
				packetMessageLobby.name = "Client:"+infoServeur.id;//ici definir Nom
				packetMessageLobby.type = ConfigLobby.TypeMessageConnectionLobby;
				packetMessageLobby.message = "";
				packetMessageLobby.heure = 0;
				packetMessageLobby.minute = 0;
				packetMessageLobby.seconde = 0;
				packetMessageLobby.color = GameCodeTools.getRandomColor();
				System.out.println(packetMessageLobby);
				client.sendTCP(packetMessageLobby);
			}else{
				System.out.println("Client :Wait - infoServer non Recu");
			}			
		}	
		
		if (lobbyClient.isRoundStart()) {
			if (screenGame.getPlayerManageur().getPlayerLocal() != null) {
				
				//Maj Joueur
				PacketUpdateGameObjectPlayer packetUpdateGameObjectPlayer = new PacketUpdateGameObjectPlayer();
				packetUpdateGameObjectPlayer.id = screenGame.getPlayerManageur().getPlayerLocal().getId();
				packetUpdateGameObjectPlayer.angle = screenGame.getPlayerManageur().getPlayerLocal().getGameObject().getBody().getAngle();
				packetUpdateGameObjectPlayer.positionX = screenGame.getPlayerManageur().getPlayerLocal().getGameObject().getBody().getPosition().x;
				packetUpdateGameObjectPlayer.positionY = screenGame.getPlayerManageur().getPlayerLocal().getGameObject().getBody().getPosition().y;
				client.sendTCP(packetUpdateGameObjectPlayer);
		
				//Maj Projectile
				for (GameObject go : screenGame.getGameObjectManageur().getGameObjectArray()) {
					if (go instanceof Projectile) {
						Projectile proj =  (Projectile)go;
						if (!proj.isEnvoyerToServer()) {
							PacketAddProjectile packetAddProjectile = new PacketAddProjectile();
							packetAddProjectile.idConnection = screenGame.getPlayerManageur().getPlayerLocal().getId();
							packetAddProjectile.idEmeteur = screenGame.getPlayerManageur().getPlayerLocal().getId();
							packetAddProjectile.idGameObject = proj.getIdArray();
							packetAddProjectile.classProjectile = proj.getClass().getName();
							
							packetAddProjectile.degat = proj.getDegat();
							
							packetAddProjectile.positionX = proj.getBody().getPosition().x;
							packetAddProjectile.positionY = proj.getBody().getPosition().y;
							packetAddProjectile.velocityX = proj.getBody().getLinearVelocity().x;
							packetAddProjectile.velocotyY = proj.getBody().getLinearVelocity().y;
							
							
							client.sendTCP(packetAddProjectile);
							proj.setEnvoyerToServer(true);
							proj.setEnvoyerToClient(true);
						}
					/*
						if (proj.isRemove()) {
							PacketRemoveProjectile packetRemoveProjectile = new PacketRemoveProjectile();
							packetRemoveProjectile.idConnection = screenGame.getPlayerManageur().getPlayerLocal().getId();
							packetRemoveProjectile.idEmeteur = screenGame.getPlayerManageur().getPlayerLocal().getId();
							packetRemoveProjectile.idGameObject = proj.getIdArray();
							client.sendTCP(packetRemoveProjectile);
						}*/
						
						
					}
				}
			}
		}
		
	}
	
	public void received(final Connection c, Object o){
		//System.out.println("Client : message Recu ! "+o.toString());
		for (int i = 0; i < 1; i++) {		
			if (infoServeurHandler(c,o)){break;}

			if (lobbyHandler(c,o)) {break;}
			
			if (playerHandler(c,o)){break;}
			
			if (projectileHandler(c,o)){break;}
					
			if (commandHandler(c,o)){break;}
			
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
			//System.out.println("Client (1): "+packet);
			setInfoServeur(packet);
			//Charge la Map
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
					screenGame.getMapManageur().loadMap(screenGame,packet.mapServeur);
				}
			});
			
			return true;
		}
		return false;
	}
	
	private boolean playerHandler(final Connection c, Object o){
		if(o instanceof PacketUpdateGameObjectPlayer){			
			final PacketUpdateGameObjectPlayer packet = (PacketUpdateGameObjectPlayer) o;
			//System.out.println("CLIENT "+packet);
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
					Vector2 newPosition = new Vector2(packet.positionX, packet.positionY);
					Player player  = screenGame.getPlayerManageur().getPlayerById(packet.id);
					if (player!=null) {
						//pour tout les Player
						player.setLife(packet.life);
						if (player.equals(screenGame.getPlayerManageur().getPlayerLocal())) {
							//pour le localPlayer
							
						}else{//pour les JoueursMulti
							Body bodyPlayer = player.getGameObject().getBody();
							Vector2 correctPosition = new Vector2(bodyPlayer.getPosition());
							if (newPosition.x != bodyPlayer.getPosition().x ) {
								correctPosition.x = newPosition.x;
							}
							if (newPosition.y != bodyPlayer.getPosition().y) {
								correctPosition.y = newPosition.y;
							}
							bodyPlayer.setTransform(correctPosition, packet.angle);
						}
					
					}else{
						System.out.println("Client : Player non present dans la map des joueurs");
					}
				}
			});
			
					
				
			
			return true;
		}
		
		if(o instanceof PacketAddMultiPlayer){	
			final PacketAddMultiPlayer packet = (PacketAddMultiPlayer) o;
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
						
						if (null != screenGame.getPlayerManageur().getPlayerById(packet.id)) {
							//	System.out.println("Client : Ce player et deja dans ma map de player "+packet);
						}else{
							//	System.out.println("Client : Ce player n'etait pas dans la map de player :"+packet+" Connection"+c.getID());
							screenGame.getPlayerManageur().createMultiPlayer(screenGame, c,packet.id,packet.name,packet.color);
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
					screenGame.getGameObjectManageur().removeByGameObject(screenGame.getPlayerManageur().getPlayerById(packet.id).getGameObject());
					screenGame.getPlayerManageur().removePlayerById(packet.id);
				}
			});		
			return true;
		}
		if(o instanceof PacketSpawnPlayer){		
			final PacketSpawnPlayer packet = (PacketSpawnPlayer) o;
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
					screenGame.getPlayerManageur().spawnPlayer(screenGame, packet.id, new Vector2(packet.positionX, packet.positionY));
					
										//screenGame.getPlayerManageur().getPlayerById(packet.id).setSpawn(packet.spawn);
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
					projectile.setEnvoyerToClient(true);
					screenGame.getGameObjectManageur().add(addProjectile.idConnection,projectile);
				}
			});
			return true;
		}
		return false;
	}
	
	public static List<InetAddress> getLanDiscovery(){
		Client staticClient = new Client();
		List<InetAddress> address = staticClient.discoverHosts(ConfigPref.Net_CommunicationPortUDP,1000);
		return address;
	}
	
	
	public void startGame(){
		//cache l'interface du HUD
		screenGame.getHudManageur().getTableStartGame().setVisible(false);
		screenGame.getHudManageur().getWindowListPlayer().setVisible(false);

	}
	
	
	
	public Client getClient() {	return client;	}
	public void setClient(Client client) {		this.client = client;	}
	public Kryo getKryo() {		return kryo;	}
	public void setKryo(Kryo kryo) {		this.kryo = kryo;	}
	public PacketInfoServeur getInfoServeur() {return infoServeur;}
	public void setInfoServeur(PacketInfoServeur infoServeur) {this.infoServeur = infoServeur;}
	public LobbyClient getLobbyClient() {return lobbyClient;}
	public void setLobbyClient(LobbyClient lobbyClient) {		this.lobbyClient = lobbyClient;}

	/*
	public NetKryoNewClientManageur(ScreenGame screenGame) {		
		this.screenGame = screenGame;
		System.out.println("------CLIENT--/----");
		client = new Client();
		registerPackets();
		address = client.discoverHost(ConfigPref.Net_CommunicationPortUDP,  ConfigPref.Net_CommunicationPortUDP);
		client.addListener(this);
		lobbyClient = new LobbyClient(screenGame,this);
		
		
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
	
	}*/
	

}
