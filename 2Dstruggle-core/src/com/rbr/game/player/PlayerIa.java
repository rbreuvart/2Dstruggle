package com.rbr.game.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.rbr.game.entity.physics.GameObject;
import com.rbr.game.entity.physics.GameObjectCollisionListener;
import com.rbr.game.screen.game.ScreenGame;

public class PlayerIa extends Player implements GameObjectCollisionListener{

	
	
	public PlayerIa(GameObject gameObject) {
		super(gameObject);	
		
		getGameObject().setAutoDeceleration(false);
	}
	
	

	@Override
	public void update(ScreenGame screenGame,float delta){
		//code de l'Ia
		
	}
	
	@Override
	public void render(ScreenGame screenGame, SpriteBatch spriteBatch,	ShapeRenderer shapeRenderer) {
		getLifeBarRender().render(screenGame, spriteBatch, shapeRenderer,getGameObject().getBody().getPosition().cpy().add(0, -0.45f), getLife(), getLifeMax());

	}


	@Override
	public void colisionBegin(GameObject called, GameObject contact2,ScreenGame screenGame) {	
		
	}
	@Override
	public void colisionEnd(GameObject called, GameObject contact2,	ScreenGame screenGame) {
		
	}
}
