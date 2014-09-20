package com.rbr.game.entity.physics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.rbr.game.screen.game.ScreenGame;

public class GameObjectChaineShape extends GameObject {

	public GameObjectChaineShape(String name, BodyDef bodyDef, Body body,
			FixtureDef fixtureDef) {
		super(name, bodyDef, body, fixtureDef);
	
	}

	@Override
	public void render(ScreenGame screenGame, SpriteBatch batch) {
		
	}

	@Override
	public void update(ScreenGame screenGame, float delta) {
		
	}

}
