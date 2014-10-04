package com.rbr.game.entity.physics;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rbr.game.entity.sprite.ISpriteRender;
import com.rbr.game.entity.sprite.SpriteRender;
import com.rbr.game.screen.game.ScreenGame;

public class GameObjectSprite extends GameObject{


	private ISpriteRender iSpriteRender;
	
	public GameObjectSprite(GameObject go,Sprite sprite) {
		super(go.getName(), go.getBodyDef(), go.getBody(), go.getFixtureDef());
		this.iSpriteRender =  new SpriteRender(sprite, go.getBody());
	}
	public GameObjectSprite(GameObject go,ISpriteRender irender) {
		super(go.getName(), go.getBodyDef(), go.getBody(), go.getFixtureDef());
		this.iSpriteRender = irender;
	}

	
	@Override
	public void update(ScreenGame screenGame, float delta) {
		super.update(screenGame, delta);
		this.iSpriteRender.update(screenGame, delta);
	
	/*	sprite.setPosition(getBody().getPosition().x-getSprite().getWidth()/2,	getBody().getPosition().y-getSprite().getHeight()/2);
		sprite.setRotation((float) (getBody().getAngle()* MathUtils.radiansToDegrees));
	*/
	}
	
	@Override
	public void render(ScreenGame screenGame, SpriteBatch batch) {
		super.render(screenGame, batch);
		//		this.iSpriteRender.render(screenGame, getBody().getPosition(),  getBody().getLinearVelocity().angle()-90, 0, batch);
		this.iSpriteRender.render(screenGame, 	getBody().getPosition(),
												getBody().getAngle(), 0, batch);
		/*getSprite().draw(batch);*/
	}
	

	public ISpriteRender getISpriteRender() {
		return iSpriteRender;
	}
	public void setISpriteRender(ISpriteRender iSpriteRender) {
		this.iSpriteRender = iSpriteRender;
	}

}
