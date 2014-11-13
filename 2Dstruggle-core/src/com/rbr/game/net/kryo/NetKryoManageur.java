package com.rbr.game.net.kryo;

import java.io.IOException;
import java.util.ArrayList;

import com.rbr.game.net.client.NetKryoNewClientManageur;
import com.rbr.game.net.server.NetKryoNewServerManageur;
import com.rbr.game.screen.game.ScreenGame;


public class NetKryoManageur {

	public enum NetApplicationType{
		Client,Serveur;
	}                            
	
	private NetKryoNewServerManageur kryoServerManageur;
	private NetKryoNewClientManageur kryoClientManageur;
	
	ArrayList<String>addressesInterface;
	
	private NetApplicationType netApplicationType;
	
	public NetKryoManageur(ScreenGame screenGame, NetApplicationContainer netApplicationContainer) {
		netApplicationType = netApplicationContainer.getApplicationType();
		if (NetApplicationType.Client.equals(netApplicationType)) {
			kryoClientManageur = new NetKryoNewClientManageur(screenGame,netApplicationContainer.getIp());
			//System.err.println("Creation Client");
		}
		if (NetApplicationType.Serveur.equals(netApplicationType)) {		
			try {
				kryoServerManageur = new NetKryoNewServerManageur(screenGame);
			} catch (IOException e) {
				e.printStackTrace();
			}
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

	public NetKryoNewServerManageur getKryoServerManageur() {
		return kryoServerManageur;
	}

	public void setKryoServerManageur(NetKryoNewServerManageur kryoServerManageur) {
		this.kryoServerManageur = kryoServerManageur;
	}

	public NetKryoNewClientManageur getKryoClientManageur() {
		return kryoClientManageur;
	}

	public void setKryoClientManageur(NetKryoNewClientManageur kryoClientManageur) {
		this.kryoClientManageur = kryoClientManageur;
	}

	public ArrayList<String> getAddressesInterface() {
		return addressesInterface;
	}

	public void setAddressesInterface(ArrayList<String> addressesInterface) {
		this.addressesInterface = addressesInterface;
	}
	
	
}
