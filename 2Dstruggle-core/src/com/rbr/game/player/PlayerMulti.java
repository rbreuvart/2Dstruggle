package com.rbr.game.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.rbr.game.entity.physics.GameObject;
import com.rbr.game.screen.game.ScreenGame;

public class PlayerMulti extends Player{

	public PlayerMulti(GameObject gameObject) {
		super(gameObject);		
	}

	@Override
	public void update(ScreenGame screenGame, float delta) {
	
	}
	@Override
	public void render(ScreenGame screenGame, SpriteBatch spriteBatch,	ShapeRenderer shapeRenderer) {
		
		getLifeBarRender().render(screenGame, spriteBatch, shapeRenderer,getGameObject().getBody().getPosition().cpy().add(0, -0.45f), getLife(), getLifeMax());

	}
}
