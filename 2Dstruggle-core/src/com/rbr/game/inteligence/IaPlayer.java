package com.rbr.game.inteligence;

import com.rbr.game.entity.physics.GameObject;
import com.rbr.game.screen.game.ScreenGame;

public class IaPlayer {

	
	
	private GameObject gameObject;

	public GameObject getGameObject() {
		return gameObject;
	}

	public void setGameObject(GameObject gameObject) {
		this.gameObject = gameObject;
	}
	
	
	public IaPlayer( GameObject gameObject) {
		this.gameObject = gameObject;
	}
	/*
	public void render(ScreenGame screenGame,SpriteBatch batch){
		gameObject.render(screenGame, batch);
	}
	*/
	public void update(ScreenGame screenGame,float delta){
		gameObject.update(screenGame, delta);
	}
}
