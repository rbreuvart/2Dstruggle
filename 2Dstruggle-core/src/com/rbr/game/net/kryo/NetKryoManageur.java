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
	
	
	                                     
	
	private NetKryoServerManageur kryoServerManageur;
	private NetKryoClientManageur kryoClientManageur;
	
	ArrayList<String>addressesInterface;
	
	private NetApplicationType netApplicationType;
	
	public NetKryoManageur(ScreenGame screenGame, NetApplicationContainer netApplicationContainer) {
		netApplicationType = netApplicationContainer.getApplicationType();
		if (NetApplicationType.Client.equals(netApplicationType)) {
			
			if (!"".equals(netApplicationContainer.getIp())) {
				
			}
			kryoClientManageur = new NetKryoClientManageur(screenGame,netApplicationContainer.getIp());
			System.err.println("Creation Client");
		}
		if (NetApplicationType.Serveur.equals(netApplicationType)) {		
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

	public NetKryoServerManageur getKryoServerManageur() {
		return kryoServerManageur;
	}

	public void setKryoServerManageur(NetKryoServerManageur kryoServerManageur) {
		this.kryoServerManageur = kryoServerManageur;
	}

	public NetKryoClientManageur getKryoClientManageur() {
		return kryoClientManageur;
	}

	public void setKryoClientManageur(NetKryoClientManageur kryoClientManageur) {
		this.kryoClientManageur = kryoClientManageur;
	}

	public ArrayList<String> getAddressesInterface() {
		return addressesInterface;
	}

	public void setAddressesInterface(ArrayList<String> addressesInterface) {
		this.addressesInterface = addressesInterface;
	}
	
	
}
