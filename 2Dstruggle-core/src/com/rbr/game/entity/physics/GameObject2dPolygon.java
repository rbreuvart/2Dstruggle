package com.rbr.game.entity.physics;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.rbr.game.screen.game.ScreenGame;

public class GameObject2dPolygon extends GameObject{

	private Sprite sprite;
	public Sprite getSprite() {
		return sprite;
	}
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	public GameObject2dPolygon(String name,BodyDef BodyDef, Body Body, FixtureDef fixtureDef,Sprite sprite) {
		super(name,BodyDef, Body, fixtureDef);
		//Level.pixelpermeter:
		this.sprite = sprite;
		getSprite().setOrigin(getSprite().getWidth(), getSprite().getHeight());
		getSprite().flip(true,false);
	}

		
	@Override
	public void render(ScreenGame screenGame, SpriteBatch batch) {
		getSprite().draw(batch);
	}
	
	@Override
	public void update(ScreenGame screenGame, float delta) {		
		getSprite().setPosition(getBody().getPosition().x-getSprite().getWidth(), getBody().getPosition().y-getSprite().getHeight());
		getSprite().setRotation(((float)((getBody().getAngle()+Math.PI)/Math.PI*360)/2));

	}


}
