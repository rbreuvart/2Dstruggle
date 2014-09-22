package com.rbr.game.entity.physics;


import com.badlogic.gdx.graphics.g2d.Sprite;

public class GameObject2dSquare extends GameObjectSprite{

	
	public GameObject2dSquare(GameObject go, Sprite sprite) {
		super(go, sprite);
		getSprite().setOrigin(getSprite().getWidth()/2, getSprite().getHeight()/2);
	}
	

}
