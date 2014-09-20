package com.rbr.game.entity.ship;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rbr.game.entity.physics.GameObject;
import com.rbr.game.screen.game.ScreenGame;

public class Ship extends GameObject {

	
	private Sprite sprite;
	public Sprite getSprite() {
		return sprite;
	}
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	public Ship(GameObject go,Sprite sprite) {
		super(go.getName(), go.getBodyDef(), go.getBody(), go.getFixtureDef());
		this.sprite = sprite;
	}

	@Override
	public void colisionBegin(GameObject contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void colisionEnd(GameObject contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(ScreenGame screenGame, SpriteBatch batch) {
		getSprite().draw(batch);
	}

	@Override
	public void update(ScreenGame screenGame, float delta) {
		getSprite().setPosition(getBody().getPosition().x,getBody().getPosition().y);
		getSprite().setRotation(((float)((getBody().getAngle()+Math.PI)/Math.PI*360)/2));
		
	}

	
	
	public void shootLazer(){
		
	}
	
	
	
}
