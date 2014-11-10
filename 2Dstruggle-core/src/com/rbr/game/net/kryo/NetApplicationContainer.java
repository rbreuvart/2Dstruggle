package com.rbr.game.net.kryo;

import com.rbr.game.net.kryo.NetKryoManageur.NetApplicationType;

public class NetApplicationContainer {
	private NetApplicationType applicationType;
	private String ip;
	
	public NetApplicationContainer(String ip,NetApplicationType applicationType) {
		this.setApplicationType(applicationType);
		this.setIp(ip);
	}

	public NetApplicationType getApplicationType() {return applicationType;	}
	public void setApplicationType(NetApplicationType applicationType) {this.applicationType = applicationType;	}
	public String getIp() {	return ip;}
	public void setIp(String ip) {this.ip = ip;}
	
	
}
