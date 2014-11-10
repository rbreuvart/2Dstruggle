package com.rbr.game.entity.map;

import com.badlogic.gdx.math.Polygon;

public class Zone {
	private Polygon polygon;
	private String name;
	
	
	public Polygon getPolygon() {
		return polygon;
	}

	public void setPolygon(Polygon polygon) {
		this.polygon = polygon;
	}
	
	
	
	
	public Zone(Polygon polygon,String name) {
		this.polygon = polygon;
		this.name = name;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
