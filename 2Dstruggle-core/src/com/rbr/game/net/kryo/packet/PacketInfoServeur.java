package com.rbr.game.net.kryo.packet;

public class PacketInfoServeur {
	public int id;
	
	public String mapServeur;//Maya
	public String serveurType;//Lan ,....
	public String mapType;//deathMatch
	public int maxPlayerSlot;//64
	public int currentPlayerSlot;//2
	public boolean requirePassword;// false
	public boolean autorisation;//false --> true si le mot de pass est le bon
	@Override
	public String toString() {
		return "PacketInfoServeur [id=" + id + ", mapServeur=" + mapServeur
				+ ", serveurType=" + serveurType + ", mapType=" + mapType
				+ ", maxPlayerSlot=" + maxPlayerSlot + ", currentPlayerSlot="
				+ currentPlayerSlot + ", requirePassword=" + requirePassword
				+ ", autorisation=" + autorisation + "]";
	}
	
	
	
	
}
