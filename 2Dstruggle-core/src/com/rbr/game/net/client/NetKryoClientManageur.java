package com.rbr.game.net.client;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.rbr.game.net.kryo.packet.PacketAddPlayer;
import com.rbr.game.net.kryo.packet.PacketRemovePlayer;
import com.rbr.game.net.kryo.packet.PacketUpdateGameObjectPlayer;
import com.rbr.game.screen.game.ScreenGame;
import com.rbr.game.utils.ConfigPref;

public class NetKryoClientManageur extends Listener{
	
	private  Client client;
	private Kryo kryo;
	
	public static Scanner scanner;
	/*String clientName;*/
	InetAddress address;
	
	
	private void registerPackets(){
		kryo = client.getKryo();		
		kryo.register(PacketAddPlayer.class);
		kryo.register(PacketRemovePlayer.class);
		kryo.register(PacketUpdateGameObjectPlayer.class);	
	}
	
	public NetKryoClientManageur() {
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
	
	}
	
	public void received(Connection c, Object o){
		if(o instanceof PacketAddPlayer){
			PacketAddPlayer packet = (PacketAddPlayer) o;
		//	createMultiPlayer(packet);
		//	MPPlayer newPlayer = new MPPlayer();
			
		//	ClientProgram.players.put(packet.id, newPlayer);
			
		}else if(o instanceof PacketRemovePlayer){
			PacketRemovePlayer packet = (PacketRemovePlayer) o;
		//	ClientProgram.players.remove(packet.id);
			
		}else if(o instanceof PacketUpdateGameObjectPlayer){
			PacketUpdateGameObjectPlayer packet = (PacketUpdateGameObjectPlayer) o;
		//	ClientProgram.players.get(packet.id).x = packet.x;
			
		}
	}
	
	
	
	
	public static void main(String[] args){
		new NetKryoClientManageur();
	}
	
	
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
