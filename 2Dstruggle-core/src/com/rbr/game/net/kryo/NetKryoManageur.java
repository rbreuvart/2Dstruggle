package com.rbr.game.net.kryo;

import java.io.IOException;
import java.util.ArrayList;

import com.rbr.game.net.client.NetKryoClientManageur;
import com.rbr.game.net.server.NetKryoServerManageur;
import com.rbr.game.screen.game.ScreenGame;


public class NetKryoManageur {

	public enum NetApplicationType{
		Client,Serveur;
	}
	
	NetKryoServerManageur kryoServerManageur;
	NetKryoClientManageur kryoClientManageur;
	
	ArrayList<String>addressesInterface;
	
	private NetApplicationType netApplicationType;
	
	public NetKryoManageur(ScreenGame screenGame, NetApplicationType type) {
		netApplicationType = type;
		if (NetApplicationType.Client.equals(type)) {
			kryoClientManageur = new NetKryoClientManageur();
			System.err.println("Creation Client");
		}
		if (NetApplicationType.Serveur.equals(type)) {		
			try {
				kryoServerManageur = new NetKryoServerManageur(screenGame);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Creation Serveur");
		}
		
		
	}
	
	public void update(ScreenGame screenGame, float delta) {
		if (kryoServerManageur !=null) {
			kryoServerManageur.update(screenGame);
		}
		if (kryoClientManageur !=null) {
			kryoClientManageur.update(screenGame);
		}
	}

	public NetApplicationType getNetApplicationType() {
		return netApplicationType;
	}

	public void setNetApplicationType(NetApplicationType netApplicationType) {
		this.netApplicationType = netApplicationType;
	}
	
	
}
