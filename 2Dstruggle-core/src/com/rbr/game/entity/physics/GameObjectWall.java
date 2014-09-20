package com.rbr.game.entity.physics;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.rbr.game.screen.game.ScreenGame;

public class GameObjectWall extends GameObject{
	
	private Sprite sprite;
	public Sprite getSprite() {
		return sprite;
	}
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public GameObjectWall(String name, BodyDef bodyDef, Body body,FixtureDef fixtureDef,Sprite sprite) {
		super(name, bodyDef, body, fixtureDef);
		setSprite(sprite);
		
		
		getSprite().setOrigin(getSprite().getWidth()/2, getSprite().getHeight()/2);
	
	}

	
	@Override
	public void render(ScreenGame screenGame, SpriteBatch batch) {
		getSprite().draw(batch);
	}
	
	@Override
	public void update(ScreenGame screenGame, float delta) {
		getBody().setTransform(getBody().getPosition(), 5f);
		getSprite().setPosition(getBody().getPosition().x-getSprite().getWidth()/2,	getBody().getPosition().y-getSprite().getHeight()/2);
		//getSprite().setRotation(((float)((getBody().getAngle()+Math.PI)/Math.PI*360)/2));
		getSprite().setRotation((float) (getBody().getAngle()* MathUtils.radiansToDegrees));
	}

}
