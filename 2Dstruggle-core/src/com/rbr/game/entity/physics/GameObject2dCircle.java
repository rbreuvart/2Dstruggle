package com.rbr.game.entity.physics;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.rbr.game.screen.game.ScreenGame;

public class GameObject2dCircle extends GameObject{

	private float radius;
	private Sprite sprite;
	public Sprite getSprite() {
		return sprite;
	}
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public GameObject2dCircle(GameObject go,Sprite sprite,float radius) {
		super(go.getName(), go.getBodyDef(), go.getBody(), go.getFixtureDef());
		setRadius(radius);
		setSprite(sprite);
		getSprite().setSize(radius*2,radius*2);
		
		getSprite().setOrigin(getSprite().getWidth()/2, getSprite().getHeight()/2);
	
	}

		
	
	@Override
	public void render(ScreenGame screenGame, SpriteBatch batch) {
		getSprite().draw(batch);
	}
	

	@Override
	public void update(ScreenGame screenGame, float delta) {
		if (isAutoDeceleration()) {
		//	System.out.println("GameObject2dCircle.update()"+this.getName());
			deceleration(getRatioDeceleration());
		}
		
		getSprite().setPosition(getBody().getPosition().x-getSprite().getWidth()/2,	getBody().getPosition().y-getSprite().getHeight()/2);
		//getSprite().setRotation(((float)((getBody().getAngle()+Math.PI)/Math.PI*360)/2));
		getSprite().setRotation((float) (getBody().getAngle()* MathUtils.radiansToDegrees));
	}
	
	public float getRadius() {
		return radius;
	}
	public void setRadius(float radius) {
		this.radius = radius;
	}

	 
}
