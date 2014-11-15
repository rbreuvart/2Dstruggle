package com.rbr.game.net.kryo.packet.projectile;

public class PacketRemoveProjectile {
	public int idConnection;
	public int idEmeteur;
	public String idGameObject; //idArray
	@Override
	public String toString() {
		return "PacketRemoveProjectile [idConnection=" + idConnection
				+ ", idEmeteur=" + idEmeteur + ", idGameObject=" + idGameObject
				+ "]";
	}
	
	
}
