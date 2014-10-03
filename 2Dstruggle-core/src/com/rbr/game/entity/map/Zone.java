package com.rbr.game.entity.map;

import com.badlogic.gdx.math.Polygon;

public class Zone {
	private Polygon polygon;

	public Polygon getPolygon() {
		return polygon;
	}

	public void setPolygon(Polygon polygon) {
		this.polygon = polygon;
	}
	public Zone(Polygon polygon) {
		this.polygon = polygon;
	}
}
