package com.rbr.game.entity.physics;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class GameObjectChaineShape extends GameObject {

	public GameObjectChaineShape(String name, BodyDef bodyDef, Body body,
			FixtureDef fixtureDef) {
		super(name, bodyDef, body, fixtureDef);
	
	}
}
