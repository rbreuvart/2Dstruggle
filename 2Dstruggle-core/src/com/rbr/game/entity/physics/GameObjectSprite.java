package com.rbr.game.entity.physics;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.rbr.game.screen.game.ScreenGame;

public class GameObjectSprite extends GameObject{

	private Sprite sprite;
	
	public GameObjectSprite(GameObject go,Sprite sprite) {
		super(go.getName(), go.getBodyDef(), go.getBody(), go.getFixtureDef());
		this.sprite = sprite;
	}

	
	@Override
	public void update(ScreenGame screenGame, float delta) {
		super.update(screenGame, delta);
			
		sprite.setPosition(getBody().getPosition().x-getSprite().getWidth()/2,	getBody().getPosition().y-getSprite().getHeight()/2);
		sprite.setRotation((float) (getBody().getAngle()* MathUtils.radiansToDegrees));
	}
	
	@Override
	public void render(ScreenGame screenGame, SpriteBatch batch) {
		super.render(screenGame, batch);
		getSprite().draw(batch);
	}
	
	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

}
