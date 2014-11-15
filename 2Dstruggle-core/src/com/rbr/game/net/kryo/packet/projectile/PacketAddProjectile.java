package com.rbr.game.net.kryo.packet.projectile;

public class PacketAddProjectile {
	public int idConnection;
	public int idEmeteur;
	public String idGameObject; //idArray
	public String classProjectile;
	
	public float degat;
	
	public float velocityX,velocotyY;
	public float positionX,positionY;
	@Override
	public String toString() {
		return "PacketAddProjectile [idConnection=" + idConnection
				+ ", idEmeteur=" + idEmeteur + ", idGameObject=" + idGameObject
				+ ", classProjectile=" + classProjectile + ", degat=" + degat
				+ ", velocityX=" + velocityX + ", velocotyY=" + velocotyY
				+ ", positionX=" + positionX + ", positionY=" + positionY + "]";
	}
	

	
}
