package com.rbr.game.entity.physics;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rbr.game.screen.game.ScreenGame;

public class GameObject2dSquare extends GameObject{

	private Sprite sprite;
	public Sprite getSprite() {
		return sprite;
	}
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public GameObject2dSquare(GameObject gameObject, Sprite sprite) {
		super(gameObject.getName(),gameObject.getBodyDef(),gameObject.getBody(),gameObject.getFixtureDef());	
		this.sprite = sprite;
		getSprite().setOrigin(getSprite().getWidth()/2, getSprite().getHeight()/2);
	}
	@Override
	public void render(ScreenGame screenGame, SpriteBatch batch) {
		getSprite().draw(batch);
	}

	@Override
	public void update(ScreenGame screenGame, float delta) {
		
		getSprite().setPosition(getBody().getPosition().x-(getSprite().getWidth()/2), getBody().getPosition().y-(getSprite().getHeight()/2));
		getSprite().setRotation(((float)(getBody().getAngle()/Math.PI*360)/2));		
	
	}

}
