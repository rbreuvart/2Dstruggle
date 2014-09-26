package com.rbr.game.entity.physics;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class GameObject2dPolygon extends GameObjectSprite{


	public GameObject2dPolygon(GameObject go,Sprite sprite) {
		super(go,sprite);
		
		sprite.setOrigin(sprite.getWidth(), sprite.getHeight());
		sprite.flip(true,false);
	}



}
