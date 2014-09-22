package com.rbr.game.entity.physics;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class GameObject2dCircle extends GameObjectSprite{

	private float radius;
	public float getRadius() {
		return radius;
	}
	public void setRadius(float radius) {
		this.radius = radius;
	}
	public GameObject2dCircle(GameObject go,Sprite sprite,float radius) {
		super(go, sprite);
		setRadius(radius);
	
		getSprite().setSize(radius*2,radius*2);		
		getSprite().setOrigin(getSprite().getWidth()/2, getSprite().getHeight()/2);	
	}

		
	

	 
}
