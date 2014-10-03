package com.rbr.game.entity.map;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public class ZoneTeleport extends Zone{
	
	private Vector2 target;
	
	

	public ZoneTeleport(Polygon polygon, Vector2 target) {
		super(polygon);
		
		this.target = target;
	}

	
	public Vector2 getTarget() {
		return target;
	}

	public void setTarget(Vector2 target) {
		this.target = target;
	}
	
}
