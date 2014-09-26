package com.rbr.game.entity.sprite;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.rbr.game.screen.game.ScreenGame;

public class SpriteRender implements ISpriteRender{

	private Sprite sprite;
	
	Body body;
	public SpriteRender(Sprite sprite,Body body) {
		this.body = body;
		this.sprite = sprite;
	}
	
	@Override
	public void update(ScreenGame screenGame, float delta) {
		sprite.setPosition(	body.getPosition().x-getSprite().getWidth()/2,
							body.getPosition().y-getSprite().getHeight()/2);
	}

	@Override
	public void render(ScreenGame screenGame, Vector2 position, float angle, float scale, SpriteBatch batch) {
		sprite.setRotation((float) (body.getAngle()* MathUtils.radiansToDegrees));	
		
		getSprite().scale(scale);
		getSprite().draw(batch);
	}

	
	public Sprite getSprite() {
		return sprite;
	}
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}



}
