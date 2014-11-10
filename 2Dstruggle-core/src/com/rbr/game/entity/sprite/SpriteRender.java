package com.rbr.game.entity.sprite;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.rbr.game.screen.game.ScreenGame;

public class SpriteRender implements ISpriteRender{

	private Sprite sprite;	
	Body body;
	
	private boolean applyRotate;
	
	public SpriteRender(Sprite sprite,Body body) {
		this.body = body;
		this.sprite = sprite;
		this.applyRotate  = true;
	}
	
	@Override
	public void update(ScreenGame screenGame, float delta) {
		sprite.setPosition(	body.getPosition().x-getSprite().getWidth()/2,
							body.getPosition().y-getSprite().getHeight()/2);
	}

	@Override
	public void render(ScreenGame screenGame, Vector2 position, float angle, float scale, SpriteBatch batch) {
		if (applyRotate) {
			sprite.setRotation(angle);		
		}
		sprite.scale(scale);
		sprite.draw(batch);
	}

	
	public Sprite getSprite() {
		return sprite;
	}
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	@Override
	public boolean getApplyRotate() {		
		return applyRotate;
	}

	@Override
	public void setApplyRotate(boolean applyRotate) {
		this.applyRotate = applyRotate;
	}



}
